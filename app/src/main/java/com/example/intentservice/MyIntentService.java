package com.example.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyIntentService extends IntentService {

    /**
     * 是否正在运行
     */
    private boolean isRunning;

    /**
     *进度
     */
    private int count;

    /**
     * 广播
     */
    private LocalBroadcastManager mLocalBroadcastManager;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送进度消息
     */
    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(MainActivity.ACTION_TYPE_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
