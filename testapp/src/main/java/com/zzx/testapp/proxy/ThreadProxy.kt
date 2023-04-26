package com.zzx.testapp.proxy

import android.util.Log

class ThreadProxy: Thread() {

    override fun start() {
        Log.d("TAG", "start: ASM切换了")
//        ThreadPoolExecutorProxy.getInstance().execute(this)
    }
}