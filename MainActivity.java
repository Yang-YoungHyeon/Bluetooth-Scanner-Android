package com.example.bluetoothscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int Bluetooth_Request_Code = 1994;
    private int Location_Request_Code = 26;
    private boolean isScanning = false;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList LIST_ITEM;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Check_Permission();

        LIST_ITEM = new ArrayList();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_ITEM);
        ListView listview = (ListView) findViewById(R.id.listview) ;
        listview.setAdapter(adapter) ;
    }

    private void Check_Permission(){
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if(btAdapter == null){
            Toast.makeText(this, R.string.Bluetooth_Unsupported_Message, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if (!btAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent,Bluetooth_Request_Code);
            }
        }
        String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,REQUIRED_PERMISSIONS[0]);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,REQUIRED_PERMISSIONS[1]);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,Location_Request_Code);
            } else {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,Location_Request_Code);
            }
        }
    }

    public void onStartClick(View view){
        if(!isScanning){
            bluetoothAdapter.startLeScan(leScanCallback);
            isScanning = !isScanning;
        }
    }

    public void onStopClick(View view){
        if(isScanning){
            bluetoothAdapter.stopLeScan(leScanCallback);
            isScanning = !isScanning;
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            if(bluetoothDevice != null) {
                if(LIST_ITEM.contains(bluetoothDevice.getAddress())==false){
                    LIST_ITEM.add(bluetoothDevice.getAddress());
                }else{
                    LIST_ITEM.remove(bluetoothDevice.getAddress());
                    LIST_ITEM.add(bluetoothDevice.getAddress());
                }
                adapter.notifyDataSetChanged();
            }
        }
    };
}
