package com.zzx.testapp.otherpool;




import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimplePool {
    public final static JavaThreadPoo2 poolExecutor = new JavaThreadPoo2(0, Runtime.getRuntime().availableProcessors() * 3,
            30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());




}
