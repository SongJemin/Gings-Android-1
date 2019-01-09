package com.computer.inu.myworkinggings.Jemin.Activity;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.computer.inu.myworkinggings.R;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

public class Test2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Handler handler = new Handler();
        new Thread()

        {

            public void run()

            {

                Message msg = handler.obtainMessage();

                handler.sendMessage(msg);

            }

        }.start();
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(Test2Activity.this)
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {

                    }


                })
                .setSelectMaxCount(3)
                .setEmptySelectionText("선택된게 없습니다! 이미지를 선택해 주세요!")
                .create();

    }

    final Handler handler = new Handler(Looper.getMainLooper())

    {

        public void handleMessage(Message msg)

        {

            // 원래 하고싶었던 일들 (UI변경작업 등...)

        }

    };





}
