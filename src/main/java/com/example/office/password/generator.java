package com.example.office.password;

import java.util.Random;

public class generator {
    public static String generatePass() {

        String upper ="QWERTYUIOPLKJHGFDSAZXCVBNM";
        String lower ="qwertyuioplkjhgfdsazxcvbnm";
        String nums ="1234567890";
        String specialch="~!@#$%^&*(){}[]_+-=?><";

        String combination=upper+lower+nums+specialch;

        int len=8;
        char[] password=new char[len];
        Random r=new Random();

        for (int i = 0; i < len; i++) {

            password[i]=combination.charAt(r.nextInt(combination.length()));

        }
        return new String(password);
    }
}
