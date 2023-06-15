/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package JFrames;

import Classes.Receipts;
import static Classes.Receipts.createReceiptsList;
import static Classes.Receipts.readReceiptsFile;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author user
 */
public class ViewReceipts extends javax.swing.JInternalFrame {

    private ArrayList<Receipts> receiptRecs = readReceiptsFile();
    private ArrayList<String[]> receiptList = new ArrayList<>();

    /**
     * Creates new form CustomerRecords
     */
    public ViewReceipts() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 00));
        BasicInternalFrameUI MCui = (BasicInternalFrameUI) this.getUI();
        MCui.setNorthPane(null);
        AddReceiptsTable();
        searchTextInputChanges();

    }

    public void AddReceiptsTable() {
        DefaultTableModel receiptsTable = (DefaultTableModel) receiptsJTable.getModel();
        receiptsTable.setRowCount(0);
        receiptList = createReceiptsList();
        for (String[] rec : receiptList) {
            receiptsTable.addRow(rec);
        }
        //sort by descending order of Boking ID
        DefaultTableModel tableModel = (DefaultTableModel) receiptsJTable.getModel();
        TableRowSorter<DefaultTableModel> sortDescBId = new TableRowSorter<>(tableModel);
        receiptsJTable.setRowSorter(sortDescBId);
        sortDescBId.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));

    }

    private void searchTextInputChanges() {
        searchReceiptTable.getDocument().addDocumentListener(new DocumentListener() {
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
        DefaultTableModel receiptsTable = (DefaultTableModel) receiptsJTable.getModel();
        receiptsTable.setRowCount(0);
        String searchKey = searchReceiptTable.getText().toLowerCase();
        receiptList = createReceiptsList();

        //get results
        if (searchKey.isEmpty()) {
            //show all
            AddReceiptsTable();
        } else {
            //show matching records
            for (String[] rec : receiptList) {
                String tempBookingRecStr = String.join("", rec);
                if (tempBookingRecStr.toLowerCase().contains(searchKey)) {
                    receiptsTable.addRow(rec);
                }
            }
        }
    }

    public void openReceiptPopup() {
        //get record of selected row from table
        int selectedRow = receiptsJTable.getSelectedRow();
        String selectedBId = (String) receiptsJTable.getValueAt(selectedRow, 0);
        Receipts target = null;
        for (Receipts rec : receiptRecs) {
            if (rec.getBId().equals(selectedBId)) {
                target = rec;
                break;
            }
        }

        //retrive the data
        String targetBId = target.getBId();
        String taretCusICp = target.getCusICp();
        String targetDate = target.getDateBooked();
        String targetRoomsBooked = target.getRoomID();
        String targetRoomC = Double.toString(target.getRoomCharges());
        String targetTTax = Double.toString(target.gettTax());
        String targetSTax = Double.toString(target.getsTax());
        String targetTotal = Double.toString(target.getTotal());

        //set the data into the Receipt Popup
        Component parentComponent = this;
        ReceiptPopup popup = new ReceiptPopup(parentComponent,true);
        popup.getBId().setText(targetBId);
        popup.getCusICp().setText(taretCusICp);
        popup.getDate().setText(targetDate);
        popup.getRooms().setText(targetRoomsBooked);
        popup.getRoomC().setText(targetRoomC);
        popup.gettTax().setText(targetTTax);
        popup.getsTax().setText(targetSTax);
        popup.getTotal().setText(targetTotal);
        
        //set visible popup
        popup.setVisible(true);
        popup.setLocationRelativeTo(null);
        
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
        receiptsJTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchReceiptTable = new javax.swing.JTextField();
        deleteBookingRec = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setMaximumSize(new java.awt.Dimension(757, 478));
        setMinimumSize(new java.awt.Dimension(757, 478));
        setPreferredSize(new java.awt.Dimension(757, 478));

        receiptsJTable.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        receiptsJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Booking ID", "Customer IC/Passport", "Receipt Date", "Rooms Booked", "Room Charges", "Tourism Tax", "Service Tax", "Total Charges"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        receiptsJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        receiptsJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(receiptsJTable);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Search:");

        searchReceiptTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        searchReceiptTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchReceiptTableActionPerformed(evt);
            }
        });

        deleteBookingRec.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        deleteBookingRec.setText("View Receipt");
        deleteBookingRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookingRecActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchReceiptTable)))
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteBookingRec)
                .addGap(311, 311, 311))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchReceiptTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteBookingRec)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchReceiptTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchReceiptTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchReceiptTableActionPerformed

    private void deleteBookingRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookingRecActionPerformed
        int selectedRow = receiptsJTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a record from the table first!");
        } else {
            openReceiptPopup();
        }
    }//GEN-LAST:event_deleteBookingRecActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteBookingRec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable receiptsJTable;
    private javax.swing.JTextField searchReceiptTable;
    // End of variables declaration//GEN-END:variables
}
