package com.leventenyiro.gps;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Naplozas {

    public static void kiiras(float hosszusag, float szelesseg) throws IOException {
        String allapot;
        File file;
        String szovegesAdat;
        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        String formatedDate = dateFormat.format(date);

        szovegesAdat = hosszusag + "," + szelesseg + "," + formatedDate + "," + "\r\n";
    }
}
