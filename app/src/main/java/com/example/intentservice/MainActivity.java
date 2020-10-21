package com.example.intentservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String ACTION_TYPE_THREAD = "action.type.thread";

    private TextView tv_status;
    private TextView tv_progress;
    private ProgressBar progressbar;
    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_status = findViewById(R.id.tv_status);
        tv_progress = findViewById(R.id.tv_progress);
        progressbar = findViewById(R.id.progressbar);

        //注册广播
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_THREAD);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

        initView();
    }

    public void initView() {
        tv_status.setText("线程状态：未运行");
        progressbar.setMax(100);
        progressbar.setProgress(0);
        tv_progress.setText("0%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, MyIntentService.class);
                startService(intent);
                break;
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {

                case ACTION_TYPE_THREAD:
                    //更改UI
                    int progress = intent.getIntExtra("progress", 0);
                    tv_status.setText("线程状态：" + intent.getStringExtra("status"));
                    progressbar.setProgress(progress);
                    tv_progress.setText(progress + "%");
                    if (progress >= 100) {
                        tv_status.setText("线程结束");
                    }
                    break;
            }
        }
    }
}
