package com.example.material_test;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

class TestHandler {
    public TestHandler () {
        Log.d("TAG", "TestHandler: 创建");
    }

    public void showTip(Context ct) {
        Toast.makeText(ct,"TestHandler showing", Toast.LENGTH_SHORT).show();
    }
}
