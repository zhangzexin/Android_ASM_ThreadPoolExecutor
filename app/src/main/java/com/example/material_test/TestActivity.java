package com.example.material_test;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.ThreadPoolExecutor;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Toast.makeText(this,"hahaha",Toast.LENGTH_SHORT).show();
        ReleaseHandler debugHandler = new ReleaseHandler();
        debugHandler.showTip(this);

    }
}