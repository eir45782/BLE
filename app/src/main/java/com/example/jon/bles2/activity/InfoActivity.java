package com.example.jon.bles2.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.example.jon.bles2.Parameter;
import com.example.jon.bles2.R;
import com.example.jon.bles2.TransData;

public class InfoActivity extends AppCompatActivity {
    Handler handler;
    TextView Speed_Now, Speed_Max, Speed_Avr;
    TextView Cadence_Now, Cadence_Max, Cadence_Avr;
    TextView Heartrate_Now, Heartrate_Max, Heartrate_Avr;
    Button info_run;
    //ImageView Speed, Content, HeartRate;

    public static int MMSFlag=0; //傳字用多執行續旗標 add 20210224
    TransData transData = new TransData();

    byte[] Speed_Data = { // km/hr
            5, 8, 13, 20, 26,
            31, 29, 27, 30, 31,
            29, 30, 28, 31, 30
    };
    byte[] Cadence_Data = { // rpm
            10, 16, 26, 40, 52,
            62, 58, 54, 60, 62,
            58, 60, 54, 62, 30
    };
    byte[] Heartrate_Data = { // rpm
            60, 65, 63, 70, 72,
            75, 74, 78, 80, 77,
            75, 78, 79, 77, 76
    };

    byte Speed_Max_Data = Speed_Data[0], Cadence_Max_Data = Cadence_Data[0], Heartrate_Max_Data = Heartrate_Data[0];
    int Speed_sum_Data = 0, Cadence_sum_Data = 0, Heartrate_sum_Data = 0;
    byte Speed_Avr_Data = 0, Cadence_Avr_Data = 0, Heartrate_Avr_Data = 0;
    int count_number = 0;

    byte state = 0;
    int i = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Info Demo");
        setContentView(R.layout.activity_info_demo);

        findViews();

        info_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.Info_Thread newinfoRun = new InfoActivity.Info_Thread();
                i = -1;

