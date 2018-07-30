package com.uwr.app.module.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.widget.Toast;

/*
 * Created by SDD on 12/11/2017.
 */

public class MyService extends android.app.Service {
    private static final String TAG = "MyService";

    private static final String DATA_URL = "data_url";
    private static final String DATA_STORAGE_PATH = "data_storage_path";
    private static final String DATA_BUNDLE = "data_bundle";

    private static final int MSG_REQUEST = 1112;
    private ServiceHandler mServiceHandler;

    public static void startService(Context context, String url, String storagePath) {
        Intent myIntent = new Intent(context, MyService.class);

        Bundle bundle = new Bundle();
        bundle.putString(DATA_URL, url);
        bundle.putString(DATA_STORAGE_PATH, storagePath);

        myIntent.putExtra(DATA_BUNDLE, bundle);
        context.startService(myIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_REQUEST:
                    if (msg.getData() != null) {
                        Bundle bundle = msg.getData();
                        downloadData(bundle.getString(DATA_URL), bundle.getString(DATA_STORAGE_PATH));

                        if (!hasMessages(MSG_REQUEST)) {
                            Message message = obtainMessage(MSG_REQUEST);
                            message.setData(bundle);
                            sendMessageDelayed(message, 5 * 1000);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread handlerThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        mServiceHandler = new ServiceHandler(handlerThread.getLooper());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBundleExtra(DATA_BUNDLE) != null) {
            if (!mServiceHandler.hasMessages(MSG_REQUEST)) {
                Message message = mServiceHandler.obtainMessage(MSG_REQUEST);
                message.setData(intent.getBundleExtra(DATA_BUNDLE));

                mServiceHandler.sendMessage(message);
            }
        }

        return START_REDELIVER_INTENT;
    }

    private void downloadData(String url, String storagePath) {
        Toast.makeText(this, "Url: " + url + " Storage Path: " + storagePath, Toast.LENGTH_LONG).show();
    }
}