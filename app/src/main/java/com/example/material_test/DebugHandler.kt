package com.example.material_test

import android.content.Context
import android.widget.Toast

class DebugHandler {

    public fun showTip(context: Context?) {
        Toast.makeText(context,"Debug showing", Toast.LENGTH_SHORT).show();
    }
}