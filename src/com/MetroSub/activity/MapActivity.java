package com.MetroSub.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.MetroSub.R;
import com.MetroSub.database.dataobjects.ShapeData;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.ui.StationListAdapter;
import com.MetroSub.ui.SubwayLinePlotter;
import com.MetroSub.utils.UIUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends BaseActivity {

    private static final String TAG = "MapActivity";

    public static final LatLng MANHATTAN = new LatLng(40.7697, -73.9735);
    public static final float DEFAULT_ZOOM_LEVEL = 12;
    public static final float CLOSE_UP_ZOOM_LEVEL = 17;

    protected GoogleMap map;
    protected View mMapOptionsBar;
    protected View mSelectTripByLinesScreen;
    protected View mStationsListScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

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

        // Add subway polylines
        List<ShapeData> shapePoints = mQueryHelper.queryForAllShapePoints("4..S01R");
        SubwayLinePlotter.drawLine("4",shapePoints,map);

        shapePoints = mQueryHelper.queryForAllShapePoints("E..N66R");
        SubwayLinePlotter.drawLine("E", shapePoints, map);

        /* Map screen UI setup
        ================================================================================================================*/

        Button zoominButton = (Button) findViewById(R.id.zoomin);
        zoominButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });
        Button zoomoutButton = (Button) findViewById(R.id.zoomout);
        zoomoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });

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


        //mGtfsFeed.sampleAPILogger();

        //getShaKey(this);     //code to troubleshoot if key for google maps api is incorrect

    }


    /* Map setup helper functions
    ====================================================================================================================*/

    public void selectLine(String line) {

        // Hide the select trip by lines screen
        mSelectTripByLinesScreen.setVisibility(View.GONE);

        // Set up stations list screen using list view adapter
        setupStationsListScreen(line);

        // Show the stations list screen
        mStationsListScreen.setVisibility(View.VISIBLE);

        //Toast.makeText(MapActivity.this, "Line " + line + " selected!", Toast.LENGTH_LONG).show();

    }

    public void setupStationsListScreen(final String line) {

        List<StationEntranceData> stationEntranceDataList = mQueryHelper.queryForLineStops(line);

        int iconResId = UIUtils.getIconForLine(line.charAt(0));
        StationListAdapter stationListAdapter = new StationListAdapter(MapActivity.this, R.layout.station_list_item,
                stationEntranceDataList, iconResId);

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

                // Pre-processing before marker can be set up on the map
                StationEntranceData stationEntranceData = (StationEntranceData) adapterView.getAdapter().getItem(position);
                int iconResId = UIUtils.getIconForLine(line.charAt(0));
                String stationLat = stationEntranceData.getStationLat();
                String stationLon = stationEntranceData.getStationLon();

                String stopId = mQueryHelper.queryForStopId(stationLat, stationLon);
                // TODO : check for North or South here using switch to be added
                String lineDirection = "N";
                List<Integer> nextTrainTimes  = mGtfsFeed.getNextTrainsArrival(line, stopId + lineDirection);
                String markerTitle = nextTrainTimes.isEmpty() ? "Live data not available."  : "Next subway:";
                String minuteString = (!nextTrainTimes.isEmpty() && nextTrainTimes.get(0) == 1) ? " minute." : " minutes.";
                String markerSnippet = nextTrainTimes.isEmpty() ? "" : "In " + nextTrainTimes.get(0) + minuteString;

                // Set up marker on google map
                LatLng stationCoordinates = new LatLng(Double.parseDouble(stationLat), Double.parseDouble(stationLon));
                map.clear();
                // Add subway polylines
                List<ShapeData> shapePoints = mQueryHelper.queryForAllShapePoints("4..S01R");
                SubwayLinePlotter.drawLine("4", shapePoints, map);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates, CLOSE_UP_ZOOM_LEVEL));
                Marker marker = map.addMarker(new MarkerOptions().position(stationCoordinates)
                        .title(markerTitle)
                        .snippet(markerSnippet)
                        .icon(BitmapDescriptorFactory.fromResource(iconResId)));
                marker.showInfoWindow();
            }
        });

    }
}
