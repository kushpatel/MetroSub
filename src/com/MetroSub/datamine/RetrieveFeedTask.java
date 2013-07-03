package com.MetroSub.datamine;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 7/2/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private String TAG = "RetrieveFeedTask";
    private String feedData;

    @Override
    protected String doInBackground(Void... params) {
        FeedHttpRequest feed = new FeedHttpRequest();
        String data = feed.getRequestData();
        return data;
    }

    protected void onPostExecute(String data) {

        // do stuff with feed data here!
        Log.d(TAG, "Http data feed = " + data);
    }
}
