package com.example.jon.bles2;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.example.jon.bles2.activity.StringAndPic;
import com.example.jon.bles2.service.BluetoothLeService;
import com.example.jon.bles2.service.BluetoothLeService_Car;
import com.example.jon.bles2.service.bluetoothUUIDS.UUIDS;

import java.util.UUID;

import static com.example.jon.bles2.activity.StringAndPic.faceNo;
import static com.example.jon.bles2.activity.StringAndPic.flashNo;
import static com.example.jon.bles2.activity.StringAndPic.mBluetoothLeService;
import static com.example.jon.bles2.activity.StringAndPic.mBluetoothLeService_Car;

public class TransData {
    private final static String TAG = "TransData";

    //透過藍芽寫入資料到別的裝置
    public void DataWrite(UUID serviceUUID, UUID characteristicUUID, byte[] value) {
        if (mBluetoothLeService.mBluetoothGatt != null) {
            BluetoothGattService service = mBluetoothLeService.geGattServices(serviceUUID);

            if (service == null) {

                Log.e(TAG, "Service is null transdata");
                return;
            }

            BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
            if (characteristic == null) {
                Log.e("CHECK", "Characteristic is null");
                return;
            }
            if(BluetoothLeService.mConnected == false) {
                Log.e("CHECK", "斷線狀態   不準寫入");
                return;
            }
            //characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            characteristic.setValue(value);
            while (!mBluetoothLeService.writeCharacteristic(characteristic)) {

                           /* for (int i = 0; i < 10000; i++) {
                                    for (int k = 0; k < 10000; k++) {
                                    }
                              }*/
            }

        } else {
            Log.e(TAG, "BluetoothGatt is null");
        }
    }

    //透過藍芽寫入資料到別的裝置
    public void DataWrite_Car(UUID serviceUUID, UUID characteristicUUID, byte[] value) {
        if (mBluetoothLeService_Car.mBluetoothGatt != null) {
            BluetoothGattService service = mBluetoothLeService_Car.geGattServices(serviceUUID);

            if (service == null) {

                Log.e(TAG, "Service CAR is null transdata");
                return;
            }

            BluetoothGattCharacteristic characteristic = service.getCharacteristic(characteristicUUID);
            if (characteristic == null) {

                Log.e("CHECK", "CAR Characteristic is null");
                //Log.e("service", String.format("service=%d,", service));
                return;
            }
            if(BluetoothLeService_Car.mConnected == false) {
                Log.e("CHECK CAR", "斷線狀態   不準寫入");
                return;
            }
            //characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            characteristic.setValue(value);
            while (!mBluetoothLeService_Car.writeCharacteristic(characteristic)) {

                           /* for (int i = 0; i < 10000; i++) {
                                    for (int k = 0; k < 10000; k++) {
                                    }
                              }*/
            }

        } else {
            Log.e(TAG, "Car BluetoothGatt is null");
        }
    }

