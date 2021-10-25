package com.example.jon.bles2.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;

import android.app.Activity;
import android.app.AlertDialog;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.DialogInterface;

import android.os.Handler;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
//import android.bluetooth.BluetoothGatt.Device;

import com.example.jon.bles2.Parameter;
import com.example.jon.bles2.R;

import java.util.ArrayList;
import java.util.List;


public class    MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 30000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    private ListAdapter adapterLeScanResult;
    private static final int RQS_ENABLE_BLUETOOTH = 1;
    private List<BluetoothDevice> listBluetoothDevice;
    private BluetoothLeScanner mBluetoothLeScanner;
    private boolean mScanning;

    TextView textView_version;
    TextView textView_scanstate;
    ListView deviceListView;
    Button btnScan;
    // Jason Add 2021/02/17
    //Button bicycle_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
/*      // Test for bicycle demo
        bicycle_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        BicycleActivity.class);
                Log.i("onCreate", "swtich to bicycle demo");

                startActivity(intent);
            }
        });
*/
    /*    new Handler().postDelayed(new Runnable() {  我自己加的
            @Override
            public void run() {
                deviceListView = (ListView)findViewById(R.id.list_v);
            }
        },100);*/

        // Check if BLE is supported on the device.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this,
                    "BLE not supported in this device!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        getBluetoothAdapterAndLeScanner();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,
                    "bluetoothManager.getAdapter() is null",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLeDevice(true);
            }
        });
        listBluetoothDevice = new ArrayList<BluetoothDevice>();
        adapterLeScanResult = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, listBluetoothDevice);
            deviceListView.setAdapter(adapterLeScanResult);
        deviceListView.setOnItemClickListener(scanResultOnItemClickListener);

        mHandler = new Handler();

        //test delete 20210712
        //test delete

    } //* END - onCreate*//

    private void findViews(){
        textView_scanstate=(TextView)findViewById(R.id.text_scanstate);
        textView_version=(TextView)findViewById(R.id.text_v);
        btnScan = (Button)findViewById(R.id.b_scan);
        deviceListView = (ListView)findViewById(R.id.list_v);
        // Jason Add 2021/02/17
        //bicycle_demo = (Button)findViewById(R.id.b_bicycle_demo);
    }

    AdapterView.OnItemClickListener scanResultOnItemClickListener =
            new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final BluetoothDevice device =
                            (BluetoothDevice) parent.getItemAtPosition(position);


                    String msg = device.getAddress() + "\n"
                            + device.getBluetoothClass().toString() + "\n"
                            + getBTDevieType(device);

                    if(TextUtils.equals(device.getName(),null) )//判斷連接哪支錶 修改通知訊息參數   //TextUtils.equals  判斷string是否null用  不判斷如果null會閃退 on a null object
                    {
                        Parameter.textSize = 16;
                        Parameter.messageSide = 8;
                        Parameter.messageWidth = 104;
                        Parameter.messageHeight = 104;
                        Parameter.turn = 0;
                        Parameter.Flash_index = 180;
                        Parameter.BlePackageByte = 26;
                    }else if(device.getName().equals("AUO HYBRID"))//判斷連接哪支錶 修改通知訊息參數
                    {
                        Parameter.textSize = 16;
                        Parameter.messageSide = 8;
                        Parameter.messageWidth = 104;
                        Parameter.messageHeight = 104;
                        Parameter.turn = 0;
                        Parameter.Flash_index = 180;
                        Parameter.BlePackageByte = 26;
                    } else{    //目前是160 *80 訊息通知用
                        Parameter.textSize = 22;
                        Parameter.textSize2 = 28;
                        Parameter.messageSide = 16;
                        Parameter.messageWidth = 160;
                        Parameter.messageHeight = 80;
                        Parameter.turn = 1;
                        Parameter.Flash_index = 30;
                        Parameter.BlePackageByte = 20;
                    }
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(device.getName())
                            .setMessage(msg)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNeutralButton("CONNECT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final Intent intent = new Intent(MainActivity.this,
                                            StringAndPic.class);
                                   // intent.putExtra(StringAndPic.EXTRAS_DEVICE_NAME,
                                   //         device.getName());
                                    intent.putExtra(StringAndPic.EXTRAS_DEVICE_ADDRESS,
                                            device.getAddress());
                                    //SPEED F8:B4:80:98:6D:53  //C2:97:46:CA:E8:B1
                                    intent.putExtra(StringAndPic.EXTRAS_DEVICE_ADDRESS_Car,"F8:B4:80:98:6D:53");
                                    if (mScanning) {
                                        mBluetoothLeScanner.stopScan(scanCallback);
                                        mScanning = false;
                                        btnScan.setEnabled(true);
                                    }
                                    startActivity(intent);

                                }
                            })
                            .show();

                }
            };

    private String getBTDevieType(BluetoothDevice d){
        String type = "";

        switch (d.getType()){
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                type = "DEVICE_TYPE_CLASSIC";
                break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                type = "DEVICE_TYPE_DUAL";
                break;
            case BluetoothDevice.DEVICE_TYPE_LE:
                type = "DEVICE_TYPE_LE";
                break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                type = "DEVICE_TYPE_UNKNOWN";
                break;
            default:
                type = "Unknown...";
        }

        return type;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, RQS_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RQS_ENABLE_BLUETOOTH && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }

        getBluetoothAdapterAndLeScanner();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,
                    "bluetoothManager.getAdapter()==null",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getBluetoothAdapterAndLeScanner(){
        // Get BluetoothAdapter and BluetoothLeScanner.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        mScanning = false;
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            listBluetoothDevice.clear();
            deviceListView.invalidateViews();

            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothLeScanner.stopScan(scanCallback);
                    deviceListView.invalidateViews();
                    /*
                            Toast.makeText(MainActivity.this,
                            "Scan timeout",
                            Toast.LENGTH_LONG).show();*/
                    mScanning = false;
                    btnScan.setEnabled(true);
                    textView_scanstate.setText("");

                }
            }, SCAN_PERIOD);

