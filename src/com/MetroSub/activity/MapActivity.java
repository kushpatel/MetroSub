package com.MetroSub.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.MetroSub.R;
import com.MetroSub.database.DatabaseHelper;
import com.MetroSub.database.QueryHelper;
import com.MetroSub.database.dao.StopsDao;
import com.MetroSub.database.dao.TripsDao;
import com.MetroSub.database.dataobjects.StopData;
import com.MetroSub.database.dataobjects.TripData;
import com.MetroSub.datamine.RetrieveFeedTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends BaseActivity {

    private static final String TAG = "MapActivity";

    public static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    public static final LatLng KIEL = new LatLng(53.551, 9.993);
    public static final LatLng MANHATTAN = new LatLng(40.7697, -73.9735);
    public static final float DEFAULT_ZOOM_LEVEL = 12;
    public static final float CLOSE_UP_ZOOM_LEVEL = 17;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        /* Map setup
        ================================================================================================================*/

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Center and zoom camera on New York City
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(MANHATTAN, DEFAULT_ZOOM_LEVEL));
        // Hide default zoom buttons
        map.getUiSettings().setZoomControlsEnabled(false);

        // TODO: add custom zoom buttons

        // Add sample markers
        if (map != null) {
            Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                    .title("Hamburg"));
            Marker kiel = map.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel")
                    .snippet("Kiel is cool"));
            //.icon(BitmapDescriptorFactory
            //      .fromResource(R.drawable.ic_launcher)));
        }

        /* Map screen UI setup
        ================================================================================================================*/

        Button tripByLinesButton = (Button) findViewById(R.id.plan_trip_lines_button);
        tripByLinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTripByLine();
            }
        });


        Button backButton = (Button) findViewById(R.id.go_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMapWithOptionsScreen();
            }
        });

        Button lineButton_1 = (Button) findViewById(R.id.line_1_button);
        lineButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_1();
            }
        });

        Button lineButton_2 = (Button) findViewById(R.id.line_2_button);
        lineButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_2();
            }
        });

        Button lineButton_3 = (Button) findViewById(R.id.line_3_button);
        lineButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_3();
            }
        });

        Button lineButton_4 = (Button) findViewById(R.id.line_4_button);
        lineButton_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_4();
            }
        });

        Button lineButton_5 = (Button) findViewById(R.id.line_5_button);
        lineButton_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_5();
            }
        });

        Button lineButton_6 = (Button) findViewById(R.id.line_6_button);
        lineButton_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_6();
            }
        });

        Button lineButton_S = (Button) findViewById(R.id.line_S_button);
        lineButton_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine_S();
            }
        });


        /* GTFS feed examples
        ================================================================================================================*/

        // get gtfs data stream from http request
        //FeedHttpRequest feed = new FeedHttpRequest();
        //InputStream inputStream = feed.getRequestData();

        // get static gtfs data stream from local resource (res/raw/gtfs) -- for testing only!
        InputStream inputStream = getResources().openRawResource(R.raw.gtfs);

        // example of how to retrieve data feed in the background
        RetrieveFeedTask task = new RetrieveFeedTask();
        task.execute(inputStream);

        /* Database query examples
        ================================================================================================================*/

        // Example of how to query the loaded database
//        DatabaseHelper databaseHelper = getMainApp().getDatabaseHelper();
//        StopsDao stopsDao = databaseHelper.getStopsDao();
//        StopData queriedData = stopsDao.queryForId("127N");   // should be Times Sq.
//        if (queriedData != null) {
//            Log.d(TAG, "Queried data = " + queriedData.getStopId() + " " + queriedData.getStopName() + " " + queriedData.getStopLat() + " " +
//                    queriedData.getStopLon() + " " + queriedData.getLocationType() + " " + queriedData.getParentStation());
//        }
//
//        TripsDao tripsDao = databaseHelper.getTripsDao();
//        TripData queriedData2 = tripsDao.queryForId("A20121216WKD_036000_1..N03R");   // should be Van Cortlandt Park - 242 St
//        if (queriedData2 != null) {
//            Log.d(TAG, "Queried data = " + queriedData2.getRouteId() + " " + queriedData2.getServiceId() + " " + queriedData2.getTripId() + " " +
//                    queriedData2.getTripHeadSign() + " " + queriedData2.getDirectionId() + " " + queriedData2.getShapeId());
//        }
//
//        // Example of how to use QueryHelper to query the database
//        QueryHelper queryHelper = getMainApp().getQueryHelper();
//        ArrayList<Character> routeLines = queryHelper.queryForStopLines("127");  // should give Times Sq lines
//        Log.d(TAG, routeLines.toString());
//        Log.d(TAG, "" + queryHelper.sample("127"));

        //getShaKey();     //code to troubleshoot if key for google maps api is incorrect
    }

    private void selectTripByLine() {

        // Hide the options bar with trip selector buttons
        findViewById(R.id.map_options_bar).setVisibility(View.GONE);

        // Show the select trip by lines screen
        findViewById(R.id.select_trip_by_line_screen).setVisibility(View.VISIBLE);

    }

    public void backToMapWithOptionsScreen() {

        // Hide the select trip by lines screen
        findViewById(R.id.select_trip_by_line_screen).setVisibility(View.GONE);

        // Show the options bar with trip selector buttons
        findViewById(R.id.map_options_bar).setVisibility(View.VISIBLE);

    }

    public void selectLine_1() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 1 selected!", Toast.LENGTH_LONG).show();

        // Reposition map to a station with the selected line near current location
        // Sample code
        // TODO: Add custom info window to marker
        LatLng TIMES_SQUARE = new LatLng(40.7566, -73.9863);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(TIMES_SQUARE, CLOSE_UP_ZOOM_LEVEL));
        map.addMarker(new MarkerOptions().position(TIMES_SQUARE)
                .title("Next subways:")
                .snippet("In 3 minutes")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.number_1)));

    }

    public void selectLine_2() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 2 selected!", Toast.LENGTH_LONG).show();

    }

    public void selectLine_3() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 3 selected!", Toast.LENGTH_LONG).show();

    }

    public void selectLine_4() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 4 selected!", Toast.LENGTH_LONG).show();

    }

    public void selectLine_5() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 5 selected!", Toast.LENGTH_LONG).show();

    }

    public void selectLine_6() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line 6 selected!", Toast.LENGTH_LONG).show();

    }

    public void selectLine_S() {

        backToMapWithOptionsScreen();

        Toast.makeText(MapActivity.this, "Line S selected!", Toast.LENGTH_LONG).show();

    }

//    public class CustomInfoWindowAdapter extends GoogleMap.InfoWindowAdapter {
//
//        public View getInfoWindow(Marker marker) {
//            return getWindow();
//        }
//
//    }

    //code to troubleshoot if key for google maps api is incorrect
    /*private void getShaKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.MetroSub.activity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v(TAG, "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

    } */

}
