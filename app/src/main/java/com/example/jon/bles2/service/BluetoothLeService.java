package com.example.jon.bles2.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Binder;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.jon.bles2.AllString;
import com.example.jon.bles2.Phone;
import com.example.jon.bles2.TransData;
import com.example.jon.bles2.activity.StringAndPic;
import com.example.jon.bles2.TimeSyncFun;
import com.example.jon.bles2.service.bluetoothUUIDS.UUIDS;

import java.util.List;
import java.util.UUID;


public class BluetoothLeService extends Service {

    private final static String TAG = BluetoothLeService.class.getSimpleName();
    public static boolean mConnected = false;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    public  BluetoothGatt mBluetoothGatt; //private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    /*   private BluetoothGattCharacteristic mNotifyCharacteristic;
        private Queue writeQueue = new LinkedList();
        private boolean isWriting = false;
    */
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_MEDIA_BUTTON = "android.intent.action.MEDIA_BUTTON";
    public final static String ACTION_GATT_CONNECTED = "android-er.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "android-er.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "android-er.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "android-er.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA = "android-er.EXTRA_DATA";
    public final static String DATA_WRITABLE = "android-er.DATA_WRITABLE";
    public final static String DATA_QUEUE = "android-er.DATA_QUEUE";

    public TelephonyManager tm;
    @Override
    public void onCreate(){
        super.onCreate();
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mGattUpdateReceiver);
    }

    public BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        public AudioManager audioManager;
        @Override
        public void onReceive(Context context, Intent intent) {
            audioManager =(AudioManager)getSystemService(context.AUDIO_SERVICE);
            final String action = intent.getAction();
            switch(action){
                case BluetoothLeService.ACTION_GATT_CONNECTED:
                    mConnected = true;
                    Log.e("車錶", "STATE : CONNECTED");
                    break;
                case BluetoothLeService.ACTION_GATT_DISCONNECTED:
                    mConnected = false;
                    Log.e("車錶", "STATE : DISCONNECTED");
                    break;
                case BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED:
                    break;
                case BluetoothLeService.ACTION_DATA_AVAILABLE:
                    try {
                        byte[] data = null;
                        data = intent.getByteArrayExtra(EXTRA_DATA);//讀取外部藍芽data嗎??????
                        Log.e("藍芽讀的資料", String.format("data[0]=%d,", data[0]));

                        if (data[0] == 68) {//掛電話  68
                            Phone.rejectCall(context);
                        }
                        if (data[0] == 65) {//接電話這個明天再試 65
                            startActivity( Phone.acceptCall_1(context) );
                        }
                        if (data[0] == 67) {//手錶撥來的電話  67
                            startActivity(Phone.makeCall(data));
                        }
                        if(data[0]==5){
                            Intent mIntent = new Intent();
                            mIntent.setAction(AllString.SEND_WX_BROADCAST);
                            mIntent.putExtra("packageName","MUSIC_PREVIOUS");
                            sendBroadcast(mIntent);
                        }
                        if(data[0]==6){
                            Intent mIntent = new Intent();
                            mIntent.setAction(AllString.SEND_WX_BROADCAST);
                            mIntent.putExtra("packageName","MUSIC_PLAY");
                            sendBroadcast(mIntent);
                        }
                        if(data[0]==7){
                            Intent mIntent = new Intent();
                            mIntent.setAction(AllString.SEND_WX_BROADCAST);
                            mIntent.putExtra("packageName","MUSIC_NEXT");
                            sendBroadcast(mIntent);
                        }
                        if(data[0]==9){//音量變大
                            audioManager.adjustVolume(AudioManager.ADJUST_RAISE,0);
                        }
                        if(data[0]==8){//音量變小
                            audioManager.adjustVolume(AudioManager.ADJUST_LOWER,0);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
            }
        }
    };

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private  BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());
                //mBluetoothGatt.requestMtu(485); //--------------------------------------------------------
                //UI MAIN Thread run time data send to device
               /* new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        boolean ans = mBluetoothGatt.discoverServices();
                        Log.e(TAG, "discover services : " + ans
                        );
                    }
                });*/

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //mBluetoothGatt.requestMtu(485); //--------------------------------------------------------
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                BluetoothGattService serviceRead = geGattServices(UUIDS.UUID_AUOService);
                BluetoothGattCharacteristic characteristicRead = serviceRead.getCharacteristic(UUIDS.UUID_AUO_Wearable_Read);
                enableNotifications(characteristicRead);
                //藍芽連線完順便時間校正
                TimeSyncFun AA=new TimeSyncFun();
                TransData transData = new TransData();
                transData.DataWrite(TimeSyncFun.CURRENT_TIME_SERVICE,TimeSyncFun.CURRENT_TIME_CHARACTERISTIC,AA.getCurrentTimeInBytes());//寫入校正後的時間
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.d("BLE","onCharacteristicRead");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            Log.d("BLE","onCharacteristicChanged");
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic,
                                          int status) {

            Log.d("BLE","onCharacteristicWrite");
            Log.d("CHECK", "Write Success" + characteristic.getValue());
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(DATA_WRITABLE, characteristic);
            }else{
                broadcastUpdate(DATA_QUEUE, characteristic);

            }
        }
        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status){
            Log.d("BLE","onMtuChanged mtu="+mtu+",status="+status);
        }
        /*@Override
        public void onDescriptorWrite(BluetoothGatt gatt,BluetoothGattDescriptor descriptor,int status ){
            super.onDescriptorWrite(gatt,descriptor,status);
            Log.d("BLE","onDescriptorWrite設置成功"+",status="+status);
        }*/
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,//這裡增加if(UUID)指定我要的功能
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);


        Log.w(TAG, "broadcastUpdate()");

        final byte[] data = characteristic.getValue();
        //Log.e("bbbbbb", String.format("data[0]=%d,data[1]=%d", data[0], data[1]));
        Log.e("bbbbbb", String.format("data[0]=%d", data[0]));

        Log.v(TAG, "data.length: " + data.length);

        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));

                Log.v(TAG, String.format("%02X ", byteChar));
            }
            //intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            intent.putExtra(EXTRA_DATA, data);
        }

        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(
     *         android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, true, mGattCallback);//連接 可調自動連接???
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;

        //mBluetoothGatt.requestMtu(485); //MTU SET
        //setMTU(485);

        return true;

    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(
     * android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(
     * android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        boolean isSuccess;
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        isSuccess = mBluetoothGatt.writeCharacteristic(characteristic);
        //Log.w("CHECK","RESULT : " + isSuccess);
        return isSuccess;

        //return mBluetoothGatt.writeCharacteristic(characteristic);
    }
/*
    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */

/*    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }*/

    public final boolean enableNotifications(final BluetoothGattCharacteristic characteristic){
        final BluetoothGatt gatt = mBluetoothGatt;
        if(gatt ==null || characteristic ==null)
            return false;
        //check characristic property
        final int properties = characteristic.getProperties();
        if((properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY) ==0)  //properties =0x10  notify   ,  properties =0x20  indication
            return false;
        Log.d("BLE","gatt.setCharacteristicNotification("+ characteristic.getUuid() +",true)");
        gatt.setCharacteristicNotification(characteristic,true);// 幹   怎麼只加這行就讀得到手錶ble data ,原本的public void setCharacteristicNotification 根本不需要 操你妹
/*        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUIDS.CLIENT_CHARACRISTIC_CONFIG_DESCRIPTOR_UUID);
        if(descriptor !=null){
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            Log.v("BLE","Enable Notification for"+characteristic.getUuid());
            Log.d("BLE","gatt.writeDescriptor("+UUIDS.CLIENT_CHARACRISTIC_CONFIG_DESCRIPTOR_UUID+",value=0x01-00");
            gatt.writeDescriptor(descriptor);
        }*/
        return false;
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    public BluetoothGattService geGattServices(UUID serviceUUID ) {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getService(serviceUUID);
    }

    public boolean setMTU(int mtu){

        return mBluetoothGatt.requestMtu(mtu);

    }



    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_MEDIA_BUTTON);
        return intentFilter;
    }

} // End - BluetoothLeService