/*
            //*** scan specified devices only with ScanFilter
            ScanFilter scanFilter =
                    new ScanFilter.Builder()
                            .setServiceUuid(BluetoothLeService.ParcelUuid_AUOService)
                            .build();
            List<ScanFilter> scanFilters = new ArrayList<ScanFilter>();
            scanFilters.add(scanFilter);

            ScanSettings scanSettings =
                    new ScanSettings.Builder().build();

            mBluetoothLeScanner.startScan(scanFilters, scanSettings, scanCallback);
            */


            //*** scan all devices
            mBluetoothLeScanner.startScan(scanCallback);

            mScanning = true;
            btnScan.setEnabled(false);
            textView_scanstate.setText(R.string.textview_scan_ing);
        } else {
            mBluetoothLeScanner.stopScan(scanCallback);
            mScanning = false;
            btnScan.setEnabled(true);
            textView_scanstate.setText("");


        }
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            String Name = result.getDevice().getName();
            if(Name != null)
                addBluetoothDevice(result.getDevice());
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for(ScanResult result : results){
                String Name = result.getDevice().getName();
                if(Name != null)
                    addBluetoothDevice(result.getDevice());
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Toast.makeText(MainActivity.this,
                    "onScanFailed: " + String.valueOf(errorCode),
                    Toast.LENGTH_LONG).show();
        }

        private void addBluetoothDevice(BluetoothDevice device){
            if(!listBluetoothDevice.contains(device)){
                //listBluetoothDevice.add(new BluetoothDevice().Device(device.getName() , device.getAddress() , device.getBondState()));
                //listBluetoothDevice.add(device.getName()+"     "+device.getAddress());
                listBluetoothDevice.add(device);
                ((BaseAdapter) adapterLeScanResult).notifyDataSetChanged();//就只是加這行scan就不會閃退 20190226
                deviceListView.invalidateViews();
            }
        }
    };

} //* END *//
