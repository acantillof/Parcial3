package com.example.bledemo.log;

import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogManager {

    public List<String> messagesLogs = new ArrayList<String>();


    public void addRegister(String  message) {
        LocalTime time = LocalTime.now();
        String timestamp = "[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] ";
        messagesLogs.add(timestamp + message);
    }
}
