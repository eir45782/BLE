package com.example.jon.bles2;


import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class LongTouchBtn extends Button {

    private boolean ClickDown = false;//按鈕是否按下
    public LongTouchListener mListener;
    private int mTime;//長按處理間隔時間

    public LongTouchBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClickDown = true;
            new LongTouchTask().execute();
            Log.i("lanpa", "按下");

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            ClickDown = false;
            Log.i("lanpa", "彈起");
        }
        return super.onTouchEvent(event);
    }

    //當前Thread dalay多久
    private void sleep(int mTime) {
        try {
            Thread.sleep(mTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //長按任務
    class LongTouchTask extends AsyncTask<Void,Integer,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            while (ClickDown) {
                sleep(mTime);
                publishProgress(0);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mListener.onLongTouch();
        }
    }

    public void setOnLongTouchListener(LongTouchListener listener,int Time){
        mListener = listener;
        mTime = Time;
    }

    public interface LongTouchListener{
        void onLongTouch();
    }

}
