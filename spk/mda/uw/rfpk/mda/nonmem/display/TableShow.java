/*
 * TableShow.java
 *
 * Created on December 17, 2003, 4:15 PM
 */

package uw.rfpk.mda.nonmem.display;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Comparator;
import java.util.Arrays;
import javax.swing.DefaultListModel;

/** This class's instance processes table information and displays and saves tables
 *
 * @author  Jiaji Du
 */
public class TableShow extends javax.swing.JFrame {
    
    /** Creates new form TableShow
     * @param tableAll a String[][][] containing information for all the tables
     * @param dataAll a double[][] containing all pesentation data 
     * @param labelAll an Arraylist object containing the data labels
     * @param dataLabelMap a Properties object containing the data label-alias mapping
     */
    public TableShow(String[][][] tableAll, double[][] dataAll, ArrayList labelAll,
                     Properties dataLabelMap) 
    {
        this.tableAll = tableAll;
        this.dataAll = dataAll;
        this.labelAll = labelAll;
        this.dataLabelMap = dataLabelMap;
       
        // Replace the label name by the alias if exist
        for(int i = 0; i < labelAll.size(); i++)
        {
            String alias = dataLabelMap.getProperty((String)labelAll.get(i));
            if(alias == null)
                alias = (String)labelAll.get(i);  
            aliasAll.add(i, alias);
        }         
    }
    
    /** Display table list window
     */              
    public void showTableList()
    {
        // Display the window
        setSize(500, 500);
        initComponents();
        setVisible(true);

        // Display a table list
        DefaultListModel model = new DefaultListModel();
        jList1.setModel(model);
        for(int i = 0; i < tableAll.length; i++)
        {
            String[][] tableI = tableAll[i];
            String element = "Table " + (i + 1) + ":";
            for(int j = 0; j < tableI[1].length; j++)
            {
                // For item "DV" replace it by the alias
                if(tableI[1][j].equals("DV"))
                    tableI[1][j] = dataLabelMap.getProperty("DV");
                element += " " + tableI[1][j];
            }
            int nSort = Integer.parseInt(tableI[0][3]); 
            if(nSort != 0)
            {
                element += " sorted by";
                for(int k = 0; k < nSort; k++)
                    element += " " + tableI[1][k];
            }
            model.addElement(element);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTextPane1 = new javax.swing.JTextPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Table List");
        setLocationRelativeTo(this);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 200));
        jList1.setFixedCellHeight(15);
        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTextPane1.setEditable(false);
        jTextPane1.setText("Select a table or tables to display");
        getContentPane().add(jTextPane1, java.awt.BorderLayout.NORTH);

        jButton1.setText("Display");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton1, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int[] selectedIndex = jList1.getSelectedIndices();
        if(selectedIndex.length == 1 && selectedIndex[0] < 0)
            return;
        
        // Fill the tables and display
        for(int j = 0; j < selectedIndex.length; j++)
        {
        String[][] tableI = tableAll[selectedIndex[j]]; 
        String[][] data = new String[dataAll.length][tableI[1].length + 1];
        String[] header = new String[tableI[1].length + 1];
        fillTable(tableI, data, header);
        
        // Display the table
        new MatrixShow(data, header, "Table " + (selectedIndex[j] + 1), "Table " + 
                       (selectedIndex[j] + 1), 800, 600, 50 * j, 50 * j, false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /** This function fills data and header into a table
     * @param tableI String[][] containing information for the table
     * @param data String[][] to hold data for the table
     * @param header String[] to hold headers for the table
     */
    public void fillTable(String[][] tableI, String[][] data, String[] header)
    {
        // Get data for the table from the presentation data
        for(int i = 0; i < tableI[1].length; i++)
        {            
            int index = aliasAll.indexOf(tableI[1][i]); 
            for(int j = 0; j < dataAll.length; j++)
                data[j][i + 1] = String.valueOf(dataAll[j][index]);
        }
        
        // Sort the data if it is specified
        if(!tableI[0][3].equals("0"))
            sort(data, Integer.parseInt(tableI[0][3]));
        
        // Get line number
        for(int i = 0; i < dataAll.length; i++)
            data[i][0] = String.valueOf(i + 1);  
        
        // Get column names
        header[0] = "LINE NO.";
        for(int i = 1; i <= tableI[1].length; i++)
            header[i] = tableI[1][i - 1];    
    }
    
    // Sort the data
    private void sort(String[][] data, int nSort)
    {
        int nRow = data.length;
        int nCol = data[0].length - 1;
        if(nRow == 0 || nCol == 0)
            return;
        
        // Convert data from String to double
        double[][] d = new double[nRow][nCol];
        for(int i = 0; i < nRow; i++)
            for(int j = 0; j < nCol; j++)
                d[i][j] = Double.parseDouble(data[i][j + 1]);
        
        // Sort the data
        Arrays.sort(d, new CompareRows(nSort)); 
        
        // Convert data from double to String
        for(int i = 0; i < nRow; i++)
            for(int j = 0; j < nCol; j++)
                data[i][j + 1] = String.valueOf(d[i][j]);
    }
    
    // Class to implements Comparator interface
    private class CompareRows implements Comparator 
    {
        // Constructor
        public CompareRows(int nCompare)
        {
            this.nCompare = nCompare;
        }
        
        // Compare the two rows
        public int compare(Object obj, Object obj1) {
            double[] d1 = (double[])obj;
            double[] d2 = (double[])obj1;
            for(int i = 0; i < nCompare; i++) 
            {
                if(d1[i] < d2[i])
                    return -1;
                if(d1[i] > d2[i])
                    return 1;
            } 
            return 0;
        } 
        
        // Number of the first columns to compare
        private int nCompare;
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
//        System.exit(0);
    }//GEN-LAST:event_exitForm

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
    
    private String[][][] tableAll = null;
    private double[][] dataAll = null; 
    private ArrayList labelAll = null; 
    private ArrayList aliasAll = new ArrayList();
    private Properties dataLabelMap = null;
}
