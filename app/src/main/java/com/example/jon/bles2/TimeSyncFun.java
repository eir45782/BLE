package com.example.jon.bles2;


import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import static android.content.ContentValues.TAG;

//校正手機時間給手錶
public class TimeSyncFun{
    public static final UUID CURRENT_TIME_SERVICE = UUID.fromString("00001805-0000-1000-8000-00805f9b34fb");
    public static final UUID CURRENT_TIME_CHARACTERISTIC = UUID.fromString("00002A2B-0000-1000-8000-00805f9b34fb");

    public byte[] getCurrentTimeInBytes(){
        byte[] bytes = new byte[10];
        Calendar c = new GregorianCalendar();
        bytes[0]=(byte)(c.get(Calendar.YEAR)& 0xFF);
        bytes[1]=(byte)(c.get(Calendar.YEAR)>>8);
        bytes[2]=(byte)(c.get(Calendar.MONTH)+1);
        bytes[3]=(byte)(c.get(Calendar.DAY_OF_MONTH));
        bytes[4]=(byte)(c.get(Calendar.HOUR_OF_DAY));
        bytes[5]=(byte)(c.get(Calendar.MINUTE));
        bytes[6]=(byte)(c.get(Calendar.SECOND));
       // Log.w(TAG, "Calendar.DAY_OF_WEEK : " +(byte) c.get(Calendar.DAY_OF_WEEK));
        switch( c.get(Calendar.DAY_OF_WEEK)){
            case 1:
                bytes[7]=(byte) 0x07;
                break;
            case 2:
                bytes[7]=(byte) 0x01;
                break;
            case 3:
                bytes[7]=(byte) 0x02;
                break;
            case 4:
                bytes[7]=(byte) 0x03;
                break;
            case 5:
                bytes[7]=(byte) 0x04;
                break;
            case 6:
                bytes[7]=(byte) 0x05;
                break;
            case 7:
                bytes[7]=(byte) 0x06;
                break;
            default:
                bytes[7]=(byte) 0x00;
                break;
        }
        bytes[8]=(byte) 0x00;
        bytes[9]=(byte) 0x01;//時間更新

       // Log.w(TAG, "byte" +" "+ bytes[0] +" "+ bytes[1]+" "+ bytes[2]+" "+ bytes[3]+" "+ bytes[4]+" "+ bytes[5]+" "+ bytes[6]+" "+ bytes[7]+" "+ bytes[8]
                //+" "+ bytes[9]);
        return bytes;
    }
}
