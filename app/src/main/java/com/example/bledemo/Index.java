package com.example.bledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Index extends AppCompatActivity {
    Button StartScan, StopScan, ConnectDev, DisconnectDev, TurnOn, TurnOff, exit;
    BluetoothAdapter BluetoothCon;
    Activity mainactivity;
    ImageView bleIm;
    TextView INF;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        StartScan = findViewById(R.id.button);
        StopScan = findViewById(R.id.button2);
        ConnectDev = findViewById(R.id.button3);
        DisconnectDev = findViewById(R.id.button4);
        TurnOn = findViewById(R.id.button5);
        TurnOff = findViewById(R.id.button6);
        bleIm = findViewById(R.id.imageView2);
        exit = findViewById(R.id.button7);
        mainactivity = this;

        StartScan.setVisibility(View.INVISIBLE);
        StopScan.setVisibility(View.INVISIBLE);
        ConnectDev.setVisibility(View.INVISIBLE);
        DisconnectDev.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.INVISIBLE);
        BluetoothCon = BluetoothAdapter.getDefaultAdapter();
        INF = findViewById(R.id.textView2);
        //Check if device is ENABLE
        if (BluetoothCon == null) {
            INF.setText("---BLE is not available---");
            TurnOn.setVisibility(View.INVISIBLE);
            TurnOff.setVisibility(View.INVISIBLE);
            exit.setVisibility(View.VISIBLE);
        } else {
            INF.setText("---BLE is available ---");

        }


        //Check if Bluetooth is ON or Not
        if (BluetoothCon.isEnabled()) {
            bleIm.setImageResource(R.drawable.ic_action_blue);
        } else {
            bleIm.setImageResource(R.drawable.ic_action_bleoff);
        }


        //Start Scan and change to MainActivity

        StartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });

        //Encender el bluetooth
        TurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!BluetoothCon.isEnabled()) {
                        MainActivity.ShowToast(mainactivity,"Turning ON BLE");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        mainactivity.startActivityForResult(intent, REQUEST_ENABLE_BT);
                        //Set visible  first 4 buttons
                        StartScan.setVisibility(View.VISIBLE);
                        StopScan.setVisibility(View.VISIBLE);
                        ConnectDev.setVisibility(View.VISIBLE);
                        DisconnectDev.setVisibility(View.VISIBLE);
                    } else {
                        MainActivity.ShowToast(mainactivity,"BLE technology is already ON");
                    }
                } catch (Exception error) {
                    MainActivity.ShowToast(mainactivity,""+error.getMessage());
                }


            }
        });





        //Turn off the Bluetooth

        TurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BluetoothCon.isEnabled()) {
                    BluetoothCon.disable();
                    Toast.makeText(null, "Tuning OFF BLE", Toast.LENGTH_LONG);
                    bleIm.setImageResource(R.drawable.ic_action_bleoff);
                } else {
                    Toast.makeText(null, "BLE is already OFF", Toast.LENGTH_LONG);
                }


            }
        });

        //EXIT application if the device can't use BLE technology

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}
