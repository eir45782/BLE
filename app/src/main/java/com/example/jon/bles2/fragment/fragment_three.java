package com.example.jon.bles2.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jon.bles2.R;


public class fragment_three extends Fragment implements View.OnClickListener {

    private View fragmentView;//https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment  幹  參考裡面為了連接兩個函示寫法
    private TextView fragment_3;
    private Button button_3;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_three, container, false);
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
        fragment_3 = (TextView) fragmentView.findViewById(R.id.fragment_three);

    }

    //BUTTON宣告放這
    public void ButtonInitial(){
        button_3 = (Button) fragmentView.findViewById(R.id.button);
        button_3.setOnClickListener(this);
    }

    //一大堆按鈕
    @Override
    public void onClick(View v){
        int Id = v.getId();
        switch(Id){
            case R.id.button:
                fragment_3.setText("按three");
                break;
        }
    }

}