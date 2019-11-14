package com.example.bledemo.adapters;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bledemo.CaracteristicActivity;
import com.example.bledemo.MainActivity;
import com.example.bledemo.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapterList extends ArrayAdapter<BluetoothGattService> {
    private final Context context;
    private Activity mainActivity;
    private List<BluetoothGattService> BluetoothServiceList;
    public static List<BluetoothGattCharacteristic> bluetoothGattCharacteristics;

    public ServiceAdapterList(@NonNull Context context, List<BluetoothGattService> scanResultList, Activity mainActivity) {
        super(context, R.layout.device_list_item,scanResultList);
        this.context = context;
        this.mainActivity=mainActivity;
        this.BluetoothServiceList = scanResultList;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.device_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.device_list_item_text_view);
        txtTitle.setText(BluetoothServiceList.get(position).getUuid().toString());
        // TextView deviceNameTxtView = (TextView) rowView.findViewById(R.id.device_list_item_text_view2);
        //deviceNameTxtView.setText(deviceName);


        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceAdapterList.bluetoothGattCharacteristics = BluetoothServiceList.get(position).getCharacteristics();
                //MainActivity.ShowToast(mainActivity,"CHARACTERISTICS :\n" + BluetoothServiceList.get(position).getCharacteristics().toString());
                Intent intent = new Intent(mainActivity.getApplicationContext(), CaracteristicActivity.class);
                mainActivity.startActivity(intent);
                // mainActivity.bleManager.connectToGATTServer(mainActivity.bleManager.getByAddress(address));
                //mainActivity.bleManager.connectToGATTServer(mainActivity.bleManager.getByAddress(address));
            }
        });

        return rowView;
    }
}
