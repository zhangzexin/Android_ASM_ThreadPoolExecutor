package com.example.material_test.build

import com.example.material_test.base.BaseThreadPoolExecutor
import com.example.material_test.base.BaseThreadPoolExt
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadPool1 : BaseThreadPoolExt() {


    override fun execute() {
        val executor = BaseThreadPoolExecutor(
            0, Runtime.getRuntime().availableProcessors() * 3,
            30L, TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>()
        )
        executor.execute {

        }
    }
}