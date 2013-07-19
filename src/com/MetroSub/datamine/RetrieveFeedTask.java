package com.MetroSub.datamine;

import android.os.AsyncTask;
import android.util.Log;
import com.google.protobuf.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 7/2/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetrieveFeedTask extends AsyncTask<Void, Void, ByteString> {

    private String TAG = "RetrieveFeedTask";

    @Override
    protected ByteString doInBackground(Void... voids) {

        // get GTFS data stream from http request
        FeedHttpRequest feed = new FeedHttpRequest();
        InputStream inputStream = feed.getRequestData();

        ByteString feedByteString = null;
        try {
            feedByteString = ByteString.readFrom(inputStream);
        } catch (IOException e) {
            Log.e(TAG,"IOException: " + e.getMessage());
        }

        return feedByteString;
    }

    protected void onPostExecute(ByteString data) {

        // do stuff with feed data here!

        Log.d(TAG, "Http data feed = " + data.toStringUtf8());

        GtfsParser gtfsParser = new GtfsParser(data);
        // stub method that currently just Log.d various items parsed from GTFS data
        gtfsParser.sampleAPILogger();

    }
}
