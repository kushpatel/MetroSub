package com.MetroSub.datamine;

import android.util.Log;
import com.google.protobuf.ByteString;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime;
import com.google.transit.realtime.NyctSubway;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/7/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class GtfsParser {

    private String TAG = "GtfsParser";

    private GtfsRealtime.FeedMessage feedMessage;

    public GtfsParser(ByteString gtfsData) {

        // Remember to add NYCT registries to the extension registry which is then passed as parameter in GTFS parsing
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        NyctSubway.registerAllExtensions(registry);

        try {
            // parse GTFS data using GTFS real-time library
            feedMessage = GtfsRealtime.FeedMessage.parseFrom(gtfsData,registry);

        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "InvalidProtocolBufferException: " + e.getMessage());
        }
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
        Log.d(TAG,"Entity 1, train id = " + tripDescriptor.getExtension(NyctSubway.nyctTripDescriptor).getTrainId());

        Log.d(TAG,"Entity 1, number of stations = " + tripUpdate.getStopTimeUpdateCount());
        GtfsRealtime.TripUpdate.StopTimeUpdate stopTimeUpdate_1 = tripUpdate.getStopTimeUpdate(0);
        Log.d(TAG, "Entity 1, 1st stop id = " + stopTimeUpdate_1.getStopId());
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


    }

}
