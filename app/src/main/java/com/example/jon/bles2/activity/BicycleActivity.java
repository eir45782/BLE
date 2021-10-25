package com.example.jon.bles2.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;

import com.example.jon.bles2.AllString;
import com.example.jon.bles2.Parameter;
import com.example.jon.bles2.PermissionsChecker;
import com.example.jon.bles2.Phone;
import com.example.jon.bles2.R;
import com.example.jon.bles2.TransData;
import com.example.jon.bles2.service.BluetoothLeService;
import com.example.jon.bles2.service.NotificationService;

import java.lang.System;
import java.util.ArrayList;

public class BicycleActivity extends AppCompatActivity{
    TextView textView_info;
    Button Auto_select;
    ImageButton s_prev, s_next;
    ImageView img;

    Handler handler;
    public static int MMSFlag=0; //傳字用多執行續旗標 add 20210224
    TransData transData = new TransData();

    int i = -1;

    int[] maps = {
            R.drawable.map01, R.drawable.map02, R.drawable.map03, R.drawable.map04, R.drawable.map05,
            R.drawable.map06, R.drawable.map07, R.drawable.map08, R.drawable.map09, R.drawable.map10,
            R.drawable.map11, R.drawable.map12, R.drawable.map13, R.drawable.map14, R.drawable.map15,
    };

    short[] distance = {
            400, 0, 150, 400, 0,
            300, 200, 0, 100, 0,
            0, 200, 0, 150, 0
    };

    //distance high byte low byte in onCreate add 洪正傑 20210302
    byte[] highbyte_distance = new byte[distance.length];
    byte[] lowbyte_distance = new byte[distance.length];

    byte[] direction = {
            3, 3, 1, 3, 3,
            1, 2, 2, 1, 2,
            3, 1, 2, 1, 5
    };

    String[] Road_Name = {
            "力行二路", "力行二路", "力行路", "園區一路", "園區一路",
            "園區三路", "園區三路", "園區三路", "園區二路", "園區二路",
            "工業東四路", "工業東四路", "工業東四路", "工業東三路", "工業東三路"
    };

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

    String text_info = "Bicycle Demo\nL3B → GRC";
    String direct = "None";
    byte state = 0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bicycle Demo");
        setContentView(R.layout.activity_bicycle_demo);
        findViews();

        img.setImageResource(R.drawable.map00);
        textView_info.setText(text_info);
        // ble data for distance high low byte

        for(int k=0;k< distance.length ; k++){
            highbyte_distance[k]= (byte)(distance[k] >> (byte)8);
            lowbyte_distance[k] = (byte) (distance[k]);
        }
        //for(int x=0;x<distance.length ;x++)
            //Log.e("newAutoRun.start", "幹high byte = " + highbyte_distance[x]);


        transData.GoogleMapData((byte)2,(byte)0,(byte)4,(byte)1);//total distance 1.4km  程式啟動第一張圖的總距離 20210302   add by 洪正傑

        Auto_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoRunThread newAutoRun = new AutoRunThread();
                i = -1;
                img.setImageResource(R.drawable.map00);

