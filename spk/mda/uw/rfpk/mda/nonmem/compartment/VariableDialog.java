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

import javax.swing.DefaultListModel;
import java.util.*;
import javax.swing.JList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** This class defines variable dialog.
 *
 * @author  Jiaji Du
 */
public class VariableDialog extends javax.swing.JDialog {
    
    /** Creates new form VariableDialog.
     * @param parent DesignTool object.
     */
    public VariableDialog(DesignTool parent) {
        super(parent, false);
        initComponents();
        tool = parent;
        setVariableList();
        setSize(350, 350);
    }
    
    /** Set variable list */
    protected void setVariableList()
    {
        Properties variables = Model.variables;
        List<Object> keySet = new Vector<Object>(variables.keySet());
        Iterator keyIter = keySet.iterator();
        String key, value;
        listModel.removeAllElements();
        while(keyIter.hasNext())
        {
            key = (String)keyIter.next();
            value = variables.getProperty(key);
            if(value != null)
                listModel.addElement(key + "=" + value);
        }
        jList1.setModel(listModel);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("User defined mixed effect variables");
        setLocationRelativeTo(this);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText("Select one to model");
        getContentPane().add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(259, 200));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        jButton3.setText("Help");
        jButton3.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel1.add(jButton3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Model.variables.clear();
        String[] item;
        for(int i = 0; i < listModel.size(); i++)
        {
            item = ((String)listModel.get(i)).split("=");
            if(item.length == 1)
                Model.variables.setProperty(item[0], "");
            if(item.length == 2)
                Model.variables.setProperty(item[0], item[1]);
        }
        Model.equations = equations;
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        index = jList1.getSelectedIndex();
        String selectedItem = (String)jList1.getSelectedValue();
        String variable = selectedItem.substring(0, selectedItem.indexOf("=")).trim();
        String modelExpression = selectedItem.substring(selectedItem.indexOf("=") + 1).trim();
        String[] model = new String[2];
        model[0] = modelExpression;
        model[1] = Model.equations;
        MixedModelDialog modelDialog = new MixedModelDialog(null, model, variable, tool.object.getDataLabels());
        modelExpression = model[0];
        equations = model[1];
        if(modelExpression.equals(""))
        {
            JOptionPane.showMessageDialog(null, "The variable's mixed effect model has not been defined.",
                                          "Error Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Model.variables.setProperty(variable, modelExpression);
        listModel.setElementAt(variable + "=" + modelExpression, index);
        setVariableList();
        tool.setRecords();
    }//GEN-LAST:event_jList1MouseClicked

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /** Main method.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new VariableDialog(new DesignTool()).setVisible(true);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private DefaultListModel listModel = new DefaultListModel();
    private String equations = "";
    private int index = -1;
    private DesignTool tool;
}
