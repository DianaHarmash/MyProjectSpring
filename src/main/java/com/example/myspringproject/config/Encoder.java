package com.example.myspringproject.config;
import com.example.myspringproject.controller.MainController;

import javax.crypto.*;
import java.io.FileInputStream;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encoder {

    public String encode(String text) {
        try {
            return getEncode(text);
        } catch (Exception ex) {
            MainController.logger.log(Level.WARNING, "Exception while generating password");
        }
        return null;
    }

    private String getEncode(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean checkInputLogin(String login) {;
        Pattern pattern = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9@.\\-_]{3,15}$");
        Matcher matcher = pattern.matcher(login);
        return matcher.find() ? true : false;
    }

    public static boolean checkInputPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_*~#@.\\-]{4,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find() ? true : false;
    }

    public static boolean checkInputHours(String password) {
        Pattern pattern = Pattern.compile("^[\\d]{1,2}[hHdm]$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find() ? true : false;
    }
}
