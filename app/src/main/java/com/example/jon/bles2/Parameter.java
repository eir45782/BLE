package com.example.jon.bles2;

import android.widget.EditText;

public class Parameter {
    //圖片START
    public static int message = 0; //訊息種類  0: text  1: call  2: mail  3: line   4: fb
    public static int MMflag=0;//傳圖指數
    public static int MMSflag = 0 ;//訊息指數
    public static int timeNo = 1;//時間存在手錶的位置
    public static int timeType = 0;//時間存在手錶的格式
    public static int faceNo = 0;//圖片存在手錶的位置
    public static int picNo = 23;//按鈕按下從picNo =0 開始顯示
    public static int PicH082No = 15;

    public static byte visible,x= (byte)40,y=(byte)80;//手錶時間座標初始位置

    public static int pixelWidth =240;
    public static int pixelHeight;//pixelHeight 隨不同產品會改變

    //message 通知改的參數
   /* public static   float textSize = 16;  //字大小
    public static  int messageWidth = 104;//訊息框
    public static int messageSide = 8;//訊息邊框
    public static int messageHeight = 104;//訊息框
    public static int turn = 1;//0表示旋轉9度 , 1 表示不旋轉
    public static int Flash_index = 180;//傳送到手錶的記憶體位置*/

    public static   float textSize = 16;  //訊息通知字大小
    public static   float textSize2 = 16;  //路名字大小
    public static  int messageWidth = 160;//訊息框
    public static int messageSide = 16;//訊息邊框
    public static int messageHeight = 80;//訊息框
    public static int turn = 1;//0表示旋轉9度 , 1 表示不旋轉
    public static int Flash_index = 30;//傳送到手錶的記憶體位置
    public static int BlePackageByte = 20;//藍牙單次封包資料量參數  詳情
}
