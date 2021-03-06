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

    //?????????????????????
    private EditText number1;
    private EditText number2;
    private EditText number3;
    private EditText number4;
    private Button person1;
    private Button person2;
    private Button person3;
    private Button person4;

    //??????????????????
    private  Button ButtonThread;
    private int SDcard;// 0 ???APP????????????????????? ,1 ??????????????????????????????, 2 ???APP???????????????H082
    private Bitmap APPbitmap;//app??????bitmap??????

    //?????????
    private Button selectImage;
    Bitmap SDbitmap;//????????????????????????

    //PhoneStateListener
    final private static int REQUEST_CODE = 29; // ?????????
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    public PermissionsChecker permissionsChecker;

    //?????? lan ya
    private final static String TAG = StringAndPic.class.getSimpleName();
    public static BluetoothLeService mBluetoothLeService; //=new BluetoothLeService(); //new BluetoothLeService();?????????????????????????????????????????????????????????
    //public ExpandableListView mGattServicesList;  //?????????private   ??????public ?????????StringAndPic Activity?????????????????????????????????  ,?????????????????????????????????  ,????????????,????????????
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

    public static double distanceTotal=0.000f; //????????????????????????????????????0
    public static double predistanceTotal=0.000f;
    public static int predistance = 0;

    //???????????????
    private ImageView chinesePic;
    //???????????????end

    //??????START
    public Button button1POC,button2POC,button3POC,button4POC;  //??????OASIS-04??????
    public static int POCBtn_flag =1;
    public static int message = 0; //????????????  0: text  1: call  2: mail  3: line   4: fb
    public  int MMflag=0;//????????????
    public int MMSflag = 0 ;//????????????
    public int timeNo = 1;//???????????????????????????
    public int timeType = 0;//???????????????????????????
    public static int faceNo = 10;//???????????????????????????
    public static int flashNo = 0;//?????????????????????????????????
    public int picNo = 26;//???????????????picNo =0 ????????????
    public int PicH082No = 29;
    public int picDualNo = 95;
    public int picCarNo = 28;
    public int picCarPOC = 22;
    ArrayList<String> pic = null;
    ImageView image01;
    byte visible,x= (byte)40,y=(byte)80;//??????????????????????????????
    public byte stepShort;
    public byte stepLong;
    private EditText moveStepLong;
    private EditText moveStepShort;
    private Button btnUP;//??????????????????????????????????????????
    private Button btnDOWN;
    private Button btnLEFT;
    private Button btnRIGHT;
    private Button btnCENTER;
    private Button btnTime;//????????????????????????
    private Button btnSendImgr01;//??????????????????  ????????????h082
    private Button btnSendImgr02;//??????????????????  ??????????????????
    private Button btnSendImgr03;//??????????????????  ????????????Dual??????
    private Button btnSendImgr04;//??????????????????  ????????????Car ??????(dialog)
    private Button BtnChangePicDual;
    private Button BtnchangePic;
    public  Button BtnChangeH082Pic;
    private Button BtnChangeCarPic;
    public static Spinner SpinFlashNo;//flash index ????????????
    private Spinner SpinNumberNo;//?????????????????? ????????????
    private Spinner SpinTimeNo;//????????????????????????
    private Spinner SpinTimeType;//????????????????????????
    private TextView TimeNo;
    private TextView FaceNo;
    public static int pixelWidth;
    public static int pixelHeight;//pixelHeight ????????????????????????
    //??????END
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
        distanceTotal = 0;//????????????0

        //fragment
        layout = (ConstraintLayout)findViewById(R.id.main_content);
       /* this.getSupportFragmentManager()             // ???????????????
                .beginTransaction()                  // ????????????
                .add(R.id.fffmain_content,oneFragment,"oneFragment")
                .add(R.id.fffmain_content,twoFragment,"twoFragment")
                .add(R.id.fffmain_content,threeFragment,"threeFragment")
                .add(R.id.fffmain_content,fourFragment,"fourFragment")
                .hide(twoFragment)
                .hide(threeFragment)
                .hide(fourFragment)
                .commit();                                               // ??????
        this.fragmentTag = "oneFragment";  //???????????????
        //fragment end*/

        //mActivityMessenger = new Messenger(mMessageHandler_POC);//???????????????HANDLER????????? ?????????????????????

        Intent intent = getIntent();//?????????mac address
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        mDeviceAddress_Car = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS_Car);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        Intent gattServiceIntent_Car = new Intent(this, BluetoothLeService_Car.class);
        //startService(gattServiceIntent);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        bindService(gattServiceIntent_Car, mServiceConnection_Car, BIND_AUTO_CREATE);
        Log.i("onCreate", "???????????????????????? ??????????????????");
        //??????
        registBroadCast();//???????????????????????????
        toggleNotificationListenerService();
        openSetting();
        //????????????

        if(timeNo==0) {//timeNO=0 ????????????????????????????????? , ????????????????????????visible = 0
            visible = 1;
        } else{
            visible = 1;
        }



        //PhoneStateListener
        permissionsChecker = new PermissionsChecker(this);
        phoneListenerStart();//??????????????????????????????;

        SpinnerFuntion();//??????????????????
        TextviewInitial();
        EditTextInitial();
        ImageviewInitial();
        ButtonInitial();//??????????????????   //fragment??????menu?????????

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
    //------------------------------------------------------------------------onCreate???????????????-----------------------------------------------------------

    //??????????????????
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

    //ImageView????????????
    public void ImageviewInitial(){
        image01 = (ImageView) findViewById(R.id.imageView3);//?????????????????????
    }

    //TextView????????????
    public void TextviewInitial(){
        transfer = (TextView) findViewById(R.id.textTransfor); //??????????????????
        SpeedText = (TextView) findViewById(R.id.speedText);//????????????km/h
        DistanceText = (TextView) findViewById(R.id.distanceText);//??????m
        DistanceTotalText = (TextView) findViewById(R.id.distanceTotalText); //??????km
    }

    //EditText????????????
    public void EditTextInitial(){

    }
    //BUTTON????????????
    public void ButtonInitial(){

        BtnChangeH082Pic = (Button)findViewById(R.id.changePOCPic);//240x120
        BtnChangeH082Pic.setOnClickListener(this);

        btnSendImgr01 = (Button) findViewById(R.id.allPic);//??????????????????  ????????????h082
        btnSendImgr01.setOnClickListener(this);

        selectImage =(Button)findViewById(R.id.SelectImage);//????????????????????????
        selectImage.setOnClickListener(this);
        ButtonThread = (Button)findViewById(R.id.btnThread);//???????????????????????? 240 x 240  70x80   10x240 ??????????????????
        ButtonThread.setOnClickListener(this);

        button1POC =(Button)findViewById(R.id.Button1POC);
        button1POC.setOnClickListener(this);
        button2POC =(Button)findViewById(R.id.Button2POC);
        button2POC.setOnClickListener(this);
        button3POC =(Button)findViewById(R.id.Button3POC);
        button3POC.setOnClickListener(this);
        button4POC =(Button)findViewById(R.id.Button4POC);
        button4POC.setOnClickListener(this);


/*        //fragment ??????menu????????????
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
    //???????????? ????????????????????????
    @Override
    public boolean onLongClick(View v){
        int Id = v.getId();
        switch(Id){
        }
        return true;
    }
    //???????????????
    @Override
    public void onClick(View v){
        int Id = v.getId();
        switch(Id){
            case R.id.btnThread: //???????????????????????? 240 x 240  70x80   10x240  240x120  ????????????
                if(MMflag==0) {
                    MMflag=1;
                    picThread MM = new picThread();
                    MM.start();
                } break;
            case R.id.allPic: //??????????????????  ????????????12???  240x240
                if(MMflag==0){
                    MMflag=1;
                    allPicThread MM = new allPicThread();
                    MM.start();
                } break;
            case R.id.SelectImage://????????????????????????
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                SDcard = 1; //????????????????????????
                startActivityForResult(intent,0);//??????????????????
                break;
            case R.id.changePOCPic://???????????????????????????????????????  ?????????????????????????????????   ?????????H082?????? 240x120 ?????????icon??????
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

                SDcard =5;//???APP????????????POC
                transfer.setText("width=" + pixelWidth + "    height=" + pixelHeight);//???????????????????????????
                image01.setImageResource(POC_CarFace[picCarPOC]);
                break;
            //??????fragment??????menu?????????
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

    //?????????????????????
    public void SpinnerFuntion(){
        //???????????????-??????????????????
        ArrayAdapter FcaeIndexAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new String[]{
                "1","2","3","4","5","6","7","8","9","10"/*,"11","12","13","14","15","16","17","18","19","20","21","22"
                ,"23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"
                ,"43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62"
                ,"63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82"
                ,"83","84","85","86","87","88","89","90","91","92","93","94","95"*/
        });
        SpinFlashNo = (Spinner) findViewById(R.id.spinnnerFace);
        SpinFlashNo.setAdapter(FcaeIndexAdapter);
        SpinFlashNo.setSelection(0, true);//????????????????????????????????????????????????
        SpinFlashNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                     flashNo = position;//???0????????????1??????
                                                 }
                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> parent) {
                                                 }
                                             }
        );

        //???????????????-????????????
        ArrayAdapter NumberMaxAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new String[]{
                "1","2","3","4","5","6","7","8","9","10"/*,"11","12","13","14","15","16","17","18","19","20","21","22"
                ,"23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42"
                ,"43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62"
                ,"63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82"
                ,"83","84","85","86","87","88","89","90","91","92","93","94","95"*/
        });
        SpinNumberNo = (Spinner) findViewById(R.id.spinnnerFace2);
        SpinNumberNo.setAdapter(NumberMaxAdapter);
        SpinNumberNo.setSelection(9, true);//????????????????????????????????????????????????
        SpinNumberNo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                                 @Override
                                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                     faceNo = position+1;  //???0????????????1??????
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

            /*//service ?????????????????????  ?????????????????????Handler ??? handleMessenge??????????????????service to activity??????
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
    //PhoneStateListener ????????
    private void startPermissionsActivity() {
        permissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
    //PhoneStateListener
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ?????????, ????????????, ??????????????????, ????????????  REQUEST_CODE =29
        if (requestCode == REQUEST_CODE && resultCode == permissionActivity.PERMISSIONS_DENIED) {
            finish();
        }
        switch (requestCode) {
            case 0://??????????????????
                try {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    Log.i("mengyuanuri","uri:"+uri.getScheme()+":"+uri.getSchemeSpecificPart());
                    SDbitmap =BitmapFactory.decodeStream(cr.openInputStream(uri));
                    image01.setVisibility(View.VISIBLE);
                    image01.setImageBitmap(SDbitmap);//???????????????activity???
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case 1://???????????????????????????Image01   //???????????????
                //?????????ImageView????????????
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
                    image01.setImageBitmap(SDbitmap);//???????????????activity???
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                break;
        }
    }
    ////???????????????Intent??????
    public static Intent getCropImageIntent(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop", "true");// crop=true ?????????????????????????????????.
        intent.putExtra("scale", true); //????????????????????????
        intent.putExtra("aspectX", 1);// ??????????????????????????????.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 240);//??????????????????X
        intent.putExtra("outputY", 120);//??????????????????Y
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
        unRegistBroadcast();//????????????????????????
    }



/*    private static HashMap<String, String> attributes = new HashMap();

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }*/
    //????????????

    //????????????
    //sensor data from BluetoothLeService sendbroadcast
    public BroadcastReceiver POCVelocityAndCandence = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int velocity = bundle.getInt("velocity");
            int distance = bundle.getInt("distance");
            Log.e("StringAndPic", velocity+" m/s,"+distance+" m");
            POCThread pocThread =new POCThread(velocity,distance);
            pocThread.start();//??????SENSOR?????????UI

            //transData activity no use , expect bluetoothLeservice fail
            //transData.POCSensorData(1,0,0,velocity);
            //transData.POCSensorData(1,3,distance%10,(int)distance/1000);

        }
    };

    //????????????
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
                    Bitmap WaterWX = transData.createChinese2(Parameter.textSize, "wx    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap Water = transData.createChinese2(Parameter.textSize, "QQ    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap WaterMMS = transData.createChinese2(Parameter.textSize, "??????    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap WaterCall = transData.createChinese2(Parameter.textSize, "??????    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap WaterLine = transData.createChinese2(Parameter.textSize, "Line    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap WaterMail = transData.createChinese2(Parameter.textSize, "MAIL    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                    Bitmap WaterFB = transData.createChinese2(Parameter.textSize, "FB    "+Title+" : "+Text, Parameter.messageWidth,    Parameter.messageSide,Parameter.messageHeight);////?????????
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
                case AllString.MUSIC_PLAY://?????? ?????? ??????
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
                case AllString.MUSIC_PREVIOUS://?????? ?????????
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
                case AllString.MUSIC_NEXT://?????? ?????????
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

    //??????????????????
    public void phoneListenerStart() {
        Phone phone = new Phone(getApplicationContext());
        phone.phoneStatelistener();
    }

    //???????????? ???????????????????????????
    private void registBroadCast() {
        IntentFilter filter=new IntentFilter(AllString.SEND_WX_BROADCAST);
        IntentFilter POCfilter=new IntentFilter(AllString.VELOCITY);

        registerReceiver(POCVelocityAndCandence,POCfilter);
        registerReceiver(BleMessage,filter);
    }

    //????????????
    public void unRegistBroadcast(){
        unregisterReceiver(POCVelocityAndCandence);
        unregisterReceiver(BleMessage);
    }

    //??????Notification Service
    public void openSetting(){
        if (!isEnabled()) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        } else {
            Toast.makeText(this, "?????????????????????", Toast.LENGTH_LONG).show();
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

    //????????????????????? thread 240 x240  70x80   10x240
    public class picThread extends Thread{
        private Bitmap bm;
        @Override
        public void run() {
            super.run();

            Message msg1 = new Message();
            msg1.what = 1; //???????????????
            picHandler.sendMessage(msg1);

            Resources res = getResources();
            if(SDcard == 0) {//???app?????????
                APPbitmap = BitmapFactory.decodeResource(res, WatchFace[picNo]); //??????bm1?????????bmp??????,???res / drawable ?????????????????????  rgbwb ????????????
                bm = APPbitmap;
            }else if(SDcard == 1){//???????????????????????????
                //add 20210713 for H082ILN01 TO AUDI
                pixelWidth = 240;
                pixelHeight = 120;
                bm = SDbitmap;
            }else if(SDcard == 2){  //???app????????????
                APPbitmap = BitmapFactory.decodeResource(res,H082WatchFace[PicH082No]); //??????bm1?????????bmp??????,???res / drawable ?????????????????????  rgbwb ????????????
                bm = APPbitmap;
            } else if(SDcard == 3){
                APPbitmap = BitmapFactory.decodeResource(res,DualWatchFace[picDualNo]); //??????bm1?????????bmp??????,???res / drawable ?????????????????????  rgbwb ????????????
                bm = APPbitmap;
            }else if(SDcard == 5){
            APPbitmap = BitmapFactory.decodeResource(res,POC_CarFace[picCarPOC]); //??????bm1?????????bmp??????,???res / drawable ?????????????????????  rgbwb ????????????
            bm = APPbitmap;
        }

            //SpinnerThread spinnerThread = new SpinnerThread();
            //spinnerThread.start();
            if(SDcard == 5){
                if(picCarPOC <=13) {  //??????????????????3bit  ????????????????????????1bit???
                    transData.ImageTransmission_COPCar(transData.rgbData_3bit(bm), 3); //???rgb111  3bit
                    Log.e("???????????????", String.format("3bit"));
                }else if(picCarPOC >= 14){
                    Log.e("???????????????", String.format("1bit"));
                    transData.ImageTransmission_COPCar(transData.rgbData_1bit(bm), 1); //???rgb1    1bit
                }
            }else {
                transData.ImageTransmission(transData.rgbData565(bm));//???????????????
            }

            Message msg2 = new Message();
            msg2.what = 0; //????????????
            picHandler.sendMessage(msg2);
            MMflag=0;
        }
    }
    //??????????????????
    public class chineseThread extends Thread{
        private Bitmap bitmap;
        public chineseThread(Bitmap bitmap){//???????????????????????????????????????
            this.bitmap = bitmap;
        }
        @Override
        public void run() {
            super.run();
            Log.e("Thread start","999");
            transData.testChineseTransmission(transData.rgbDataString(bitmap));//??????16?????????,????????????160*80  , 104 x 104
            Log.e("Thread end", "999");
            MMSflag=0;
        }
    }

    //????????????????????????+??????UI  ???h082
    public class allPicThread extends Thread{
        public void run(){
            Message msg1 = new Message();
            msg1.what = 1;
            picHandler.sendMessage(msg1);


                transData.ATImageProtocol(H082AU.length);//???????????????????????????
                for(int i=0 ; i<H082AU.length ;i++) {
                    pixelWidth = 240;
                    pixelHeight = 120;
                    //faceNo = a[i];
                    flashNo = i+10;
                    Resources res = getResources();
                    Bitmap bm2 = BitmapFactory.decodeResource(res, H082AU[i]);

                    transData.ImageTransmission(transData.rgbData565(bm2));
                }
                flashNo = Integer.valueOf((String)SpinFlashNo.getSelectedItem())-1;//??????????????????????????????
            Log.e("flashNo?????????", String.format("flashNo=%d", flashNo));
                transData.ATImageProtocol(H082AU.length);//???????????????????????????
            Message msg2 = new Message();
            msg2.what = 0;
            picHandler.sendMessage(msg2);
            }
    }

    //??????UI ???????????????
    public static  Handler picHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //int arg1 = msg.arg1;//????????????????????????????????????
            //int arg2 = msg.arg2;//??????????????????????????????%
            int what = msg.what;
            if(what == 1) {
                transfer.setText(".........");
            }else if(what == 0) {
                transfer.setText("   ");
            }
        }
    };

    //?????????sensor data
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
                msg1.arg1 = velocity; //???????????????
                msg1.arg2 = distance;
                mMessageHandler_POC.sendMessage(msg1);

        }
    }

    //??????UI ???????????? ????????????
    public static Handler mMessageHandler_POC =new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            int velocity = msg.arg1;
            int distance = msg.arg2;

            SpeedText.setText("\n" +"  "+ Integer.toString(velocity) );
            DistanceText.setText( "\n" +"  "+ Integer.toString(distance) );
            if(distance < predistance){//????????????????????????
                distanceTotal = predistanceTotal; //?????????   ?????????????????????????????????????????????????????????
            }
            //DecimalFormat DistanceTotalstring = new DecimalFormat("#.##");
            DistanceTotalText.setText( "\n"+ String.format("  %.2f",distanceTotal + (double) distance/1000) );
            Log.e("activity", distanceTotal+","+distance/1000);
            predistanceTotal = distanceTotal+ (double) distance/1000 ;
            predistance = distance;
        }
    };
}
