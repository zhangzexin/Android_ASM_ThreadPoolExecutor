package com.zzx.testapp.build;

import com.zzx.testapp.proxy.JavaThreadPoolExecutorProxy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

public class TestRun {

   public final static String test_p_string = "cs";
   public final static int test_p_int = 10;
   public final static boolean test_p_boolean = false;
   public final static long  test_p_long = 10000l;
   public final static JavaThreadPoolExecutorProxy pool2 = new JavaThreadPoolExecutorProxy(0, Runtime.getRuntime().availableProcessors() * 3,
           30L, TimeUnit.SECONDS,
           new LinkedBlockingQueue<Runnable>());
   public final static double  test_p_double = 10000.022212;


   public void run(Runnable runnable) {
      pool2.execute(runnable);
   }

   public String getString() {
      return pool2.getBaseString();
   }
}
