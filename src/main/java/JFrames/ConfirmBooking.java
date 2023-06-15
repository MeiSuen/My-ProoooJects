/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package JFrames;

import Classes.Bookings;
import Classes.Customer;
import static Classes.Customer.readCusFile;
import Classes.Receipts;
import Classes.RoomAvailability;
import java.awt.BorderLayout;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author user
 */
public class ConfirmBooking extends javax.swing.JInternalFrame {

    private Stack<JInternalFrame> frameStack = new Stack<>();
    ArrayList<String> cusList = createCusList();

    public void setCusList(ArrayList<String> cusList) {
        this.cusList = cusList;
    }

    /**
     * Creates new form ConfirmBooking
     */
    public ConfirmBooking(Stack<JInternalFrame> frameStack) {
        this.frameStack = frameStack;
        initComponents();
        initializing();

    }

    private void initializing() {
        //remove border
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 00));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        //add event listener for customer search bar
        textInputChanges();
        chooseCustomer.removeAllItems();

        //populate the customer combo box with all customer recs
        for (String rec : cusList) {
            chooseCustomer.addItem(rec);
        }
        chooseCustomer.setSelectedIndex(-1);
    }

    //setter for the JComboBox
    public void setRoomSelected(ListModel<String> setData) {
        roomSelected.setModel(setData);
    }

    //getter
    public JTextField getChoosenDateIn() {
        return choosenDateIn;
    }

    public JTextField getChoosenDateOut() {
        return choosenDateOut;
    }

    public JList<String> getRoomSelected() {
        return roomSelected;
    }

    public JTextField getNightsStayed() {
        return nightsStayed;
    }

    public JTextField getRoomCharges() {
        return roomCharges;
    }

    public JTextField getsTax() {
        return sTax;
    }

    public JTextField gettTax() {
        return tTax;
    }

    public JTextField getTotalCharges() {
        return totalCharges;
    }

    public static ArrayList<String> createCusList() {
        ArrayList<String> joinedCusList = new ArrayList<>();
        ArrayList<Customer> customerRec = readCusFile();
        for (Customer record : customerRec) {
            String[] temp = {record.getICpassport(), record.getName()};
            String joinedStr = String.join("-", temp);
            joinedCusList.add(joinedStr);
        }
        return joinedCusList;
    }

    public void performCusSearch() {
        //Remove existing searchResults
        chooseCustomer.removeAllItems();
        String searchKey = searchCustomer.getText().toLowerCase();

        //Show all customer results is no text input
        if (searchKey.isEmpty()) {
            for (String rec : cusList) {
                chooseCustomer.addItem(rec);
            }
            chooseCustomer.setSelectedIndex(-1);
        } else {
            //show matching records by custoemr name or id
            for (String rec : cusList) {
                if (rec.toLowerCase().contains(searchKey)) {
                    chooseCustomer.addItem(rec);
                }
            }
        }
    }

    //add event listener for input changes
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

    public ArrayList<String> getDatesBetween(String dateInStr, String dateOutStr) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date dateIn = null, dateOut = null;
        Calendar calIn = null, calOut = null;
        try {
            dateIn = formatter.parse(dateInStr);
            dateOut = formatter.parse(dateOutStr);
            calIn = Calendar.getInstance();
            calOut = Calendar.getInstance();
        } catch (ParseException ex) {
            Logger.getLogger(ConfirmBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        calIn.setTime(dateIn);
        calOut.setTime(dateOut);
        ArrayList<String> datesBetween = new ArrayList<>();

        //Count from check in date to chceck out date
        while (calIn.getTime().before(calOut.getTime())) {
            //Get the date into string format
            int tempYear, tempMonth, tempDay;
            tempYear = calIn.get(Calendar.YEAR);
            tempMonth = 1 + (calIn.get(Calendar.MONTH));
            tempDay = calIn.get(Calendar.DAY_OF_MONTH);
            String tempDate = (String.valueOf(tempYear)) + (String.valueOf(tempMonth < 10 ? "0" + tempMonth : tempMonth)) + (String.valueOf(tempDay < 10 ? "0" + tempDay : tempDay));

            //Add the dates into the array
            datesBetween.add(tempDate);

            //Increment day by 1
            calIn.add(Calendar.DATE, 1);
        }
        return datesBetween;
    }

    public void addNewCusPopupClosed() {
        chooseCustomer.removeAllItems();
        //refresh cusList after ading new customer record
        setCusList(createCusList());
        //populate the customer combo box with all customer recs
        for (String rec : cusList) {
            chooseCustomer.addItem(rec);
        }
        chooseCustomer.setSelectedIndex(-1);

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
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jScrollPane3 = new javax.swing.JScrollPane();
        roomSelected = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        assCusBtn = new javax.swing.JButton();
        chooseCustomer = new javax.swing.JComboBox<>();
        searchCustomer = new javax.swing.JTextField();
        selCusName = new javax.swing.JTextField();
        selCusIC = new javax.swing.JTextField();
        selCusNum = new javax.swing.JTextField();
        selCusEmail = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        choosenDateIn = new javax.swing.JTextField();
        choosenDateOut = new javax.swing.JTextField();
        nightsStayed = new javax.swing.JTextField();
        selCusName2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        RoomChargesLabel = new javax.swing.JLabel();
        TourismTaxLabel = new javax.swing.JLabel();
        ServiceTaxLabel = new javax.swing.JLabel();
        TotalLabel = new javax.swing.JLabel();
        roomCharges = new javax.swing.JTextField();
        tTax = new javax.swing.JTextField();
        sTax = new javax.swing.JTextField();
        totalCharges = new javax.swing.JTextField();
        confirmBtn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(757, 478));
        setMinimumSize(new java.awt.Dimension(757, 478));
        setPreferredSize(new java.awt.Dimension(757, 478));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel1.setText("<html>Room Selected: <html>");

        roomSelected.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        roomSelected.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        roomSelected.setEnabled(false);
        jScrollPane3.setViewportView(roomSelected);

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel6.setText("Email:");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel2.setText("Customer:");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel3.setText("Name:");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel4.setText("IC/Passport:");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel5.setText("Contact Number:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jLabel5)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(68, 68, 68)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(12, 12, 12))
        );

        assCusBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        assCusBtn.setText("<html>Add New <br>Customer <html>");
        assCusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assCusBtnActionPerformed(evt);
            }
        });

        chooseCustomer.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        chooseCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        chooseCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseCustomerActionPerformed(evt);
            }
        });

        searchCustomer.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
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

        selCusName.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        selCusName.setEnabled(false);

        selCusIC.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        selCusIC.setEnabled(false);

        selCusNum.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        selCusNum.setEnabled(false);

        selCusEmail.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        selCusEmail.setEnabled(false);
        selCusEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selCusEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(selCusNum, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(selCusIC, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selCusName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selCusEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchCustomer, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chooseCustomer, javax.swing.GroupLayout.Alignment.LEADING, 0, 164, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(assCusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(assCusBtn)
                    .addComponent(chooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selCusIC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selCusNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selCusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel7.setText("Date In:");

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel8.setText("Date Out:");

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel9.setText("Nights Stayed:");

        jLabel21.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel21.setText("<html>Customer:<br> Name");

        choosenDateIn.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        choosenDateIn.setEnabled(false);

        choosenDateOut.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        choosenDateOut.setEnabled(false);

        nightsStayed.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        nightsStayed.setEnabled(false);

        selCusName2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        selCusName2.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nightsStayed, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(selCusName2)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(choosenDateIn, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(choosenDateOut))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(choosenDateIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(choosenDateOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(nightsStayed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selCusName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        RoomChargesLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        RoomChargesLabel.setText("Room Charges:");

        TourismTaxLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        TourismTaxLabel.setText("<html>Tourism tax:<br>RM10/night");

        ServiceTaxLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        ServiceTaxLabel.setText("<html>Service Tax:<br> (10%)");

        TotalLabel.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        TotalLabel.setText("Total:");

        roomCharges.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        roomCharges.setEnabled(false);

        tTax.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        tTax.setEnabled(false);

        sTax.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        sTax.setEnabled(false);

        totalCharges.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        totalCharges.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RoomChargesLabel)
                    .addComponent(TourismTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ServiceTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TotalLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sTax, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(tTax, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalCharges)
                    .addComponent(roomCharges, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RoomChargesLabel)
                    .addComponent(roomCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TourismTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ServiceTaxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalLabel)
                    .addComponent(totalCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        confirmBtn.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        confirmBtn.setText("Confirm");
        confirmBtn.setEnabled(false);
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confirmBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmBtn)
                        .addGap(96, 96, 96))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selCusEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selCusEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selCusEmailActionPerformed

    private void searchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCustomerActionPerformed
        // TODO add your handling code here:
        performCusSearch();
    }//GEN-LAST:event_searchCustomerActionPerformed


    private void searchCustomerInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_searchCustomerInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_searchCustomerInputMethodTextChanged

    private void chooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseCustomerActionPerformed
        // TODO add your handling code here:

        if (chooseCustomer.getSelectedItem() != null) {
            //retreive details of customer record selected from matching ID
            JComboBox<String> selected = chooseCustomer;
            String selectedRec = (String) selected.getSelectedItem();
            String[] ICpAndName = selectedRec.split("-");
            String cusICp = ICpAndName[0];
            Customer result = Customer.searchCus(cusICp);

            //populating customer data
            String Name = result.getName();
            String ICpassport = result.getICpassport();
            String PhoneNum = result.getPhoneNum();
            String Email = result.getEmail();

            //setting the text fields with data
            selCusName.setText(Name);
            selCusName2.setText(Name);
            selCusIC.setText(ICpassport);
            selCusNum.setText(PhoneNum);
            selCusEmail.setText(Email);

            confirmBtn.setEnabled(true);

        } else {
            selCusName.setText("N/A");
            selCusName2.setText("N/A");
            selCusIC.setText("N/A");
            selCusNum.setText("N/A");
            selCusEmail.setText("N/A");

            confirmBtn.setEnabled(false);
        }
    }//GEN-LAST:event_chooseCustomerActionPerformed

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed

        JComboBox<String> customer = chooseCustomer;
        if (chooseCustomer.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please select a customer record first.");
        } else {
            //get the needed data
            String newBId = Bookings.IncremetnBId();
            String CheckInDate = choosenDateIn.getText();
            String CheckOutDate = choosenDateOut.getText();
            String CusId = selCusIC.getText();
            String roomC = roomCharges.getText();
            String tourismTax = tTax.getText();
            String serviceTax = sTax.getText();
            String total = totalCharges.getText();

            //add all booked room Ids to one string Eg: 101,102,103
            ListModel<String> rooms = roomSelected.getModel();
            int roomsSize = rooms.getSize();
            String roomIds = "";
            for (int i = 0; i < roomsSize; i++) {
                roomIds += rooms.getElementAt(i);
                if (i < roomsSize - 1) {
                    roomIds += ",";
                }
            }
            String[] bookingsRec = {newBId, CheckInDate, CheckOutDate, roomIds, CusId, total};
            boolean addStatus = Bookings.addBookingToDb(bookingsRec);

            //add records to Room Availability file
            String dateIn = choosenDateIn.getText();
            String dateOut = choosenDateOut.getText();
            ArrayList<String> datesBetween = getDatesBetween(dateIn, dateOut);

            //loop through rooms selected
            boolean allSuccess = true;
            for (int i = 0; i < roomsSize; i++) {
                //loop thorugh each date
                for (String date : datesBetween) {
                    String[] roomAvailabilityRec = {date, rooms.getElementAt(i), newBId};
                    boolean partialSuccess = RoomAvailability.addRAToDb(roomAvailabilityRec);
                    if (!partialSuccess) {
                        allSuccess = false;
                    }
                }
            }

            //add record to Receipts file
            boolean receiptSuccess = false;
            LocalDate today = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            String todayDateStr = today.format(dateFormat);
            String[] newReceipt = {newBId, CusId, todayDateStr, roomIds, roomC, tourismTax, serviceTax, total};
            receiptSuccess = Receipts.addReceiptToDb(newReceipt);

            //check if success
            if (addStatus && allSuccess && receiptSuccess) {
                JOptionPane.showMessageDialog(null, "New Booking and Room Availability records successfully saved to database.");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to add record.");
            }

            //resest to BookARoom window
            JDesktopPane ws = getDesktopPane();
            BookARoom targetFrame = new BookARoom();
            ws.setLayout(new BorderLayout());
            ws.removeAll();
            ws.add(targetFrame, BorderLayout.CENTER);
            targetFrame.setVisible(true);
        }
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void assCusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assCusBtnActionPerformed
        // TODO add your handling code here:
        ConfirmBooking parentInstance = this;
        Component parentComponent = this;
        AddNewCusPopup popup = new AddNewCusPopup(parentComponent, true, parentInstance, null, null);
        popup.setVisible(true);
        popup.setLocationRelativeTo(null);

    }//GEN-LAST:event_assCusBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JInternalFrame previousFrame = frameStack.pop();
        JDesktopPane ws = getDesktopPane();
        ws.removeAll();
        ws.add(previousFrame);
        previousFrame.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel RoomChargesLabel;
    private javax.swing.JLabel ServiceTaxLabel;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JLabel TourismTaxLabel;
    private javax.swing.JButton assCusBtn;
    private javax.swing.JComboBox<String> chooseCustomer;
    private javax.swing.JTextField choosenDateIn;
    private javax.swing.JTextField choosenDateOut;
    private javax.swing.JButton confirmBtn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nightsStayed;
    private javax.swing.JTextField roomCharges;
    private javax.swing.JList<String> roomSelected;
    private javax.swing.JTextField sTax;
    private javax.swing.JTextField searchCustomer;
    private javax.swing.JTextField selCusEmail;
    private javax.swing.JTextField selCusIC;
    private javax.swing.JTextField selCusName;
    private javax.swing.JTextField selCusName2;
    private javax.swing.JTextField selCusNum;
    private javax.swing.JTextField tTax;
    private javax.swing.JTextField totalCharges;
    // End of variables declaration//GEN-END:variables
}
