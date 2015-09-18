package com.quinn.githubknife.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service {

    private Authenticator authenticator;
//
    public AccountService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return authenticator.getIBinder();
    }
}
