package com.example.jon.bles2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jon.bles2.R;

//@ContentView(R.layout.fragment_four)
public class fragment_four extends Fragment implements View.OnClickListener {

    private View fragmentView;//https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment  幹  參考裡面為了連接兩個函示寫法
    private TextView fragment_4;
    private Button button_4;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
               fragmentView = inflater.inflate(R.layout.fragment_four, container, false);
        TextViewInitial();
        ButtonInitial();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
       // TextViewInitial();
       // ButtonInitial();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    //TextView宣告放這
    public void TextViewInitial(){
        fragment_4 = (TextView) fragmentView.findViewById(R.id.fragment_four);

    }

    //BUTTON宣告放這
    public void ButtonInitial(){
        button_4 = (Button) fragmentView.findViewById(R.id.button);
        button_4.setOnClickListener(this);
    }

    //一大堆按鈕
    @Override
    public void onClick(View v){
        int Id = v.getId();
        switch(Id){
            case R.id.button:
                fragment_4.setText("使用說明" +"\n"+"藍牙連線後可使用以下功能"+"\n"+
                        "1.更換錶面"+"\n"+
                        "2.修改聯絡人"+"\n" +
                        "3.訊息通知(無須操作)"+"\n" +
                        "4.通話控制(僅支援Android6.0)"+"\n" +
                        "5.音樂控制(僅支援Android 6.0)");
            break;
        }
    }

}
