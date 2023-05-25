package com.zzx.testapp.proxy;


import android.util.Log;

import com.zzx.testapp.otherpool.JavaThreadPoo2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JavaThreadPoolExecutorProxy extends ThreadPoolExecutor {

   public String BaseString = "这是BaseThreadPoolExecutor";

   public JavaThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
   }

   public JavaThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
   }

   public JavaThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
   }

   public JavaThreadPoolExecutorProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
   }


   public static JavaThreadPoolExecutorProxy asw_getInstance() {
      return CoreThreadPoolExecutor.INSTANCE;
   }

   private static class CoreThreadPoolExecutor {
      public final static JavaThreadPoolExecutorProxy INSTANCE = new JavaThreadPoolExecutorProxy(0, Runtime.getRuntime().availableProcessors() * 3,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<Runnable>());
   }

   @Override
   public void execute(Runnable command) {
      Log.d("TAG", "execute: JavaThreadPoolExecutorProxy");
      asw_getInstance().executeProxy(command);
//      CoreThreadPoolExecutor.INSTANCE.execute(command);
   }

   public void executeProxy(Runnable command) {
      super.execute(command);
   }


   public String getBaseString() {
      return BaseString;
   }
}