    //id =29  洪正傑 20210226
    public void GoogleMapData(byte ContorlMode,byte ControlIndex ,byte Data1, byte Data2){
        byte[] databyte_process;
        int wp;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Map;

        if(ContorlMode !=2 || ControlIndex!=2)  //判斷陣列要不要多一個位置
            wp=1;
        else
            wp=0;

        databyte_process = new byte[3+wp];

        databyte_process[0] =  ContorlMode;
        databyte_process[1] =  ControlIndex;//type
        databyte_process[2] =  Data1;//
        if(ContorlMode !=2 || ControlIndex!=2)
          databyte_process[3] =  Data2;
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    //輸入號碼紀錄到手錶 id= 0x01 , 儲存號碼到手錶 id=0x04, order 4個聯絡人順序1~4
    public void PhoneRunOrder(byte id,byte order,String phoneNumber) {
        byte[] databyte_array;
        byte[] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Read;
        databyte_array = phoneNumber.getBytes();  //電話string轉成ASCII  BYTE
        databyte_process = new byte[databyte_array.length+3];
        for(int i=0 ;i<databyte_array.length;i++){
            databyte_process[i+3] = databyte_array[i];   //將電話號碼往後移兩個位元
        }
        databyte_process[0] =  id;//給手錶辨識碼
        databyte_process[1] =  order;//聯絡人順序
        databyte_process[2] = (byte) databyte_array.length;//給手錶辨識電話長度
        //Log.e("手機寫入手錶資料", String.format("data[0]=%d,data[1]=%d,data[2]=%d,data[3]=%d,data[4]=%d,data[5]=%d,",
        //        databyte_process[0], databyte_process[1], databyte_process[2],
        //        databyte_process[3],databyte_process[4],databyte_process[5]));
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    public void PhoneRun(byte id,String phoneNumber) {//來電號碼透過藍牙給手錶 id= 0x01 , 儲存號碼到手錶 id=0x04
        byte[] databyte_array;
        byte[] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Read;
        databyte_array = phoneNumber.getBytes();  //電話string轉成ASCII  BYTE
        databyte_process = new byte[databyte_array.length+2];
        for(int i=0 ;i<databyte_array.length;i++){
            databyte_process[i+2] = databyte_array[i];   //將電話號碼往後移兩個位元
        }
        databyte_process[0] =  id;//給手錶辨識碼
        databyte_process[1] = (byte) databyte_array.length;//給手錶辨識電話長度
        //Log.e("手機寫入手錶資料", String.format("data[0]=%d,data[1]=%d,data[2]=%d,data[3]=%d,data[4]=%d,data[5]=%d,",
        //       databyte_process[0], databyte_process[1], databyte_process[2],
        //      databyte_process[3],databyte_process[4],databyte_process[5]));
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    public void Hang(int x) {// 給1電話響震動   給2掛電話停止震動   給3掛電話停止震動
        byte[] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Read;
        databyte_process = new byte[2];
        databyte_process[0] = (byte) x;
        databyte_process[1] = (byte) 0x00;
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    //傳時間位置給手錶 及時改變
    public void moveWatchTime(int watchNo,int x,int y ,byte type ,byte visible){
        byte [] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Image;
        databyte_process = new byte[6];
        databyte_process[0] = (byte) 31;//怡婷姊那邊船位置用的固定參數
        databyte_process[1] = (byte) watchNo;//1~6
        databyte_process[2] = (byte) x;
        databyte_process[3] = (byte) y;
        databyte_process[4] = (byte) type;
        databyte_process[5] = (byte) visible;
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    //傳通知訊息給手錶
    public void testChineseTransmission(byte[] rgbDataString) {
        byte[] line;
        byte[] databyte_process;
        String databuffer;
        int pic_width = Parameter.messageWidth/8;  //檔案壓縮8倍
        int pic_height = Parameter.messageHeight;   //32;  //8;
        int data_index = 0;     //8;
        int total_data = pic_width * pic_height;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Notification;
        //databyte_process = new byte[pic_width*20 + 3];//單次藍芽封包資料量 BYTE   for 160( 20 ) x 80    一個封包 20 x 20 + 3   AUO WRBL
        databyte_process = new byte[pic_width * Parameter.BlePackageByte + 3];//單次藍芽封包資料量 BYTE for 104 ( 13 ) x104   一個封包 13 x 26 + 3  AUO HYBIRD
        while (data_index < total_data) {
            for (int write_line = 0; write_line < pic_height/Parameter.BlePackageByte; write_line++) {
                for (int i = 0; i < pic_width*Parameter.BlePackageByte; i++) {
                    databyte_process[0] = (byte) Parameter.Flash_index;//參數固定30 , 1801
                    databyte_process[1] = (byte) StringAndPic.message;  //TEXT,CALL,MAIL,LINE,FB
                    databyte_process[2] = (byte) write_line;
                    databyte_process[3 + i] = (byte) rgbDataString[data_index];
                    //Log.e("rgbdata", String.format("rgbDataString[%d]=%d",  data_index,rgbDataString[data_index]));
                    data_index++;
                }
                //Log.e("關鍵字", String.format("data[0]=%d,data[1]=%d", databyte_process[0], databyte_process[1]));
                DataWrite(serviceUUID, characteristicUUID, databyte_process);
            }
        }
    }


    //OASIS-04 按鈕
    public void buttonProtocol(int button,int Device){

        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.U082Button;
        UUID serviceUUID_2 = UUIDS.UUID_AUOService_2;
        UUID characteristicUUID_2 = UUIDS.U082Button_2;
        byte[] databyte_process;
        databyte_process = new byte[2];
        databyte_process[0] = 1;
        databyte_process[1] = (byte) button;

        if(Device==1) {
            DataWrite(serviceUUID_2, characteristicUUID_2, databyte_process);
        }else if(Device ==2) {
            DataWrite_Car(serviceUUID_2, characteristicUUID_2, databyte_process);
            //DataWrite_Car(serviceUUID_2, characteristicUUID_2, databyte_process);
        }
    }

    //POC 按鈕
    public void POCbuttonProtocol(int button){

        UUID serviceUUID = UUIDS.POC_Service_UUID;
        UUID characteristicUUID = UUIDS.POC_DeviceControl_UUID;
        byte[] databyte_process;
        databyte_process = new byte[2];
        databyte_process[0] = 1;
        databyte_process[1] = (byte) button;

        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    /*  fuck dont understand
    public  class POCSensorData2{
        int controlMode;
        int controlIndex;
        int data1;
        int data2;
        byte[] databyte_process;
        public void sendSensorData(int ControlMode,int ControlIndex){
            this.controlMode = ControlMode;
            this.controlIndex = ControlIndex;
            UUID serviceUUID = UUIDS.POC_Service_UUID;
            UUID characteristicUUID = UUIDS.POC_SensorData_UUID;
            databyte_process = new byte[2];
            databyte_process[0] = (byte) ControlMode;
            databyte_process[1] = (byte) ControlIndex;
            DataWrite(serviceUUID, characteristicUUID, databyte_process);
        }

        public void sendSensorData(int ControlMode,int ControlIndex ,int Data1, int Data2){
            this.controlMode = ControlMode;
            this.controlIndex = ControlIndex;
            this.data1 = Data1;
            this.data2 = Data2;
            UUID serviceUUID = UUIDS.POC_Service_UUID;
            UUID characteristicUUID = UUIDS.POC_SensorData_UUID;
            databyte_process = new byte[2];
            databyte_process[0] = (byte) ControlMode;
            databyte_process[1] = (byte) ControlIndex;
            databyte_process[2] = (byte) Data1;
            databyte_process[3] = (byte) Data2;
            DataWrite(serviceUUID, characteristicUUID, databyte_process);
        }
    }
    */

    //POC 按鈕
    public void POCSensorData(int ControlMode,int ControlIndex ,int Data1, int Data2){
        UUID serviceUUID = UUIDS.POC_Service_UUID;
        UUID characteristicUUID = UUIDS.POC_SensorData_UUID;
        byte[] databyte_process = new byte[2];
        if(ControlMode==0){       //refreash POC value
            databyte_process = new byte[2];
            databyte_process[0] = (byte) ControlMode;
            databyte_process[1] = (byte) ControlIndex;
        }else if(ControlMode==1){  //sensor data
            databyte_process = new byte[4];
            databyte_process[0] = (byte) ControlMode;
            databyte_process[1] = (byte) ControlIndex;
            databyte_process[2] = (byte) Data1;
            databyte_process[3] = (byte) Data2;
        }


        DataWrite(serviceUUID, characteristicUUID, databyte_process);
    }

    public void EngImageProtocol() {// 傳一張圖要選下拉選單最大數量
        byte[] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Map;
        databyte_process = new byte[3];
        databyte_process[0] =  0;//ENG
        databyte_process[1] = (byte) faceNo;  //low byte
        databyte_process[2] = (byte) (faceNo>>8);  //high byte
        Log.e("關鍵字1", String.format("data[0]=%d,data[1]=%d,data[2]=%d", databyte_process[0], databyte_process[1],databyte_process[2]));
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
        DataWrite_Car(serviceUUID, characteristicUUID, databyte_process);
    }

    public void ATImageProtocol(int number) {// number 傳全部圖要先傳這個給最大數量
        byte[] databyte_process;
        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Map;
        databyte_process = new byte[3];
        databyte_process[0] =  1; //AT
        databyte_process[1] = (byte) number;  //low byte
        databyte_process[2] = (byte) (number>>8);  //high byte
        Log.e("關鍵字2", String.format("data[0]=%d,data[1]=%d,data[2]=%d", databyte_process[0], databyte_process[1],databyte_process[2]));
        DataWrite(serviceUUID, characteristicUUID, databyte_process);
        //DataWrite_Car(serviceUUID, characteristicUUID, databyte_process);
    }

    public void ImageTransmission(byte[] rgbData565) {//送底圖 小ICON 都用這個 ，寬高自己調整
        byte[] line;
        byte[] databyte_process;
        String databuffer;
        int pic_width = StringAndPic.pixelWidth*2;   //120;
        int pic_height = StringAndPic.pixelHeight;   //32;  //8;
        int data_index = 0;     //8;
        int total_data = StringAndPic.pixelWidth * 2 * StringAndPic.pixelHeight;

        UUID serviceUUID = UUIDS.UUID_AUOService;
        UUID characteristicUUID = UUIDS.UUID_AUO_Wearable_Image;
        databyte_process = new byte[pic_width + 2];//LINE的寬度
        //int readBar=0;
        //Message msg = Message.obtain();

        while (data_index < total_data) {
            for (int write_line = 0; write_line < pic_height; write_line++) {
                for (int i = 0; i < pic_width ; i++) {
                    databyte_process[0] = (byte) flashNo;//圖片在flash位置
                    databyte_process[1] = (byte) write_line;//第幾條line
                    //databyte_process[2] = (byte) timeNo;//時間位置
                    //databyte_process[3] = (byte) timeType;//時間字型
                    databyte_process[2 + i] = (byte) rgbData565[data_index];
                    data_index++;
                }
                Log.e("關鍵字3", String.format("data[0]=%d,data[1]=%d,data[2]=%d", databyte_process[0], databyte_process[1],databyte_process[2]));
                DataWrite(serviceUUID, characteristicUUID, databyte_process);
                //DataWrite_Car(serviceUUID, characteristicUUID, databyte_process);

                //readBar = 1;


                //msg.arg2 = readBar;
                //StringAndPic.picHandler.sendMessage(msg);
            }
        }
        //readBar = 0;
        //msg.arg2 = readBar;
        //StringAndPic.picHandler.sendMessage(msg);

    } //End - ImageTransmission

    //COP車錶專用  送3bit  1bit都用這個   插在data資料量不同
    public void ImageTransmission_COPCar(byte[] rgbData_3bit,int rgbFormat) {
        //rgbFormat :  rgb111  3bit輸入3    ,  rgb1bit 輸入1  1bit POC ICON去背專用
        int pic_width = StringAndPic.pixelWidth * rgbFormat/8;
        int total_data = (int) (StringAndPic.pixelWidth * rgbFormat/8 * StringAndPic.pixelHeight);
        Log.e("車錶傳你全家", String.format("pic_width=%d,rgbFormat=%d", pic_width,rgbFormat));
        int pic_height = StringAndPic.pixelHeight;   //208
        int data_index = 0;     //8;

        UUID serviceUUID = UUIDS.POC_Service_UUID;
        UUID characteristicUUID = UUIDS.POC_ImageTransfer_UUID;
        if(rgbFormat==1) {
            characteristicUUID = UUIDS.POC_1bitImageMessageTransfer_UUID;  //rgb 3 bits
        }


        byte[] databyte_process = new byte[pic_width+2];//LINE的寬度

        while (data_index < total_data) {
            for (int write_line = 0; write_line < pic_height; write_line++) {
                for (int i = 0; i < pic_width ; i++) {
                    databyte_process[0] = (byte) flashNo;//圖片在flash位置
                    databyte_process[1] = (byte) write_line;//第幾條line
                    databyte_process[2 + i] = (byte) rgbData_3bit[data_index];
                    data_index++;
                }

                //display decimal log
                int a[] = new int[pic_width+2];
                for(int j=0; j<pic_width+2;j++) {
                    a[j] = databyte_process[j] & 0xff;
                }
                Log.e("寬度40", String.format("line=%d,data[2]=%d,data[3]=%d"
                        ,write_line, a[2],a[3] ));

                DataWrite(serviceUUID, characteristicUUID, databyte_process);
            }
        }
    } //End - ImageTransmission_COPCar

    //文字轉圖片的data 160x80   , 104 x 104
    public byte[] rgbDataString(Bitmap bm1) {

        int width = bm1.getWidth();
        int height = bm1.getHeight();

        //修改BMP 長寬為240 *240,改完新圖名稱newbm1
        int newWidth = Parameter.messageWidth;
        int newHeight = Parameter.messageHeight;//隨文字越多框框高度越高，寬度不變固定
        //int newHeight =240/(width/height);//隨文字越多框框高度越高，寬度不變固定
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm1 = Bitmap.createBitmap(bm1, 0, 0, width, height, matrix, true); //新圖 newbm1 240*240
        //修改結束
        int width2 = newbm1.getWidth();
        int height2 = newbm1.getHeight();
        //byte[] rgbValues = new byte[width2 * height2 * 3];
        byte[] temp = new byte[width2 * height2];
        byte[] temp2 = new byte[width2 * height2];
        int k = 0;
        byte[] messagePic = new byte[width2 * height2];//整張圖含文字背景
        byte[] msgPic = new byte[width2 * height2];//縮減文字圖片大小
        for (int y = 0; y < height2; y++) {//文字圖分解成1和0
            for (int x = 0; x < width2; x++) {
                int pixel = newbm1.getPixel(x, y);
                int redPixel = (byte) Color.red(pixel);
                int greenPixel = (byte) Color.green(pixel);
                int bluePixel = (byte) Color.blue(pixel);

                if ((bluePixel == (byte) 255) && (redPixel == (byte) 0) && (greenPixel == (byte) 0))//文字 白色   , , ,綠色(7,224)  紅色(248,0) 藍色(0,31)
                {
                    messagePic[k] = (byte) 1;//字
                } else if ((bluePixel == (byte) 0) && (redPixel == (byte) 255) && (greenPixel == (byte) 0))//為了傳頭像    像素在128~255之間
                {
                    messagePic[k] = (byte) 0;   //背景
                } else if ((bluePixel <= (byte) 127) && (bluePixel >= (byte) 0) && (redPixel <= (byte) 127) && (redPixel >= (byte) 0) && (greenPixel <= (byte) 127) && (greenPixel >= (byte) 0))//為了傳頭像    像素在0~127之間
                {
                    messagePic[k] = (byte) 0;   //藍
                } else                              //其她都白色
                {
                    messagePic[k] = (byte) 1;//綠
                }
                k++;// k = 0 ~ 240*240*2 -1
            }
        }
        /*換硬體不要圖片反轉
        for (int i = 0; i < width2 * height2; i++) {   //圖片備份
            temp[i] = messagePic[i];
        }
        for (int j = width2 * height2 - 1; j >= 0; j--) {   //圖片上下左右反轉
            messagePic[j] = temp[(width2 * height2 - 1) - j];
        }
        */

        for (int kk = 0; kk < width2 * height2; kk++) {   //圖片備份
            temp[kk] = messagePic[kk];
        }

        //turn = 0訊息旋轉90度 , 默認turn = 1 不旋轉
        if(Parameter.turn==0){
            for (int j = 0; j < width2 ; j++) {// width =104 ,height = 104   圖片順時針轉90度
                for(int i = 0 ; i< height2 ; i++ ){
                    messagePic[j*width2+i] = temp[(height2-i)*width2 - (width2-1) +j-1];
                }
            }
        }


        for (int m = 0; m < (width2 / 8) * height2; m++)     //縮小16倍剩黑白 矩陣剩240*30   ,
        {
            msgPic[m] = (byte) (((messagePic[m * 8] << (byte) 7)) + ((messagePic[m * 8 + 1] << (byte) 6)) +
                    (messagePic[m * 8 + 2] << (byte) 5) + (messagePic[m * 8 + 3] << (byte) 4) +
                    (messagePic[m * 8 + 4] << (byte) 3) + (messagePic[m * 8 + 5] << (byte) 2) +
                    (messagePic[m * 8 + 6] << (byte) 1) + (messagePic[m * 8 + 7]));
        }
        return msgPic;
    }

    //RGB565
    public byte[] rgbData565(Bitmap bm1) {

        int width = bm1.getWidth();
        int height = bm1.getHeight();

        //修改BMP 長寬為240 *240,改完新圖名稱newbm1
        int newWidth = StringAndPic.pixelWidth;
        int newHeight = StringAndPic.pixelHeight;
        float scaleWidth = ((float) newWidth) / (float) width;
        float scaleHeight = ((float) newHeight) /(float) height;
        Matrix matrix = new Matrix();
        matrix.postScale( scaleWidth, scaleHeight);
        Bitmap newbm1 = Bitmap.createBitmap(bm1, 0, 0, width, height, matrix, true); //新圖 newbm1 240*240
        //修改結束

        int width2 = newbm1.getWidth();
        int height2 = newbm1.getHeight();
        byte[] rgbValues = new byte[width2 * height2 * 2]; //rgb 565
        //byte[] messagePic = new byte[width2 * height2 * 2];//整張圖含文字背景
        byte[] temp = new byte[width2 * height2 * 2];
        byte[] temp2 = new byte[width2 * height2 * 2];
        int k = 0;

        for (int y = 0; y < height2; y++) {
            for (int x = 0; x < width2; x++) {
                int pixel = newbm1.getPixel(x, y);
                byte redPixel = (byte) Color.red(pixel);
                byte greenPixel = (byte) Color.green(pixel);
                byte bluePixel = (byte) Color.blue(pixel);
                int redPixel_MSB = (redPixel & 0xf8);
                byte greenPixel_MSB = (byte) ((greenPixel & 0xfc) >> (byte) 0x05);
                byte greenPixel_LSB = (byte) (((greenPixel & 0xfc) << (byte) 3));
                byte bluePixel_MSB = (byte) ((bluePixel & 0xf8) >> (byte) 3);
                //rgbValues[k * 3] = (byte) (redPixel);//rgbValues[0],rgbValues[3]...rgbValues[240*240*3 - 3]
                //rgbValues[k * 3 + 1] = (byte) (greenPixel);//rgbValues[1],rgbValues[4]...rgbValues[240*240*3 - 2]
                //rgbValues[k * 3 + 2] = (byte) (bluePixel);//rgbValues[2],rgbValues[5]...rgbValues[240*240*3 - 1]
                rgbValues[k * 2] = (byte) (redPixel_MSB + greenPixel_MSB);//rgbValues[0],rgbValues[2]...rgbValues[240*240*2 - 2]  //BYTE1
                rgbValues[k * 2 + 1] = (byte) (greenPixel_LSB + bluePixel_MSB);//rgbValues[1],rgbValues[3]...rgbValues[240*240*2 - 1]    //BYTE2
                k++;// k = 0 ~ 240*240*2 -1
            }
        }
        /*換硬體不要圖片反轉
        for (int i = 0; i < width2 * height2 * 2; i++) {   //圖片備份
            temp[i] = rgbValues[i];
        }
        for (int j = width2 * height2 * 2 - 1; j >= 0; j--) {   //圖片上下左右反轉
            rgbValues[j] = temp[(width2 * height2 * 2 - 1) - j];
        }
        for (int m = 0; m < width2 * height2 * 2; m = m + 2) {   //RGB565 BYTE1 BYTE2 因反轉過再互換
            temp2[m] = rgbValues[m];//中繼站
            rgbValues[m] = rgbValues[m + 1];
            rgbValues[m + 1] = temp2[m];
        }
        */
        return rgbValues;
    }

    //RGB111  3bit 20210914  POC USE  可能做好了 還是還沒?  到底
    public byte[] rgbData_3bit(Bitmap bm1) {

        int width = bm1.getWidth();
        int height = bm1.getHeight();


        int newWidth = StringAndPic.pixelWidth;
        int newHeight = StringAndPic.pixelHeight;
        float scaleWidth = ((float) newWidth) / (float) width;
        float scaleHeight = ((float) newHeight) /(float) height;
        Matrix matrix = new Matrix();
        matrix.postScale( scaleWidth, scaleHeight);
        Bitmap newbm1 = Bitmap.createBitmap(bm1, 0, 0, width, height, matrix, true); //新圖 newbm1 240*240
        //修改結束

        int width2 = newbm1.getWidth();
        int height2 = newbm1.getHeight();
        byte[] rgbValues = new byte[width2 * height2*3/8]; //rgb 111   LINE width 208變78
        int k = 0;


        for (int y = 0; y < height2; y++) {
            int pixelFull;
            int rgb_8bit=0;//集滿8pixel就噴出
            int SubPixelCount = 23; //左移位元初始值

            for (int x = 0; x < width2; x++) {

                pixelFull = x % 8; //每8個pixel 塞滿3個byte一個週期
                int pixel = newbm1.getPixel(x, y);
                int redPixel = (int) Color.red(pixel);
                int greenPixel = (int) Color.green(pixel);
                int bluePixel = (int) Color.blue(pixel);
                byte redPixel_1bit;
                byte greenPixel_1bit;
                byte bluePixel_1bit;
                //Log.e("rgb111傳你爸", String.format("redPixel=%d,greenPixel=%d,bluePixel=%d", redPixel, greenPixel,bluePixel));
                if(redPixel > (byte)127){
                    redPixel_1bit = 1;
                }else{
                    redPixel_1bit = 0;
                }
                if(greenPixel >(byte) 127){
                    greenPixel_1bit = 1;
                }else{
                    greenPixel_1bit = 0;
                }
                if(bluePixel >(byte) 127){
                    bluePixel_1bit = 1;
                }else{
                    bluePixel_1bit = 0;
                }

                int red = (redPixel_1bit & 0x01) << SubPixelCount ;
                SubPixelCount--;
                int green = (greenPixel_1bit & 0x01) << SubPixelCount;
                SubPixelCount--;
                int blue = (bluePixel_1bit & 0x01) << SubPixelCount ;
                SubPixelCount--;

                rgb_8bit |= red | green | blue;

                if(pixelFull == 7){
                    SubPixelCount = 23; //rgbValues滿 重製次數

                    rgbValues[k * 3] =(byte)( rgb_8bit >> 16);
                    rgbValues[k * 3 + 1] = (byte)( rgb_8bit >> 8);
                    rgbValues[k * 3 + 2] = (byte)( rgb_8bit );
                    k++;
                    rgb_8bit = 0;//重製3 BYTE 8個PIXEL 資料
                    // k = 0 ~ 208*208/8 -1
                }
            }
        }
        return rgbValues;
    }

    //RGB 1bit 20210923   POC USE
    public byte[] rgbData_1bit(Bitmap bm1) {

        int width = bm1.getWidth();
        int height = bm1.getHeight();


        int newWidth = StringAndPic.pixelWidth;
        int newHeight = StringAndPic.pixelHeight;
        float scaleWidth = ((float) newWidth) / (float) width;
        float scaleHeight = ((float) newHeight) /(float) height;
        Matrix matrix = new Matrix();
        matrix.postScale( scaleWidth, scaleHeight);
        Bitmap newbm1 = Bitmap.createBitmap(bm1, 0, 0, width, height, matrix, true); //新圖 newbm1 240*240
        //修改結束

        int width2 = newbm1.getWidth();
        int height2 = newbm1.getHeight();
        byte[] rgbValues = new byte[width2 * height2/8]; //rgb 111   LINE width 208變78
        int k = 0;


        for (int y = 0; y < height2; y++) {
            int pixelFull;
            int rgb_1bit=0;//集滿8bit就噴出
            int SubPixelCount = 7; //左移位元初始值

            for (int x = 0; x < width2; x++) {

                pixelFull = x % 8; //每8個pixel 塞滿3個byte一個週期
                int pixel = newbm1.getPixel(x, y);
                int redPixel = (int) Color.red(pixel);
                int greenPixel = (int) Color.green(pixel);
                int bluePixel = (int) Color.blue(pixel);
                byte Pixel_1bit;

                //Log.e("rgb_1bit傳你阿公", String.format("redPixel=%d,greenPixel=%d,bluePixel=%d", redPixel, greenPixel,bluePixel));
                if(redPixel > 127 || greenPixel > 127 || bluePixel > 127 ){
                    Pixel_1bit = 1;
                }else{
                    Pixel_1bit = 0;
                }

                int pixel_8bit = (Pixel_1bit & 0x01) << SubPixelCount ;
                SubPixelCount--;

                rgb_1bit |= pixel_8bit ;

                if(pixelFull == 7){
                    SubPixelCount = 7; //rgbValues滿 重製次數


                    rgbValues[k] = (byte)( rgb_1bit );
                    k++;
                    rgb_1bit = 0;//重製3 BYTE 8個PIXEL 資料
                    // k = 0 ~ 208*208/8 -1
                }
            }
        }
        return rgbValues;
    }

  //把文字訊息畫成一張圖片
    //加換行功能   標題內容一起塞    width  -  Side_width  = 全寬- 邊框 = 訊息寬
    public Bitmap createChinese2(float textSize, String mark ,int width ,int Side_width, int height) {

        TextPaint textPaint = new TextPaint();//換行用
        textPaint.setColor(Color.BLUE);//字的顏色
        //textPaint.setAntiAlias(true);   //抗鋸齒 幹  難用死 字體加毛邊 無法判斷RGB DATA值
        //textPaint.setFakeBoldText(false);
        textPaint.setTypeface(Typeface.create("DEFAULT_BOLD", Typeface.NORMAL));
        textPaint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(mark, textPaint, width-Side_width, Layout.Alignment.ALIGN_NORMAL, 1.1f, 0.0f, true);//width 字的寬度 width/textsize =1 line幾字
        //Bitmap bmp = Bitmap.createBitmap(layout.getWidth()+20,layout.getHeight()+20,Bitmap.Config.ARGB_8888);
        Bitmap bmp = Bitmap.createBitmap(layout.getWidth() + Side_width, height, Bitmap.Config.ARGB_8888);//layout.getWidth() + 0 指多出來的寬度
        Canvas canvas = new Canvas(bmp);
        canvas.translate(10, 10);
        canvas.drawColor(Color.RED);//背景顏色
        layout.draw(canvas);
        Log.e("文字換行", String.format("1:%d%d", layout.getWidth(), layout.getHeight()));
        return bmp;
    }

    //加換行,先畫上標題,根據標題高度再下一行繼續畫內容
    public Bitmap createChinese3(float textSize, String Title,String Text) {
        //畫標題
        TextPaint textPaint = new TextPaint();//換行用
        textPaint.setColor(Color.BLUE);//字的顏色
        //textPaint.setAntiAlias(true);   //抗鋸齒 幹  難用死 字體加毛邊 無法判斷RGB DATA值
        //textPaint.setFakeBoldText(false);
        textPaint.setTypeface(Typeface.create("DEFAULT_BOLD", Typeface.BOLD));//標題粗體
        textPaint.setTextSize(textSize);
        StaticLayout layout = new StaticLayout(Title+" :", textPaint, 150, Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, true);//width 字的寬度 width/textsize =1 line幾字
        layout.getHeight();
        Bitmap bmp = Bitmap.createBitmap(layout.getWidth() + 10, 80, Bitmap.Config.ARGB_8888);//layout.getWidth() + 0 指多出來的寬度
        Canvas canvas = new Canvas(bmp);
        canvas.translate(10, 10);
        canvas.drawColor(Color.RED);//背景顏色
        layout.draw(canvas);
        Log.e("文字換行", String.format("1:%d %d", layout.getWidth(), layout.getHeight()));
        //畫內容
        TextPaint textPaint2 = new TextPaint();//換行用
        textPaint2.setColor(Color.BLUE);//字的顏色
        textPaint2.setTypeface(Typeface.create("DEFAULT_BOLD", Typeface.NORMAL));
        textPaint2.setTextSize(textSize);
        StaticLayout layout2 = new StaticLayout(Text, textPaint, 150, Layout.Alignment.ALIGN_NORMAL, 1.3f, 0.0f, true);//width 字的寬度 width/textsize =1 line幾字
        Canvas canvas2 = new Canvas(bmp);//劃過標題的bmp再拿來畫內容複印上去
        canvas2.translate(10, 10+layout.getHeight());//複印內容 依據標題Y軸高度垂直移動內容
        //canvas2.drawColor(Color.RED);
        layout2.draw(canvas2);
        Log.e("文字換行2", String.format("2:%d %d", layout2.getWidth(), layout2.getHeight()));
        return bmp;
    }
}
