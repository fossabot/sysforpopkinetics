/**********************************************************************
From:   Resource Facility for Population Kinetics                    
        Department of Bioengineering Box 352255                      
        University of Washington                                     
        Seattle, WA 98195-2255                                       

This file is part of the System for Population Kinetics (SPK), which
was developed with support from NIH grants RR-12609 and P41-
EB001975. Please cite these grants in any publication for which this
software is used and send a notification to the address given above.

SPK is Copyright (C) 1998-2003, by the University of Washington,
Resource Facility for Population Kinetics, and is made available as
free open source software under the terms of the University of
Washington Free-Fork License as a public service.  A copy of the
License can be found in the COPYING file in the root directory of this
distribution.
**********************************************************************/
package uw.rfpk.mda.nonmem.compartment;

import uw.rfpk.mda.nonmem.Utility;
import java.awt.Dimension;
import java.awt.Cursor;
import java.util.regex.*;
import javax.swing.JOptionPane;
import javax.help.*;
import uw.rfpk.mda.nonmem.MDAFrame;

/** This class defines mixed effects model dialog.
 *
 * @author  Jiaji Du
 */
public class IndModelDialog extends javax.swing.JDialog {
    
    /** Creates new form IndModelDialog.     
     * @param parent parent of this dialog.
     * @param modelExpression a String array containing model expression.
     * @param name name of the parameter to define a model on.
     * @param dataLabels a String array containing data labels of the dataset.
     */
    public IndModelDialog(java.awt.Frame parent, String[] modelExpression, String name, String[] dataLabels)
    {
        super(parent, true);
        this.modelExpression = modelExpression;
        this.name = name;
        initComponents();
        nameLabel.setText("Name: " + name);        
        for(int i = 0; i < dataLabels.length; i++)
            if(!Utility.isStdItem(dataLabels[i]) && !dataLabels[i].equals("ID"))
                dataNameComboBox.addItem(dataLabels[i]);
        if(modelExpression[0].length() == 0)
            userDefinedTextArea.setText(name + "=");
        else
            userDefinedTextArea.setText(modelExpression[0]);
//        helpButton.addActionListener(new CSH.DisplayHelpFromSource(MDAFrame.getHelpBroker()));
//        CSH.setHelpIDString(helpButton, "Prepare_Input_Model_Parameters");
        Dimension wndSize = getToolkit().getScreenSize();
        setLocation(wndSize.width/2, wndSize.height/3);
        setSize(410, 310);
        setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dataNameComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        userDefinedTextArea = new javax.swing.JTextArea();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Individual Model");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        nameLabel.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        getContentPane().add(nameLabel, gridBagConstraints);

        jTextArea1.setBackground(new java.awt.Color(238, 238, 238));
        jTextArea1.setText("Specify an individual model:\n  - Model must contain fixed effect parameter THETA\n  - Model must not contain random effect parameter ETA\n  - Enter appropriate number in ( ) folowing parameter (e.g.\n     THETA(1), THETA(2) etc.).");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 8, 12);
        getContentPane().add(jTextArea1, gridBagConstraints);

        jButton1.setText("OK");
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

        helpButton.setText("Help");
        helpButton.setPreferredSize(new java.awt.Dimension(75, 25));
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        jPanel1.add(helpButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        getContentPane().add(jPanel1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText("Select data item to be added to the model");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        dataNameComboBox.setMinimumSize(new java.awt.Dimension(32, 20));
        dataNameComboBox.setPreferredSize(new java.awt.Dimension(32, 20));
        dataNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataNameComboBoxActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 15, 2, 12);
        getContentPane().add(dataNameComboBox, gridBagConstraints);

        jScrollPane1.setViewportView(userDefinedTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 3, 12);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "Help is not currently available for this topic.");
    }//GEN-LAST:event_helpButtonActionPerformed

    private void dataNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataNameComboBoxActionPerformed
        String data = (String)dataNameComboBox.getSelectedItem();
        userDefinedTextArea.insert(data, userDefinedTextArea.getCaretPosition());
    }//GEN-LAST:event_dataNameComboBoxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String text = userDefinedTextArea.getText().trim();
        if(text.replaceAll(" ", "").startsWith(name + "="))
        {
            if(!Pattern.compile("\\bTHETA\\b", Pattern.UNIX_LINES).matcher(text).find())
            {
                JOptionPane.showMessageDialog(null, "THETA is missing in the model.",
                                              "Input Error", JOptionPane.ERROR_MESSAGE);
                setCursor(null);
                return;
            }
            else
            {
                if(Pattern.compile("\\bTHETA\\(\\)", Pattern.UNIX_LINES).matcher(text).find())
                {
                    JOptionPane.showMessageDialog(null, "THETA Number is missing.",
                                                  "Input Error", JOptionPane.ERROR_MESSAGE);
                    setCursor(null);
                    return;
                }
            }
            if(Pattern.compile("\\bETA\\b", Pattern.UNIX_LINES).matcher(text).find())
            {
                JOptionPane.showMessageDialog(null, "ETA is not a valid model parameter for an individual model.",
                                              "Input Error", JOptionPane.ERROR_MESSAGE);
                setCursor(null);
                return;
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "\"" + name + "=\" is missing in the model.",
                                              "Input Error", JOptionPane.ERROR_MESSAGE);
            setCursor(null);
            return;
        }
        modelExpression[0] = text;
        setVisible(false);
        setCursor(null);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /** The main function to test the dialog.
     * @param args the command line arguments which are not being used.
     */
    public static void main(String args[]) {
        String[] model = {"name=expression"};
        String[] dataLabels = {"ID", "TIME", "DV", "AMT"};
        new IndModelDialog(new DesignTool(), model, "name", dataLabels);
        System.out.println(model[0]);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox dataNameComboBox;
    private javax.swing.JButton helpButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextArea userDefinedTextArea;
    // End of variables declaration//GEN-END:variables
    
    private String[] modelExpression;
    private String name;
}
