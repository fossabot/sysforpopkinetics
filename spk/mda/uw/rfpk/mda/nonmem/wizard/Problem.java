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
import org.netbeans.ui.wizard.*;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class defines a step to create the $PROBLEm record.
 * @author  Jiaji Du
 */
public class Problem extends javax.swing.JPanel implements WizardStep {
    
    private StepDescriptor sd = new MyStepDescriptor(); 
    private JComponent panel = this;
    private JWizardPane wizardPane = null;
    private boolean isValid = false;
    private boolean isHighlight = false;
    private String PROBLEM = null;
    private MDAIterator iterator = null;
    private DefaultHighlighter highlighter = new DefaultHighlighter();
    private DefaultHighlighter.DefaultHighlightPainter highlight_painter =
            new DefaultHighlighter.DefaultHighlightPainter(new Color(200,200,250));
    
    /** Creates new form PROBLEM.
     * @param iter a MDAIterator object to initialize the field iterator.
     */
    public Problem(MDAIterator iter) {
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

        jTextArea1 = new javax.swing.JTextArea();
        jTextPane1 = new javax.swing.JTextPane();

        setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 24, 12);
        add(jTextArea1, gridBagConstraints);

        jTextPane1.setBackground(new java.awt.Color(204, 204, 204));
        jTextPane1.setText("Enter the title of the problem into the following text area.  \nOnly the first 72 characters of the text are used. ");
        jTextPane1.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        add(jTextPane1, gridBagConstraints);

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
        if(!jTextArea1.getText().equals(""))
        {
            isValid = true;
            wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());
        }
    }//GEN-LAST:event_jTextArea1KeyTyped
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jTextArea1;
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
  	    return "Problem Identifier";
  	}

	public String getStepTitle(){
	    return "Problem Identifier";
	}

	public void showingStep(JWizardPane wizard){
            wizardPane = wizard;
            if(iterator.getIsReload())
            {
                String text = iterator.getReload().getProperty("PROBLEM");
                if(text != null)
                {
                    iterator.getReload().remove("PROBLEM");
                    jTextArea1.setText(text.substring(text.concat(" ").indexOf(" ")).trim());
                    isValid = true;
                    wizardPane.setLeftOptions(wizardPane.getUpdatedLeftOptions().toArray());
                }
            }
            String text = jTextArea1.getText();
            jTextArea1.setCaretPosition(text.length());
            jTextArea1.setHighlighter(highlighter);
            try
            {
                highlighter.addHighlight(0, text.length(), highlight_painter);
                isHighlight = true;
            }
            catch(BadLocationException e) 
            {
                JOptionPane.showMessageDialog(null, e, "BadLocationException", JOptionPane.ERROR_MESSAGE);
            }
            jTextArea1.requestFocusInWindow();
        }
        
	public void hidingStep(JWizardPane wizard){
            if(iterator.getIsBack())
            {
                iterator.setIsBack(false);
                return;
            }            
            if(iterator.getIsBack())
            {
                iterator.setIsBack(false);
                return;
            }
            String record = jTextArea1.getText().replaceAll("\n", "");
            MDAObject object = (MDAObject)wizard.getCustomizedObject();
            object.getRecords().setProperty("Problem", "$PROBLEM " + record);
            object.getSource().problem = record;            
	}

	public boolean isValid(){
	    return isValid;
	}

	public ActionListener getHelpAction(){
	    return new ActionListener(){
                public void actionPerformed(ActionEvent e){ 
                    if(!iterator.getIsOnline()) 
                        new Help("Help for $PROBLEM Record", 
                                 Problem.class.getResource("/uw/rfpk/mda/nonmem/help/Problem.html"));
                    else
                        Utility.openURL("https://" + iterator.getServerName() + 
                                        ":" + iterator.getServerPort() + "/user/help/Problem.html");  
                }
            };
	}
        
        public String getHelpID() {
            return "Prepare_Input__Problem_Identifier";
        }
        
    }
}
