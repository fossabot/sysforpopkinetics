package uw.rfpk.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;
import java.util.Properties;
import java.nio.*;
import java.sql.*;
import rfpk.spk.spkdb.*;
import uw.rfpk.beans.UserInfo;

/** This servlet receives a String array containing three String objects from the client.
 * The first String object is the secret code to identify the client.  The second String 
 * is the job_id.  The third String object is a flag that specified if this call is from 
 * a library patron.  The servlet first checks if this id belongs to the user using database 
 * API method, getUser, to get the user_id and using database API method, getJob, to get 
 * user_id, then comparing them.  If they are the same, the servlet calls database 
 * API method, getJob, to get model_id, model_version, dataset_id, dataset_version. Then,
 * the model_id is paased in the database API method getModel to get model name and the 
 * dataset_id is passed in the database API method getDataset to get dataset name.  The 
 * servlet puts these data into a java.util.Properties object.  The servlet sends back 
 * two objects.  The first object is a String containing the error message if there 
 * is an error or an empty String if there is not any error.  The second object is the 
 * Properties object containing the returned data.
 *
 * @author Jiaji Du
 * @version 1.0
 */
public class GetJobInfo extends HttpServlet
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
        // Get the user name of the session
        UserInfo user = (UserInfo)req.getSession().getAttribute("validUser");
        String username = user.getUserName();
                
        // Prepare output message
        String messageOut = "";
        Properties jobInfo = new Properties();

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
            Vector jobIds = (Vector)(req.getSession().getAttribute("JOBIDS"));  
            if(secret.equals((String)req.getSession().getAttribute("SECRET")))               
            {           
                long jobId = Long.parseLong(messageIn[1]);
                if(messageIn[2].equals("true"))
                    username = "librarian";
                
                // Connect to the database
                ServletContext context = getServletContext();
                Connection con = Spkdb.connect(context.getInitParameter("database_name"),
                                               context.getInitParameter("database_host"),
                                               context.getInitParameter("database_username"),
                                               context.getInitParameter("database_password")); 
                
                // Get user id
                ResultSet userRS = Spkdb.getUser(con, username);
                userRS.next();
                long userId = userRS.getLong("user_id");
                
                // Get job for the job_id
                ResultSet jobRS = Spkdb.getJob(con, jobId); 
                jobRS.next();

                 // Check if the job belongs to the user
                if(jobRS.getLong("user_id") == userId)
                {               
                    // Get job information 
                    long modelId = jobRS.getLong("model_id");
                    long datasetId = jobRS.getLong("dataset_id"); 
                    String modelVersion = jobRS.getString("model_version");
                    String datasetVersion = jobRS.getString("dataset_version");
                    ResultSet modelRS = Spkdb.getModel(con, modelId); 
                    modelRS.next();
                    String modelName = modelRS.getString("name"); 
                    ResultSet datasetRS = Spkdb.getDataset(con, datasetId);
                    datasetRS.next();
                    String datasetName = datasetRS.getString("name");          
               
                    // Disconnect to the database
                    Spkdb.disconnect(con);
             
                    // Put returning objects into the Properties
                    jobInfo.setProperty("modelName", modelName);
                    jobInfo.setProperty("modelVersion", modelVersion.substring(2));
                    jobInfo.setProperty("datasetName", datasetName);
                    jobInfo.setProperty("datasetVersion", datasetVersion.substring(2));
                }
                else
                {
                    // Write the outgoing messages
                    messageOut = "Authorization error.";                    
                }
            }
            else
            {
                // Write the outgoing messages
                messageOut = "Authentication error.";              
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
         
        // Write the data to our internal buffer
        out.writeObject(messageOut);
        if(messageOut.equals(""))
            out.writeObject(jobInfo);
        
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
