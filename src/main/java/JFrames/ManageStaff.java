/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package JFrames;

import Classes.staff;
import static Classes.staff.readStaffFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ManageStaff extends javax.swing.JInternalFrame {

    private ArrayList<staff> staffRecs = readStaffFile();

    /**
     * Creates new form CustomerRecords
     */
    public ManageStaff() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 00));
        BasicInternalFrameUI MCui = (BasicInternalFrameUI) this.getUI();
        MCui.setNorthPane(null);
        //populate the table
        AddStaffTable();
        //add event listener for selected in the table
        addStaffJTableEvent();
        //add event listener for search text field
        searchTextInputChanges();
    }

    public void AddStaffTable() {
        staffRecs = readStaffFile();
        DefaultTableModel staffTable = (DefaultTableModel) staffJTable.getModel();
        staffTable.setRowCount(0);
        for (staff staffRec : staffRecs) {
            String ID = staffRec.getId();
            String pw = staffRec.getPassword();
            String name = staffRec.getName();

            if (!ID.equals("owner")) {
                String[] tempData = {ID, pw, name};
                staffTable.addRow(tempData);
            }
        }
    }

    public void addStaffJTableEvent() {
        staffJTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                staffJTableRowSelected();
            }
        });
    }

    private void staffJTableRowSelected() {
        //get selected customer row
        int selectedRow = staffJTable.getSelectedRow();

        if (selectedRow != -1) {
            //get the ID
            String selectedstaffID = (String) staffJTable.getValueAt(selectedRow, 0);
            for (staff staffRec : staffRecs) {
                if (staffRec.getId().equals(selectedstaffID)) {
                    String tempID = staffRec.getId();
                    String tempPw = staffRec.getPassword();
                    String tempName = staffRec.getName();
                    modifyID.setText(tempID);
                    modifyPw.setText(tempPw);
                    modifyName.setText(tempName);
                    break;
                }
            }
        } else {
            modifyID.setText("N/A");
            modifyPw.setText("N/A");
            modifyName.setText("N/A");
        }
    }

    private void searchTextInputChanges() {
        searchStaffTable.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performTableSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performTableSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performTableSearch();
            }
        });

    }

    private void performTableSearch() {
        DefaultTableModel staffTable = (DefaultTableModel) staffJTable.getModel();
        staffTable.setRowCount(0);
        String searchKey = searchStaffTable.getText().toLowerCase();

        //get results
        if (searchKey.isEmpty()) {
            AddStaffTable();
        } else {
            //show matching records
            for (staff staffRec : staffRecs) {
                String tempstaffRecStr = staffRec.getId() + staffRec.getPassword() + staffRec.getName();
                if (tempstaffRecStr.toLowerCase().contains(searchKey)) {
                    String[] matchingData = {staffRec.getId(), staffRec.getPassword(), staffRec.getName()};
                    staffTable.addRow(matchingData);
                }
            }
        }
    }

    private void updateStaffRec() {
        String tempID = modifyID.getText();
        String tempPw = modifyPw.getText();
        String tempName = modifyName.getText();
        staff toChange = new staff(tempID, tempPw, tempName);
        boolean updated = toChange.modifyStaffRec(toChange);
        if (updated) {
            JOptionPane.showMessageDialog(null, "Staff Rec updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to make changes...");
        }
        AddStaffTable();
    }

    private void deleteStaffRec() {
        String tempID = modifyID.getText();
        String tempPw = modifyPw.getText();
        String tempName = modifyName.getText();
        staff toDelete = new staff(tempID, tempPw, tempName);
        boolean deleted = toDelete.deleteFromStaffRecs(toDelete);
        if (deleted) {
            JOptionPane.showMessageDialog(null, "Staff Record deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to make changes...");
        }
        AddStaffTable();
    }

    private void addNewStaffRec() {
        String tempID = addID.getText();
        String tempPw = addPw.getText();
        String tempName = addName.getText();
        staff newStaff = new staff(tempID, tempPw, tempName);
        boolean added = newStaff.addNewStaffToRec(newStaff);
        if (added) {
            JOptionPane.showMessageDialog(null, "New staff record added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to make changes...");
        }
        AddStaffTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        staffJTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchStaffTable = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        addPw = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        addID = new javax.swing.JTextField();
        addStaff = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        modifyPw = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        modifyName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        modifyID = new javax.swing.JTextField();
        updateStaff = new javax.swing.JButton();
        deleteStaff = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setMaximumSize(new java.awt.Dimension(757, 478));
        setMinimumSize(new java.awt.Dimension(757, 478));
        setPreferredSize(new java.awt.Dimension(757, 478));

        staffJTable.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        staffJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Password", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        staffJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        staffJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(staffJTable);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Search:");

        searchStaffTable.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        searchStaffTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStaffTableActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Add New Staff"));

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel3.setText("Login Password:");

        addPw.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        addPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPwActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel5.setText("Staff Name:");

        addName.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel2.setText("Staff ID:");

        addID.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        addID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIDActionPerformed(evt);
            }
        });

        addStaff.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        addStaff.setText("Add New");
        addStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStaffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addID, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPw, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(136, 136, 136))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(addName, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(addStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPw, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addStaff)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Modify Staff"));

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel6.setText("Login Password:");

        modifyPw.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        modifyPw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyPwActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel7.setText("Staff Name:");

        modifyName.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel8.setText("Staff ID:");

        modifyID.setEditable(false);
        modifyID.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        modifyID.setEnabled(false);
        modifyID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyIDActionPerformed(evt);
            }
        });

        updateStaff.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        updateStaff.setText("Update");
        updateStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateStaffActionPerformed(evt);
            }
        });

        deleteStaff.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        deleteStaff.setText("Delete");
        deleteStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStaffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(modifyID, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modifyPw, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))
                                .addGap(136, 136, 136))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(modifyName, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(updateStaff)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteStaff)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyPw, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateStaff)
                    .addComponent(deleteStaff))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchStaffTable, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchStaffTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addPwActionPerformed

    private void addStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStaffActionPerformed
        addNewStaffRec();
    }//GEN-LAST:event_addStaffActionPerformed

    private void searchStaffTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStaffTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchStaffTableActionPerformed

    private void addIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addIDActionPerformed

    private void modifyPwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyPwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modifyPwActionPerformed

    private void modifyIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modifyIDActionPerformed

    private void updateStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateStaffActionPerformed
        updateStaffRec();
    }//GEN-LAST:event_updateStaffActionPerformed

    private void deleteStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStaffActionPerformed
        deleteStaffRec();
    }//GEN-LAST:event_deleteStaffActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addID;
    private javax.swing.JTextField addName;
    private javax.swing.JTextField addPw;
    private javax.swing.JButton addStaff;
    private javax.swing.JButton deleteStaff;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField modifyID;
    private javax.swing.JTextField modifyName;
    private javax.swing.JTextField modifyPw;
    private javax.swing.JTextField searchStaffTable;
    private javax.swing.JTable staffJTable;
    private javax.swing.JButton updateStaff;
    // End of variables declaration//GEN-END:variables
}
