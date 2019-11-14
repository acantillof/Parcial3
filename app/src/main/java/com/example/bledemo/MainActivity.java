package com.example.bledemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.bledemo.adapters.BluetoothDeviceListAdapter;
import com.example.bledemo.ble.BLEManager;
import com.example.bledemo.ble.BLEManagerCallerInterface;
import com.example.bledemo.log.LogManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BLEManagerCallerInterface {
    private static int REQUEST_ENABLE_BTN = 0;
    public BLEManager bleManager;
    public MainActivity mainActivity;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        listView= (ListView)findViewById(R.id.devices_list_id);
        setSupportActionBar(toolbar);
        mainActivity = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton start =findViewById(R.id.TurnOn);




        //DESHABILITA BLUETOOTH
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bleManager!=null){
                    bleManager.bluetoothoff();
                    ShowToast(mainActivity,"Turn OFF BLE technology");
                    bleManager = new BLEManager(mainActivity,mainActivity);
                }else {
                    ShowToast(mainActivity,"BLE is already OFF");
                }

            }
        });
        bleManager=new BLEManager(this,this);
        //HABILITAR BLUETOOTH
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bleManager!=null){
                    bleManager.enableBluetoothDevice(mainActivity,REQUEST_ENABLE_BTN);
                    ShowToast(mainActivity,"Starting BLE technology");
                }else{
                    ShowToast(mainActivity,"BLE is already ON");

                }
            }
        });


        if(!bleManager.isBluetoothOn()){
            bleManager.enableBluetoothDevice(this, 1001);
        }else{
            bleManager.requestLocationPermissions(this,1002);
        }
        mainActivity=this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id== R.id.action_StartScan){

            Index.logManager.addRegister("Scanning....");
            bleManager.scanDevices();

        }
        if (id == R.id.action_logs){
            Intent intent = new Intent(getApplicationContext(),LogActivity.class);
            startActivity(intent);
        }
        if(id== R.id.action_DisconnectD){
            Index.logManager.addRegister("Disconected device.");
        }

        if (id==R.id.action_StopScan){
            Index.logManager.addRegister("Stop scan.");
            bleManager.stopScanDevices();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean allPermissionsGranted=true;
        if (requestCode == 1002) {
            for (int currentResult:grantResults
            ) {
                if(currentResult!= PackageManager.PERMISSION_GRANTED){
                    allPermissionsGranted=false;
                    break;
                }
            }
            if(!allPermissionsGranted){
                AlertDialog.Builder builder=new AlertDialog.Builder(this)
                        .setTitle("Permissions")
                        .setMessage("Camera and Location permissions must be granted in order to execute the app")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if(requestCode==1001){
                if(resultCode!=Activity.RESULT_OK){

                }else{
                    bleManager.requestLocationPermissions(this,1002);

                }
            }


        }catch (Exception error){

        }
    }

    @Override
    public void scanStartedSuccessfully() {

    }

    @Override
    public void scanStoped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
 //               bleManager.scanResults.clear();
                BluetoothDeviceListAdapter adapter=new BluetoothDeviceListAdapter(getApplicationContext(),bleManager.scanResults,mainActivity);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        ShowToast(mainActivity,"Error realizando el scanner");
    }

    @Override
    public void scanFailed(int error) {

    }

    @Override
    public void newDeviceDetected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    //ListView listView= (ListView)findViewById(R.id.devices_list_id);
                    BluetoothDeviceListAdapter adapter=new BluetoothDeviceListAdapter(getApplicationContext(),bleManager.scanResults,mainActivity);
                    listView.setAdapter(adapter);
                }catch (Exception error){

                }

            }
        });


    }

    public void ToLogActivity(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),LogActivity.class);
        startActivity(intent);
    }

    public static void ShowToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
