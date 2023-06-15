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
public class Customer {

    private String name, ICpassport, phoneNum, email;

    public Customer(String name, String ICpassport, String phoneNum, String email) {
        this.name = name;
        this.ICpassport = ICpassport;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public static ArrayList<Customer> readCusFile() {
        ArrayList<String[]> customer = fileToArray(Files.customerFile);
        ArrayList<Customer> cusList = new ArrayList<>();
        for (String[] rec : customer) {
            Customer cus = new Customer(rec[0], rec[1], rec[2], rec[3]);
            cusList.add(cus);
        }
        return cusList;
    }


    public static boolean addNewCus(String[] newRec) {
        boolean addStatus;
        addStatus = Files.appendFile(Files.customerFile, newRec);
        return addStatus;

    }

    public static Customer searchCus(String ICp) {

        ArrayList<Customer> cusData = readCusFile();
        Customer target = null;
        for (Customer rec : cusData) {
            if (rec.getICpassport().equals(ICp)) {
                target = rec;
                break;
            }
        }
        return target;

    }
    
    public static boolean updateCusRecsToDB(ArrayList<Customer> newCusRecs){
        ArrayList<String[]> newCusRecsStr = new ArrayList<>();
        for (Customer rec : newCusRecs){
            String name = rec.getName();
            String ICP = rec.getICpassport();
            String num = rec.getPhoneNum();
            String email = rec.getEmail();
            String[] tempStrRec = {name,ICP,num,email};
            newCusRecsStr.add(tempStrRec);
        }
        boolean success = overwriteFile(Files.customerFile, newCusRecsStr);
        return success;
    }
    
    public boolean modifyCusRecs(Customer cusChanges){
        ArrayList<Customer> cusRecs = readCusFile();
        boolean updateSuccess = false;
        
        ListIterator<Customer> cusIterator = cusRecs.listIterator();
        while (cusIterator.hasNext()){
            Customer cusRec = cusIterator.next();
            //remove the original record and add the modified custoemr record
            if (cusRec.getICpassport().equals(cusChanges.getICpassport())){
                cusIterator.remove();
                cusIterator.add(cusChanges);
                //save changes into the text file
                updateSuccess = updateCusRecsToDB(cusRecs);
                break;
            }
        }
        return updateSuccess;
    }
    
    public boolean deleteFromCusRecs(Customer toDelete){
        ArrayList<Customer> cusRecs = readCusFile();
        boolean dltSuccess = false;
        
        ListIterator<Customer> cusIterator = cusRecs.listIterator();
        while (cusIterator.hasNext()){
            Customer cusRec = cusIterator.next();
            //remove the recorddd from array list
            if (cusRec.getICpassport().equals(toDelete.getICpassport())){
                cusIterator.remove();
                dltSuccess = updateCusRecsToDB(cusRecs);
                break;
            }
        }
        return dltSuccess;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getICpassport() {
        return ICpassport;
    }

    public void setICpassport(String ICpassport) {
        this.ICpassport = ICpassport;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
