/*
 * Data.java
 *
 * Created on August 15, 2003, 1:17 PM
 */

package uw.rfpk.mda.nonmem.wizard;

import uw.rfpk.mda.nonmem.Utility;
import org.netbeans.ui.wizard.*;
import java.util.Vector;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class defines a step to create the $DATA record
 * @author  Jiaji Du
 */
public class Data extends javax.swing.JPanel implements WizardStep {
    
    private StepDescriptor sd = new MyStepDescriptor(); 
    private JComponent panel = this;
    private MDAIterator iterator = null;
    private JWizardPane wizardPane = null;
    private boolean isValid = false;
    private boolean isHighlight = false;    
    private DefaultHighlighter highlighter = new DefaultHighlighter();
    private DefaultHighlighter.DefaultHighlightPainter highlight_painter =
            new DefaultHighlighter.DefaultHighlightPainter(new Color(200,200,250));
    
    /** Creates new form Data
     *  @param iter A MDAIterator object to initialize the field iterator
     */
    public Data(MDAIterator iter) {
        iterator = iter;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setLayout(new java.awt.GridBagLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(2);
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea1KeyTyped(evt);
            }
        });
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 12, 6);
        add(jTextArea1, gridBagConstraints);

        jLabel1.setText("Enter NONMEM data file name or browse");
        jLabel1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 0, 12);
        add(jLabel1, gridBagConstraints);

        jButton1.setText("Browse");
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 6, 15, 12);
        add(jButton1, gridBagConstraints);

        jButton2.setText("Load File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 12, 12);
        add(jButton2, gridBagConstraints);

        jLabel2.setText("           ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 18, 60, 12);
        add(jLabel2, gridBagConstraints);

        jLabel3.setText("Click the \"Load File\" button to proceed");
        jLabel3.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 12, 12);
        add(jLabel3, gridBagConstraints);

        jRadioButton1.setText("Use the loaded data file in this tool");
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setEnabled(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 12, 12, 12);
        add(jRadioButton1, gridBagConstraints);

        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Load a new data file into this tool");
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 24, 12);
        add(jRadioButton2, gridBagConstraints);

    }//GEN-END:initComponents

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
        highlighter.removeAllHighlights();
        isHighlight = false;
    }//GEN-LAST:event_jTextArea1MouseClicked

    private void jTextArea1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyTyped
        if(isHighlight)
        {
            jTextArea1.setText("");
            highlighter.removeAllHighlights();
            isHighlight = false;
        }        
    }//GEN-LAST:event_jTextArea1KeyTyped

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // Initialize GUI components
        jLabel1.setEnabled(false);
        jLabel3.setEnabled(false);
        jTextArea1.setEnabled(false);
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);   
        isValid = true;
        wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());        
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // Initialize GUI components
        jLabel1.setEnabled(true);
        jLabel3.setEnabled(true);
        jTextArea1.setEnabled(true);
        jButton1.setEnabled(true);
        jButton2.setEnabled(true);    
        jLabel2.setText(" "); 
        isValid = false;
        wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());
        jTextArea1.requestFocusInWindow();        
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String path = jTextArea1.getText().trim();
        Vector data = new Vector(); 
        int nDataCol = Utility.parseDataFile(path, data, iterator.getIsInd());   
        if(nDataCol <= 0)
        {
            JOptionPane.showMessageDialog(null, "Error in data file.",   
                                          "File Error",    
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        ((MDAObject)wizardPane.getCustomizedObject()).setData(data);
        iterator.setNDataCol(nDataCol);           
        jLabel2.setText("There are " + nDataCol + " columns in the data file.");
        iterator.setIsNewData(true);
        iterator.setIsDataXML(false);
        isValid = true;
        wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser files = new JFileChooser();
        int result = files.showOpenDialog(null); 
        if(result == files.APPROVE_OPTION)
	{
            File file = files.getSelectedFile(); 
            jTextArea1.setText(file.getPath());
        }               
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    /**
     * This method is to return the StepDescriptor object
     * @return  A StepDescriptor object
     */    
    public StepDescriptor getStepDescription(){
	return sd;
    }

    private class MyStepDescriptor extends StepDescriptor{ 

	public Component getComponent(){
	    return panel;
	}
       
  	public String getContentItem(){
  	    return "Data File Selection";
  	}

	public String getStepTitle(){
	    return "Data File Selection";
	}

	public void showingStep(JWizardPane wizard){
            wizardPane = wizard;
            MDAObject object = (MDAObject)wizard.getCustomizedObject();
            if(iterator.getIsReload() && iterator.getIsDataXML())
            {
                String text = iterator.getReload().getProperty("DATA");
                if(text != null)
                    jTextArea1.setText(text.substring(5).trim());
                Vector data = new Vector(); 
                int nDataCol = Utility.parseDataXML(iterator.getDataXML(), data, iterator.getIsInd());   
                if(nDataCol <= 0)
                {
                    JOptionPane.showMessageDialog(null, "Error in data file.",   
                                                  "File Error",    
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ((MDAObject)wizardPane.getCustomizedObject()).setData(data);
                iterator.setNDataCol(nDataCol);           
                jLabel2.setText("There are " + nDataCol + " columns in the data file.");
//                iterator.setIsNewData(true);
                isValid = true;
                wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());                
                jRadioButton1.setEnabled(true);
                jRadioButton1.doClick();                
            }
            else
            {
                if(object.getSource().data != null && !iterator.getIsNewData())
                {
                    jRadioButton1.setEnabled(true);
                    jRadioButton1.doClick();
                }
                else
                    jRadioButton2.doClick();                
            }
            jTextArea1.requestFocusInWindow();            
	}

	public void hidingStep(JWizardPane wizard){
            if(iterator.getIsBack())
            {
                iterator.setIsBack(false);
                return;
            }            
            String filePath = jTextArea1.getText().trim();
            if(filePath == "")
                return;
            String[] path = filePath.replace('\\', '/').split("/");            
            String fileName = path[path.length - 1]; 
            if(Utility.checkTag(fileName, "File name"))
                return;
            MDAObject object = (MDAObject)wizard.getCustomizedObject();
            String record = "$DATA " + fileName.replaceAll("\r", "");
            object.getRecords().setProperty("Data", record); 
            object.getSource().data = record.substring(6);
	}

	public boolean isValid(){
	    return isValid; 
	}

	public ActionListener getHelpAction(){
	    return new ActionListener(){
                public void actionPerformed(ActionEvent e){ 
                    if(!iterator.getIsOnline()) 
                        new Help("Help for $DATA record", 
                                 Data.class.getResource("/uw/rfpk/mda/nonmem/help/Data.html"));
                    else
                        Utility.openURL("https://" + iterator.getServerName() + 
                                        ":" + iterator.getServerPort() + "/user/help/Data.html");  
                }
            };
	}
    }
}
