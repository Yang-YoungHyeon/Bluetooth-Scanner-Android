# Bluetooth-Scanner

# Using Permission
<div>
  
  <!-- GPS -->
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <!-- Bluetooth -->
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
</div>
  
# Check Permission
```
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
```

# Bluetooth.LeScanCallback
```
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
 ```

<div>
  <img weith="200" src="https://user-images.githubusercontent.com/58409497/70030584-dd732f00-15ec-11ea-9fd3-7af15978179c.jpg"/>
</div>
