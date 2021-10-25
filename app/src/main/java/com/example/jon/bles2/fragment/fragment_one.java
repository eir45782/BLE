package com.example.jon.bles2.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jon.bles2.R;


public class fragment_one extends Fragment implements View.OnClickListener {

    private View fragmentView;//https://stackoverflow.com/questions/6495898/findviewbyid-in-fragment  幹  參考裡面為了連接兩個函示寫法
    private TextView fragment_1;
    private Button button_1;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_one, container, false);
        TextViewInitial();
        ButtonInitial();
        return fragmentView;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        //TextViewInitial();
        //ButtonInitial();
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
        fragment_1 = (TextView) fragmentView.findViewById(R.id.fragment_one);

    }

    //BUTTON宣告放這
    public void ButtonInitial(){
        button_1 = (Button) fragmentView.findViewById(R.id.button);
        button_1.setOnClickListener(this);
    }

    //一大堆按鈕
    @Override
    public void onClick(View v){
        int Id = v.getId();
        switch(Id){
            case R.id.button:
                fragment_1.setText("按one");
                break;
        }
    }

}