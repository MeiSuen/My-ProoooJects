/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import static Classes.Files.fileToArray;
import static Classes.Files.overwriteFile;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author user
 */
public class staff {

    private String id, password, name;

    public staff(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public static ArrayList<staff> readStaffFile() {
        ArrayList<String[]> staffList = fileToArray(Files.staffFile);
        ArrayList<staff> staffRecs = new ArrayList<>();
        for (String[] rec : staffList) {
            staff newStaff = new staff(rec[0], rec[1], rec[2]);
            staffRecs.add(newStaff);
        }
        return staffRecs;
    }

    public static boolean login(String username, String password) {
        boolean success = false;
        ArrayList<staff> staffCreds = readStaffFile();
        for (staff staffRec : staffCreds) {
            if (username.equals(staffRec.getId()) && (password.equals(staffRec.getPassword()))) {
                success = true;
                break;
            }
        }
        return success;
    }
    
    public boolean modifyStaffRec(staff updatedRec){
        boolean updateSuccess = false;
        ArrayList<staff> staffRecs = readStaffFile();
        
        ListIterator<staff> staffIterator = staffRecs.listIterator();
        while (staffIterator.hasNext()){
            staff staffRec = staffIterator.next();
            //remove the original record and add the modified custoemr record
            if (staffRec.getId().equals(updatedRec.getId())){
                staffIterator.remove();
                staffIterator.add(updatedRec);
                //save changes into the text file
                updateSuccess = updateStaffRecsToDB(staffRecs);
                break;
            }
        }
        return updateSuccess;
    }
    
    public boolean updateStaffRecsToDB(ArrayList<staff> newStaffRecs){
        ArrayList<String[]> newStaffRecsStr = new ArrayList<>();
        for (staff rec : newStaffRecs){
            String staffId = rec.getId();
            String pw = rec.getPassword();
            String name = rec.getName();
            String[] tempStrRec = {staffId,pw,name};
            newStaffRecsStr.add(tempStrRec);
        }
        boolean success = overwriteFile(Files.staffFile, newStaffRecsStr);
        return success;
    }
    
    public boolean deleteFromStaffRecs(staff toBeDeleted){
        ArrayList<staff> staffRecs = readStaffFile();
        boolean dltSuccess = false;
        
        ListIterator<staff> staffIterator = staffRecs.listIterator();
        while (staffIterator.hasNext()){
            staff staffRec = staffIterator.next();
            //remove the record from array list
            if (staffRec.getId().equals(toBeDeleted.getId())){
                staffIterator.remove();
                dltSuccess = updateStaffRecsToDB(staffRecs);
                break;
            }
        }
        return dltSuccess;
    }
    
    public boolean addNewStaffToRec(staff newRec){
        String[] newRecStr = {newRec.getId(), newRec.getPassword(), newRec.getName()};
        boolean addSuccess = Files.appendFile(Files.staffFile, newRecStr);
        return addSuccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
