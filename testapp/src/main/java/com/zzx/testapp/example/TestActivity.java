package com.zzx.testapp.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zzx.testapp.R;
import com.zzx.testapp.exp.TestThreadPoolExecutor;
import com.zzx.testapp.otherpool.SimplePool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestActivity extends AppCompatActivity {
    long temp_time;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final ThreadPoolExecutor activityPoolExecutor = new ThreadPoolExecutor(0, Runtime.getRuntime().availableProcessors() * 3,
            30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SimplePool.poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: 运行了SimplePool中的Run方法");
            }
        });
        TestThreadPoolExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: 运行了TestThreadPoolExecutor中的Run方法");
            }
        });
//
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: 运行了Executors.newCachedThreadPool()中的Run方法");
            }
        });

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0, Runtime.getRuntime().availableProcessors() * 3,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        Log.d("TAG", "run: 下面将要运行 new ThreadPoolExecutor()后，executor.execute()中的Run方法");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: 运行了executor.execute()中的Run方法");
            }
        });
        Log.d("TAG", "run: 下面将要运行activityPoolExecutor.execute()中的Run方法");
        activityPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: 运行了poolExecutor.execute()中的Run方法");
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: executorService.execute()中的Run方法");
            }
        });

    }
}