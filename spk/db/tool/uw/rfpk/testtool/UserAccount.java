/*
 * UserAccount.java
 *
 * Created on March 25, 2004, 3:27 PM
 */
package uw.rfpk.testtool;

import java.sql.*;
import rfpk.spk.spkdb.*;
import javax.swing.JOptionPane;

/**
 * This is a user account management tool.
 * @author  Jiaji
 */
public class UserAccount extends javax.swing.JFrame {
    
    /** Creates new form UserAccount */
    public UserAccount() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User Account Manager");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(new javax.swing.border.TitledBorder("Database"));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Host Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 12, 6);
        jPanel1.add(jLabel1, gridBagConstraints);

        jTextField1.setMaximumSize(new java.awt.Dimension(151, 19));
        jTextField1.setMinimumSize(new java.awt.Dimension(151, 19));
        jTextField1.setPreferredSize(new java.awt.Dimension(151, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 12, 12);
        jPanel1.add(jTextField1, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Database");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 6);
        jPanel1.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jPanel1.add(jTextField2, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Username");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 6);
        jPanel1.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 12);
        jPanel1.add(jTextField3, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 8, 6);
        jPanel1.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 12);
        jPanel1.add(jTextField4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 6, 12);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBorder(new javax.swing.border.TitledBorder("SPK User"));
        jLabel5.setText("First Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 12, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel5, gridBagConstraints);

        jTextField5.setMaximumSize(new java.awt.Dimension(151, 19));
        jTextField5.setMinimumSize(new java.awt.Dimension(151, 19));
        jTextField5.setPreferredSize(new java.awt.Dimension(151, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        jPanel2.add(jTextField5, gridBagConstraints);

        jLabel6.setText("Last Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 12, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel6, gridBagConstraints);

        jTextField6.setMinimumSize(new java.awt.Dimension(150, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        jPanel2.add(jTextField6, gridBagConstraints);

        jLabel7.setText("Username");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 12);
        jPanel2.add(jTextField7, gridBagConstraints);

        jLabel8.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 12, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel8, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        jPanel2.add(jTextField8, gridBagConstraints);

        jLabel9.setText("Company");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 11);
        jPanel2.add(jTextField9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 12);
        jPanel2.add(jTextField10, gridBagConstraints);

        jLabel10.setText("Country");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setText("State");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jPanel2.add(jTextField11, gridBagConstraints);

        jLabel12.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 8, 6);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 8, 12);
        jPanel2.add(jTextField12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 6);
        getContentPane().add(jPanel2, gridBagConstraints);

        jButton1.setText("New User");
        jButton1.setMargin(new java.awt.Insets(2, 12, 2, 12));
        jButton1.setMaximumSize(new java.awt.Dimension(90, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(90, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(90, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(12, 6, 12, 0);
        getContentPane().add(jButton1, gridBagConstraints);

        jButton2.setText("Update User");
        jButton2.setMargin(new java.awt.Insets(2, 12, 2, 12));
        jButton2.setMaximumSize(new java.awt.Dimension(110, 25));
        jButton2.setMinimumSize(new java.awt.Dimension(110, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(110, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        getContentPane().add(jButton2, gridBagConstraints);

        jButton3.setText("Clear");
        jButton3.setMargin(new java.awt.Insets(2, 12, 2, 12));
        jButton3.setMaximumSize(new java.awt.Dimension(64, 25));
        jButton3.setMinimumSize(new java.awt.Dimension(64, 25));
        jButton3.setPreferredSize(new java.awt.Dimension(64, 25));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 12);
        getContentPane().add(jButton3, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel3.setBorder(new javax.swing.border.TitledBorder("Assignment"));
        jCheckBox1.setText("The user is a SPK tester");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 12, 0, 12);
        jPanel3.add(jCheckBox1, gridBagConstraints);

        jCheckBox2.setText("The user is a SPK developer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 4, 12);
        jPanel3.add(jCheckBox2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 12);
        getContentPane().add(jPanel3, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);                     
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!checkDBInfo(jTextField2.getText(),
                        jTextField1.getText(),
                        jTextField3.getText(),
                        jTextField4.getText()))
            return;
        if(!checkUserInfo(jTextField5.getText(),
                          jTextField6.getText(),
                          jTextField7.getText(),
                          jTextField8.getText()))
            return;        
        try
        {
            // Connect to the database
            Connection con = Spkdb.connect(jTextField2.getText(),  
                                           jTextField1.getText(), 
                                           jTextField3.getText(), 
                                           jTextField4.getText());    
                
            // Get user id
            ResultSet userRS = Spkdb.getUser(con, jTextField7.getText());
            userRS.next();
            long userId = userRS.getLong("user_id");            

            String[] name = {"first_name", "surname", "password", "company", 
                             "country", "state", "email", "test", "dev"};
            String tester = jCheckBox1.isSelected() ? "1" : "0";
            String developer = jCheckBox2.isSelected() ? "1" : "0";                 
            String[] value = {jTextField5.getText(),
                              jTextField6.getText(),
                              jTextField8.getText(),
                              jTextField9.getText(),
                              jTextField10.getText(),
                              jTextField11.getText(),
                              jTextField12.getText(),
                              tester, 
                              developer};
            Spkdb.updateUser(con, userId, name, value); 
            
            // Disconnect to the database
            Spkdb.disconnect(con); 
        }
        catch(SpkdbException e) 
        {
            JOptionPane.showMessageDialog(null, e, "SpkdbException", JOptionPane.ERROR_MESSAGE); 
        }
        catch(SQLException e) 
        {
            JOptionPane.showMessageDialog(null, e, "SQLException", JOptionPane.ERROR_MESSAGE);        
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!checkDBInfo(jTextField2.getText(),
                        jTextField1.getText(),
                        jTextField3.getText(),
                        jTextField4.getText()))
            return;
        if(!checkUserInfo(jTextField5.getText(),
                          jTextField6.getText(),
                          jTextField7.getText(),
                          jTextField8.getText()))
            return;        
        try
        {
            // Connect to the database
            Connection con = Spkdb.connect(jTextField2.getText(),  
                                           jTextField1.getText(), 
                                           jTextField3.getText(), 
                                           jTextField4.getText());    

            String[] name = {"first_name", "surname", "username", "password",
                             "company", "country", "state", "email", "test", "dev"};
            String tester = jCheckBox1.isSelected() ? "1" : "0";
            String developer = jCheckBox2.isSelected() ? "1" : "0";            
            String[] value = {jTextField5.getText(),
                              jTextField6.getText(),
                              jTextField7.getText(),
                              jTextField8.getText(),
                              jTextField9.getText(),
                              jTextField10.getText(),
                              jTextField11.getText(),
                              jTextField12.getText(),
                              tester, 
                              developer};
            Spkdb.newUser(con, name, value);
            
            // Disconnect to the database
            Spkdb.disconnect(con); 
        }
        catch(SpkdbException e)
        {
            JOptionPane.showMessageDialog(null, e, "SpkdbException", JOptionPane.ERROR_MESSAGE); 
        }
        catch(SQLException e) 
        {
            JOptionPane.showMessageDialog(null, e, "SQLException", JOptionPane.ERROR_MESSAGE);        
        }        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private boolean checkDBInfo(String host, String db, String user, String password)
    {
        boolean ok = true;
        if(host.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The host name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(db.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The database name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(user.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The database username is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(password.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The database password is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        return ok;
    }
    
    private boolean checkUserInfo(String first, String last, String user, String password)
    {
        boolean ok = true;
        if(first.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The first name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(last.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The last name is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(user.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The username is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        if(password.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The user password is required.", "Input Error", JOptionPane.ERROR_MESSAGE);   
            ok = false;
        }
        return ok;
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /** The main method that creates the application object and displays it.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new UserAccount().show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

}
