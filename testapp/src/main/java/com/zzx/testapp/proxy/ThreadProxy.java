package com.zzx.testapp.proxy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ThreadProxy extends Thread{

    public ThreadProxy() {
        super();
    }

    public ThreadProxy(@Nullable Runnable target) {
        super(target);
    }

    public ThreadProxy(@Nullable ThreadGroup group, @Nullable Runnable target) {
        super(group, target);
    }

    public ThreadProxy(@NonNull String name) {
        super(name);
    }

    public ThreadProxy(@Nullable ThreadGroup group, @NonNull String name) {
        super(group, name);
    }

    public ThreadProxy(@Nullable Runnable target, @NonNull String name) {
        super(target, name);
    }

    public ThreadProxy(@Nullable ThreadGroup group, @Nullable Runnable target, @NonNull String name) {
        super(group, target, name);
    }

    public ThreadProxy(@Nullable ThreadGroup group, @Nullable Runnable target, @NonNull String name, long stackSize) {
        super(group, target, name, stackSize);
    }

//    public ThreadProxy(@Nullable ThreadGroup group, @Nullable Runnable target, @NonNull String name, long stackSize, boolean inheritThreadLocals) {
//        super(group, target, name, stackSize, inheritThreadLocals);
//    }

    @Override
    public synchronized void start() {
        JavaThreadPoolExecutorProxy.asw_getInstance().execute(this);
    }
}
