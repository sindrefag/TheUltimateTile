package io.github.vampirestudios.tdg.utils;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UtilsIdk {

    /**
     * Check if it is Christmas time!
     */
    public static final boolean IS_CHRISTMAS = isChristmas();

    public static String getUsername() {
        return getUsername("Player");
    }

    public static String getUsername(String defaultName) {
        String username;
        int nameLength = 16;
        try {
            username = JOptionPane.showInputDialog("Please enter a username! Must be max " + nameLength + " characters", defaultName);
            if (username.length() > nameLength || username.equalsIgnoreCase(""))
                username = getUsername(username);
        } catch (NullPointerException e) {
            username = "you_clicked_cancel";
        }
        return username.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]+", ""); // I'ma Cat
    }

    public static String getKeyPressed(MaewilEngine maewilEngine) {
        switch (maewilEngine.getKeyManager().getCurrentKeyPressedCode()) {
            case 8:
                return "BACKSPACE";
            case 9:
                return "TAB";
            case 16:
                return "SHIFT";
            case 17:
                return "CONTROL";
            case 18:
                return "ALT";
            case 20:
                return "CAPS LOCK";
            case 27:
                return "ESCAPE";
            case 32:
                return "SPACE";
            default:
                return String.valueOf(maewilEngine.getKeyManager().getCurrentKeyPressedChar()).toUpperCase();
        }
    }

    /**
     * Code from https://examples.javacodegeeks.com/core-java/util/zip/zipoutputstream/java-zip-file-example/
     * Edited slightly!
     *
     * @param files
     * @param zipFilePath
     */
    public static void zipFiles(File[] files, String zipFilePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            for (File file : files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buf)) > 0) {
                    zipOutputStream.write(buf, 0, bytesRead);
                }
                fileInputStream.close();
            }

            zipOutputStream.closeEntry();
            zipOutputStream.close();

            fileOutputStream.close();
        } catch (IOException e) {
            MaewilLauncher.LOGGER.error(e);
        }
    }

    private static boolean isChristmas() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        boolean inRange = (day >= 20 && day <= 31);

        return (month == Calendar.DECEMBER && inRange);
    }

    public static boolean isDate(int month, int day) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        return (cal.get(Calendar.MONTH) == month && cal.get(Calendar.DAY_OF_MONTH) == day);
    }

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floorF(float value) {
        int i = (int) value;
        return value < (float) i ? i - 1 : i;
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floorD(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }
}
