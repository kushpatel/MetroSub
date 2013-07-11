package com.MetroSub.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.MetroSub.MainApp;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/10/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadDatabaseTask extends AsyncTask<Void, Void, Integer> {

    private String TAG = "LoadDatabaseTask";

    @Override
    protected Integer doInBackground(Void... voids) {

        DatabaseHelper databaseHelper = MainApp.getAppInstance().getDatabaseHelper();

        // load database code goes here...

        return 0;
    }

}
