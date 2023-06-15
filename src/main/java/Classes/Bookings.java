/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import static Classes.Customer.updateCusRecsToDB;
import static Classes.Files.fileToArray;
import static Classes.Files.overwriteFile;
import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class Bookings {

    String id, dateIn, dateOut, roomId, cusId;
    double roomTotal, serviceTax, tourismTax, total;

    public Bookings(String id, String dateIn, String dateOut, String roomId, String cusId, double total) {
        this.id = id;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.roomId = roomId;
        this.cusId = cusId;
        this.total = total;
    }

    public static ArrayList<String[]> createBookingsList() {
        ArrayList<Bookings> bookingRecs = readBookingFile();
        ArrayList<String[]> bookingList = new ArrayList<>();
        for (Bookings bookingRec : bookingRecs) {
            String BId = bookingRec.getId();
            String dateIn = bookingRec.getDateIn();
            String dateOut = bookingRec.getDateOut();
            String roomId = bookingRec.getRoomId();
            String cusId = bookingRec.getCusId();
            Double total = bookingRec.getTotal();
            String totalStr = Double.toString(total);

            String[] tempData = {BId, dateIn, dateOut, roomId, cusId, totalStr};
            bookingList.add(tempData);
        }
        return bookingList;
    }

    public static ArrayList<String> getDatesBetween(Date dateIn, Date dateOut) {
        ArrayList<String> datesNeeded = new ArrayList<>();
        //Count from check in date to check out date
        Calendar counter = Calendar.getInstance();
        counter.setTime(dateIn);
        Calendar ending = Calendar.getInstance();
        ending.setTime(dateOut);

        while (counter.getTime().before(ending.getTime())) {
            //Get the date into string format
            int tempYear, tempMonth, tempDay;
            tempYear = counter.get(Calendar.YEAR);
            tempMonth = 1 + (counter.get(Calendar.MONTH));
            tempDay = counter.get(Calendar.DAY_OF_MONTH);
            String tempDate = (String.valueOf(tempYear)) + (String.valueOf(tempMonth < 10 ? "0" + tempMonth : tempMonth)) + (String.valueOf(tempDay < 10 ? "0" + tempDay : tempDay));

            //Add the dates into the array
            datesNeeded.add(tempDate);

            //Increment day by 1
            counter.add(Calendar.DATE, 1);
        }
        return datesNeeded;
    }

    public static boolean updateBookingsRecsToDB(ArrayList<Bookings> newBookingsRecs) {
        ArrayList<String[]> newBookingsRecsStr = new ArrayList<>();
        for (Bookings rec : newBookingsRecs) {
            String tempBId = rec.getId();
            String tempdateIn = rec.getDateIn();
            String tempdateOut = rec.getDateOut();
            String temproomIds = rec.getRoomId();
            String tempcusID = rec.getCusId();
            String temptotal = Double.toString(rec.getTotal());
            String[] tempStrRec = {tempBId, tempdateIn, tempdateOut, temproomIds, tempcusID, temptotal};
            newBookingsRecsStr.add(tempStrRec);
        }
        boolean success = overwriteFile(Files.bookingsFile, newBookingsRecsStr);
        return success;
    }

    public static boolean deleteRec(String BId) {
        boolean deleted = false;
        //delete from text file
        ArrayList<Bookings> allBookings = readBookingFile();
        ListIterator<Bookings> bookingsIterator = allBookings.listIterator();
        while (bookingsIterator.hasNext()) {
            Bookings bookingRec = bookingsIterator.next();
            //remove the record from array list
            if (bookingRec.getId().equals(BId)) {
                bookingsIterator.remove();
                deleted = updateBookingsRecsToDB(allBookings);
                break;
            }
        }
        return deleted;
    }


    public static String[] calculateCharges(Date dateIn, Date dateOut, int numOfRooms) {
        long betweenDates = ChronoUnit.DAYS.between(dateIn.toInstant(), dateOut.toInstant());
        int nights = (int) betweenDates;
        String nightsStr = Long.toString(betweenDates);

        //calculate the charges
        double roomCharges = 350 * nights * numOfRooms;
        double tourismTax = 10 * nights * numOfRooms;
        double serviceTax = roomCharges * 0.1 * numOfRooms;
        double totalCharges = roomCharges + tourismTax + serviceTax;
        //convert to two decimal point
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String roomChargesStr = decimalFormat.format(roomCharges);
        String tourismTaxStr = decimalFormat.format(tourismTax);
        String serviceTaxStr = decimalFormat.format(serviceTax);
        String totalChargesStr = decimalFormat.format(totalCharges);

        String[] results = {nightsStr, roomChargesStr, tourismTaxStr, serviceTaxStr, totalChargesStr};

        return results;
    }

    public static ArrayList<Bookings> readBookingFile() {
        ArrayList<String[]> booking = fileToArray(Files.bookingsFile);
        ArrayList<Bookings> bookingsList = new ArrayList<>();

        //store data into objects
        for (String[] rec : booking) {
            double doub5 = Double.parseDouble(rec[5]);
            Bookings tempBookings = new Bookings(rec[0], rec[1], rec[2], rec[3], rec[4], doub5);
            bookingsList.add(tempBookings);
        }
        return bookingsList;
    }

    public static String IncremetnBId() {
        ArrayList<Bookings> bookings = readBookingFile();
        int lastIndex = 0;
        String newId = null;
        if (bookings.isEmpty()) {
            newId = "B1";
        } else {
            for (Bookings searchBId : bookings){
                String tempBId = searchBId.getId();
                int BIdNum = Integer.parseInt(tempBId.substring(1));
                if (BIdNum > lastIndex){
                    lastIndex = BIdNum;
                }
            }
            newId = "B" + String.valueOf(lastIndex+1);
        
        }

        return newId;
    }

    public static boolean addBookingToDb(String[] rec) {
        boolean addStatus;
        addStatus = Files.appendFile(Files.bookingsFile, rec);
        return addStatus;
    }
    
    public String getId() {
        return id;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getCusId() {
        return cusId;
    }

    public double getTotal() {
        return total;
    }
}
