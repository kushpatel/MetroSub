package com.MetroSub.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.MetroSub.R;
import com.MetroSub.database.DatabaseHelper;
import com.MetroSub.database.QueryHelper;
import com.MetroSub.database.dao.StopsDao;
import com.MetroSub.database.dao.TripsDao;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;
import com.MetroSub.database.dataobjects.TripData;
import com.MetroSub.datamine.RetrieveFeedTask;
import com.MetroSub.ui.StationListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends BaseActivity {

    public static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    public static final LatLng KIEL = new LatLng(53.551, 9.993);
    public static final LatLng MANHATTAN = new LatLng(40.7697, -73.9735);
    public static final float DEFAULT_ZOOM_LEVEL = 12;
    public static final float CLOSE_UP_ZOOM_LEVEL = 17;
    private static final String TAG = "MapActivity";
    protected GoogleMap map;
    private QueryHelper mQueryHelper;

    protected View mMapOptionsBar;
    protected View mSelectTripByLinesScreen;
    protected View mStationsListScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mQueryHelper = getMainApp().getQueryHelper();

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMapOptionsBar = findViewById(R.id.map_options_bar);
        mSelectTripByLinesScreen = findViewById(R.id.select_trip_by_line_screen);
        mStationsListScreen = findViewById(R.id.stations_list_screen);


        /* Map setup
        ================================================================================================================*/

        // Hide default zoom buttons
        map.getUiSettings().setZoomControlsEnabled(false);

        // Center and zoom camera on New York City ... default onStart case
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(MANHATTAN, DEFAULT_ZOOM_LEVEL));

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
                // Hide the options bar with trip selector buttons
                mMapOptionsBar.setVisibility(View.GONE);

                // Show the select trip by lines screen
                mSelectTripByLinesScreen.setVisibility(View.VISIBLE);
            }
        });


        Button backButtonLines = (Button) findViewById(R.id.go_back_button_select_line);
        backButtonLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the select trip by lines screen
                mSelectTripByLinesScreen.setVisibility(View.GONE);

                // Show the options bar with trip selector buttons
                mMapOptionsBar.setVisibility(View.VISIBLE);
            }
        });

        Button backButtonStations = (Button) findViewById(R.id.go_back_button_stations_list);
        backButtonStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the select stations screen
                mStationsListScreen.setVisibility(View.GONE);

                //Show the select trip by lines screen
                mSelectTripByLinesScreen.setVisibility(View.VISIBLE);
            }
        });

        Button lineButton_1 = (Button) findViewById(R.id.line_1_button);
        lineButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("1");
            }
        });

        Button lineButton_2 = (Button) findViewById(R.id.line_2_button);
        lineButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("2");
            }
        });

        Button lineButton_3 = (Button) findViewById(R.id.line_3_button);
        lineButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("3");
            }
        });

        Button lineButton_4 = (Button) findViewById(R.id.line_4_button);
        lineButton_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("4");
            }
        });

        Button lineButton_5 = (Button) findViewById(R.id.line_5_button);
        lineButton_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("5");
            }
        });

        Button lineButton_6 = (Button) findViewById(R.id.line_6_button);
        lineButton_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("6");
            }
        });

        Button lineButton_7 = (Button) findViewById(R.id.line_7_button);
        lineButton_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("7");
            }
        });

        Button lineButton_S = (Button) findViewById(R.id.line_S_button);
        lineButton_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLine("S");
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

        // Example of how to use QueryHelper to query the database
        ArrayList<Character> routeLines = mQueryHelper.queryForStopLines("127");  // should give Times Sq lines
        Log.d(TAG, routeLines.toString());

        List<StationEntranceData> stations = mQueryHelper.queryForLineStops("3");
        for (StationEntranceData station : stations) {
            Log.d(TAG, "Line 3 goes through: " + station.getStationName());
        }

        //getShaKey();     //code to troubleshoot if key for google maps api is incorrect
    }


    /* Map setup helper functions
    ====================================================================================================================*/

    public void selectLine(String line) {

        //backToMapWithOptionsScreen();

        // Hide the select trip by lines screen
        mSelectTripByLinesScreen.setVisibility(View.GONE);

        // Set up stations list screen using list view adapter
        setupStationsListScreen(line);

        // Show the stations list screen
        mStationsListScreen.setVisibility(View.VISIBLE);

        //Toast.makeText(MapActivity.this, "Line " + line + " selected!", Toast.LENGTH_LONG).show();
        // TODO: Add custom info window to marker

    }

    public void setupStationsListScreen(final String line) {

        List<StationEntranceData> stationEntranceDataList = mQueryHelper.queryForLineStops(line);

        // TODO: setup proper subway line icon in the stations list
        StationListAdapter stationListAdapter = new StationListAdapter(MapActivity.this, R.layout.station_list_item,
                stationEntranceDataList, R.drawable.metro_icon_transparent);

        ListView stationsListView = (ListView) findViewById(R.id.stations_list);
        stationsListView.setAdapter(stationListAdapter);
        stationsListView.setClickable(true);
        stationsListView.setItemsCanFocus(true);

        stationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Hide the stations list screen
                mStationsListScreen.setVisibility(View.GONE);

                // show the default map options bar
                mMapOptionsBar.setVisibility(View.VISIBLE);

                //set up marker on google map
                StationEntranceData stationEntranceData = (StationEntranceData) adapterView.getAdapter().getItem(position);
                int iconResId = getIconForLine(line.charAt(0));
                String stationLat = stationEntranceData.getStationLat();
                String stationLon = stationEntranceData.getCornerLon();
                LatLng stationCoordinates = new LatLng(Double.parseDouble(stationLat),Double.parseDouble(stationLon));
                map.clear();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates, CLOSE_UP_ZOOM_LEVEL));
                map.addMarker(new MarkerOptions().position(stationCoordinates)
                        .title("Next subways:")
                        .snippet("In 3 minutes")
                        .icon(BitmapDescriptorFactory.fromResource(iconResId)));
            }
        });

    }

    public int getIconForLine(char line) {
        int iconResId = 0;
        switch (line) {
            case '1':
                iconResId = R.drawable.number_1;
                break;
            case '2':
                iconResId = R.drawable.number_2;
                break;
            case '3':
                iconResId = R.drawable.number_3;
                break;
            case '4':
                iconResId = R.drawable.number_4;
                break;
            case '5':
                iconResId = R.drawable.number_5;
                break;
            case '6':
                iconResId = R.drawable.number_6;
                break;
            case '7':
                iconResId = R.drawable.number_7;
                break;
            case 'A':
                iconResId = R.drawable.letter_a;
                break;
            case 'B':
                iconResId = R.drawable.letter_b;
                break;
            case 'C':
                iconResId = R.drawable.letter_c;
                break;
            case 'D':
                iconResId = R.drawable.letter_d;
                break;
            case 'E':
                iconResId = R.drawable.letter_e;
                break;
            case 'F':
                iconResId = R.drawable.letter_f;
                break;
            case 'G':
                iconResId = R.drawable.letter_g;
                break;
            case 'J':
                iconResId = R.drawable.letter_j;
                break;
            case 'L':
                iconResId = R.drawable.letter_l;
                break;
            case 'M':
                iconResId = R.drawable.letter_m;
                break;
            case 'N':
                iconResId = R.drawable.letter_n;
                break;
            case 'Q':
                iconResId = R.drawable.letter_q;
                break;
            case 'R':
                iconResId = R.drawable.letter_r;
                break;
            case 'S':
                iconResId = R.drawable.letter_s;
                break;
            case 'Z':
                iconResId = R.drawable.letter_z;
                break;
        }
        return iconResId;
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
