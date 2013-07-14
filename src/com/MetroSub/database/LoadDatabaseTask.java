package com.MetroSub.database;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.MetroSub.R;
import android.content.Context;
import android.os.AsyncTask;
import com.MetroSub.MainApp;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/10/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadDatabaseTask extends AsyncTask<Context, Integer, Integer> {

    private String TAG = "LoadDatabaseTask";

    @Override
    protected Integer doInBackground(Context... ctxArr) {

        // Required context object is at the head of ctxArr
        Context ctx = ctxArr[0];

        DatabaseHelper databaseHelper = MainApp.getAppInstance().getDatabaseHelper();

        // Get input stream handles for all the files to be loaded
        InputStream routesStream = ctx.getResources().openRawResource(R.raw.routes);
        InputStream shapesStream = ctx.getResources().openRawResource(R.raw.shapes);
        InputStream stopsStream = ctx.getResources().openRawResource(R.raw.stops);
        InputStream transfersStream = ctx.getResources().openRawResource(R.raw.transfers);
        InputStream tripsStream = ctx.getResources().openRawResource(R.raw.trips);
        InputStream stationEntrancesStream = ctx.getResources().openRawResource(R.raw.station_entrances);

        // Load files in database
        DatabaseLoader.loadDatabase(databaseHelper,routesStream,DatabaseLoader.LOAD_ROUTES);
        DatabaseLoader.loadDatabase(databaseHelper,shapesStream,DatabaseLoader.LOAD_SHAPES);
        DatabaseLoader.loadDatabase(databaseHelper,stopsStream,DatabaseLoader.LOAD_STOPS);
        DatabaseLoader.loadDatabase(databaseHelper,transfersStream,DatabaseLoader.LOAD_TRANSFERS);
        DatabaseLoader.loadDatabase(databaseHelper,tripsStream,DatabaseLoader.LOAD_TRIPS);
        DatabaseLoader.loadDatabase(databaseHelper,stationEntrancesStream,DatabaseLoader.LOAD_STATION_ENTRANCES);

        // Set a flag in SharedPreferences to avoid loading database on every startup
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putBoolean(MainApp.DATABASE_IS_LOADED_SHARED_PREFERENCE,true);
        preferencesEditor.commit();

        return 0;
    }

    protected void onPostExecute(Integer complete) {
        Log.d(TAG,"Loading database successful!");
    }

}
