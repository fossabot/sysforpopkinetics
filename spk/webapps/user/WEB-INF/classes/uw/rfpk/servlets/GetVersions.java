package uw.rfpk.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.*;
import java.sql.*;
import java.util.Vector;
import rfpk.spk.spkdb.*;
import java.text.SimpleDateFormat;
import org.apache.commons.jrcs.rcs.*;
import org.apache.commons.jrcs.util.ToString;
import org.apache.commons.jrcs.diff.*;

/** This servlet receives a String array containing three String objects from the client.
 * The first String object is the secret code to identify the client.  The second String  
 * object is the id of the model or the dataset.  The third String object is archive type
 * that is either model or data.  The servlet first checks if this id belongs to the user
 * using the id list saved in the Session object, MODELIDS or DATASETIDS.  If authentified
 * the servlet calls database API methods, getModel or getDataset, to get the archive.  
 * Then the servlet uses the JRCS API methods to get the status of the versions including 
 * version number, author name, revision time and log of the versions.  The servlet puts 
 * these data into a String[][] object. 
 * The servlet sends back two objects.  The first object is a String containing the error 
 * message if there is an error or an empty String if there is not any error.  The second 
 * object is the returning data String[][] object.
 *
 * @author Jiaji Du
 * @version 1.0
 */
public class GetVersions extends HttpServlet
{
    /**
     * Dispatches client requests to the protected service method.
     * 
     * @param req  the HttpServletRequest object that contains the request the client made of the servlet
     * @param resp  the HttpServletResponse object that contains the response the servlet returns to the client
     * @exception ServletException a general exception a servlet can throw when it encounters difficulty
     * @exception IOException an I/O exception of some sort
     */
    public void service(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
    {
        // Prepare output message
        String messageOut = "";
        String[][] versionList = null;
        
        // Get the input stream for reading data from the client
        ObjectInputStream in = new ObjectInputStream(req.getInputStream());  
       
        // Set the content type we are sending
        resp.setContentType("application/octet-stream");
        
        // Data will always be written to a byte array buffer so
        // that we can tell the server the length of the data
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
      
        // Create the output stream to be used to write the data
        // to our buffer
        ObjectOutputStream out = new ObjectOutputStream(byteOut);

        try
        {
            // Read the data from the client 
            String[] messageIn = (String[])in.readObject();
            String secret = messageIn[0];
            String type = messageIn[2];
            Vector ids = null;
            if(type.equals("model"))
                ids = (Vector)(req.getSession().getAttribute("MODELIDS")); 
            if(type.equals("data"))
                ids = (Vector)(req.getSession().getAttribute("DATASETIDS"));            
            if(secret.equals((String)req.getSession().getAttribute("SECRET")) &&
               ids.contains(messageIn[1]))             
            {                        
 	        long id = Long.parseLong(messageIn[1]);

                // Connect to the database
                ServletContext context = getServletContext();
                Connection con = Spkdb.connect(context.getInitParameter("database_name"),
                                               context.getInitParameter("database_host"),
                                               context.getInitParameter("database_username"),
                                               context.getInitParameter("database_password"));                
 
                // Get model archive
                ResultSet archiveRS = null;
                if(type.equals("model"))
                    archiveRS = Spkdb.getModel(con, id); 
                if(type.equals("data"))
                    archiveRS = Spkdb.getDataset(con, id);
                
                archiveRS.next();
                String archive = archiveRS.getString("archive");  
            
                // Disconnect to the database
                Spkdb.disconnect(con);
            
                // Generate version list for the model
                Archive arch = new Archive("", new ByteArrayInputStream(archive.getBytes())); 
                int number = arch.getRevisionVersion().last(); 
                versionList = new String[number][4];
                for(int i = 0; i < number; i++)
                {
                    int n = number - i;
                    Node node = arch.findNode(new Version("1." + n));  
                    versionList[i][0] = String.valueOf(n);
                    versionList[i][1] = node.getAuthor().toString();
                    versionList[i][2] = node.getDate().toString();
                    versionList[i][3] = arch.getLog("1." + n);
                }
                req.getSession().setAttribute("ARCHIVE", arch);
            }
            else
            {
                // Write the outgoing messages
                messageOut = "Authentication or Authorization error.";              
            }                
        }      
        catch(SQLException e)
        {
            messageOut = e.getMessage();
        }    
        catch(SpkdbException e)
        {
            messageOut = e.getMessage();
        }
        catch(ClassNotFoundException e)
        {
            messageOut = e.getMessage();
        } 
        catch(ParseException e)
        { 
            messageOut = e.getMessage();
        }
        
        // Write the data to our internal buffer
        out.writeObject(messageOut);
        if(messageOut.equals(""))
            out.writeObject(versionList);

        // Flush the contents of the output stream to the byte array
        out.flush();
        
        // Get the buffer that is holding our response
        byte[] buf = byteOut.toByteArray();
        
        // Notify the client how much data is being sent
        resp.setContentLength(buf.length);
        
        // Send the buffer to the client
        ServletOutputStream servletOut = resp.getOutputStream();
        
        // Wrap up
        servletOut.write(buf);
        servletOut.close();
    }
}
