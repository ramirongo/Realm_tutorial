package com.example.camila.realmtutorial;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Camila on 08-01-2018.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}

//android:name=".BaseApplication"                   that in AndroidManifest.xml
//classpath "io.realm:realm-gradle-plugin:4.3.1"    build.grandle
//apply plugin: 'realm-android'                     build.grandle
