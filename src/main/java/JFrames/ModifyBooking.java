/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package JFrames;

import Classes.Bookings;
import static Classes.Bookings.calculateCharges;
import static Classes.Bookings.getDatesBetween;
import Classes.Customer;
import Classes.Receipts;
import Classes.RoomAvailability;
import static JFrames.ConfirmBooking.createCusList;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author user
 */
public class ModifyBooking extends javax.swing.JDialog {

    ArrayList<String> cusList = createCusList();
    String oriIn, oriOut;
    private ManageBookings manageBookingInstance;

    /**
     * Creates new form ModifyBooking
     */
    public ModifyBooking(Component parentComponent, boolean modal, String oriDateIn, String oriDateOut, ManageBookings MBparentInstance) {
        super();
        initComponents();
        //add event listener for customer search
        textInputChanges();
        //populate the customer combo box
        cusIDComboBox.removeAllItems();
        performCusSearch();
        //get original booked dates
        oriIn = oriDateIn;
        oriOut = oriDateOut;
        manageBookingInstance = MBparentInstance;
    }

    public void performCusSearch() {
        //Remove existing searchResults
        cusIDComboBox.removeAllItems();
        String searchKey = searchCustomer.getText().toLowerCase();

        //Show all customer results is no text input
        if (searchKey.isEmpty()) {
            for (String rec : cusList) {
                cusIDComboBox.addItem(rec);
            }
        } else {
            //show matching records by custoemr name or id
            for (String rec : cusList) {
                if (rec.toLowerCase().contains(searchKey)) {
                    cusIDComboBox.addItem(rec);
                }
            }
        }
    }

    public void addNewCusPopupClosed() {
        cusIDComboBox.removeAllItems();
        //refresh cusList after ading new customer record
        setCusList(createCusList());
        //populate the customer combo box with all customer recs
        for (String rec : cusList) {
            cusIDComboBox.addItem(rec);
        }
        cusIDComboBox.setSelectedIndex(-1);
    }

    public void setCusList(ArrayList<String> cusList) {
        this.cusList = cusList;
    }

