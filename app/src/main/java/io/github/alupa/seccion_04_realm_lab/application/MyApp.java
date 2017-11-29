package io.github.alupa.seccion_04_realm_lab.application;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.concurrent.atomic.AtomicInteger;

import io.github.alupa.seccion_04_realm_lab.models.City;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Alvaro on 18-10-2017.
 */

public class MyApp extends Application {

    public static AtomicInteger cityID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        setUpRealmConfig();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        Realm realm = Realm.getDefaultInstance();
        RealmResults<City> cities = realm.where(City.class).findAll();
        cityID = ( cities.size() > 0 ) ? new AtomicInteger(cities.max("id").intValue()) : new AtomicInteger();

        if (cities.isEmpty()) {
            City city = new City("Iquique", "Hermosa ciudad playera y turistica", "https://d1onimipqrtfy1.cloudfront.net/cities/iquique-og.jpg", 4.5f);
            realm.beginTransaction();
            //realm.deleteAll();
            realm.copyToRealm(city);
            realm.commitTransaction();
        }

        realm.close();
    }

    private void setUpRealmConfig() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
