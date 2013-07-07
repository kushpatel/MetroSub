package com.MetroSub.datamine;

import android.os.AsyncTask;
import android.util.Log;
import com.google.protobuf.*;
import com.google.transit.realtime.GtfsRealtime;
import com.google.transit.realtime.NyctSubway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    protected ByteString doInBackground(Void... params) {
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

        //TODO (Kush): parse data feed using protocol buffer libraries


        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        NyctSubway.registerAllExtensions(registry);

        GtfsRealtime.FeedMessage feedMessage = null;
        //GtfsRealtime.TripDescriptor tripDescriptor = null;
        //NyctSubway.NyctFeedHeader nyctFeedHeader = null;
        //NyctSubway.NyctTripDescriptor nyctTripDescriptor = null;
        //NyctSubway.NyctStopTimeUpdate nyctStopTimeUpdate = null;
        //NyctSubway.TripReplacementPeriod tripReplacementPeriod = null;
        try {
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(data,registry);
            //nyctFeedHeader = NyctSubway.NyctFeedHeader.parseFrom(data);
            //nyctTripDescriptor = NyctSubway.NyctTripDescriptor.parseFrom(data);
            //nyctStopTimeUpdate = NyctSubway.NyctStopTimeUpdate.parseFrom(data);
            //tripReplacementPeriod = NyctSubway.TripReplacementPeriod.parseFrom(data);

        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG,"InvalidProtocolBufferException: " + e.getMessage());
        }

        Log.d(TAG,"Header realtime version = " + feedMessage.getHeader().getGtfsRealtimeVersion());

        /*Log.d(TAG,"Trip id = " + tripDescriptor.getTripId());
        Log.d(TAG,"Route id = " + tripDescriptor.getRouteId());
        Log.d(TAG,"Start time = " + tripDescriptor.getStartTime());
        Log.d(TAG, "Start date = " + tripDescriptor.getStartDate());
        Log.d(TAG, "Start date present = " + tripDescriptor.hasStartDate());
        Log.d(TAG, "Route id present = " + tripDescriptor.hasRouteId()); */


        /*List<Descriptors.FieldDescriptor> fieldDescriptors = nyctTripDescriptor.getDescriptorForType().getFields();
        for (Descriptors.FieldDescriptor descriptor : fieldDescriptors) {
            String name = descriptor.getFullName();
            Log.d(TAG,"Descriptor name = " + name);
        }*/

    }
}
