/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package JFrames;

import Classes.Customer;
import static Classes.Customer.readCusFile;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ManageCustomer extends javax.swing.JInternalFrame {

    private ArrayList<Customer> cusRecs = readCusFile();

    /**
     * Creates new form CustomerRecords
     */
    public ManageCustomer() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 00));
        BasicInternalFrameUI MCui = (BasicInternalFrameUI) this.getUI();
        MCui.setNorthPane(null);
        AddCusTable();
        addCusJTableEvent();
        searchTextInputChanges();
    }

    public void AddCusTable() {
        DefaultTableModel cusTable = (DefaultTableModel) cusJTable.getModel();
        cusTable.setRowCount(0);
        for (Customer cusRec : cusRecs) {
            String name = cusRec.getName();
            String ICP = cusRec.getICpassport();
            String phoneNum = cusRec.getPhoneNum();
            String email = cusRec.getEmail();

            String[] tempData = {name, ICP, phoneNum, email};
            cusTable.addRow(tempData);
        }
    }

    public void addNewCusPopupClosed() {
        DefaultTableModel cusTable = (DefaultTableModel) cusJTable.getModel();
        cusTable.setRowCount(0);
        cusRecs = readCusFile();
        AddCusTable();
    }

    public void addCusJTableEvent() {
        cusJTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                cusJTableRowSelected();
            }
        });
    }

    private void cusJTableRowSelected() {
        //get selected customer row
        int selectedRow = cusJTable.getSelectedRow();

        if (selectedRow != -1) {
            //get the IC or Passport
            String selectedCusICp = (String) cusJTable.getValueAt(selectedRow, 1);
            for (Customer cus : cusRecs) {
                if ((cus.getICpassport()).equals(selectedCusICp)) {
                    String tempName = cus.getName();
                    String tempICp = cus.getICpassport();
                    String tempNum = cus.getPhoneNum();
                    String tempEmail = cus.getEmail();
                    nameTField.setText(tempName);
                    ICPTField.setText(tempICp);
                    numTField.setText(tempNum);
                    emailTField.setText(tempEmail);
//                    Customer tempSelected = new Customer(tempName, tempICp, tempNum, tempEmail);
                    break;
                }
            }
        } else {
            nameTField.setText("N/A");
            ICPTField.setText("N/A");
            numTField.setText("N/A");
            emailTField.setText("N/A");
        }
    }

    private void searchTextInputChanges() {
        searchCusTable.getDocument().addDocumentListener(new DocumentListener() {
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
        DefaultTableModel cusTable = (DefaultTableModel) cusJTable.getModel();
        cusTable.setRowCount(0);
        String searchKey = searchCusTable.getText().toLowerCase();

        //get results
        if (searchKey.isEmpty()) {
            cusRecs = readCusFile();
            AddCusTable();
        } else {
            //show matching records
            for (Customer rec : cusRecs) {
                String tempCusRecStr = rec.getName() + rec.getICpassport() + rec.getPhoneNum() + rec.getEmail();
                if (tempCusRecStr.toLowerCase().contains(searchKey)) {
                    String[] matchingData = {rec.getName(), rec.getICpassport(), rec.getPhoneNum(), rec.getEmail()};
                    cusTable.addRow(matchingData);
                }
            }
        }
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
        cusJTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchCusTable = new javax.swing.JTextField();
        AddNewCusbBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        emailTField = new javax.swing.JTextField();
        ICPTField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        numTField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nameTField = new javax.swing.JTextField();
        updateCusBtn = new javax.swing.JButton();
        deleteCusBtn = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setMaximumSize(new java.awt.Dimension(757, 478));
        setMinimumSize(new java.awt.Dimension(757, 478));
        setPreferredSize(new java.awt.Dimension(757, 478));

        cusJTable.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cusJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer Name", "IC/Passport", "Phone No", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cusJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cusJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(cusJTable);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Search:");

        searchCusTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        searchCusTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCusTableActionPerformed(evt);
            }
        });

        AddNewCusbBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        AddNewCusbBtn.setText("Add New");
        AddNewCusbBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewCusbBtnActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Update Selected Record"));

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel4.setText("Email:");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel3.setText("IC/Passport:");

        emailTField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        emailTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTFieldActionPerformed(evt);
            }
        });

        ICPTField.setEditable(false);
        ICPTField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        ICPTField.setEnabled(false);
        ICPTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICPTFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel5.setText("Phone Number:");

        numTField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setText("Customer Name:");

        nameTField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        nameTField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTFieldActionPerformed(evt);
            }
        });

        updateCusBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        updateCusBtn.setText("Update");
        updateCusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCusBtnActionPerformed(evt);
            }
        });

        deleteCusBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        deleteCusBtn.setText("Delete");
        deleteCusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCusBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 61, Short.MAX_VALUE)
                .addComponent(updateCusBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteCusBtn)
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameTField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ICPTField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(emailTField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(numTField, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ICPTField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numTField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailTField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteCusBtn)
                    .addComponent(updateCusBtn))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchCusTable, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddNewCusbBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCusTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(AddNewCusbBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ICPTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICPTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICPTFieldActionPerformed

    private void emailTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTFieldActionPerformed

    private void updateCusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCusBtnActionPerformed
        //retreive input
        String name, ICp, phoneNum, email;
        name = nameTField.getText();
        ICp = ICPTField.getText();
        phoneNum = numTField.getText();
        email = emailTField.getText();

        //VALIDATE INPUTS
        boolean isEmpty = false;
        Color errorColor = Color.decode("#FE876D");
        //check for empty inputs
        ArrayList<JTextField> inputs = new ArrayList<>();
        inputs.add(nameTField);
        inputs.add(ICPTField);
        inputs.add(numTField);
        inputs.add(emailTField);

        for (JTextField element : inputs) {
            element.setBorder(new LineBorder(null));
            if (element.getText().isEmpty()) {
                element.setBorder(new LineBorder(Color.decode("#FE876D")));
                isEmpty = true;
            }
        }
        if (isEmpty) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            //stop execution
            return;
        }

        //CHECK FOR WRONG FORMAT
        boolean error = false;
        //validate name input
        String pattern = "^[a-zA-Z ]+$";
        String errorMssg = "";
        if (!name.matches(pattern)) {
            errorMssg += "--Invalid Customer Name. Only alphabets allowed.\n";
            error = true;
            nameTField.setBorder(new LineBorder(Color.decode("#FE876D")));
        }

        //validate phone number input
        if (!phoneNum.matches("\\d+")) {
            errorMssg += "--Invalid phone number, phone number can only consist of numbers, without hyphens(-) or plus(+).\n";
            error = true;
            numTField.setBorder(new LineBorder(Color.decode("#FE876D")));
        }

        //validate email input
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            errorMssg += "--Invalid email address, please enter email with correct format.\n";
            error = true;
            emailTField.setBorder(new LineBorder(Color.decode("#FE876D")));
        }

        //error message foR invalid input
        if (error) {
            JOptionPane.showMessageDialog(null, errorMssg);
            return;
        }

        //update into text file
        Customer toChange = new Customer(name, ICp, phoneNum, email);
        boolean success = toChange.modifyCusRecs(toChange);
        if (success) {
            JOptionPane.showMessageDialog(null, "Customer Record updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to make changes...");
        }
        //refresh Customer table
        cusRecs = readCusFile();
        AddCusTable();
    }//GEN-LAST:event_updateCusBtnActionPerformed

    private void AddNewCusbBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewCusbBtnActionPerformed
        // TODO add your handling code here:
        ManageCustomer parentInstance = this;
        Component parentComponent = this;
        AddNewCusPopup popup = new AddNewCusPopup(parentComponent, true, null, parentInstance, null);
        popup.setVisible(true);
        popup.setLocationRelativeTo(null);
    }//GEN-LAST:event_AddNewCusbBtnActionPerformed

    private void searchCusTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCusTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchCusTableActionPerformed

    private void deleteCusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCusBtnActionPerformed

        //retreive input from textFields
        String name, ICp, phoneNum, email;
        name = nameTField.getText();
        ICp = ICPTField.getText();
        phoneNum = numTField.getText();
        email = emailTField.getText();

        //delete customer rec from file
        Customer toDelete = new Customer(name, ICp, phoneNum, email);
        boolean dltSuccess = toDelete.deleteFromCusRecs(toDelete);
        if (dltSuccess) {
            JOptionPane.showMessageDialog(null, "Customer record deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to save changes, changes discarded! Please contact the developer.");
        }
        //update the cusRecs Array List with the updated customer records
        cusRecs = readCusFile();
        AddCusTable();
    }//GEN-LAST:event_deleteCusBtnActionPerformed

    private void nameTFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNewCusbBtn;
    private javax.swing.JTextField ICPTField;
    private javax.swing.JTable cusJTable;
    private javax.swing.JButton deleteCusBtn;
    private javax.swing.JTextField emailTField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameTField;
    private javax.swing.JTextField numTField;
    private javax.swing.JTextField searchCusTable;
    private javax.swing.JButton updateCusBtn;
    // End of variables declaration//GEN-END:variables
}
