package uw.rfpk.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.*;

/** This servlet receives a String array containing three String objects from the client.
 * The first String object is the secret code to identify the client.  The second String  
 * object is the first text.  The third String object is the second String object.  The 
 * servlet saves the texts in two files and then call Linux command 'diff' to get revision.
 * The servlet sends back two objects.  The first object is a String containing the error 
 * message if there is an error or an empty String if there is not any error.  The second 
 * object is the returning revision as a String object.
 *
 * @author Jiaji Du
 * @version 1.0
 */
public class DiffFiles extends HttpServlet
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
        String revision = "";
        
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

        // Declare File objects
        File file1 = null;
        File file2 = null;
        
        try
        {
            // Read the data from the client 
            String[] messageIn = (String[])in.readObject();
            String secret = messageIn[0];
            if(secret.equals((String)req.getSession().getAttribute("SECRET")))             
            {                        
 	        String text1 = messageIn[1]; 
                String text2 = messageIn[2];
                
                // Get Runtime object
                Runtime runtime = Runtime.getRuntime();

                // Save passed in Strings into files
                file1 = new File("/tmp/" + secret + "_1.diff");
                file2 = new File("/tmp/" + secret + "_2.diff");                
                BufferedWriter out1 = new BufferedWriter(new FileWriter(file1));
                out1.write(text1);
                out1.close();        
                BufferedWriter out2 = new BufferedWriter(new FileWriter(file2));
                out2.write(text2);
                out2.close();
            
                // Create a subprocess
                String[] c = new String[]{"diff", file1.getPath(), file2.getPath()}; 
                Process process = runtime.exec(c);
            
                // Get stdout and stderr of the subprocess
                BufferedInputStream in1 = new BufferedInputStream(process.getInputStream());
                BufferedInputStream er1 = new BufferedInputStream(process.getErrorStream());
                while(true)
                {
                    int i = in1.read();
                    if(i == -1)
                        break;
                    revision += (char)i;
                }
                String error = "";
                while(true)
                {
                    int i = er1.read();
                    if(i == -1)
                        break;
                    error += (char)i;
                }
                in1.close();
                er1.close();

                // Destroy the subprocess
                process.destroy(); 
                
                messageOut = error;
            }
            else
            {
                // Write the outgoing messages
                messageOut = "Authentication error.";              
            }                
        }      
        catch(ClassNotFoundException e)
        {
            messageOut = e.getMessage();
        }
        finally
        {
            file1.delete();
            file2.delete();
        }
        
        // Write the data to our internal buffer
        out.writeObject(messageOut);
        if(messageOut.equals(""))
            out.writeObject(revision);

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