    private void textInputChanges() {
        searchCustomer.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                createCusList();
                performCusSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                createCusList();
                performCusSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                createCusList();
                performCusSearch();
            }
        });

    }

    public void saveModifiedToDB() {
        //DELELTE MATCHING BOOKING ID REC
        String BId = BookingIDTextField.getText();
        boolean BDeleted = Bookings.deleteRec(BId);
        boolean RADeleted = RoomAvailability.deleteRec(BId);
        boolean RDeleted = Receipts.deleteRec(BId);

        //ADD NEW RECORDS
        if (BDeleted && RADeleted && RDeleted) {
            //get the needed data
            String oriBId = BookingIDTextField.getText();

            //get dates
            Date dateIn = whatDateIn.getDate();
            Date dateOut = whatDateOut.getDate();
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
            String formattedDateIn = myFormat.format(dateIn);
            String formattedDateOut = myFormat.format(dateOut);

            //get custoemr IC/Passport
            JComboBox<String> selected = cusIDComboBox;
            String selectedCus = (String) selected.getSelectedItem();
            String[] ICpAndName = selectedCus.split("-");
            String cusICp = ICpAndName[0];
            Customer result = Customer.searchCus(cusICp);
            String CusId = result.getICpassport();

            //get rooms
            JList<String> rooms = whatRoomList;
            ArrayList<String> selectedRooms = (ArrayList<String>) rooms.getSelectedValuesList();
            int roomsSize = selectedRooms.size();
            String roomIds = "";
            for (int i = 0; i < roomsSize; i++) {
                roomIds += selectedRooms.get(i);
                if (i < roomsSize - 1) {
                    roomIds += ",";
                }
            }

            //get charges
            String[] charges = calculateCharges(dateIn, dateOut,roomsSize);
            //ArrayLIst<String> charges = 
            //[0-nights, 1-roomCharges, 2-tourismTax, 3-serviceTax, 4-totalCharges]
            String roomC = charges[1];
            String tourismTax = charges[2];
            String serviceTax = charges[3];
            String total = charges[4];
            
            
            //add new record into Bookings.txt
            String[] newBookingsRec = {oriBId, formattedDateIn, formattedDateOut, roomIds, CusId, total};
            boolean addStatus = Bookings.addBookingToDb(newBookingsRec);
            
            //add new records into RoomAvailability.txt
            ArrayList<String> datesBetween = getDatesBetween(dateIn, dateOut);
            //loop through rooms selected
            boolean allSuccess = true;
            for (int i = 0; i < roomsSize; i++) {
                //loop thorugh each date
                for (String date : datesBetween) {
                    String[] roomAvailabilityRec = {date, selectedRooms.get(i), oriBId};
                    boolean partialSuccess = RoomAvailability.addRAToDb(roomAvailabilityRec);
                    if (!partialSuccess) {
                        allSuccess = false;
                    }
                }
            }
            
            //add new records into Receipts.txt
            boolean receiptSuccess = false;
            LocalDate today = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            String todayDateStr = today.format(dateFormat);
            String[] newReceipt = {oriBId, CusId, todayDateStr, roomIds, roomC, tourismTax, serviceTax, total};
            receiptSuccess = Receipts.addReceiptToDb(newReceipt);
            
            //check if success
            if (addStatus && allSuccess && receiptSuccess) {
                JOptionPane.showMessageDialog(null, "New Booking and Room Availability records successfully saved to database.");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to add record.");
            }
            
            ModifyBooking thisDialog = this;
            manageBookingInstance.refreshBookingsTable();
            thisDialog.dispose();
        }

    }

    public JTextField getBookingIDTextField() {
        return BookingIDTextField;
    }

    public JTextField getTotalTextField() {
        return totalTextField;
    }

    public JDateChooser getWhatDateIn() {
        return whatDateIn;
    }

    public JDateChooser getWhatDateOut() {
        return whatDateOut;
    }

    public JList<String> getWhatRoomList() {
        return whatRoomList;
    }

    public JComboBox<String> getCusIDComboBox() {
        return cusIDComboBox;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        BookingIDTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        totalTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        whatDateIn = new com.toedter.calendar.JDateChooser();
        whatDateOut = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        whatRoomList = new javax.swing.JList<>();
        checkRooms = new javax.swing.JButton();
        saveChanges = new javax.swing.JButton();
        cusIDComboBox = new javax.swing.JComboBox<>();
        searchCustomer = new javax.swing.JTextField();
        adCusBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("Booking ID:");

        BookingIDTextField.setEditable(false);
        BookingIDTextField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        BookingIDTextField.setEnabled(false);
        BookingIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookingIDTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setText("Check In Date:");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel3.setText("Check Out Date:");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel4.setText("Room ID:");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel5.setText("Customer:");

        totalTextField.setEditable(false);
        totalTextField.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        totalTextField.setEnabled(false);
        totalTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalTextFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel6.setText("Total:");

        whatRoomList.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        whatRoomList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(whatRoomList);

        checkRooms.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        checkRooms.setText("Check");
        checkRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRoomsActionPerformed(evt);
            }
        });

        saveChanges.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        saveChanges.setText("Save");
        saveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesActionPerformed(evt);
            }
        });

        cusIDComboBox.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        cusIDComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cusIDComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cusIDComboBoxActionPerformed(evt);
            }
        });

        searchCustomer.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        searchCustomer.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                searchCustomerInputMethodTextChanged(evt);
            }
        });
        searchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCustomerActionPerformed(evt);
            }
        });

        adCusBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        adCusBtn.setText("<html>Add New <br>Customer <html>");
        adCusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adCusBtnActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel7.setText("Modify Booking");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(16, 16, 16))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BookingIDTextField)
                            .addComponent(searchCustomer)
                            .addComponent(cusIDComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(whatDateIn, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(whatDateOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adCusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkRooms))
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(saveChanges))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jLabel7)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(BookingIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cusIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adCusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(whatDateIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(whatDateOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkRooms))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(saveChanges)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BookingIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookingIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookingIDTextFieldActionPerformed

    private void totalTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalTextFieldActionPerformed

    private void checkRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRoomsActionPerformed
        //get the needed dates between
        Date dateIn = whatDateIn.getDate();
        Date dateOut = whatDateOut.getDate();
        ArrayList<String> newDatesNeeded = getDatesBetween(dateIn, dateOut);

        //find rooms that are not available
        String BId = BookingIDTextField.getText();
        ArrayList<String> notAvailable = RoomAvailability.checkAvailability(newDatesNeeded, BId);

        //Get the room IDs into an ArrayList for iteration
        String[] rooms = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210"};
        ArrayList<String> allRooms = new ArrayList<>();
        for (String room : rooms) {
            allRooms.add(room);
        }

        //Remove room ID if it is not Available
        Iterator<String> checkRoom = allRooms.iterator();
        while (checkRoom.hasNext()) {
            String roomID = checkRoom.next();
            if (notAvailable.contains(roomID)) {
                checkRoom.remove();
            }
        }
        DefaultListModel<String> roomListModel = (DefaultListModel<String>) whatRoomList.getModel();
        roomListModel.clear();
        for (String updatedRoom : allRooms) {
            roomListModel.addElement(updatedRoom);
        }

    }//GEN-LAST:event_checkRoomsActionPerformed

    private void searchCustomerInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_searchCustomerInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_searchCustomerInputMethodTextChanged

    private void searchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCustomerActionPerformed
        // TODO add your handling code here:
        performCusSearch();
    }//GEN-LAST:event_searchCustomerActionPerformed

    private void cusIDComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cusIDComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cusIDComboBoxActionPerformed

    private void adCusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adCusBtnActionPerformed
        // TODO add your handling code here:
        ModifyBooking parentInstance = this;
        Component parentComponent = this;
        AddNewCusPopup popup = new AddNewCusPopup(parentComponent, true, null, null, parentInstance);
        popup.setVisible(true);
        popup.setLocationRelativeTo(null);
    }//GEN-LAST:event_adCusBtnActionPerformed

    private void saveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesActionPerformed
        saveModifiedToDB();
    }//GEN-LAST:event_saveChangesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModifyBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModifyBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModifyBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModifyBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModifyBooking dialog = new ModifyBooking(new javax.swing.JFrame(), true, null, null,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BookingIDTextField;
    private javax.swing.JButton adCusBtn;
    private javax.swing.JButton checkRooms;
    private javax.swing.JComboBox<String> cusIDComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveChanges;
    private javax.swing.JTextField searchCustomer;
    private javax.swing.JTextField totalTextField;
    private com.toedter.calendar.JDateChooser whatDateIn;
    private com.toedter.calendar.JDateChooser whatDateOut;
    private javax.swing.JList<String> whatRoomList;
    // End of variables declaration//GEN-END:variables
}
