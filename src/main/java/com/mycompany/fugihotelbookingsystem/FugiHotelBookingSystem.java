/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fugihotelbookingsystem;

import JFrames.Login;
import JFrames.LandingPage;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */
public class FugiHotelBookingSystem {

    public static void main(String[] args) {
//        System.out.println("Hello World!");
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Login startingFrame = new Login();
        startingFrame.setVisible(true);
        startingFrame.setLocationRelativeTo(null);

//        parent myframe = new parent();
//        myframe.setVisible(true);
        
        
    }
    
    
}
