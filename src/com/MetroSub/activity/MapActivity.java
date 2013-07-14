package com.MetroSub.activity;

import android.os.Bundle;
import android.util.Log;
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
    public static final LatLng MANHATTAN = new LatLng(40.7697,-73.9735);
    public static final float DEFAULT_ZOOM_LEVEL = 12;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Center and zoom camera on New York City
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(MANHATTAN, DEFAULT_ZOOM_LEVEL));

        // Add sample markers
        if (map!=null){
            Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                    .title("Hamburg"));
            Marker kiel = map.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel")
                    .snippet("Kiel is cool"));
            //.icon(BitmapDescriptorFactory
            //      .fromResource(R.drawable.ic_launcher)));
        }


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

        // Example of how to use QueryHelper to query the database
        QueryHelper queryHelper = getMainApp().getQueryHelper();
        ArrayList<String> routeLines = queryHelper.queryForStopLines("127");  // should give Times Sq lines
        Log.d(TAG,routeLines.toString());

        //getShaKey();     //code to troubleshoot if key for google maps api is incorrect
    }

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
