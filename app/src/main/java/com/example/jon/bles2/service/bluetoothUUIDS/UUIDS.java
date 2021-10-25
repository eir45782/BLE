package com.example.jon.bles2.service.bluetoothUUIDS;

import java.util.UUID;

public class UUIDS {
    //Service uuid
    public static String String_AUOService = "2ea78970-7d44-44bb-b097-26183f402400";
    public final static UUID UUID_AUOService = UUID.fromString(String_AUOService);

    //characteristic UUID
    public static  String DESCRIPTOR ="00002902-0000-1000-8000-00805f9b34fb";
    public final static UUID CLIENT_CHARACRISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString(DESCRIPTOR);

    public static String String_AUO_Wearable = "2ea78970-7d44-44bb-b097-26183f402409";
    public final static UUID UUID_AUO_Wearable_Chars = UUID.fromString(String_AUO_Wearable);

    public static String Image_AUO_Wearable = "2ea78970-7d44-44bb-b097-26183f40240b"; //寫入圖片
    public final static UUID UUID_AUO_Wearable_Image = UUID.fromString(Image_AUO_Wearable);

    public static String Notification_AUO_Wearable = "2ea78970-7d44-44bb-b097-26183f40240c";//寫入通知
    public final static UUID UUID_AUO_Wearable_Notification = UUID.fromString(Notification_AUO_Wearable);

    public static String Notification_AUO_ICON = "2ea78970-7d44-44bb-b097-26183f40240b";//寫入icon
    public final static UUID UUID_AUO_ICON_Notification = UUID.fromString(Notification_AUO_ICON);

    public static String Read_AUO_Wearable = "2ea78970-7d44-44bb-b097-26183f40240e";//藍芽讀取
    public final static UUID UUID_AUO_Wearable_Read = UUID.fromString(Read_AUO_Wearable);

    public static String Read_AUO_Wearable_Map = "2ea78970-7d44-44bb-b097-26183f40240f";//google map demo指令
    public final static UUID UUID_AUO_Wearable_Map = UUID.fromString(Read_AUO_Wearable_Map);

    public static String Write_U082Button = "2ea78970-7d44-44bb-b097-26183f402407";//google map demo指令
    public final static UUID U082Button = UUID.fromString(Write_U082Button);

    //Service uuid Device 第二個藍芽裝置
    public static String String_AUOService_2 = "2ea78970-7d44-44bb-b097-26183f402300";
    public final static UUID UUID_AUOService_2 = UUID.fromString(String_AUOService_2);

    public static String Write_U082Button_2 = "2ea78970-7d44-44bb-b097-26183f402307";//google map demo指令
    public final static UUID U082Button_2 = UUID.fromString(Write_U082Button_2);

    public static String Read_AUO_Wearable_Map_2 = "2ea78970-7d44-44bb-b097-26183f40230f";//google map demo指令
    public final static UUID UUID_AUO_Wearable_Map_2 = UUID.fromString(Read_AUO_Wearable_Map_2);


    //咖打掐的uuid
    public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"; //descriptor
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG);

    public static final String CSC_SERVICE = "00001816-0000-1000-8000-00805f9b34fb";
    public static final UUID CSC_SERVICE_UUID = UUID.fromString(CSC_SERVICE);

    public static final String CSC_CHARACTERISTIC = "00002a5b-0000-1000-8000-00805f9b34fb";
    public static final UUID CSC_CHARACTERISTIC_UUID = UUID.fromString(CSC_CHARACTERISTIC);

    //POC UUID
    //Service UUID
    public static final String POC_Service = "2ea78970-7d44-44bb-b097-26183f402600";
    public static final UUID POC_Service_UUID = UUID.fromString(POC_Service);

    //Characteristic UUID
    public static final String POC_RGBImageTransfer = "2ea78970-7d44-44bb-b097-26183f402603";
    public static final UUID POC_ImageTransfer_UUID = UUID.fromString(POC_RGBImageTransfer);

    public static final String POC_1bitImageMessageTransfer = "2ea78970-7d44-44bb-b097-26183f402605";
    public static final UUID POC_1bitImageMessageTransfer_UUID = UUID.fromString(POC_1bitImageMessageTransfer);

    public static final String POC_DeviceControl = "2ea78970-7d44-44bb-b097-26183f402607";
    public static final UUID POC_DeviceControl_UUID = UUID.fromString(POC_DeviceControl);

    public static final String POC_SensorData = "2ea78970-7d44-44bb-b097-26183f402609";
    public static final UUID POC_SensorData_UUID = UUID.fromString(POC_SensorData);

    public static final String POC_MeterData = "2ea78970-7d44-44bb-b097-26183f40260b";
    public static final UUID POC_MeterData_UUID = UUID.fromString(POC_MeterData);

    public static final String POC_DemoParameter = "2ea78970-7d44-44bb-b097-26183f40260f";
    public static final UUID POC_DemoParameter_UUID = UUID.fromString(POC_DemoParameter);
}
