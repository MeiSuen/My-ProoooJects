/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Files {
    public static String staffFile = "C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\StaffCredentials.txt";
    public static String bookingsFile = "C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\Booking Details.txt";
    public static String roomAvailability = "C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\RoomAvailability.txt";
    public static String customerFile = "C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\CustomerDetails.txt";
    public static String receiptsFile = "C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\Receipt.txt";
    
    public static ArrayList<String> readFile(String fileLoc){
        String line;
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileLoc));
            while ((line = reader.readLine()) != null){
                fileData.add(line);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found...");
        } catch (IOException ex) {
            System.out.println("IO not found...");;
        }
        return fileData;
    }

    public static ArrayList<String[]> fileToArray(String fileLoc){
        ArrayList<String[]> result = new ArrayList<String[]>();
        ArrayList<String> tempArray = readFile(fileLoc);
        for (String i : tempArray){
            String[] innerArray = i.split("\\|",0);
            result.add(innerArray);
        }
        return result;
    } 
   
    public static boolean overwriteFile(String fileLoc, ArrayList<String[]> data){
        boolean success = true;
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileLoc, false));
            for (String[] record : data) {
                for (int i = 0; i < record.length; i++){
                    writer.write(record[i]);
                    if (i != record.length - 1) {
                    writer.write("|");
                }
                }
                writer.write("\n");
            }
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found...");
           success = false;
        } catch (IOException ex) {
            System.out.println("IO not found");;
            success = false;
        }
        return success;
    }

    public static boolean appendFile(String fileLoc, String[] data){
        boolean success = true;
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileLoc, true));
            for (int i = 0; i < data.length; i++) {
                writer.write(data[i]);
                if (i != data.length - 1) {
                    writer.write("|");
                }
            }
            writer.write("\n");
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found...");
            success = false;
        } catch (IOException ex) {
            System.out.println("IO not found");
            success =  false;
        }
        return success;
    }
    
    public static void main(String[] args) {
        fileToArray("C:\\Users\\user\\Documents\\NetBeansProjects\\FugiHotelBookingSystem\\src\\main\\resources\\Data files\\AdminCredentials.txt");
    }
}
