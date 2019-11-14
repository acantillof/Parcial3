package com.example.bledemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bledemo.adapters.ServiceAdapterList;
import com.example.bledemo.ble.BLEManager;

public class ServiceActivity extends Activity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ServiceAdapterList adapter = new ServiceAdapterList(getApplicationContext(),
                BLEManager.lastBluetoothGatt.getServices(), this);

        list = (ListView) findViewById(R.id.service_list);

        list.setAdapter(adapter);


    }
}
