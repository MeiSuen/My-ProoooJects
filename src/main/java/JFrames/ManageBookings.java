/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package JFrames;

import Classes.Bookings;
import static Classes.Bookings.createBookingsList;
import static Classes.Bookings.readBookingFile;
import Classes.Receipts;
import Classes.RoomAvailability;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
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
public class ManageBookings extends javax.swing.JInternalFrame {

    private ArrayList<Bookings> bookingRecs = readBookingFile();
    private ArrayList<String[]> bookingList = new ArrayList<>();

    /**
     * Creates new form CustomerRecords
     */
    public ManageBookings() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 00));
        BasicInternalFrameUI MCui = (BasicInternalFrameUI) this.getUI();
        MCui.setNorthPane(null);
        AddBookingsTable();
        searchTextInputChanges();
    }

    public void AddBookingsTable() {
        DefaultTableModel receiptsTable = (DefaultTableModel) bookingsJTable.getModel();
        receiptsTable.setRowCount(0);
        bookingList = createBookingsList();
        for (String[] rec : bookingList) {
            receiptsTable.addRow(rec);
        }
        //sort by descending order of Boking ID
        DefaultTableModel tableModel = (DefaultTableModel) bookingsJTable.getModel();
        TableRowSorter<DefaultTableModel> sortDescBId = new TableRowSorter<>(tableModel);
        bookingsJTable.setRowSorter(sortDescBId);
        sortDescBId.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING)));

    }

    private void searchTextInputChanges() {
        searchBookingsTable.getDocument().addDocumentListener(new DocumentListener() {
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
        DefaultTableModel receiptsTable = (DefaultTableModel) bookingsJTable.getModel();
        receiptsTable.setRowCount(0);
        String searchKey = searchBookingsTable.getText().toLowerCase();
        bookingList = createBookingsList();

        //get results
        if (searchKey.isEmpty()) {
            bookingRecs = readBookingFile();
            AddBookingsTable();
        } else {
            //show matching records
            for (String[] rec : bookingList) {
                String tempBookingRecStr = String.join("", rec);
                if (tempBookingRecStr.toLowerCase().contains(searchKey)) {
                    receiptsTable.addRow(rec);
                }
            }
        }
    }

    public void modifyBtnClicked(Bookings selectedRec) {
        
        String dateIn = selectedRec.getDateIn();
        String dateOut = selectedRec.getDateOut();
        Component parentComponent = this;
        ManageBookings parentInstance = this;
        ModifyBooking popup = new ModifyBooking(parentComponent, true, dateIn, dateOut, parentInstance);
        popup.setVisible(true);
        popup.setLocationRelativeTo(null);

        //set the fields with ori data from selected Bookings record
        ///set booking ID
        popup.getBookingIDTextField().setText(selectedRec.getId());

        //set total
        String totalStr = Double.toString(selectedRec.getTotal());
        popup.getTotalTextField().setText(totalStr);

        //set Check In Date and Check Out Date
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        Date checkInDate = null;
        Date checkOutDate = null;
        try {
            checkInDate = myFormat.parse(dateIn);
            checkOutDate = myFormat.parse(dateOut);
        } catch (ParseException ex) {
            Logger.getLogger(ManageBookings.class.getName()).log(Level.SEVERE, null, ex);
        }
        popup.getWhatDateIn().setDate(checkInDate);
        popup.getWhatDateOut().setDate(checkOutDate);

        //set rooms booked into list, and add selected
        DefaultListModel<String> roomListModel = new DefaultListModel<>();
        String roomIds = selectedRec.getRoomId();
        String[] roomsBooked = roomIds.split(",");
        int[] selectedIndices = new int[roomsBooked.length];
        Integer counter = 0;
        for (String room : roomsBooked) {
            roomListModel.addElement(room);
            selectedIndices[counter] = counter;
            counter++;
        }
        JList<String> roomList = popup.getWhatRoomList();
        roomList.setModel(roomListModel);
        roomList.setSelectedIndices(selectedIndices);

        //set selected customer in combo box
        String cusID = selectedRec.getCusId();
        int selectedIndex = -1;
        JComboBox<String> cusComboBox = popup.getCusIDComboBox();
        for (int i = 0; i < cusComboBox.getItemCount(); i++) {
            String rec = cusComboBox.getItemAt(i);
            if (rec.contains(cusID)) {
                selectedIndex = i;
                break;
            }
        }
        cusComboBox.setSelectedIndex(selectedIndex);
    }

    public void refreshBookingsTable() {
        DefaultTableModel bookingsModel = (DefaultTableModel) bookingsJTable.getModel();
        bookingsModel.setRowCount(0);
        AddBookingsTable();
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
        bookingsJTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchBookingsTable = new javax.swing.JTextField();
        modifyBookingRec = new javax.swing.JButton();
        deleteBookingRec = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        setMaximumSize(new java.awt.Dimension(757, 478));
        setMinimumSize(new java.awt.Dimension(757, 478));
        setPreferredSize(new java.awt.Dimension(757, 478));

        bookingsJTable.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        bookingsJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date In", "Date Out", "Rooms", "Customer ", "Total Charges"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bookingsJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bookingsJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(bookingsJTable);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Search:");

        searchBookingsTable.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        searchBookingsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookingsTableActionPerformed(evt);
            }
        });

        modifyBookingRec.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        modifyBookingRec.setText("Modify");
        modifyBookingRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyBookingRecActionPerformed(evt);
            }
        });

        deleteBookingRec.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        deleteBookingRec.setText("Delete");
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
                .addGap(279, 279, 279)
                .addComponent(modifyBookingRec)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteBookingRec)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBookingsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchBookingsTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifyBookingRec)
                    .addComponent(deleteBookingRec))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBookingsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookingsTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBookingsTableActionPerformed

    private void modifyBookingRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyBookingRecActionPerformed
        int selectedRow = bookingsJTable.getSelectedRow();

        if (selectedRow != -1) {
            //get the IC or Passport
            String selectedBId = (String) bookingsJTable.getValueAt(selectedRow, 0);

            //get the other data
            String BId = null;
            String dateIn = null;
            String dateOut = null;
            String roomId = null;
            String cusId = null;
            double total = 0;
            for (Bookings rec : bookingRecs) {
                if ((rec.getId()).equals(selectedBId)) {
                    BId = rec.getId();
                    dateIn = rec.getDateIn();
                    dateOut = rec.getDateOut();
                    roomId = rec.getRoomId();
                    cusId = rec.getCusId();
                    total = rec.getTotal();
                    break;
                }
            }
            Bookings selectedBookingRec = new Bookings(BId, dateIn, dateOut, roomId, cusId, total);
            //call the modify popup and set the respective textfields
            modifyBtnClicked(selectedBookingRec);
        } else {
            JOptionPane.showMessageDialog(null, "Please choose select a Cookings record from the table first!!!");
        }
    }//GEN-LAST:event_modifyBookingRecActionPerformed

    private void deleteBookingRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookingRecActionPerformed
        int selectedRow = bookingsJTable.getSelectedRow();
        String selectedBId = (String) bookingsJTable.getValueAt(selectedRow, 0);
        boolean BDeleted = Bookings.deleteRec(selectedBId);
        boolean RADeleted = RoomAvailability.deleteRec(selectedBId);
        boolean RDeleted = Receipts.deleteRec(selectedBId);
        if (BDeleted && RADeleted && RDeleted){
            JOptionPane.showMessageDialog(null,"Record deleted successfully!");
        } else{
            JOptionPane.showMessageDialog(null,"Unable to delete record.");
        }
        refreshBookingsTable();
    }//GEN-LAST:event_deleteBookingRecActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookingsJTable;
    private javax.swing.JButton deleteBookingRec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modifyBookingRec;
    private javax.swing.JTextField searchBookingsTable;
    // End of variables declaration//GEN-END:variables
}
