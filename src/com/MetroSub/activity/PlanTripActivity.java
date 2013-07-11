package com.MetroSub.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.MetroSub.R;
import com.MetroSub.database.DatabaseHelper;
import com.MetroSub.database.DatabaseLoader;
import com.MetroSub.database.LoadDatabaseTask;
import com.MetroSub.database.dao.StopsDao;
import com.MetroSub.database.dao.TripsDao;
import com.MetroSub.database.dataobjects.StopData;
import com.MetroSub.database.dataobjects.TripData;
import com.MetroSub.datamine.RetrieveFeedTask;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 6/30/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlanTripActivity extends BaseActivity {

    private String TAG = "PlanTripActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_planner_screen);

        Button planTripLocationButton = (Button) findViewById(R.id.plan_trip_location_button);
        planTripLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTripByLocationActivity(PlanTripActivity.this);
            }
        });

        Button planTripSubwayButton = (Button) findViewById(R.id.plan_trip_subway_lines_button);
        planTripSubwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTripBySubwayLinesActivity(PlanTripActivity.this);
            }
        });

        // get gtfs data stream from http request
        //FeedHttpRequest feed = new FeedHttpRequest();
        //InputStream inputStream = feed.getRequestData();

        // get static gtfs data stream from local resource (res/raw/gtfs) -- for testing only!
        InputStream inputStream = getResources().openRawResource(R.raw.gtfs);

        // example of how to retrieve data feed in the background
        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute(inputStream);

        // Example of how to query the loaded database
        DatabaseHelper databaseHelper = getMainApp().getDatabaseHelper();
        StopsDao stopsDao = databaseHelper.getStopsDao();
        StopData queriedData = stopsDao.queryForId("127N");   // should be Times Sq.
        if (queriedData != null) {
            Log.d(TAG, "Queried data = " + queriedData.getStopId() + " " + queriedData.getStopName() + " " + queriedData.getStopLat() + " " +
                    queriedData.getStopLon() + " " + queriedData.getLocationType() + " " + queriedData.getParentStation());
        }

        TripsDao tripsDao = databaseHelper.getTripsDao();
        TripData queriedData2 = tripsDao.queryForId("A20121216WKD_036000_1..N03R");   // should be Van Cortlandt Park - 242 St
        if (queriedData2 != null) {
            Log.d(TAG, "Queried data = " + queriedData2.getRouteId() + " " + queriedData2.getServiceId() + " " + queriedData2.getTripId() + " " +
                    queriedData2.getTripHeadSign() + " " + queriedData2.getDirectionId() + " " + queriedData2.getShapeId());
        }

    }

    private void startTripByLocationActivity(Context ctx) {
        Intent intent = new Intent(ctx, TripByLocationActivity.class);
        startActivity(intent);
    }

    private void startTripBySubwayLinesActivity(Context ctx) {
        Intent intent = new Intent(ctx, TripBySubwayLinesActivity.class);
        startActivity(intent);
    }
}
