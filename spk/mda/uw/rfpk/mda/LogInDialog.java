/*
 * LogInDialog.java
 *
 * Created on September 28, 2004, 3:42 PM
 */

package uw.rfpk.mda;

import uw.rfpk.mda.Network;
import java.awt.*;
import javax.net.ssl.*;
import javax.swing.JOptionPane;

/** This class is a login dialog.  It is used only for testing Model Design Agent.
 *
 * @author  jiaji Du
 */
public class LogInDialog extends javax.swing.JDialog {
    
    /** Creates new form LogInDialog.
     * @param parent the parent Frame object.
     * @param modal true if it is a modal dialog, false otherwise.
     */
    public LogInDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Toolkit theKit = getToolkit();
        Dimension wndSize = theKit.getScreenSize();
        setLocation(wndSize.width/2 - 120, wndSize.height/2 - 98);
        setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Logging In Dialog");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Username");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 2, 4);
        getContentPane().add(jLabel1, gridBagConstraints);

        jTextField1.setText("vicini");
        jTextField1.setMaximumSize(new java.awt.Dimension(150, 19));
        jTextField1.setMinimumSize(new java.awt.Dimension(150, 19));
        jTextField1.setPreferredSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 2, 12);
        getContentPane().add(jTextField1, gridBagConstraints);

        jLabel2.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 2, 4);
        getContentPane().add(jLabel2, gridBagConstraints);

        jTextField3.setText("192.168.1.5");
        jTextField3.setMaximumSize(new java.awt.Dimension(150, 19));
        jTextField3.setMinimumSize(new java.awt.Dimension(150, 19));
        jTextField3.setPreferredSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 12);
        getContentPane().add(jTextField3, gridBagConstraints);

        jLabel4.setText("Host Port");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 12, 4);
        getContentPane().add(jLabel4, gridBagConstraints);

        jTextField4.setText("8443");
        jTextField4.setMaximumSize(new java.awt.Dimension(150, 19));
        jTextField4.setMinimumSize(new java.awt.Dimension(150, 19));
        jTextField4.setPreferredSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 12, 12);
        getContentPane().add(jTextField4, gridBagConstraints);

        jPasswordField1.setText("new");
        jPasswordField1.setMaximumSize(new java.awt.Dimension(150, 19));
        jPasswordField1.setMinimumSize(new java.awt.Dimension(150, 19));
        jPasswordField1.setPreferredSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 12);
        getContentPane().add(jPasswordField1, gridBagConstraints);

        jLabel3.setText("Host    IP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 2, 4);
        getContentPane().add(jLabel3, gridBagConstraints);

        jButton1.setText("OK");
        jButton1.setMaximumSize(new java.awt.Dimension(75, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(75, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(75, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 12, 12);
        getContentPane().add(jPanel1, gridBagConstraints);

        jTextField2.setBackground(new java.awt.Color(205, 205, 205));
        jTextField2.setText("Logging in to test Model Design Agent");
        jTextField2.setBorder(null);
        jTextField2.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 12);
        getContentPane().add(jTextField2, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean ok = true;
        if(jTextField1.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        }
        if(jPasswordField1.getPassword().length == 0)
        {
            JOptionPane.showMessageDialog(null, "Password is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        }
        if(jTextField3.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Host IP is required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            ok = false;
        }
        if(ok)
        {
            initArgs();
            if(args != null)
            {
                setVisible(false);
                dispose();
            }
            else
                JOptionPane.showMessageDialog(null, "You are not logged in. Please retry.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        System.exit(0);
    }//GEN-LAST:event_closeDialog

     // This method is for testing only
    private void initArgs()
    {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};
    
        try 
        {
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    HttpsURLConnection.setDefaultHostnameVerifier(new NoHostnameVerifier());
            
            // Get session id and secret code from the server
            String host = jTextField3.getText().trim();
            String port = jTextField4.getText().trim();
            Network network = new Network("https://" + host + ":" + port + 
                                          "/user/servlet/uw.rfpk.servlets.InitMDATest", "");
            String[] user = {jTextField1.getText().trim(), String.valueOf(jPasswordField1.getPassword())};
            String[] sessionCodes = (String[])network.talk(user);
            
            // Initialize argument for MDA
            if(sessionCodes != null)
            {
                args = new String[7];
                args[0] = host;
                args[1] = port;
                args[2] = sessionCodes[0];
                args[3] = sessionCodes[1];
                args[4] = "1";
                args[5] = "1";
                args[6] = jTextField1.getText();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Overrides verify method to bypass host name check.
    private class NoHostnameVerifier implements HostnameVerifier
    {
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    }
       
    /** Gets MDA application argument.
     * @return a String array to be used as the MDA application argument. 
     */
    public String[] getArgs()
    {
        return args;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
    
    private String[] args = null;
}
