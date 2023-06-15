/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import static Classes.Bookings.readBookingFile;
import static Classes.Files.fileToArray;
import static Classes.Files.overwriteFile;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class Receipts {

    String BId, cusICp, dateBooked, roomID;
    double roomCharges, tTax, sTax, total;

    public Receipts(String Bid, String cusICp, String dateBooked, String roomID, double roomCharges, double tTax, double sTax, double total) {
        this.BId = Bid;
        this.cusICp = cusICp;
        this.dateBooked = dateBooked;
        this.roomID = roomID;
        this.roomCharges = roomCharges;
        this.tTax = tTax;
        this.sTax = sTax;
        this.total = total;
    }

    public static ArrayList<Receipts> readReceiptsFile() {
        ArrayList<String[]> receipt = fileToArray(Files.receiptsFile);
        ArrayList<Receipts> receiptsList = new ArrayList<>();

        //store data into objects
        for (String[] rec : receipt) {
            double doub4 = Double.parseDouble(rec[4]);
            double doub5 = Double.parseDouble(rec[5]);
            double doub6 = Double.parseDouble(rec[6]);
            double doub7 = Double.parseDouble(rec[7]);
            Receipts tempReceipts = new Receipts(rec[0], rec[1], rec[2], rec[3], doub4, doub5, doub6, doub7);
            receiptsList.add(tempReceipts);
        }
        return receiptsList;
    }
    
    public static ArrayList<String[]> createReceiptsList() {
        ArrayList<Receipts> receiptRecs = readReceiptsFile();
        ArrayList<String[]> receiptList = new ArrayList<>();
        for (Receipts receiptRec : receiptRecs) {
            String tempBId = receiptRec.getBId();
            String tempCusICp = receiptRec.getCusICp();
            String tempDateBooked = receiptRec.getDateBooked();
            String tempRoomId = receiptRec.getRoomID();
            String tempRoomCharges = Double.toString(receiptRec.getRoomCharges());
            String tempTTaxx = Double.toString(receiptRec.gettTax());
            String tempSTaxx = Double.toString(receiptRec.getsTax());
            String tempTotal = Double.toString(receiptRec.getTotal());

            String[] tempData = {tempBId, tempCusICp, tempDateBooked, tempRoomId, tempRoomCharges, tempTTaxx,tempSTaxx,tempTotal};
            receiptList.add(tempData);
        }
        return receiptList;
    }

    public static boolean addReceiptToDb(String[] newReceipt) {
        boolean addStatus;
        addStatus = Files.appendFile(Files.receiptsFile, newReceipt);
        return addStatus;
    }

    public static boolean updateReceiptsRecsToDB(ArrayList<Receipts> newReceiptsRecs) {
        ArrayList<String[]> newReceiptsRecsStr = new ArrayList<>();
        for (Receipts rec : newReceiptsRecs) {
            String tempBId = rec.getBId();
            String tempCusICp = rec.getCusICp();
            String tempDateBooked = rec.getDateBooked();
            String tempRoomId = rec.getRoomID();
            String tempRoomCharges = Double.toString(rec.getRoomCharges());
            String tempTTaxx = Double.toString(rec.gettTax());
            String tempSTaxx = Double.toString(rec.getsTax());
            String tempTotal = Double.toString(rec.getTotal());
            
            String[] tempStrRec = {tempBId, tempCusICp, tempDateBooked, tempRoomId, tempRoomCharges, tempTTaxx,tempSTaxx,tempTotal};
            newReceiptsRecsStr.add(tempStrRec);
        }
        boolean success = overwriteFile(Files.receiptsFile, newReceiptsRecsStr                                                                                                                              );
        return success;
    }

    public static boolean deleteRec(String BId) {
        boolean deleted = false;
        //delete from text file
        ArrayList<Receipts> allReceipts = readReceiptsFile();
        ListIterator<Receipts> receiptsIterator = allReceipts.listIterator();
        while (receiptsIterator.hasNext()) {
            Receipts receiptRec = receiptsIterator.next();
            //remove the record from array list
            if (receiptRec.getBId().equals(BId)) {
                receiptsIterator.remove();
                deleted = updateReceiptsRecsToDB(allReceipts);
                break;
            }
        }
        return deleted;
    }

    public String getBId() {
        return BId;
    }

    public void setBId(String Bid) {
        this.BId = Bid;
    }

    public String getCusICp() {
        return cusICp;
    }

    public void setCusICp(String cusICp) {
        this.cusICp = cusICp;
    }

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public double getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public double gettTax() {
        return tTax;
    }

    public void settTax(double tTax) {
        this.tTax = tTax;
    }

    public double getsTax() {
        return sTax;
    }

    public void setsTax(double sTax) {
        this.sTax = sTax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
