package com.example.bledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.bledemo.adapters.CaracteristicAdapterList;
import com.example.bledemo.adapters.ServiceAdapterList;

public class CaracteristicActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristic);
        listView = findViewById(R.id.caracteristic_list);
        CaracteristicAdapterList adapter = new CaracteristicAdapterList(getApplicationContext(),
                ServiceAdapterList.bluetoothGattCharacteristics,this);

        listView.setAdapter(adapter);
    }
}
