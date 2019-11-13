package com.example.bledemo.log;

import android.app.Activity;
import android.app.AlertDialog;
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

    public void showAlertDialog(Activity activity, String message, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setTitle(title);
        builder.create().show();
    }
}
