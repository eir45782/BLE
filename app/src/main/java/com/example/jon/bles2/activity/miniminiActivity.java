package com.example.jon.bles2.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.jon.bles2.service.BluetoothLeService;
import com.example.jon.bles2.Phone;

public class miniminiActivity extends AppCompatActivity {
    Context context;

     @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         final Window window = getWindow();
         window.setGravity(Gravity.LEFT | Gravity.TOP);
         final WindowManager.LayoutParams params = window.getAttributes();//這段代碼要放在setContentView()前要放在setContentView( )前面要放在setContentView( )前面
         params.x=0;
         params.y=1;
         params.height=1;
         params.width=1;
         //setContentView(R.layout.minimini_activity);

         params.flags |=WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;//鎖螢幕時執行ACTIVITY用這個代碼
         window.setAttributes(params);

         Log.e("minimini", "111111111111");
         //autoAnswerPhone(context,telephonyManager);
         try {
             Log.e("minimini", "2222222222");
             Phone.acceptCall_2(context);
         }catch (NullPointerException e){
             e.printStackTrace();
         }
         Log.e("minimini", "3333333333");
         //finish();
         Log.e("minimini", "4444444444");
     }

     @Override
    public void onResume(){
         super.onResume();
         //finish();

     }
 /*   static public ITelephony getITelephony(TelephonyManager telMgr)
            throws Exception {
        Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod(
                "getITelephony");
        getITelephonyMethod.setAccessible(true);// 私有化函数也能使用
        return (ITelephony) getITelephonyMethod.invoke(telMgr);
    }
    public static void autoAnswerPhone(Context c,TelephonyManager tm) {
        try {
            Log.i("ssss", "autoAnswerPhone");
            ITelephony itelephony = getITelephony(tm);
            // itelephony.silenceRinger();
            itelephony.answerRingingCall();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Log.e("ssss", "用于Android2.3及2.3以上的版本上");
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                c.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                intent = new Intent("android.intent.action.MEDIA_BUTTON");
                keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                c.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent1.putExtra("state", 1);
                localIntent1.putExtra("microphone", 1);
                localIntent1.putExtra("name", "Headset");
                c.sendOrderedBroadcast(localIntent1,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent2.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent1);
                c.sendOrderedBroadcast(localIntent2,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent3.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent2);
                c.sendOrderedBroadcast(localIntent3,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent4.putExtra("state", 0);
                localIntent4.putExtra("microphone", 1);
                localIntent4.putExtra("name", "Headset");
                c.sendOrderedBroadcast(localIntent4,
                        "android.permission.CALL_PRIVILEGED");
            } catch (Exception e2) {
                e2.printStackTrace();
                Intent meidaButtonIntent = new Intent(
                        Intent.ACTION_MEDIA_BUTTON);
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                c.sendOrderedBroadcast(meidaButtonIntent, null);
            }
        }
    }*/
}
