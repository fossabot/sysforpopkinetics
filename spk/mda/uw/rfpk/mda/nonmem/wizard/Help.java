/*
 * Help.java
 *
 * Created on April 29, 2004, 9:20 AM
 */

package uw.rfpk.mda.nonmem.wizard;

import java.net.URL;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author  Jiaji Du
 */
public class Help extends javax.swing.JFrame {
    
    /** Creates new form Help */
    public Help(String title, URL url) {  
        initComponents();
        jButton1.addActionListener(new javax.swing.text.DefaultEditorKit.CopyAction());        
        setTitle(title);
        setSize(700, 500);
        setVisible(true);
        try
        {
            jEditorPane1.setPage(url);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, e, "IOException", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        copyMenu = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();

        jMenuItem1.setText("Copy");
        copyMenu.add(jMenuItem1);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jEditorPane1.setEditable(false);
        jScrollPane1.setViewportView(jEditorPane1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("Copy");
        getContentPane().add(jButton1, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
//        System.exit(0);
    }//GEN-LAST:event_exitForm

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu copyMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
