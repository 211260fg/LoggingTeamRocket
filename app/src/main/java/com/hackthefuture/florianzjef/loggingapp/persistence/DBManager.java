package com.hackthefuture.florianzjef.loggingapp.persistence;

import android.content.Context;

import com.hackthefuture.florianzjef.loggingapp.models.Sample;
import com.hackthefuture.florianzjef.loggingapp.repo.Repository;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Florian on 8/12/2016.
 */

public class DBManager {

    private Realm db;

    public DBManager(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("stuckytoys.realm").build();
        try {
            db = Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            Realm.deleteRealm(realmConfiguration);
            //Realm file has been deleted.
            db = Realm.getInstance(realmConfiguration);
        }

    }

    public Realm getDb() {
        return db;
    }

    public void clearDB() {
        db.beginTransaction();
        db.deleteAll();
        db.commitTransaction();
    }

    private void put(Iterable objects) {
        db.beginTransaction();
        db.copyToRealmOrUpdate(objects);
        db.commitTransaction();
    }

    private void deleteSamples() {
        final RealmResults<Sample> results = db.where(Sample.class).findAll();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public void querySamples(){
        final RealmResults<Sample> storyResults = db.where(Sample.class).findAllAsync();
        storyResults.addChangeListener(new RealmChangeListener<RealmResults<Sample>>() {
            @Override
            public void onChange(RealmResults<Sample> element) {
                Repository.onDbStoriesDataChanged(element);
                storyResults.removeChangeListener(this);
            }
        });
    }

    public void onSamplesLoaded(List<Sample> samples) {
        this.deleteSamples();
        this.put(samples);
        querySamples();
    }
}
