/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import static Classes.Files.fileToArray;
import static Classes.Files.overwriteFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class RoomAvailability {

    private static ArrayList<String[]> readRoomAvailabilityFile() {
        removeOldRecs();
        ArrayList<String[]> roomAvailability = fileToArray(Files.roomAvailability);
        return roomAvailability;
    }

    public static void removeOldRecs() {
        ArrayList<String[]> roomAvailability = fileToArray(Files.roomAvailability);

        //get today's date in string : yyyyMMdd
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter yMdFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        String yMdDate = todayDate.format(yMdFormat);

        //loop through the araylist and remove past records
        Iterator<String[]> iterator = roomAvailability.iterator();
        while (iterator.hasNext()) {
            String[] record = iterator.next();
            String dateValue = record[0];
            if ((Integer.valueOf(dateValue)) < (Integer.valueOf(yMdDate))) {
                iterator.remove();
            }
        }
        overwriteFile(Files.roomAvailability, roomAvailability);
    }

    public static ArrayList<String> checkAvailability(ArrayList<String> dates, String toIgnore) {
        //Read data from bookings file
        ArrayList<String> booked = new ArrayList<>();
        ArrayList<String[]> roomAvailability = readRoomAvailabilityFile();
        
        //for ModifyBookings usage, to ignore the room availability records of current modified booking rec
        if (toIgnore != null) {
            Iterator<String[]> whichAvailable = roomAvailability.iterator();
            while (whichAvailable.hasNext()) {
                String[] array = whichAvailable.next();
                // Check if the array contains the search string
                if (Arrays.asList(array).contains(toIgnore)) {
                    whichAvailable.remove();
                }
            }
        }
        
        String[] rooms = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210"};
        //loop through dates needed to check room availability
        for (String room : rooms) {
            outerloop:
            for (String date : dates) {
                for (String[] record : roomAvailability) {
                    if (((record[0].equals(date)) && (record[1].equals(room)))) {
                        booked.add(room);
                        break outerloop;
                    }
                }
            }

        }
        return booked;
    }

    public static boolean addRAToDb(String[] rec) {
        boolean addStatus;
        addStatus = Files.appendFile(Files.roomAvailability, rec);
        return addStatus;
    }

    public static boolean deleteRec(String BId) {
        boolean deleted = false;
        //delete from text file
        ArrayList<String[]> allRA = readRoomAvailabilityFile();
        ListIterator<String[]> RAIterator = allRA.listIterator();
        while (RAIterator.hasNext()) {
            String[] RARec = RAIterator.next();
            //remove the record from array list
            if (Arrays.asList(RARec).contains(BId)) {
                RAIterator.remove();
            }
        }
        deleted = overwriteFile(Files.roomAvailability, allRA);
        return deleted;
    }
    
}
