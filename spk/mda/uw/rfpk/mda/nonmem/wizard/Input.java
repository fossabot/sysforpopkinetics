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
package uw.rfpk.mda.nonmem.wizard;

import uw.rfpk.mda.nonmem.Utility;
import uw.rfpk.mda.nonmem.compartment.DesignTool;
import org.netbeans.ui.wizard.*;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import javax.swing.JTable;  
import javax.swing.SwingConstants; 
import java.util.Vector; 
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class defines a step to create the $INPUT record.
 * @author  Jiaji Du 
 */
public class Input extends javax.swing.JPanel implements WizardStep {
    
    private StepDescriptor sd = new MyStepDescriptor(); 
    private JComponent panel = this; 
    private int nDataCol = 0;
    private int index = -1;
    private boolean isValid = false;
    private MDAIterator iterator = null;
    private String[] input = null; 
    private Vector<Vector> data = null;
    private String[][] dataArray = null;
    private String[][] dataTemp = null;
    private int nDataRow = 0;    
    private JWizardPane wizardPane = null;
    private TableModel tableModel = new ATableModel();
    private DefaultTableModel tableEditModel;
    private String[] stdItems = new String[] { "DV", "MDV", "EVID", "TIME", "DATE", 
                                "DATE1", "DATE2", "DATE3", "AMT", "RATE", "SS", 
                                "ADDL", "II", "ABS", "LAG", "UPPER", "LOWER", "L1", 
                                "L2", "CMT", "PCMT", "CALL", "CONT" };