                if (state == 0) {
                    state = 1;
                    info_run.setText("stop");

                    newinfoRun.start();
                    Log.e("newinfoRun.start","run");
                }
                else {
                    state = 0; i = 0; count_number = 0;
                    info_run.setText("start");
                    Speed_sum_Data = 0; Cadence_sum_Data = 0; Heartrate_sum_Data = 0;
                    Speed_Avr_Data = 0; Cadence_Avr_Data = 0; Heartrate_Avr_Data = 0;
                    Speed_Max_Data = 0; Cadence_Max_Data = 0; Heartrate_Max_Data = 0;

                    String text_info = "0";
                    Speed_Now.setText(text_info);
                    Speed_Max.setText(text_info);
                    Speed_Avr.setText(text_info);

                    Cadence_Now.setText(text_info);
                    Cadence_Max.setText(text_info);
                    Cadence_Avr.setText(text_info);

                    Heartrate_Now.setText(text_info);
                    Heartrate_Max.setText(text_info);
                    Heartrate_Avr.setText(text_info);
                    Log.e("newinfoRun.stop","end runing");
                }
            }
        });

        // message handler MVP architecture
        handler = new Handler(){
            public void handleMessage(Message msg) {
                change_view();
            }
        };
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            state = 0;
            ConfirmExit();//按返回鍵，則執行退出確認
            Log.e("Back key event","key down");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit(){//退出確認
        AlertDialog.Builder ad = new AlertDialog.Builder(InfoActivity.this);
        ad.setTitle("離開");
        ad.setMessage("確定要離開 Info Demo 嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                InfoActivity.this.finish();//關閉activity
                transData.GoogleMapData((byte)2,(byte)3,(byte)0,(byte)0); // Exit Navigation Mode
            }
        });
        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
    }

    private void findViews() {
        Speed_Now = (TextView) findViewById(R.id.Speed1);
        Speed_Max = (TextView) findViewById(R.id.Speed2);
        Speed_Avr = (TextView) findViewById(R.id.Speed3);

        Cadence_Now = (TextView) findViewById(R.id.Cadence1);
        Cadence_Max = (TextView) findViewById(R.id.Cadence2);
        Cadence_Avr = (TextView) findViewById(R.id.Cadence3);

        Heartrate_Now = (TextView) findViewById(R.id.heartrate1);
        Heartrate_Max = (TextView) findViewById(R.id.heartrate2);
        Heartrate_Avr = (TextView) findViewById(R.id.heartrate3);

        info_run = (Button) findViewById(R.id.button2);
    }

    private void change_view(){
        String text_info = "" + Speed_Data[i];
        Speed_Now.setText(text_info);
        text_info = "" + Cadence_Data[i];
        Cadence_Now.setText(text_info);
        text_info = "" + Heartrate_Data[i];
        Heartrate_Now.setText(text_info);

        text_info = "" + Speed_Max_Data;
        Speed_Max.setText(text_info);
        text_info = "" + Cadence_Max_Data;
        Cadence_Max.setText(text_info);
        text_info = "" + Heartrate_Max_Data;
        Heartrate_Max.setText(text_info);

        text_info = "" + Speed_Avr_Data;
        Speed_Avr.setText(text_info);
        text_info = "" + Cadence_Avr_Data;
        Cadence_Avr.setText(text_info);
        text_info = "" + Heartrate_Avr_Data;
        Heartrate_Avr.setText(text_info);
    }

    /* BLE Send Commend */
    private void BLE_SendMessage() {
        //傳距離 方向
        transData.GoogleMapData((byte)1,(byte)0,(byte)0,Speed_Data[i]);
        transData.GoogleMapData((byte)1,(byte)0,(byte)1,Speed_Avr_Data);
        transData.GoogleMapData((byte)1,(byte)0,(byte)2,Speed_Max_Data);

        transData.GoogleMapData((byte)1,(byte)1,(byte)0,Cadence_Data[i]);
        transData.GoogleMapData((byte)1,(byte)1,(byte)1,Cadence_Avr_Data);
        transData.GoogleMapData((byte)1,(byte)1,(byte)2,Cadence_Max_Data);

        transData.GoogleMapData((byte)1,(byte)2,(byte)0,Heartrate_Data[i]);
        transData.GoogleMapData((byte)1,(byte)2,(byte)1,Heartrate_Avr_Data);
        transData.GoogleMapData((byte)1,(byte)2,(byte)2,Heartrate_Max_Data);
    }

    class Info_Thread extends Thread { // 新的執行緒
        public void run() { // 新執行緒要執行的內容
            try {
                while (state == 1) { // 不斷執行
                    i++;
                    count_number++;
                    if (i >= Speed_Data.length || i < 0) {
                        i = 0;
                    }

                    if (i < 0) {
                        Log.e("newinfoRun.stop","end runing");
                        break;
                    }

                    Log.e("newinfoRun.start","still runing");

                    if (Speed_Data[i] > Speed_Max_Data) {
                        Speed_Max_Data = Speed_Data[i];
                    }
                    Speed_sum_Data += Speed_Data[i];
                    Speed_Avr_Data = (byte) (Speed_sum_Data / count_number);

                    if (Cadence_Data[i] > Cadence_Max_Data) {
                        Cadence_Max_Data = Cadence_Data[i];
                    }
                    Cadence_sum_Data += Cadence_Data[i];
                    Cadence_Avr_Data = (byte) (Cadence_sum_Data / count_number);

                    if (Heartrate_Data[i] > Heartrate_Max_Data) {
                        Heartrate_Max_Data = Heartrate_Data[i];
                    }
                    Heartrate_sum_Data += Heartrate_Data[i];
                    Heartrate_Avr_Data = (byte) (Heartrate_sum_Data / count_number);

                    handler.sendEmptyMessage(0);

                    BLE_SendMessage();

                    sleep(2000);
                }
            } catch (InterruptedException e) {
                Log.e("Info Thread stop", "Exception");
            }
        }
    }
}
