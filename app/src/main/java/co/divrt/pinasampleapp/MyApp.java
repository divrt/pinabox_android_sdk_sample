package co.divrt.pinasampleapp;


import android.app.Application;

import co.divrt.pinasdk.Beacon.PinaInitialiser;
import co.divrt.pinasdk.pina.PinaConfig;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new PinaInitialiser(this, PinaConfig.DivrtSystem.Dev);
    }
}

