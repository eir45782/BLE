package com.example.jon.bles2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.example.jon.bles2.activity.miniminiActivity;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static android.content.Context.MODE_APPEND;


public class Phone  {
    public Context phoneContext;

    //讓Phone  這個class可以輸入context這個東西，給TelephonyManager getSystemService，才能開啟電話監聽PhoneStateListener
    public  Phone(Context context) {
        this.phoneContext = context;
    }

    public static Intent makeCall(byte[] data) {//穿戴裝置撥電話
        byte[] example = data; //範例data[0]=0 , data[1]=9, ...  09xxxxxxxx 每個陣列依序為手機號碼
        String[] t = new String[example.length];//號碼
        String phoneNumber = new String();
        for (int i = 1; i <= example.length - 1; i++) {//依陣列長度轉換成字串次數
            t[i] = Integer.toString(example[i]);
        }
        for (int i = 1; i <= example.length - 1; i++) {//組合字串變成電話號碼
            phoneNumber = phoneNumber + t[i];
        }
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//幹  加這行就可以後臺打電話  ?????????
        call.setData(Uri.parse("tel:" + phoneNumber));
        return call;
    }

    //穿戴裝置掛電話
    public static void rejectCall(Context context) {
        try {
            //Log.e("掛電話掛電話掛電話掛電話", "掛電話1");
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(telephonyManager.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            ITelephony iTelephony = (ITelephony) m.invoke(telephonyManager); // invoke endCall()
            iTelephony.endCall();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //finish();
    }

    //穿戴裝置接電話 第一步 開啟1 pixel視窗
    public static Intent acceptCall_1(Context context) {
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClass(context, miniminiActivity.class);
        return i;
    }
    public static void acceptCall_2(Context context){//穿戴裝置接電話 第二步 接電話 已開啟1 pixel時接電話
        try {
            //Log.e("接電話接電話接電話接電話", "接電話1");
            Runtime.getRuntime().exec("input keyevent " +
                    Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
            //Log.e("接電話接電話接電話接電話", "接電話2");
        } catch (IOException e) {
            //Log.e("接電話CATCH接電話CATCH接電話", "接電話CATCH");
            // Runtime.exec(String) had an I/O problem, try to fall back
            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_HEADSETHOOK));
            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_HEADSETHOOK));
            context.sendOrderedBroadcast(btnDown, enforcedPerm);
            context.sendOrderedBroadcast(btnUp, enforcedPerm);
        }
    }

    static boolean listenerStart = false;//這給phoneStatelistener電話監聽用，只執行一次就會持續監聽，不用再繼續進入

    //開啟電話監聽
    public void phoneStatelistener() {
        Log.d("電話監聽", "onCreate 前");//手機響就送0x01 0x00給手錶
        if(!listenerStart){
            Log.d("電話監聽", "onCreate後");//手機響就送0x01 0x00給手錶
            final TransData transData = new TransData();
            TelephonyManager telephonyManager = (TelephonyManager) phoneContext.getSystemService(Context.TELEPHONY_SERVICE);
            PhoneStateListener listener = new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber){
                    switch (state){
                        case TelephonyManager.CALL_STATE_RINGING://手機響
                            OutputStream os = null;
                            try {
                                os = phoneContext.openFileOutput("phoneList", MODE_APPEND);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            transData.PhoneRun( (byte) 1,incomingNumber);  //1表示辨識碼id= 0x01 手機響傳送號碼給手錶
                            Log.d("手機正在響", incomingNumber);//手機響就送0x01 0x00給手錶
                            try {
                                transData.Hang(1);
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                            break;
                        case TelephonyManager.CALL_STATE_IDLE://這是掛掉，手機掛掉，手錶掛掉  全都送0x02 0x00給手錶停止震動指令
                            Log.v("手機超閒  ", incomingNumber);
                            try {
                                transData.Hang(2);
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                            break;
                        case TelephonyManager.CALL_STATE_OFFHOOK://這是我從手機按撥出 , 還有手機接電話，手錶接電話 全都送0x02 0x00給手錶停止震動指令
                            Log.v("手機接了  ", incomingNumber);
                            try {
                                transData.Hang(3);
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }
                    super.onCallStateChanged(state, incomingNumber);
                }
            };
            telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
            //listenerStart = true;
        }
    }

}
