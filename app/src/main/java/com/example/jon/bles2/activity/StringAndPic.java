package com.example.jon.bles2.activity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jon.bles2.AllString;
import com.example.jon.bles2.Parameter;
import com.example.jon.bles2.PermissionsChecker;
import com.example.jon.bles2.Phone;
import com.example.jon.bles2.R;
import com.example.jon.bles2.TransData;
import com.example.jon.bles2.fragment.fragment_four;
import com.example.jon.bles2.fragment.fragment_one;
import com.example.jon.bles2.fragment.fragment_three;
import com.example.jon.bles2.fragment.fragment_two;
import com.example.jon.bles2.service.BluetoothLeService;
import com.example.jon.bles2.service.BluetoothLeService_Car;
import com.example.jon.bles2.service.NotificationService;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StringAndPic extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    //new TransData( )
    TransData transData = new TransData();

    private int WatchFace[] = {R.drawable.r00,R.drawable.r01,R.drawable.r02,R.drawable.r03,//r00~r15  0~9
            R.drawable.r04,R.drawable.r05,R.drawable.r06,R.drawable.r09,
            R.drawable.r12,R.drawable.r13,R.drawable.r14,R.drawable.r15                                 //r17~r19 10~12   //R.drawable.r06,R.drawable.r09
            ,R.drawable.r17,R.drawable.r18,R.drawable.r19,R.drawable.r20//r20~r25 13~18
            ,R.drawable.r21,R.drawable.r22,R.drawable.r23,R.drawable.r24//r41~r46 19~24
            ,R.drawable.r25,R.drawable.r41,R.drawable.r42,R.drawable.r43
            ,R.drawable.r44,R.drawable.r45,R.drawable.r46};

    public int H082WatchFace[] = {
            R.drawable.eng00,R.drawable.eng01,R.drawable.eng02,R.drawable.eng03,R.drawable.eng04
    };

    public int DualWatchFace[] = {R.drawable.d0,R.drawable.d1,R.drawable.d2,R.drawable.d3,
            R.drawable.d4,R.drawable.d5,R.drawable.d6,R.drawable.d7,
            R.drawable.d8,R.drawable.d9,R.drawable.d10,R.drawable.d11,
            R.drawable.d12,R.drawable.d13,R.drawable.d14,R.drawable.d15,
            R.drawable.d16,R.drawable.d17,R.drawable.d18,R.drawable.d19,
            R.drawable.d20,R.drawable.d21,R.drawable.d22,R.drawable.d23,
            R.drawable.d24,R.drawable.d25,R.drawable.d26,R.drawable.d27,
            R.drawable.d28,R.drawable.d29,R.drawable.d30,R.drawable.d31,
            R.drawable.d32,R.drawable.d33,R.drawable.d34,R.drawable.d35,
            R.drawable.d36,R.drawable.d37,R.drawable.d38,R.drawable.d39,
            R.drawable.d40,R.drawable.d41,R.drawable.d42,R.drawable.d43,
            R.drawable.d44,R.drawable.d45,R.drawable.d46,R.drawable.d47,
            R.drawable.d48,R.drawable.d49,R.drawable.d50,R.drawable.d51,
            R.drawable.d52,R.drawable.d53,R.drawable.d54,R.drawable.d55,
            R.drawable.d56,R.drawable.d57,R.drawable.d58,R.drawable.d59,
            R.drawable.d60,R.drawable.d61,R.drawable.d62,R.drawable.d63,
            R.drawable.d64,R.drawable.d65,R.drawable.d66,R.drawable.d67,
            R.drawable.d68,R.drawable.d69,R.drawable.d70,R.drawable.d71,
            R.drawable.d72,R.drawable.d73,R.drawable.d74,R.drawable.d75,
            R.drawable.d76,R.drawable.d77,R.drawable.d78,R.drawable.d79,
            R.drawable.d80,R.drawable.d81,R.drawable.d82,R.drawable.d83,
            R.drawable.d84,R.drawable.d85,R.drawable.d86,R.drawable.d87,
            R.drawable.d88,R.drawable.d89,R.drawable.d90,R.drawable.d91,
            R.drawable.d92,R.drawable.d93,R.drawable.d94,R.drawable.d95
    };

    public int POC_CarFace[] = {
            R.drawable.e00, R.drawable.e01, R.drawable.e02, R.drawable.e03, R.drawable.e04, R.drawable.e05, R.drawable.e06, R.drawable.e07, R.drawable.e08, R.drawable.e09,
            R.drawable.e11, R.drawable.e12, R.drawable.e13, R.drawable.e14, //208x208
            R.drawable.e20, R.drawable.e21, R.drawable.e22, R.drawable.e23, //24X12
            R.drawable.e24, R.drawable.e25, R.drawable.e26,//40X30
            R.drawable.e27, R.drawable.e28  //16X16
    };

    public static int H082AU[] = {
            R.drawable.au01, R.drawable.au02, R.drawable.au03, R.drawable.au04, R.drawable.au05, R.drawable.au06, R.drawable.au07, R.drawable.au08,
            R.drawable.au09, R.drawable.au10, R.drawable.au11, R.drawable.au12, R.drawable.au13, R.drawable.au14, R.drawable.au15, R.drawable.au16,
            R.drawable.au17, R.drawable.au18, R.drawable.au19, R.drawable.au20, R.drawable.au21, R.drawable.au22, R.drawable.au23, R.drawable.au24,
            R.drawable.au25, R.drawable.au26, R.drawable.au27, R.drawable.au28, R.drawable.au29, R.drawable.au30/*, R.drawable.au31, R.drawable.au32,
            R.drawable.au33, R.drawable.au34, R.drawable.au35, R.drawable.au36, R.drawable.au37, R.drawable.au38, R.drawable.au39, R.drawable.au40,*/
    };

    //儲存號碼到手錶
    private EditText number1;
    private EditText number2;
    private EditText number3;
    private EditText number4;
    private Button person1;
    private Button person2;
    private Button person3;
    private Button person4;

    //多執行緒傳圖
    private  Button ButtonThread;
    private int SDcard;// 0 傳APP內建圖檔給手錶 ,1 瀏覽手機裡的圖片傳送, 2 傳APP內建圖檔給H082
    private Bitmap APPbitmap;//app內的bitmap圖檔

    //瀏覽圖
    private Button selectImage;
    Bitmap SDbitmap;//要拿來傳檔案用的

    //PhoneStateListener
    final private static int REQUEST_CODE = 29; // 请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    public PermissionsChecker permissionsChecker;

    //藍芽 lan ya
    private final static String TAG = StringAndPic.class.getSimpleName();
    public static BluetoothLeService mBluetoothLeService; //=new BluetoothLeService(); //new BluetoothLeService();做完這動作可能可以後台使用手錶電話功能
    //public ExpandableListView mGattServicesList;  //原本是private   改成public 開啟此StringAndPic Activity就不會第一次連藍芽閃退  ,好像相關功能都拿掉更好  ,我又開了,我又關了
    public static BluetoothLeService_Car mBluetoothLeService_Car;
    private boolean mConnected = false;
    private String mDeviceAddress;
    private String mDeviceAddress_Car;
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_ADDRESS_Car = "DEVICE_ADDRESS_Car";
    private TextView textViewState;

    public static TextView transfer;
    public static TextView SpeedText;
    public static TextView DistanceText;
    public static TextView DistanceTotalText;

    public static double distanceTotal=0.000f; //每次重新點擊連線初始值為0
    public static double predistanceTotal=0.000f;
    public static int predistance = 0;

    //文字轉圖片
    private ImageView chinesePic;
    //文字轉圖片end

    //圖片START
    public Button button1POC,button2POC,button3POC,button4POC;  //代替OASIS-04按鈕
    public static int POCBtn_flag =1;
    public static int message = 0; //訊息種類  0: text  1: call  2: mail  3: line   4: fb
    public  int MMflag=0;//傳圖指數
    public int MMSflag = 0 ;//訊息指數
    public int timeNo = 1;//時間存在手錶的位置
    public int timeType = 0;//時間存在手錶的格式
    public static int faceNo = 10;//圖片存在手錶的數量
    public static int flashNo = 0;//圖片存在手錶記憶體位置
    public int picNo = 26;//按鈕按下從picNo =0 開始顯示
    public int PicH082No = 29;
    public int picDualNo = 95;
    public int picCarNo = 28;
    public int picCarPOC = 22;
    ArrayList<String> pic = null;
    ImageView image01;
    byte visible,x= (byte)40,y=(byte)80;//手錶時間座標初始位置
    public byte stepShort;
    public byte stepLong;
    private EditText moveStepLong;
    private EditText moveStepShort;
    private Button btnUP;//直接上下左右移動手錶時間位置
    private Button btnDOWN;
    private Button btnLEFT;
    private Button btnRIGHT;
    private Button btnCENTER;
    private Button btnTime;//修改手錶時間位置
    private Button btnSendImgr01;//多執行緒傳圖  一次傳完h082
    private Button btnSendImgr02;//多執行緒傳圖  一次傳完手錶
    private Button btnSendImgr03;//多執行緒傳圖  一次傳完Dual手錶
    private Button btnSendImgr04;//多執行緒傳圖  一次傳完Car 車錶(dialog)
    private Button BtnChangePicDual;
    private Button BtnchangePic;
    public  Button BtnChangeH082Pic;
    private Button BtnChangeCarPic;
    public static Spinner SpinFlashNo;//flash index 下拉選單
    private Spinner SpinNumberNo;//最大圖片數量 下拉選單
    private Spinner SpinTimeNo;//時間位置下拉選單
    private Spinner SpinTimeType;//時間字型下拉選單
    private TextView TimeNo;
    private TextView FaceNo;
    public static int pixelWidth;
    public static int pixelHeight;//pixelHeight 隨不同產品會改變
    //圖片END
    public Context mContext;

    // Jason Add 2021/02/17
    Button bicycle_demo, Info_Demo;

    //Fragment
    public ConstraintLayout layout;
    public LinearLayout Rone;
    public LinearLayout Rtwo;
    public LinearLayout Rthree;
    public LinearLayout Rfour;
    public String fragmentTag;
    public Fragment oneFragment = new fragment_one(),
                    twoFragment = new fragment_two(),
                    threeFragment = new fragment_three(),
                    fourFragment = new fragment_four();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_and_pic);

        //picture permission
        permissionActivity  picturePermission = new permissionActivity();//20210712 add
        //picturePermission.picture_permission();//20210712 add
        distanceTotal = 0;//總距離清0

        //fragment
        layout = (ConstraintLayout)findViewById(R.id.main_content);
       /* this.getSupportFragmentManager()             // 获取管理类
                .beginTransaction()                  // 开启事物
                .add(R.id.fffmain_content,oneFragment,"oneFragment")
                .add(R.id.fffmain_content,twoFragment,"twoFragment")
                .add(R.id.fffmain_content,threeFragment,"threeFragment")
                .add(R.id.fffmain_content,fourFragment,"fourFragment")
                .hide(twoFragment)
                .hide(threeFragment)
                .hide(fourFragment)
                .commit();                                               // 提交
        this.fragmentTag = "oneFragment";  //第一頁主頁
        //fragment end*/

        //mActivityMessenger = new Messenger(mMessageHandler_POC);//開一個新的HANDLER來用??? 媽的根本看不董

        Intent intent = getIntent();//拿藍芽mac address
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        mDeviceAddress_Car = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS_Car);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        Intent gattServiceIntent_Car = new Intent(this, BluetoothLeService_Car.class);
        //startService(gattServiceIntent);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        bindService(gattServiceIntent_Car, mServiceConnection_Car, BIND_AUTO_CREATE);
        Log.i("onCreate", "ㄚㄚㄚㄚㄚㄚㄚㄚ ㄚㄚㄚㄚㄚㄚ");
        //廣播
        registBroadCast();//傳送文字通知的廣播
        toggleNotificationListenerService();
        openSetting();
        //廣播結束

        if(timeNo==0) {//timeNO=0 沒選擇手錶時間擺放位置 , 則手錶不顯示時間visible = 0
            visible = 1;
        } else{
            visible = 1;
        }



        //PhoneStateListener
        permissionsChecker = new PermissionsChecker(this);
        phoneListenerStart();//開始開啟來電監聽功能;

        SpinnerFuntion();//下拉選單功能
        TextviewInitial();
        EditTextInitial();
        ImageviewInitial();
        ButtonInitial();//按鈕宣告函式   //fragment底部menu也在這

