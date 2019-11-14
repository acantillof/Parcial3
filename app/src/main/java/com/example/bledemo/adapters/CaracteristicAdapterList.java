package com.example.bledemo.adapters;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bledemo.MainActivity;
import com.example.bledemo.R;

import java.util.List;

public class CaracteristicAdapterList extends ArrayAdapter<BluetoothGattCharacteristic> {
    private final Context context;
    private Activity mainActivity;
    private List<BluetoothGattCharacteristic> BluetoothCaracteristicList;

    public CaracteristicAdapterList(@NonNull Context context, List<BluetoothGattCharacteristic> scanResultList, Activity mainActivity) {
        super(context, R.layout.device_list_item,scanResultList);
        this.context = context;
        this.mainActivity=mainActivity;
        this.BluetoothCaracteristicList = scanResultList;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.device_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.device_list_item_text_view);
        txtTitle.setText(BluetoothCaracteristicList.get(position).getUuid().toString());
        // TextView deviceNameTxtView = (TextView) rowView.findViewById(R.id.device_list_item_text_view2);
        //deviceNameTxtView.setText(deviceName);


        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.ShowToast(mainActivity,"CHARACTERISTICS :\n" + BluetoothCaracteristicList.get(position).getDescriptors().toString());
                // mainActivity.bleManager.connectToGATTServer(mainActivity.bleManager.getByAddress(address));
                //mainActivity.bleManager.connectToGATTServer(mainActivity.bleManager.getByAddress(address));
            }
        });

        return rowView;
    }
}
