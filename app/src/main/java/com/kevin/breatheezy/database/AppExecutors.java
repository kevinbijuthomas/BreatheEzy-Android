package com.kevin.breatheezy.database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final Object LOCK =new Object();
    private static AppExecutors sInstance;
    private final Executor discIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor discIO, Executor networkIO, Executor mainThread){
        this.discIO = discIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static  AppExecutors getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() { return discIO;}
    public Executor mainThread() { return mainThread;}
    public Executor networkThread() { return networkIO;}

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
