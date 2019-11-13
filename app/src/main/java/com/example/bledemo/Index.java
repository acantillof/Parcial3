package com.example.bledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bledemo.log.LogManager;

public class Index extends AppCompatActivity {
    Button StartScan, StopScan, ConnectDev, DisconnectDev, TurnOn, TurnOff, exit;
    BluetoothAdapter BluetoothCon;
    ImageView bleIm;
    TextView INF;
    public static LogManager logManager;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final int REQUEST_DISABLE_BT = 2;

    private BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        logManager = new LogManager();
        logManager.addRegister("Aplication initiated.");
        StartScan = findViewById(R.id.button);
        StopScan = findViewById(R.id.button2);
        ConnectDev = findViewById(R.id.button3);
        DisconnectDev = findViewById(R.id.button4);
        TurnOn = findViewById(R.id.button5);
        TurnOff = findViewById(R.id.button6);
        bleIm = findViewById(R.id.imageView2);
        exit = findViewById(R.id.button7);


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
            StartScan.setVisibility(View.VISIBLE);
            StopScan.setVisibility(View.VISIBLE);
            ConnectDev.setVisibility(View.VISIBLE);
            DisconnectDev.setVisibility(View.VISIBLE);
            bleIm.setImageResource(R.drawable.ic_action_blue);
            TurnOff.setVisibility(View.VISIBLE);
            TurnOn.setVisibility(View.GONE);
        } else {
            bleIm.setImageResource(R.drawable.ic_action_bleoff);
            TurnOff.setVisibility(View.GONE);
            TurnOn.setVisibility(View.VISIBLE);
        }


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();

                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:
                            StartScan.setVisibility(View.INVISIBLE);
                            StopScan.setVisibility(View.INVISIBLE);
                            ConnectDev.setVisibility(View.INVISIBLE);
                            DisconnectDev.setVisibility(View.INVISIBLE);
                            bleIm.setImageResource(R.drawable.ic_action_bleoff);
                            TurnOn.setVisibility(View.VISIBLE);
                            TurnOff.setVisibility(View.GONE);
                            logManager.addRegister("BLE turn off.");
//                            Toast.makeText(getApplicationContext(), "Turning OFF BLE", Toast.LENGTH_LONG);

                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            logManager.addRegister("BLE turning off.");
                            break;
                        case BluetoothAdapter.STATE_ON:
                            StartScan.setVisibility(View.VISIBLE);
                            StopScan.setVisibility(View.VISIBLE);
                            ConnectDev.setVisibility(View.VISIBLE);
                            DisconnectDev.setVisibility(View.VISIBLE);
                            bleIm.setImageResource(R.drawable.ic_action_blue);
                            TurnOn.setVisibility(View.GONE);
                            TurnOff.setVisibility(View.VISIBLE);
                            logManager.addRegister("BLE turn on.");

                        case BluetoothAdapter.STATE_TURNING_ON:
                            logManager.addRegister("BLE turning on.");
                            break;
                    }
                }
            }
        };
        //Start Scan and change to MainActivity


        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);


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
                        Toast.makeText(getApplicationContext(), "Turning ON BLE", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);


                        //Set visible  first 4 buttons

                    } else {
                        Toast.makeText(getApplicationContext(), "BLE is already ON", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception error) {
                    Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });





        //Turn off the Bluetooth

        TurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BluetoothCon.isEnabled()) {

                    BluetoothCon.disable();

                } else {
                    Toast.makeText(getApplicationContext(), "BLE is already OFF", Toast.LENGTH_LONG);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if(requestCode == REQUEST_ENABLE_BT){
                if(resultCode == Activity.RESULT_OK){

                }else{

                }
            }
            if(requestCode == REQUEST_DISABLE_BT){
                if(resultCode == Activity.RESULT_OK){

                }
            }


        }catch (Exception error){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /* ... */

        // Unregister broadcast listeners
        unregisterReceiver(mReceiver);
    }

}
