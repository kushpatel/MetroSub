package com.MetroSub.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/11/13
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcatenateDatabaseTask extends AsyncTask<Context, Integer, Integer> {

    private static final String TAG = ConcatenateDatabaseTask.class.getCanonicalName();

    @Override
    protected Integer doInBackground(Context... ctxArr) {

        // Required context object is at the head of ctxArr
        Context ctx = ctxArr[0];

        // Create database from raw chunks
        DatabaseConcatenator.createDatabaseFromChunks(ctx);

        return 0;
    }

    protected void onPostExecute(Integer complete) {
        Log.d(TAG, "Loading database from chunks successful!");
    }
}
