package com.example.material_test

import android.content.Context
import android.widget.Toast

class ReleaseHandler {

    public fun showTip(context: Context?) {
        Toast.makeText(context,"Release showing", Toast.LENGTH_SHORT).show();
    }
}