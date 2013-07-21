package com.MetroSub;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import com.MetroSub.database.DatabaseCopier;
import com.MetroSub.database.DatabaseHelper;
import com.MetroSub.database.LoadDatabaseTask;
import com.MetroSub.database.QueryHelper;
import com.MetroSub.datamine.GtfsParser;
import com.MetroSub.datamine.RetrieveFeedTask;
import com.google.protobuf.ByteString;

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
    protected QueryHelper mQueryHelper = null;

    public boolean mNetworkConnectionStatus;

    public GtfsParser mGtfsParser = null;

    public static MainApp getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        mNetworkConnectionStatus = isNetworkAvailable();

        // Uncomment this line to load database on device --- !! TAKES VERY LONG !!
        // instead concatenate the .db chunks in res/raw and copy the .db file to device's database folder
        //setupDatabase();

        // Copies .db file from res/raw folder to device's database path .. no need to run in background
        DatabaseCopier.createDatabaseFromFile(getApplicationContext());

        mDatabaseHelper = getDatabaseHelper();
        mQueryHelper = new QueryHelper(mDatabaseHelper);

        if (isNetworkAvailable()) {
            try {
                RetrieveFeedTask task = new RetrieveFeedTask();
                ByteString data = task.execute().get();
                mGtfsParser = new GtfsParser(data);
            } catch (Exception e) {
                Log.e(TAG, "Unable to retrieve GTFS data: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // close database connection when app terminates
        if (mDatabaseHelper != null) {
            mDatabaseHelper.close();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void setupDatabase() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        boolean isDatabaseLoaded = preferences.getBoolean(DATABASE_IS_LOADED_SHARED_PREFERENCE, false);
        if (!isDatabaseLoaded) {
            Log.d(TAG, "Loading database in background...");
            LoadDatabaseTask loadDatabaseTask = new LoadDatabaseTask();
            loadDatabaseTask.execute(this.getApplicationContext());
        } else {
            Log.d(TAG, "Database was already loaded.");
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = DatabaseHelper.init(this);
        }
        return mDatabaseHelper;
    }

    public QueryHelper getQueryHelper() {
        return mQueryHelper;
    }

    public GtfsParser getGtfsParser() {
        return mGtfsParser;
    }

    public boolean getNetworkConnectionStatus() {
        return this.mNetworkConnectionStatus;
    }
}