    /** Creates new form Input.
     * @param iter a MDAIterator object to initialize the field iterator.
     */
    public Input(MDAIterator iter) { 
        initComponents();
        iterator = iter;
        jTable2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        dataEditor = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jTextPane1 = new javax.swing.JTextPane();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();

        dataEditor.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dataEditor.setTitle("Dataset Editor");
        dataEditor.setModal(true);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 500));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setPreferredScrollableViewportSize(new java.awt.Dimension(150, 400));
        jScrollPane2.setViewportView(jTable2);

        dataEditor.getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButton2.setText("OK");
        jButton2.setPreferredSize(new java.awt.Dimension(75, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton3);

        dataEditor.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jLabel2.setText("You may change any data element value in this table.");
        dataEditor.getContentPane().add(jLabel2, java.awt.BorderLayout.NORTH);

        setLayout(new java.awt.GridBagLayout());

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ID", "DV", "MDV", "EVID", "TIME", "DATE", "DATE1", "DATE2", "DATE3", "AMT", "RATE", "ABS", "LAG", "UPPER", "LOWER", "L1", "L2", "CMT", "PCMT", "CALL", "CONT" }));
        jComboBox1.setMinimumSize(new java.awt.Dimension(0, 20));
        jComboBox1.setPreferredSize(new java.awt.Dimension(81, 24));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 84;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 8, 0);
        add(jComboBox1, gridBagConstraints);

        jTextField1.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 53;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 11, 10, 12);
        add(jTextField1, gridBagConstraints);

        jTextField2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 14, 0);
        add(jTextField2, gridBagConstraints);

        addButton.setText("Enter");
        addButton.setPreferredSize(new java.awt.Dimension(83, 25));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 12, 5, 12);
        add(addButton, gridBagConstraints);

        jTextPane1.setBackground(new java.awt.Color(238, 238, 238));
        jTextPane1.setEditable(false);
        jTextPane1.setText("Click data column and choose data item name:\n- ID (if used) item must be in first column\n- DV (dependent variable) item is required\n- Item name and alias must be uppercase and unique");
        jTextPane1.setPreferredSize(new java.awt.Dimension(435, 66));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 12);
        add(jTextPane1, gridBagConstraints);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Standard item");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 12, 9, 28);
        add(jRadioButton1, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("User-defined item");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 12, 11, 0);
        add(jRadioButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jSeparator1, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setSelectionForeground(new java.awt.Color(255, 51, 51));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 88;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 6, 12);
        add(jScrollPane1, gridBagConstraints);

        jLabel1.setText("Alias");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 58, 10, 0);
        add(jLabel1, gridBagConstraints);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Drop data column");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 8, 7);
        add(jRadioButton3, gridBagConstraints);

        jCheckBox1.setText("Centered");
        jCheckBox1.setEnabled(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 10, 12, 0);
        add(jCheckBox1, gridBagConstraints);

        jButton1.setText("Remove");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 6, 0);
        add(jButton1, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        add(jSeparator2, gridBagConstraints);

        jButton4.setText("Edit Data");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 5, 12);
        add(jButton4, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String[] header;
        if(indexOf(input, "MDV") == -1 && JOptionPane.showConfirmDialog(null, 
                                         "Do you want to add a data column as MDV?",   
                                         "Question Dialog",
                                         JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE) == 0)
        {
            header = new String[nDataCol + 1];
            for(int i = 0; i < nDataCol; i++)
                header[i] = input[i];
            header[nDataCol] = "MDV";
            dataTemp= new String[dataArray.length][nDataCol + 1];
            for(int j = 0; j < dataArray.length; j++)
            {
                for(int i = 0; i < nDataCol; i++)
                    dataTemp[j][i] = dataArray[j][i];
                dataTemp[j][nDataCol] = "0";
            }
        }
        else
        {
            /*
            header = new String[nDataCol];
            for(int i = 0; i < nDataCol; i++)
                header[i] = input[i];
            dataTemp = new String[dataArray.length][nDataCol];
            for(int j = 0; j < dataArray.length; j++)
            {
                for(int i = 0; i < nDataCol; i++)
                    dataTemp[j][i] = dataArray[j][i];
            }
             */
            header = input;
            dataTemp = (String[][])dataArray.clone();
        }
        tableEditModel = new DefaultTableModel(dataTemp, header);
        jTable2.setModel(tableEditModel);
        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        dataEditor.setSize(500, 400);
        dataEditor.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dataTemp = null;
        dataEditor.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Check data
        for(int i = 0; i < dataTemp.length; i++)
            for(int j = 0; j < dataTemp[0].length; j++)
                if(!Utility.isFloatNumber((String)tableEditModel.getValueAt(i, j)))
                {
                    JOptionPane.showMessageDialog(null, "Data missing or format error.",
                                                  "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;   
                }
        dataArray = dataTemp;
        for(int i = 0; i < dataTemp.length; i++)
            for(int j = 0; j < dataTemp[0].length; j++)
                dataArray[i][j] = (String)tableEditModel.getValueAt(i, j);
        if(dataArray[0].length > nDataCol)
        {
            String[] temp = new String[nDataCol + 1];
            for(int i = 0; i < nDataCol; i++)
                temp[i] = input[i];
            temp[nDataCol] = "MDV";
            input = temp;
        }
        dataTemp = null;
        dataEditor.dispose();
        
        setDataObject();
        checkValidity();
        setTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void setTable()
    {
           // Create a column model for the main table.  
        TableColumnModel cm = new DefaultTableColumnModel() {
            public void addColumn(TableColumn tc) {
                tc.setMinWidth(80);
                super.addColumn(tc);
            }
        };
            
        // Set table model and column model.
        jTable1.setModel(tableModel);  
        jTable1.setColumnModel(cm);
            
        // Shut off autoResizeMode.
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         
        // Create columns.
        jTable1.createDefaultColumnsFromModel();
        jTable1.setColumnSelectionAllowed(true);
            
        // Set table cell renderer.
        TableCellRenderer cellRenderer = new CellRenderer(); 
        for(int i = 0; i < input.length; i++)
            cm.getColumn(i).setCellRenderer(cellRenderer);
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(index == 0 && (iterator.analysis.equals("population") || iterator.analysis.equals("two-stage") || iterator.analysis.equals("nonparametric")))
            return;
        
        if(!input[index].equalsIgnoreCase("MDV"))
        {
            input[index] = "";
        
            // Repaint the table
            jTable1.repaint();
            
            // Disable the left options 
            isValid = false;
            wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());
                    
            // If input is empty, gray out the Remove button
            int j = 0;
            if(iterator.analysis.equals("population") || iterator.analysis.equals("two-stage") || iterator.analysis.equals("nonparametric"))
                j = 1;
            for(int i = j; i < input.length; i++)
                if(!input[i].equals(""))
                    return;
            jButton1.setEnabled(false);
        }
        else
        {
            String[] temp = new String[input.length - 1];
            for(int i = 0; i < temp.length; i++)
                temp[i] = input[i];
            input = temp;
            for(int j = 0; j < dataArray.length; j++)
            {
                temp = new String[input.length];
                for(int i = 0; i < temp.length; i++)
                    temp[i] = dataArray[j][i];
                dataArray[j] = temp;
            }
            setTable();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        jLabel1.setEnabled(false);
        jComboBox1.setEnabled(false);
        jTextField1.setEnabled(false);
        jCheckBox1.setEnabled(false);       
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        index = jTable1.getSelectedColumn();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jTextField1.setText("");
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jComboBox1.setEnabled(false);
        jLabel1.setEnabled(false); 
        jTextField1.setEnabled(false); 
        jTextField1.setText("");        
        jTextField2.setEnabled(true);  
        jCheckBox1.setEnabled(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jComboBox1.setEnabled(true);
        jLabel1.setEnabled(true); 
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(false);
        jTextField2.setText("");
        jCheckBox1.setEnabled(false); 
        jCheckBox1.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if(iterator.analysis.equals("population") || iterator.analysis.equals("two-stage") || 
           iterator.analysis.equals("nonparametric") && index == 0 && input[0].equals("ID"))
        {
            JOptionPane.showMessageDialog(null, 
                                          "The first data labe must be ID for a population dataset.", 
                                          "Input Error",    
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        String element = null;
        if(jRadioButton1.isSelected())
        {
            element = (String)jComboBox1.getSelectedItem();
            String alias = jTextField1.getText().trim().toUpperCase();
            if(!Utility.checkDataLabels(new String[]{alias}))
                return;
            if(!alias.equals(""))
            {
                element = element + "=" + alias;
            }
        }
        else if(jRadioButton2.isSelected())
        {
            element = jTextField2.getText().trim().toUpperCase();
            if(!Utility.checkDataLabels(new String[]{element}))
                return;
            if(element.equals(""))
                return;
            if(indexOf(stdItems, element) != -1)
            {
                JOptionPane.showMessageDialog(null, 
                                              element + " is one of the reserved " +
                                              "names for standard data items.", 
                                              "Input Error",    
                                              JOptionPane.ERROR_MESSAGE);             
                return;
            }
            if(jCheckBox1.isSelected())
                element = element + "=CENTERED";           
        }
        else
            element = "DROP";
        for(int i = 0; i < nDataCol; i++)
            if(input[i].split("=")[0].equals(element.split("=")[0])
               && !element.equals("DROP")) 
            {
                JOptionPane.showMessageDialog(null, 
                                              "Data item " + element.split("=")[0] +
                                              " is already in column " + (i + 1) + ".",   
                                              "Input Error",    
                                              JOptionPane.ERROR_MESSAGE);  
                return;
            }
                
        // Enter the element
        if(index > -1) input[index] = element;
        
        // Repaint the table
        jTable1.repaint();  
        
        // Check validaty
        checkValidity();
        
        jTextField1.setText("");
        jTextField2.setText("");
        jCheckBox1.setSelected(false);

        // Repaint the table
        jTable1.repaint();
    }//GEN-LAST:event_addButtonActionPerformed
    
    // Check validity
    private void checkValidity()
    {
        if(indexOf(input, "") == -1) 
        {
            if(indexOf(input, "DV") != -1 && Utility.checkDataLabels(input) && checkMDV())
            {                
                isValid = true;
                wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray()); 
            }
            else
            {
                JOptionPane.showMessageDialog(null, 
                                              "Data item \"DV\" is required.",   
                                              "Input Error",    
                                              JOptionPane.ERROR_MESSAGE); 
                isValid = false;
                wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());                    
            }
        }
    }
    
    // Check if every individual has DV
    private boolean checkMDV()
    {
        int indexMDV = indexOf(input, "MDV");
        if(indexMDV != -1)
        {
            Vector indData;
            int j;
            int indSize;
            String mdv;
            for(int i = 0; i < data.size(); i++)
            {
                indData = data.get(i);
                indSize = indData.size();
                for(j = 0; j < indSize; j++)
                {
                    mdv = ((String[])indData.get(j))[indexMDV];   
                    if(mdv.equals("0")) 
                        break;
                }
                if(j == indSize)
                {
                    JOptionPane.showMessageDialog(null, "Individual " + (i + 1) + " missed all DV record.",
                                                  "Input Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        return true;
    }
    
        private class ATableModel extends AbstractTableModel {
        
        public String getColumnName(int c) {
             return "Column " + (c + 1);
        }
        public Class getColumnClass(int c) {
            return String.class; 
        }
        public int getColumnCount() {
            return input.length;
        }
        public int getRowCount() {
//            return ((Vector)data.get(0)).size() + 1;    // Show first individual data only
            return nDataRow + 1;
        }
        public Object getValueAt(int r, int c) {
            Object value = null;
            if(r == 0)
                value = input[c]; 
            else
//                value = ((String[])((Vector)data.get(0)).get(r - 1))[c];  // Show first individual data only
                value = dataArray[r - 1][c];
            return value; 
        }
        public boolean isCellEditable(int r, int c) {
            return false;
        }
        public void setValueAt(Object value, int r, int c) {
            if(r == 0)
                input[c] = (String)value; 
//            else
//                ((String[])((Vector)data.get(0)).get(r - 1))[c] = (String)value;  // Show first individual data only
//                dataArray[r - 1][c] = (String)value;
        }
    }

    private class CellRenderer extends DefaultTableCellRenderer 
    { 
        public Component getTableCellRendererComponent(JTable table,
            Object value,boolean isSelected, boolean hasFocus, int row,int col) 
        {
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,col);
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
	}
    }
    
    private int indexOf(String[] strings, String string)
    {
        String first = string.split("=")[0];
        for(int i = 0; i < strings.length; i++)
            if(first.equals(strings[i].split("=")[0]))
                return i;
        return -1;
    }
    
    private void setDataObject()
    {
        Vector<Vector> dataObject = new Vector<Vector>();
        Vector<String[]> indData = new Vector<String[]>();
        String[] row;
        int nDataRow = tableEditModel.getRowCount();
        int nDataColumn = tableEditModel.getColumnCount();
        if(iterator.analysis.equals("individual") || iterator.analysis.equals("identifiability"))
        {
            for(int i = 0; i < nDataRow; i++)
            {
                row = new String[nDataColumn];
                for(int j = 0; j < nDataColumn; j++)
                    row[j] = (String)tableEditModel.getValueAt(i, j);
                indData.add(row);
            }
            dataObject.add(indData);
        }
        else
        {
            String id = "";
            for(int i = 0; i < nDataRow; i++)
            {
                row = new String[nDataColumn];
                for(int j = 0; j < nDataColumn; j++)
                    row[j] = (String)tableEditModel.getValueAt(i, j);
                if(row[0].equalsIgnoreCase(id))
                    indData.add(row);
                else
                {
                    id = row[0];
                    if(i != 0) dataObject.add(indData);
                    indData = new Vector<String[]>();
                    indData.add(row);
                }
            }
            dataObject.add(indData);
        }
        ((MDAObject)wizardPane.getCustomizedObject()).setData(dataObject);
        data = dataObject;
    }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JDialog dataEditor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    /**
     * This method is to return the StepDescriptor object.
     * @return a StepDescriptor object.
     */    
    public StepDescriptor getStepDescription(){
	return sd;
    }

    private class MyStepDescriptor extends StepDescriptor{ 

	public Component getComponent(){
	    return panel;
	}
       
  	public String getContentItem(){
  	    return "Data Labels";
  	}

	public String getStepTitle(){
	    return "Data Labels";
	}

	public void showingStep(JWizardPane wizard){
            if(!iterator.getIsNewData())
                return;
                        
            // Set data as old
            iterator.setIsNewData(false);
            
            wizardPane = wizard;
            data = ((MDAObject)wizard.getCustomizedObject()).getData();
            nDataCol = iterator.getNDataCol();
            setDataArray();
            input = ((MDAObject)wizard.getCustomizedObject()).getDataLabels();
            
            index = -1;
            jComboBox1.removeItem("ID");
            if(iterator.analysis.equals("population") || iterator.analysis.equals("two-stage") || iterator.analysis.equals("nonparametric"))
            {
                input[0] = "ID";
            }
            else
            {
                jComboBox1.insertItemAt("ID", 1);
            }

            checkValidity();
            setTable();
	}

        private void setDataArray()
        {
            int nRow = 0;
            for(int i = 0; i < data.size(); i++)
                nRow += ((Vector)data.get(i)).size();             
            dataArray = new String[nRow][nDataCol];
            int k = 0;
            for(int i = 0; i < data.size(); i++)
            {
                Vector indData = (Vector)data.get(i);
                for(int j = 0; j < indData.size(); j++)
                    dataArray[k++] = (String[])indData.get(j);
            }
            nDataRow = nRow;
        }        
	public void hidingStep(JWizardPane wizard){
            if(iterator.getIsBack())
            {
                iterator.setIsBack(false);
                return;
            }            
            if(nDataCol == 0)
                return;
            MDAObject object = (MDAObject)wizard.getCustomizedObject();
            String inputs = "";
            for(int i = 0; i < nDataCol - 1; i++)
                inputs = inputs + input[i] + " ";
            inputs += input[nDataCol - 1];
            if(input.length > nDataCol)
            {
                inputs += " MDV";
                iterator.setNDataCol(input.length);
                nDataCol = input.length;
                setDataObject();
            }
            object.getSource().input = inputs.split(" ");
            if(iterator.analysis.equals("two-stage") || iterator.analysis.equals("nonparametric"))
                inputs = inputs.substring(3);
            String record = "$INPUT " + inputs.replaceAll("\r", "");
            object.getRecords().setProperty("Input", record);
	}

	public boolean isValid(){
            return isValid;
	}

	public ActionListener getHelpAction(){
	    return new ActionListener(){
                public void actionPerformed(ActionEvent e){ 
                    if(!iterator.getIsOnline()) 
                        new Help("Help for $INPUT Record", 
                                 Input.class.getResource("/uw/rfpk/mda/nonmem/help/Input.html"));
                    else
                        Utility.openURL("https://" + iterator.getServerName() + 
                                        ":" + iterator.getServerPort() + "/user/help/Input.html");  
                }
            };
	}
        
        public String getHelpID() {
            return "Prepare_Input_Data_Labels";
        } 
    }
}
