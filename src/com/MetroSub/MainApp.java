package com.MetroSub;

import android.app.Application;
import com.MetroSub.database.DatabaseHelper;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/9/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainApp extends Application {

    private static final String TAG = "MainApp";

    private static MainApp appInstance = null;
    protected DatabaseHelper mDatabaseHelper = null;

    public static MainApp getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
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

    public DatabaseHelper getDatabaseHelper() {
        if(mDatabaseHelper == null) {
            mDatabaseHelper = DatabaseHelper.init(this);
        }
        return mDatabaseHelper;
    }
}
