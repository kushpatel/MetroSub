package com.MetroSub.datamine;

import android.util.Log;
import com.google.protobuf.ByteString;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime;
import com.google.transit.realtime.NyctSubway;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/7/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class GtfsFeed {

    private String TAG = "GtfsFeed";

    private GtfsRealtime.FeedMessage feedMessage;

    public GtfsFeed(ByteString gtfsData) {
        feedMessage = createFeedMessage(gtfsData);
    }

    private GtfsRealtime.FeedMessage createFeedMessage(ByteString gtfsData) {

        // Remember to add NYCT registries to the extension registry which is then passed as parameter in GTFS parsing
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        NyctSubway.registerAllExtensions(registry);

        try {
            // parse GTFS data using GTFS real-time library
            return GtfsRealtime.FeedMessage.parseFrom(gtfsData,registry);

        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "InvalidProtocolBufferException: " + e.getMessage());
            return null;
        }
    }

    public void updateGtfsFeed(ByteString gtfsData) {
        synchronized (feedMessage) {
            feedMessage = createFeedMessage(gtfsData);
        }
    }

    private List<Long> getNextTrainsTimestamps(String line, String stopId) {

        List<Long> nextTrainTimesList = new ArrayList<Long>();

        // for each entity (train) in the feed
        for (GtfsRealtime.FeedEntity feedEntity : feedMessage.getEntityList()) {
            // trains matching the line provided as parameter
            if (feedEntity.getTripUpdate().getTrip().getRouteId().equals(line)) {
                // Iterate over all the trains stops
                for (GtfsRealtime.TripUpdate.StopTimeUpdate stopTimeUpdate : feedEntity.getTripUpdate().getStopTimeUpdateList()) {
                    // trains having stops at the stopId specified
                    if (stopTimeUpdate.getStopId().equals(stopId)) {
                        nextTrainTimesList.add(stopTimeUpdate.getArrival().getTime());
                    }
                }
            }
        }
        return nextTrainTimesList;
    }

    // Fun fact: Unix timestamp = number of seconds since 1st Jan 1970, 00:00:00 UTC
    private Calendar getTimeFromUnixTimeStamp(long unixTimeStamp) {
        // java.util.Date expects time in milliseconds
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTimeStamp * 1000);
        return calendar;
    }

    public synchronized List<Integer> getNextTrainsArrival(String line, String stopId) {

        // Current time is in seconds
        long currentTime = System.currentTimeMillis() / 1000;

        List<Long> trainTimestamps = getNextTrainsTimestamps(line, stopId);
        List<Integer> nextTrainsInMinutesList = new ArrayList<Integer>();

        for (long trainTimestamp : trainTimestamps) {
            int timeDifference = (int) (trainTimestamp - currentTime) / 60;
            // only add train time if it is yet to arrive
            if (timeDifference > 0) {
                nextTrainsInMinutesList.add(timeDifference);
            }
        }

        return nextTrainsInMinutesList;
    }

    public void sampleAPILogger() {

        /**
         * FeedMessage has: 1) FeedHeader
         *                  2) FeedEntity
         **/
        GtfsRealtime.FeedHeader feedHeader = feedMessage.getHeader();
        GtfsRealtime.FeedEntity feedEntity_1 = feedMessage.getEntity(0);


        /***************************************************************************************************************
         * FeedHeader has: 1) gtfs_realtime_version
         *                 2) incrementally
         *                 3) timestamp
         *                 4) extension = nyctFeedHeader
         **/
        Log.d(TAG,"Header realtime version = " + feedHeader.getGtfsRealtimeVersion());
        Log.d(TAG, "Feed timestamp = " + feedHeader.getTimestamp());

        /**
         * Extension = nyctFeedHeader has: 1) nyct_subway_version
         *                                 2) trip_replacement_period
         **/
        Log.d(TAG,"Nyct subway version = " + feedHeader.getExtension(NyctSubway.nyctFeedHeader).getNyctSubwayVersion());
        /**
         * trip_replacement_period has: 1) route_id
         *                              2) replacement_period
         **/
        Log.d(TAG,"Trip replacement id for index 1 = " + feedHeader.getExtension(NyctSubway.nyctFeedHeader)
                .getTripReplacementPeriod(0).getRouteId());



        /***************************************************************************************************************
         * FeedEntity has: 1) id
         *                 2) trip_update
         *                 3) vehicle
         *                 4) alert
         **/
        Log.d(TAG,"Entity 1, id = " + feedEntity_1.getId());


        /**
         * trip_update has: 1) trip : trip_id, route_id, start_time, start_date, schedule_relationship,
         *                            extension = nyct_trip_descriptor: train_id, is_assigned, direction (enum Direction)
         *                  2) stop_time_update(@count): stop_id, arrival, departure,
         *                                               extension = nyct_stop_time_update: scheduled_track, actual_track
         *                  3) stop
         */
        GtfsRealtime.TripUpdate tripUpdate = feedEntity_1.getTripUpdate();

        GtfsRealtime.TripDescriptor tripDescriptor = tripUpdate.getTrip();
        Log.d(TAG, "Entity 1, trip id = " + tripDescriptor.getTripId());
        Log.d(TAG, "Entity 1, route id = " + tripDescriptor.getRouteId());
        Log.d(TAG,"Entity 1, train id = " + tripDescriptor.getExtension(NyctSubway.nyctTripDescriptor).getTrainId());
        Log.d(TAG, "Entity 1, direction = " + tripDescriptor.getExtension(NyctSubway.nyctTripDescriptor).getDirection().toString());
        Log.d(TAG, "Entity 1, start time = " + tripDescriptor.getStartTime());
        Log.d(TAG, "Entity 1, start date = " + tripDescriptor.getStartDate());

        Log.d(TAG,"Entity 1, number of stations = " + tripUpdate.getStopTimeUpdateCount());
        GtfsRealtime.TripUpdate.StopTimeUpdate stopTimeUpdate_1 = tripUpdate.getStopTimeUpdate(0);
        Log.d(TAG, "Entity 1, 1st stop id = " + stopTimeUpdate_1.getStopId());
        Log.d(TAG, "Entity 1, 1st stop arrival = " + stopTimeUpdate_1.getArrival().getTime());
        Log.d(TAG, "Entity 1, 1st stop departure = " + stopTimeUpdate_1.getDeparture().getTime());
        Log.d(TAG,"Entity 1, 1st stop id's actual track = " +
                stopTimeUpdate_1.getExtension(NyctSubway.nyctStopTimeUpdate).getActualTrack());


        /**
         * vehicle has: 1) trip
         *              2) current_stop_sequence
         *              3) stop_id
         *              4) current_status
         *              5) time_stamp
         */
        Log.d(TAG, "Entity 1, current stop sequence = " + feedEntity_1.getVehicle().getCurrentStopSequence());
        Log.d(TAG, "Entity 1, time stamp = " + feedEntity_1.getVehicle().getTimestamp());

        /**
         * alert has: 1) informed_entity
         *            2) header_text
         */
        Log.d(TAG, "Entity 1, alert header text = " + feedEntity_1.getAlert().getHeaderText());

        // TODO: What is EntitySelector and how is it used?
        //GtfsRealtime.EntitySelector entitySelector = GtfsRealtime.EntitySelector.getDefaultInstance();
        //entitySelector.getRouteId();

        // TODO: How to access StopTimeEvent?


        List<Integer> times = getNextTrainsArrival("1","137N");
        Log.d(TAG, "Next line 1 at station 137N times are: " + times.toString());
    }

}
