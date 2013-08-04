package com.MetroSub.activity;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.MetroSub.R;
import com.MetroSub.database.dataobjects.ShapeData;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.ui.StationListAdapter;
import com.MetroSub.ui.SubwayLinePlotter;
import com.MetroSub.ui.SubwayTimesListAdapter;
import com.MetroSub.utils.UIUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapActivity extends BaseActivity {

    public static final LatLng MANHATTAN = new LatLng(40.7697, -73.9735);
    public static final float DEFAULT_ZOOM_LEVEL = 12;
    public static final float CLOSE_UP_ZOOM_LEVEL = 17;
    private static final String TAG = "MapActivity";
    protected GoogleMap map;
    protected View mMapOptionsBar;
    protected View mSelectTripByLinesScreen;
    protected View mStationsListScreen;
    protected View mScheduleAlertsOptionsBar;
    protected View mScheduleAlertsScreen;
    protected ActionBar mActionBar;
    protected List<Integer> mNextTrainTimes;
    protected String mCurrentLine;
    protected String mCurrentStation;
    protected String mCurrentStopId;
    protected String mCurrentLineDirection;
    protected String mStartAlertsAfter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mMapOptionsBar = findViewById(R.id.map_options_bar);
        mSelectTripByLinesScreen = findViewById(R.id.select_trip_by_line_screen);

        mStationsListScreen = findViewById(R.id.stations_list_screen);
        mScheduleAlertsOptionsBar = findViewById(R.id.schedule_alerts_option_bar);
        mScheduleAlertsScreen = findViewById(R.id.schedule_alerts_screen);

        mActionBar = getActionBar();


        /* Map setup
        ================================================================================================================*/

        // Hide default zoom buttons
        map.getUiSettings().setZoomControlsEnabled(false);

        // Center and zoom camera on New York City ... default onStart case
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(MANHATTAN, DEFAULT_ZOOM_LEVEL));

        // Add subway polylines
        SubwayLinePlotter.plotLine("1", mQueryHelper, map);
        SubwayLinePlotter.plotLine("2", mQueryHelper, map);
        SubwayLinePlotter.plotLine("3", mQueryHelper, map);
        SubwayLinePlotter.plotLine("4", mQueryHelper, map);
        SubwayLinePlotter.plotLine("5", mQueryHelper, map);
        SubwayLinePlotter.plotLine("6", mQueryHelper, map);
        SubwayLinePlotter.plotLine("7", mQueryHelper, map);

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
                setDefaultActionBar();

                //Show the select trip by lines screen
                mSelectTripByLinesScreen.setVisibility(View.VISIBLE);

            }
        });

        Button startOverButton = (Button) findViewById(R.id.start_over_button);
        startOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the schedule alerts options bar
                mScheduleAlertsOptionsBar.setVisibility(View.GONE);

                // Show the options bar with trip selector buttons
                mMapOptionsBar.setVisibility(View.VISIBLE);

                // Center and zoom camera on New York City ... default onStart case
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(MANHATTAN, DEFAULT_ZOOM_LEVEL));
            }
        });

        Button scheduleAlertsButton = (Button) findViewById(R.id.schedule_alerts_button);
        scheduleAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupSubwayTimesList();
                setupAlertTimesSelector();
            }
        });

        Button backButtonSchedule = (Button) findViewById(R.id.go_back_button_schedule_screen);
        backButtonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the schedule alerts screen
                mScheduleAlertsScreen.setVisibility(View.GONE);

                // Show the schedule alerts options bar
                mScheduleAlertsOptionsBar.setVisibility(View.VISIBLE);
            }
        });

        Button setAlertsButton = (Button) findViewById(R.id.set_alerts_button);
        setAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the schedule alerts screen
                mScheduleAlertsScreen.setVisibility(View.GONE);

                // Show the schedule alerts options bar
                mScheduleAlertsOptionsBar.setVisibility(View.VISIBLE);

                long notificationDelay = 5000;
                //long notificationDelay = UIUtils.getNotificationTime(mStartAlertsAfter);
                if (notificationDelay > 0) {
                    Timer notificationTimer = new Timer();
                    notificationTimer.schedule(new NotificationTimerTask(), notificationDelay);
                }
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

        mActionBar.setCustomView(R.layout.actionbar_custom_stationlist);
        mActionBar.setDisplayShowCustomEnabled(true);
        final RadioGroup selectDirection = (RadioGroup) findViewById(R.id.select_dir);
        final String theline = line;
        selectDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupStationsListScreen(theline);

              /*  int selectedID = selectDirection.getCheckedRadioButtonId();
                if (selectedID == R.id.dir_north)
                {

                }
                else if (selectedID == R.id.dir_south)
                {

                }  */
            }
        });


        //Toast.makeText(MapActivity.this, "Line " + line + " selected!", Toast.LENGTH_LONG).show();

    }

    public void setupStationsListScreen(final String line) {

        mCurrentLine = line;

        ArrayList<StationEntranceData> stationEntranceDataList = mQueryHelper.queryForLineStops(line);

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

                // Pre-processing before marker can be set up on the map
                StationEntranceData stationEntranceData = (StationEntranceData) adapterView.getAdapter().getItem(position);
                int iconResId = UIUtils.getIconForLine(line.charAt(0));
                String stationLat = stationEntranceData.getStationLat();
                String stationLon = stationEntranceData.getStationLon();

                mCurrentStation = stationEntranceData.getStationName();

                String stopId = mQueryHelper.queryForStopId(stationLat, stationLon);
                mCurrentStopId = stopId;

                // Trips suffixed with "S" are northbound!!
                String lineDirection = "S"; //default
                RadioGroup selectDirection = (RadioGroup) findViewById(R.id.select_dir);
                int selectedID = selectDirection.getCheckedRadioButtonId();
                if (selectedID == R.id.dir_south) {
                    lineDirection = "N";
                }
                mCurrentLineDirection = lineDirection;

                mNextTrainTimes = mGtfsFeed.getNextTrainsArrival(line, stopId + lineDirection);
                String markerTitle = mNextTrainTimes.isEmpty() ? "Live data not available." : "Next subway:";
                String minuteString = (!mNextTrainTimes.isEmpty() && mNextTrainTimes.get(0) == 1) ? " minute." : " minutes.";
                String markerSnippet = mNextTrainTimes.isEmpty() ? "" : "In " + mNextTrainTimes.get(0) + minuteString;

                // Hide the stations list screen
                mStationsListScreen.setVisibility(View.GONE);
                setDefaultActionBar();

                if (mNextTrainTimes.isEmpty()) {
                    /// Show the options bar with trip selector buttons
                    mMapOptionsBar.setVisibility(View.VISIBLE);
                } else {
                    // Show the schedule alerts options bar
                    mScheduleAlertsOptionsBar.setVisibility(View.VISIBLE);
                }

                // Set up marker on google map
                LatLng stationCoordinates = new LatLng(Double.parseDouble(stationLat), Double.parseDouble(stationLon));
                map.clear();
                // Add subway polylines
                SubwayLinePlotter.plotLine(line, mQueryHelper, map);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(stationCoordinates, CLOSE_UP_ZOOM_LEVEL));
                Marker marker = map.addMarker(new MarkerOptions().position(stationCoordinates)
                        .title(markerTitle)
                        .snippet(markerSnippet)
                        .icon(BitmapDescriptorFactory.fromResource(iconResId)));
                marker.showInfoWindow();
            }
        });

    }

    public void setupSubwayTimesList() {

        SubwayTimesListAdapter timesAdapter = new SubwayTimesListAdapter(this, R.layout.subway_times_list_item,
                mNextTrainTimes);
        ListView subwayTimesListView = (ListView) findViewById(R.id.subway_times_list);
        subwayTimesListView.setAdapter(timesAdapter);

        TextView currentStationName = (TextView) findViewById(R.id.schedule_station_name);
        currentStationName.setText(mCurrentStation);

        ImageView currentStationIcon = (ImageView) findViewById(R.id.schedule_station_icon);
        currentStationIcon.setImageResource(UIUtils.getIconForLine(mCurrentLine.charAt(0)));

        // Hide the schedule alerts options bar
        mScheduleAlertsOptionsBar.setVisibility(View.GONE);

        // Show the schedule alerts screen
        mScheduleAlertsScreen.setVisibility(View.VISIBLE);

    }

    public void setupAlertTimesSelector() {
        Spinner alertTimesSelector = (Spinner) findViewById(R.id.alert_times_selector);
        alertTimesSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MapActivity.this, "Alerts in " + parent.getItemAtPosition(position).toString() +
                //        " selected!", Toast.LENGTH_LONG).show();
                mStartAlertsAfter = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void setupNotificationForAlert() {

        mNextTrainTimes = mGtfsFeed.getNextTrainsArrival(mCurrentLine, mCurrentStopId + mCurrentLineDirection);
        if (mNextTrainTimes.isEmpty()) return;
        String minuteString = (mNextTrainTimes.get(0) == 1) ? " minute." : " minutes.";
        String contentText = "Next subway is arriving in " + mNextTrainTimes.get(0) + minuteString;

        // Prepare intent which is triggered if the notification is selected
        Intent intent = new Intent(this, MapActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Subway Alert")
                .setContentText(contentText)
                .setLargeIcon(((BitmapDrawable) getResources().getDrawable(R.drawable.metro_icon_transparent)).getBitmap())
                .setSmallIcon(R.drawable.metro_icon_transparent)
                .setContentIntent(pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;

        notificationManager.notify(0, notification);
    }

    private class NotificationTimerTask extends TimerTask {
        public void run() {
            setupNotificationForAlert();
        }
    }

    public void setDefaultActionBar() {
        mActionBar.setDisplayShowCustomEnabled(false);
    }
}