                if (state == 0) {
                    text_info = "Auto Run\nReStart";
                    textView_info.setText(text_info);

                    state = 1;
                    Auto_select.setText("Non-Auto");

                    newAutoRun.start();
                    Log.e("newAutoRun.start","run");
                }
                else {
                    text_info = "Bicycle Demo\nL3B → GRC";
                    textView_info.setText(text_info);

                    state = 0;
                    Auto_select.setText("Auto Run");
                    //  ble
                }
            }
        });

        s_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1) {state = 0; Auto_select.setText("Auto Run");}

                i--;
                if (i < 0){ i = 0;}
                //img.setImageResource(maps[i]);
                //System.out.println("distance: " + distance[i]);
                //System.out.println("direction: " + direction[i]);
                switch (direction[i])
                {
                    case 1:
                        direct = "繼續直行";
                        break;
                    case 2:
                        direct = "向右轉";
                        break;
                    case 3:
                        direct = "向左轉";
                        break;
                    default:
                        direct = "抵達目地";
                        break;
                }

                text_info = "前方" + distance[i] + "公尺處  " + direct + "\n" + Road_Name[i];
                //textView_info.setText(text_info);
                change_view();

                // Test for Bluetooth send data // Jason add on 2021/02/19
                //transData.PhoneRunOrder((byte) 4,(byte)3,Road_Name[i]);

                BLE_SendMessage();

            }
        });

        s_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1) {state = 0; Auto_select.setText("Auto Run");}

                i++;
                if (i >= maps.length){ i = maps.length - 1;}
                //img.setImageResource(maps[i]);
                //System.out.println("distance: " + distance[i]);
                //System.out.println("direction: " + direction[i]);
                switch (direction[i])
                {
                    case 1:
                        direct = "繼續直行";
                        break;
                    case 2:
                        direct = "向右轉";
                        break;
                    case 3:
                        direct = "向左轉";
                        break;
                    default:
                        direct = "抵達目地";
                        break;
                }

                text_info = "前方" + distance[i] + "公尺處  " + direct + "\n" + Road_Name[i];
                //textView_info.setText(text_info);
                change_view();

                BLE_SendMessage();
            }
        });

        // message handler MVP architecture
        handler = new Handler(){
            public void handleMessage(Message msg) {
                change_view();
            }
        };

    } //* END - onCreate*//

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
        AlertDialog.Builder ad = new AlertDialog.Builder(BicycleActivity.this);
        ad.setTitle("離開");
        ad.setMessage("確定要離開 Bicycle Demo 嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                BicycleActivity.this.finish();//關閉activity
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
        textView_info = (TextView) findViewById(R.id.textView_info);
        Auto_select = (Button) findViewById(R.id.Auto_Select);
        s_prev=(ImageButton)findViewById(R.id.prev_button);
        s_next=(ImageButton)findViewById(R.id.next_button);
        img = (ImageView) findViewById(R.id.imageView);
    }

    private void change_view() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), maps[i]);
        img.setImageBitmap(bitmap);
        //bitmap.recycle();
        textView_info.setText(text_info);
    }

    /* BLE Send Commend */
    private void BLE_SendMessage() {
        // Test for Bluetooth send data // Jason add on 2021/02/19
        //transData.PhoneRunOrder((byte) 4, (byte) 3, distance[i]);
        //transData.PhoneRunOrder((byte) 4, (byte) 3, direction[i]);
        //transData.PhoneRunOrder((byte) 4, (byte) 3, Road_Name[i]);
        //傳距離 方向
        transData.GoogleMapData((byte)2,(byte)1,lowbyte_distance[i],highbyte_distance[i]);//distance
        transData.GoogleMapData((byte)2,(byte)2,direction[i],(byte)0);//direction

        //洪正傑 20210224 傳字
        String s = Road_Name[i];
        Bitmap NAMEText = transData.createChinese2(Parameter.textSize2, s, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);
        chineseThread NAMETrans = new chineseThread(NAMEText);
        if(MMSFlag==0){//正在傳送名字時 MMSFlag = 1 , 此時按按鈕無法再次傳送,直到傳完清0,再次按按鈕才有辦法傳
            MMSFlag = 1;  
            NAMETrans.start();
        }

        transData.GoogleMapData((byte)1,(byte)0,(byte)0,Speed_Data[i]);
        transData.GoogleMapData((byte)1,(byte)1,(byte)0,Cadence_Data[i]);
        transData.GoogleMapData((byte)1,(byte)2,(byte)0,Heartrate_Data[i]);
    }

    class AutoRunThread extends Thread { // 新的執行緒
        public void run() { // 新執行緒要執行的內容
            try {
                while(state == 1) { // 不斷執行
                    i++;
                    if (i >= maps.length || i < 0){
                        i = -1;
                        break;
                    }

                    //img.setImageResource(maps[i]);
                    //System.out.println("distance: " + distance[i]);
                    //System.out.println("direction: " + direction[i]);
                    switch (direction[i])
                    {
                        case 1:
                            direct = "繼續直行";
                            break;
                        case 2:
                            direct = "向右轉";
                            break;
                        case 3:
                            direct = "向左轉";
                            break;
                        default:
                            direct = "抵達目地";
                            break;
                    }

                    //System.out.println("distance: " + distance[i]);
                    //System.out.println("direction: " + direction[i]);
                    //System.out.println("i: " + i);

                    text_info = "前方" + distance[i] + "公尺處  " + direct + "\n" + Road_Name[i];
                    System.out.println("text_info: " + text_info);
                    //textView_info.setText(text_info);
                    handler.sendEmptyMessage(0);
                    transData.GoogleMapData((byte)2,(byte)2,direction[i],(byte)0); //control mode , control index
                    BLE_SendMessage();

                    sleep(2000);
                }
            }
            catch(InterruptedException e) {
                Log.e("AutoRunThread stop","Exception");
            }
        }
    }

    //多執行緒傳字  洪正傑 20210224
    public class chineseThread extends Thread{
        private Bitmap bitmap;
        public chineseThread(Bitmap bitmap){//這方法可以讓自己可以傳入值
            this.bitmap = bitmap;
        }
        @Override
        public void run() {
            super.run();
            Parameter.Flash_index = 29; //送出文字圖片
            Log.e("Thread start","999");
            transData.testChineseTransmission(transData.rgbDataString(bitmap));//縮小容量,又裁減成240*80
            Log.e("Thread end", "999");
            Parameter.Flash_index =30; //改回訊息通知
            MMSFlag=0;  //
        }
    }
}