/*        bicycle_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StringAndPic.this,
                        BicycleActivity.class);
                Log.i("onCreate", "swtich to bicycle demo");

                startActivity(intent);
            }
        });

        Info_Demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StringAndPic.this,
                        InfoActivity.class);
                Log.i("onCreate", "swtich to bicycle demo");

                startActivity(intent);
            }
        });*/
    }
    //------------------------------------------------------------------------onCreate實體化結束-----------------------------------------------------------

    //以下都副程式
    //fragment
    class MyFragment extends FragmentActivity{
        public void switchFragment(FragmentManager fm,String toTag,String foTag){
            Fragment fo = fm.findFragmentByTag(foTag);
            Fragment to = fm.findFragmentByTag(toTag);
            if(fo != to){
                fm.beginTransaction().hide(fo).show(to).commit();
            }
        }
    }

    public void switchFragment(String toTag){
        MyFragment mf = new MyFragment();
        mf.switchFragment(getSupportFragmentManager(),toTag,fragmentTag);
        this.fragmentTag = toTag;
    }

    //ImageView宣告放這
    public void ImageviewInitial(){
        image01 = (ImageView) findViewById(R.id.imageView3);//換圖的初始底圖
    }

    //TextView宣告放這
    public void TextviewInitial(){
        transfer = (TextView) findViewById(R.id.textTransfor); //傳圖顯示傳完
        SpeedText = (TextView) findViewById(R.id.speedText);//顯示時速km/h
        DistanceText = (TextView) findViewById(R.id.distanceText);//顯示m
        DistanceTotalText = (TextView) findViewById(R.id.distanceTotalText); //顯示km
    }

    //EditText宣告放這
    public void EditTextInitial(){

    }
    //BUTTON宣告放這
    public void ButtonInitial(){

        BtnChangeH082Pic = (Button)findViewById(R.id.changePOCPic);//240x120
        BtnChangeH082Pic.setOnClickListener(this);

        btnSendImgr01 = (Button) findViewById(R.id.allPic);//多執行緒傳圖  一次傳完h082
        btnSendImgr01.setOnClickListener(this);

        selectImage =(Button)findViewById(R.id.SelectImage);//瀏覽手機內的圖片
        selectImage.setOnClickListener(this);
        ButtonThread = (Button)findViewById(R.id.btnThread);//多執行緒傳一張圖 240 x 240  70x80   10x240 根據寬高可調
        ButtonThread.setOnClickListener(this);

        button1POC =(Button)findViewById(R.id.Button1POC);
        button1POC.setOnClickListener(this);
        button2POC =(Button)findViewById(R.id.Button2POC);
        button2POC.setOnClickListener(this);
        button3POC =(Button)findViewById(R.id.Button3POC);
        button3POC.setOnClickListener(this);
        button4POC =(Button)findViewById(R.id.Button4POC);
        button4POC.setOnClickListener(this);


/*        //fragment 底部menu當按鈕用
        Rone = (LinearLayout) findViewById(R.id.layout_menu_one);
        Rone.setOnClickListener(this);
        Rtwo = (LinearLayout) findViewById(R.id.layout_menu_two);
        Rtwo.setOnClickListener(this);
        Rthree = (LinearLayout) findViewById(R.id.layout_menu_three);
        Rthree.setOnClickListener(this);
        Rfour = (LinearLayout) findViewById(R.id.layout_menu_four);
        Rfour.setOnClickListener(this);*/

        // Jason Add 2021/02/17
        //bicycle_demo = (Button)findViewById(R.id.b_bicycle_demo2);
        //Info_Demo = (Button)findViewById(R.id.Info_Demo);
    }
    //長按按鈕 上下左右粗調時間
    @Override
    public boolean onLongClick(View v){
        int Id = v.getId();
        switch(Id){
        }
        return true;
    }
    //一大堆按鈕
    @Override
    public void onClick(View v){
        int Id = v.getId();
        switch(Id){
            case R.id.btnThread: //多執行緒傳一張圖 240 x 240  70x80   10x240  240x120  寬高可調
                if(MMflag==0) {
                    MMflag=1;
                    picThread MM = new picThread();
                    MM.start();
                } break;
            case R.id.allPic: //多執行緒傳圖  一次傳完12張  240x240
                if(MMflag==0){
                    MMflag=1;
                    allPicThread MM = new allPicThread();
                    MM.start();
                } break;
            case R.id.SelectImage://瀏覽手機內的圖片
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                SDcard = 1; //傳手機裡面的檔案
                startActivityForResult(intent,0);//取得圖片返回
                break;
            case R.id.changePOCPic://依圖片編號編號有不同解析度  換圖完再按多執行緒傳圖   傳送的H082圖片 240x120 和各種icon大小
                if(picCarPOC >= POC_CarFace.length -1) {
                    picCarPOC = 0;
                } else {
                    picCarPOC++;
                }

                if(picCarPOC >=0 && picCarPOC <=9){
                    pixelWidth = 208;
                    pixelHeight = 208;
                }else if(picCarPOC >=10 && picCarPOC <=13){
                    pixelWidth = 208;
                    pixelHeight = 208;
                }else if(picCarPOC >=14 && picCarPOC <=17){
                    pixelWidth = 24;
                    pixelHeight = 12;
                }else if(picCarPOC >=18 && picCarPOC <=20){
                    pixelWidth = 40;
                    pixelHeight = 30;
                }else if(picCarPOC >=21 && picCarPOC <=22){
                    pixelWidth = 16;
                    pixelHeight = 16;
                }

                SDcard =5;//傳APP內建圖檔POC
                transfer.setText("width=" + pixelWidth + "    height=" + pixelHeight);//顯示當下圖片解析度
                image01.setImageResource(POC_CarFace[picCarPOC]);
                break;
            //以下fragment底部menu當按鈕
            case R.id.layout_menu_one:
                this.switchFragment("oneFragment");
                layout.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_menu_two:
                this.switchFragment("twoFragment");
                layout.setVisibility(View.GONE);
                break;
            case R.id.layout_menu_three:
                layout.setVisibility(View.GONE);
                this.switchFragment("threeFragment");
                break;
            case R.id.layout_menu_four:
                layout.setVisibility(View.GONE);
                this.switchFragment("fourFragment");
                break;
            case R.id.Button1POC:
                transData.POCbuttonProtocol(0);//demo info
                break;
            case R.id.Button2POC:
                transData.POCbuttonProtocol(1);//backward image
                break;
            case R.id.Button3POC:
                transData.POCbuttonProtocol(2); //forward image
                break;
            case R.id.Button4POC:

                if(POCBtn_flag ==1) {
                    transData.POCbuttonProtocol(3);//AT Mode enable
                    POCBtn_flag = 0;
                } else{
                    transData.POCbuttonProtocol(4);//AT Mode disable
                    POCBtn_flag = 1;
                }
                break;
        }
    }

    //下拉選單都放這
    public void SpinnerFuntion(){
        //下拉式選單-圖片儲存位置
        ArrayAdapter FcaeIndexAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new String[]{
                "1","2","3","4","5","6","7","8","9","10"/*,"11","12","13","14","15","16","17","18","19","20","21","22"
                ,"23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"
                ,"43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62"
                ,"63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82"
                ,"83","84","85","86","87","88","89","90","91","92","93","94","95"*/
        });
        SpinFlashNo = (Spinner) findViewById(R.id.spinnnerFace);
        SpinFlashNo.setAdapter(FcaeIndexAdapter);
        SpinFlashNo.setSelection(0, true);//讓程式一開始不直接啟動第一次監聽
        SpinFlashNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                     flashNo = position;//第0位置要從1開始
                                                 }
                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {
                                                 }
                                             }
        );

        //下拉式選單-圖片數量
        ArrayAdapter NumberMaxAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new String[]{
                "1","2","3","4","5","6","7","8","9","10"/*,"11","12","13","14","15","16","17","18","19","20","21","22"
                ,"23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"
                ,"43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62"
                ,"63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82"
                ,"83","84","85","86","87","88","89","90","91","92","93","94","95"*/
        });
        SpinNumberNo = (Spinner) findViewById(R.id.spinnnerFace2);
        SpinNumberNo.setAdapter(NumberMaxAdapter);
        SpinNumberNo.setSelection(9, true);//讓程式一開始不直接啟動第一次監聽
        SpinNumberNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                     faceNo = position+1;  //第0位置要從1開始
                                                     //transData.EngImageProtocol();
                                                     SpinnerThread spinnerThread = new SpinnerThread();
                                                     spinnerThread.start();
                                                 }
                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {
                                                 }
                                             }
        );
    }

    // To manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // To manage Service lifecycle.
    private final ServiceConnection mServiceConnection_Car = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService_Car = ((BluetoothLeService_Car.LocalBinder) service).getService();
            if (!mBluetoothLeService_Car.initialize()) {
                Log.e(TAG, "Car Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService_Car.connect(mDeviceAddress_Car);

            /*//service 傳來的車錶資料  以下主要是觸發Handler 的 handleMessenge方法主動更新service to activity資料
            mServiceMessenger = new Messenger(service);
            Message msg = Message.obtain();
            msg.replyTo = mActivityMessenger;

            try{
                mServiceMessenger.send(msg);
            }catch (RemoteException e){
                e.printStackTrace();
            }*/

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService_Car = null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
        if (mBluetoothLeService_Car != null) {
            final boolean result_Car = mBluetoothLeService_Car.connect(mDeviceAddress_Car);
            Log.d(TAG, "Connect request result_Car=" + result_Car);
        }
        //PhoneStateListener
        if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }
    //PhoneStateListener 權限??
    private void startPermissionsActivity() {
        permissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
    //PhoneStateListener
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行  REQUEST_CODE =29
        if (requestCode == REQUEST_CODE && resultCode == permissionActivity.PERMISSIONS_DENIED) {
            finish();
        }
        switch (requestCode) {
            case 0://取得相簿圖片
                try {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    Log.i("mengyuanuri","uri:"+uri.getScheme()+":"+uri.getSchemeSpecificPart());
                    SDbitmap =BitmapFactory.decodeStream(cr.openInputStream(uri));
                    image01.setVisibility(View.VISIBLE);
                    image01.setImageBitmap(SDbitmap);//顯示圖片在activity上
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case 1://裁剪完的圖片更新到Image01   //裁切先不用
                //先釋放ImageView上的圖片
                try {
                    if (image01.getDrawable() != null) {
                        image01.setImageBitmap(null);
                        System.gc();
                    }
                    Log.d(TAG, "YYYYYYYYYYYYYYYYYYYYYYES222");
                    //SDbitmap = data.getExtras().getParcelable("data");
                    //SDbitmap = data.getParcelableExtra("data");
                    Log.d(TAG, "YYYYYYYYYYYYYYYYYYYYYYES333");
                    image01.setVisibility(View.VISIBLE);
                    image01.setImageBitmap(SDbitmap);//顯示圖片在activity上
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
        }
    }
    ////裁剪圖片的Intent設定
    public static Intent getCropImageIntent(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
        intent.putExtra("scale", true); //讓裁剪框支援縮放
        intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 240);//回傳照片比例X
        intent.putExtra("outputY", 120);//回傳照片比例Y
        intent.putExtra("return-data", true);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        unbindService(mServiceConnection_Car);
        mBluetoothLeService = null;
        mBluetoothLeService_Car = null;
        unRegistBroadcast();//廣播接收通知字串
    }



/*    private static HashMap<String, String> attributes = new HashMap();

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }*/
    //藍芽結束

    //以下廣播
    //sensor data from BluetoothLeService sendbroadcast
    public BroadcastReceiver POCVelocityAndCandence = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int velocity = bundle.getInt("velocity");
            int distance = bundle.getInt("distance");
            Log.e("StringAndPic", velocity+" m/s,"+distance+" m");
            POCThread pocThread =new POCThread(velocity,distance);
            pocThread.start();//顯示SENSOR資料到UI

            //transData activity no use , expect bluetoothLeservice fail
            //transData.POCSensorData(1,0,0,velocity);
            //transData.POCSensorData(1,3,distance%10,(int)distance/1000);

        }
    };

    //通知廣播
    public BroadcastReceiver BleMessage=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            String pachageName=bundle.getString("packageName");
            String Title =bundle.getString("Title");
            String Text =bundle.getString("Text");
            switch (pachageName){
                case AllString.WECHAT:
                    message = 0;
                    Bitmap WaterWX = transData.createChinese2(Parameter.textSize, "wx    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterWX);
                    chineseThread MMWX = new chineseThread(WaterWX);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMWX.start();
                    }
                    break;
                case AllString.QQ:
                    message = 0;
                    Bitmap Water = transData.createChinese2(Parameter.textSize, "QQ    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(Water);
                    chineseThread MMQQ = new chineseThread(Water);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMQQ.start();
                    }
                    break;
                case AllString.MMS:
                    message = 0;
                    Bitmap WaterMMS = transData.createChinese2(Parameter.textSize, "簡訊    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterMMS);
                    chineseThread MMMMS = new chineseThread(WaterMMS);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMMMS.start();
                    }
                    break;
                case AllString.CALL:
                    message = 1;
                    Bitmap WaterCall = transData.createChinese2(Parameter.textSize, "來電    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterCall);
                    chineseThread MMCall = new chineseThread(WaterCall);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMCall.start();
                    }
                    break;
                case AllString.LINE:
                    message = 3;
                    Bitmap WaterLine = transData.createChinese2(Parameter.textSize, "Line    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterLine);
                    chineseThread MMLine = new chineseThread(WaterLine);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMLine.start();
                    }
                    break;
                case AllString.MAIL:
                    message = 2;
                    Bitmap WaterMail = transData.createChinese2(Parameter.textSize, "MAIL    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterMail);
                    chineseThread MMMail = new chineseThread(WaterMail);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMMail.start();
                    }
                    break;
                case AllString.FB:
                    message = 4;
                    Bitmap WaterFB = transData.createChinese2(Parameter.textSize, "FB    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////可換行
                    //chinesePic.setVisibility(View.VISIBLE);
                    //chinesePic.setImageBitmap(WaterFB);
                    chineseThread MMFB = new chineseThread(WaterFB);
                    if(MMSflag==0){
                        MMSflag=1;
                        MMFB.start();
                    }
                    break;
                case AllString.CALLING:
                    Log.e("CALLING","  Start");
                    break;
                case AllString.MUSIC_PLAY://音樂 播放 暫停
                {

                    Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
                    downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                    sendBroadcast(downIntent);

                    Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
                    upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                    sendBroadcast(upIntent);

                }
                break;
                case AllString.MUSIC_PREVIOUS://音樂 上一首
                {
                    Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                    downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                    sendBroadcast(downIntent);

                    Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                    upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                    sendBroadcast(upIntent);
                }
                break;
                case AllString.MUSIC_NEXT://音樂 下一首
                {
                    Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent downEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT);
                    downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
                    sendBroadcast(downIntent);

                    Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT);
                    upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
                    sendBroadcast(upIntent);
                }
                break;
            }
        }
    };

    //開啟電話監聽
    public void phoneListenerStart() {
        Phone phone = new Phone(getApplicationContext());
        phone.phoneStatelistener();
    }

    //註冊廣播 傳通知訊息給手錶用
    private void registBroadCast() {
        IntentFilter filter=new IntentFilter(AllString.SEND_WX_BROADCAST);
        IntentFilter POCfilter=new IntentFilter(AllString.VELOCITY);

        registerReceiver(POCVelocityAndCandence,POCfilter);
        registerReceiver(BleMessage,filter);
    }

    //解除廣播
    public void unRegistBroadcast(){
        unregisterReceiver(POCVelocityAndCandence);
        unregisterReceiver(BleMessage);
    }

    //開啟Notification Service
    public void openSetting(){
        if (!isEnabled()) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        } else {
            Toast.makeText(this, "已開啟服務權限", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                AllString.ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName( this, NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(
                new ComponentName(this , NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }



    public  class SpinnerThread extends Thread{
        public void run(){
            super.run();
            transData.EngImageProtocol();
        }
    }

    //傳圖用的執行緒 thread 240 x240  70x80   10x240
    public class picThread extends Thread{
        private Bitmap bm;
        @Override
        public void run() {
            super.run();

            Message msg1 = new Message();
            msg1.what = 1; //表示傳送中
            picHandler.sendMessage(msg1);

            Resources res = getResources();
            if(SDcard == 0) {//傳app內的圖
                APPbitmap = BitmapFactory.decodeResource(res, WatchFace[picNo]); //建立bm1為一張bmp圖檔,從res / drawable 資料夾裡抓圖檔  rgbwb 是圖檔名
                bm = APPbitmap;
            }else if(SDcard == 1){//傳手機內部檔案的圖
                //add 20210713 for H082ILN01 TO AUDI
                pixelWidth = 240;
                pixelHeight = 120;
                bm = SDbitmap;
            }else if(SDcard == 2){  //傳app內部圖檔
                APPbitmap = BitmapFactory.decodeResource(res,H082WatchFace[PicH082No]); //建立bm1為一張bmp圖檔,從res / drawable 資料夾裡抓圖檔  rgbwb 是圖檔名
                bm = APPbitmap;
            } else if(SDcard == 3){
                APPbitmap = BitmapFactory.decodeResource(res,DualWatchFace[picDualNo]); //建立bm1為一張bmp圖檔,從res / drawable 資料夾裡抓圖檔  rgbwb 是圖檔名
                bm = APPbitmap;
            }else if(SDcard == 5){
            APPbitmap = BitmapFactory.decodeResource(res,POC_CarFace[picCarPOC]); //建立bm1為一張bmp圖檔,從res / drawable 資料夾裡抓圖檔  rgbwb 是圖檔名
            bm = APPbitmap;
        }

            //SpinnerThread spinnerThread = new SpinnerThread();
            //spinnerThread.start();
            if(SDcard == 5){
                if(picCarPOC <=13) {  //只有大圖才傳3bit  其他小圖全部都用1bit傳
                    transData.ImageTransmission_COPCar(transData.rgbData_3bit(bm), 3); //傳rgb111  3bit
                    Log.e("車錶傳你妹", String.format("3bit"));
                }else if(picCarPOC >= 14){
                    Log.e("車錶傳你妹", String.format("1bit"));
                    transData.ImageTransmission_COPCar(transData.rgbData_1bit(bm), 1); //傳rgb1    1bit
                }
            }else {
                transData.ImageTransmission(transData.rgbData565(bm));//送圖到手錶
            }

            Message msg2 = new Message();
            msg2.what = 0; //表示傳完
            picHandler.sendMessage(msg2);
            MMflag=0;
        }
    }
    //多執行緒傳字
    public class chineseThread extends Thread{
        private Bitmap bitmap;
        public chineseThread(Bitmap bitmap){//這方法可以讓自己可以傳入值
            this.bitmap = bitmap;
        }
        @Override
        public void run() {
            super.run();
            Log.e("Thread start","999");
            transData.testChineseTransmission(transData.rgbDataString(bitmap));//縮小16倍容量,又裁減成160*80  , 104 x 104
            Log.e("Thread end", "999");
            MMSflag=0;
        }
    }

    //一次傳完全部的圖+更新UI  傳h082
    public class allPicThread extends Thread{
        public void run(){
            Message msg1 = new Message();
            msg1.what = 1;
            picHandler.sendMessage(msg1);


                transData.ATImageProtocol(H082AU.length);//依塞的陣列圖片長度
                for(int i=0 ; i<H082AU.length ;i++) {
                    pixelWidth = 240;
                    pixelHeight = 120;
                    //faceNo = a[i];
                    flashNo = i+10;
                    Resources res = getResources();
                    Bitmap bm2 = BitmapFactory.decodeResource(res, H082AU[i]);

                    transData.ImageTransmission(transData.rgbData565(bm2));
                }
                flashNo = Integer.valueOf((String)SpinFlashNo.getSelectedItem())-1;//拿到當下下拉選單的值
            Log.e("flashNo關鍵字", String.format("flashNo=%d", flashNo));
                transData.ATImageProtocol(H082AU.length);//依塞的陣列圖片長度
            Message msg2 = new Message();
            msg2.what = 0;
            picHandler.sendMessage(msg2);
            }
    }

    //更新UI 傳圖進度條
    public static  Handler picHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //int arg1 = msg.arg1;//傳全部圖顯示正在傳遞幾張
            //int arg2 = msg.arg2;//顯示單張圖讀取進度幾%
            int what = msg.what;
            if(what == 1) {
                transfer.setText(".........");
            }else if(what == 0) {
                transfer.setText("   ");
            }
        }
    };

    //傳車錶sensor data
    public class POCThread extends Thread{
        int velocity;
        int distance;
        int ble_connect_flag;
        public POCThread(int vel,int dis){
            this.velocity = vel;
            this.distance = dis;
            //this.ble_connect_flag = flag;
        }

        @Override
        public void run() {
            super.run();
                Message msg1 = new Message();
                msg1.arg1 = velocity; //表示傳送中
                msg1.arg2 = distance;
                mMessageHandler_POC.sendMessage(msg1);

        }
    }

    //更新UI 車錶速度 踏頻資料
    public static Handler mMessageHandler_POC =new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            int velocity = msg.arg1;
            int distance = msg.arg2;

            SpeedText.setText("\n" +"  "+ Integer.toString(velocity) );
            DistanceText.setText( "\n" +"  "+ Integer.toString(distance) );
            if(distance < predistance){//判斷是否重新連線
                distanceTotal = predistanceTotal; //取公里   只有重新連線且取得距離小於現在才會重置
            }
            //DecimalFormat DistanceTotalstring = new DecimalFormat("#.##");
            DistanceTotalText.setText( "\n"+ String.format("  %.2f",distanceTotal + (double) distance/1000) );
            Log.e("activity", distanceTotal+","+distance/1000);
            predistanceTotal = distanceTotal+ (double) distance/1000 ;
            predistance = distance;
        }
    };
}
