package com.MetroSub;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.MetroSub.database.DatabaseHelper;
import com.MetroSub.database.LoadDatabaseTask;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/9/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainApp extends Application {

    private static final String TAG = "MainApp";
    public static final String DATABASE_IS_LOADED_SHARED_PREFERENCE = "database_is_loaded";

    private static MainApp appInstance = null;
    protected DatabaseHelper mDatabaseHelper = null;

    public static MainApp getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        setupDatabase();
        mDatabaseHelper = getDatabaseHelper();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // close database connection when app terminates
        if(mDatabaseHelper != null) {
            mDatabaseHelper.close();
        }
    }

    public void setupDatabase() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        boolean isDatabaseLoaded = preferences.getBoolean(DATABASE_IS_LOADED_SHARED_PREFERENCE,false);
        if(!isDatabaseLoaded) {
            Log.d(TAG,"Loading database in background...");
            LoadDatabaseTask loadDatabaseTask = new LoadDatabaseTask();
            loadDatabaseTask.execute(this.getApplicationContext());
        } else {
            Log.d(TAG,"Database was already loaded.");
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if(mDatabaseHelper == null) {
            mDatabaseHelper = DatabaseHelper.init(this);
        }
        return mDatabaseHelper;
    }
}
