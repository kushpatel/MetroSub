package com.MetroSub.database;

import android.text.TextUtils;
import android.util.Log;
import com.MetroSub.database.dao.RoutesDao;
import com.MetroSub.database.dao.StopsDao;
import com.MetroSub.database.dataobjects.RouteData;
import com.MetroSub.database.dataobjects.StopData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 7:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseLoader {

    private static String TAG = "DatabaseLoader";

    private static String DELIMITER = ",";

    public static final int LOAD_ROUTES = 1;
    public static final int LOAD_SHAPES = 2;
    public static final int LOAD_STOPS = 3;
    public static final int LOAD_TRANSFERS = 4;
    public static final int LOAD_TRIPS = 5;

    public static void loadDatabase(DatabaseHelper databaseHelper, InputStream inputStream, int selector) {
        switch (selector) {
            case LOAD_ROUTES:
                loadRoutes(databaseHelper, inputStream);
                break;
            case LOAD_SHAPES:
                loadShapes(databaseHelper, inputStream);
                break;
            case LOAD_STOPS:
                loadStops(databaseHelper, inputStream);
                break;
            case LOAD_TRANSFERS:
                loadTransfers(databaseHelper, inputStream);
                break;
            case LOAD_TRIPS:
                loadTrips(databaseHelper, inputStream);
                break;
        }
    }

    /* Function for loading data from routes.txt
    ====================================================================================================================*/

    private static void loadRoutes(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int ROUTE_ID_POS = 0;
        final int AGENCY_ID_POS = 1;
        final int ROUTE_SHORT_NAME_POS = 2;
        final int ROUTE_LONG_NAME_POS = 3;
        final int ROUTE_DESC_POS = 4;
        final int ROUTE_TYPE_POS = 5;
        final int ROUTE_URL_POS = 6;
        final int ROUTE_COLOR_POS = 7;
        final int ROUTE_TEXT_COLOR_POS = 8;

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        RoutesDao routesDao = databaseHelper.getRoutesDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row,DELIMITER);

                RouteData routeData = new RouteData();
                routeData.setRouteId(tokens[ROUTE_ID_POS]);
                routeData.setShortName(tokens[ROUTE_SHORT_NAME_POS]);
                routeData.setLongName(tokens[ROUTE_LONG_NAME_POS]);
                routeData.setRouteDesc(tokens[ROUTE_DESC_POS]);
                routeData.setRouteType(tokens[ROUTE_TYPE_POS]);
                routeData.setRouteUrl(tokens[ROUTE_URL_POS]);
                routeData.setRouteColor(tokens[ROUTE_COLOR_POS]);

                routesDao.create(routeData);
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                // don't really care what happens here
            }
        }
    }

    /* Function for loading data from shapes.txt
    ====================================================================================================================*/

    private static void loadShapes(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int SHAPE_ID_POS = 0;
        final int SHAPE_PT_LAT_POS = 1;
        final int SHAPE_PT_LON_POS = 2;
        final int SHAPE_PT_SEQUENCE_POS = 3;
        final int SHAPE_DIST_TRAVELED = 4;

//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//        StopsDao stopsDao = databaseHelper.getStopsDao();
//
//        try {
//            // skip the first header line
//            String row = in.readLine();
//
//            while ((row = in.readLine()) != null) {
//                String[] tokens = TextUtils.split(row,DELIMITER);
//
//                StopData stopData = new StopData();
//                stopData.setStopId(tokens[STOP_ID_POS]);
//                stopData.setStopName(tokens[STOP_NAME_POS]);
//                stopData.setStopLat(tokens[STOP_LAT_POS]);
//                stopData.setStopLon(tokens[STOP_LON_POS]);
//                stopData.setLocationType(tokens[LOCATION_TYPE_POS]);
//                stopData.setParentStation(tokens[PARENT_STATION_POS]);
//
//                stopsDao.create(stopData);
//            }
//
//        } catch (IOException e) {
//            Log.e(TAG, "IOException: " + e.getMessage());
//        } finally {
//            try {
//                in.close();
//            } catch (Exception e) {
//                // don't really care what happens here
//            }
//        }
    }

    /* Function for loading data from stops.txt
    ====================================================================================================================*/

    private static void loadStops(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int STOP_ID_POS = 0;
        final int STOP_CODE_POS = 1;
        final int STOP_NAME_POS = 2;
        final int STOP_DESC_POS = 3;
        final int STOP_LAT_POS = 4;
        final int STOP_LON_POS = 5;
        final int ZONE_ID_POS = 6;
        final int STOP_URL_POS = 7;
        final int LOCATION_TYPE_POS = 8;
        final int PARENT_STATION_POS = 9;


        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StopsDao stopsDao = databaseHelper.getStopsDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row, DELIMITER);

                StopData stopData = new StopData();
                stopData.setStopId(tokens[STOP_ID_POS]);
                stopData.setStopName(tokens[STOP_NAME_POS]);
                stopData.setStopLat(tokens[STOP_LAT_POS]);
                stopData.setStopLon(tokens[STOP_LON_POS]);
                stopData.setLocationType(tokens[LOCATION_TYPE_POS]);
                stopData.setParentStation(tokens[PARENT_STATION_POS]);

                stopsDao.create(stopData);
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                // don't really care what happens here
            }
        }
    }

    /* Function for loading data from transfers.txt
    ====================================================================================================================*/

    private static void loadTransfers(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int ROUTE_ID_POS = 0;
        final int SERVICE_ID_POS = 1;
        final int TRIP_ID_POS = 2;
        final int TRIP_HEADSIGN_POS = 3;
        final int DIRECTION_ID_POS = 4;
        final int BLOCK_ID_POS = 5;
        final int SHAPE_ID_POS = 6;

//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//        StopsDao stopsDao = databaseHelper.getStopsDao();
//
//        try {
//            // skip the first header line
//            String row = in.readLine();
//
//            while ((row = in.readLine()) != null) {
//                String[] tokens = TextUtils.split(row,DELIMITER);
//
//                StopData stopData = new StopData();
//                stopData.setStopId(tokens[STOP_ID_POS]);
//                stopData.setStopName(tokens[STOP_NAME_POS]);
//                stopData.setStopLat(tokens[STOP_LAT_POS]);
//                stopData.setStopLon(tokens[STOP_LON_POS]);
//                stopData.setLocationType(tokens[LOCATION_TYPE_POS]);
//                stopData.setParentStation(tokens[PARENT_STATION_POS]);
//
//                stopsDao.create(stopData);
//            }
//
//        } catch (IOException e) {
//            Log.e(TAG, "IOException: " + e.getMessage());
//        } finally {
//            try {
//                in.close();
//            } catch (Exception e) {
//                // don't really care what happens here
//            }
//        }
    }

    /* Function for loading data from trips.txt
    ====================================================================================================================*/

    private static void loadTrips(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int FROM_STOP_ID_POS = 0;
        final int TO_STOP_ID_POS = 1;
        final int TRANSFER_TYPE_POS = 2;
        final int MIN_TRANSFER_TYPE_POS = 3;

//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//        StopsDao stopsDao = databaseHelper.getStopsDao();
//
//        try {
//            // skip the first header line
//            String row = in.readLine();
//
//            while ((row = in.readLine()) != null) {
//                String[] tokens = TextUtils.split(row,DELIMITER);
//
//                StopData stopData = new StopData();
//                stopData.setStopId(tokens[STOP_ID_POS]);
//                stopData.setStopName(tokens[STOP_NAME_POS]);
//                stopData.setStopLat(tokens[STOP_LAT_POS]);
//                stopData.setStopLon(tokens[STOP_LON_POS]);
//                stopData.setLocationType(tokens[LOCATION_TYPE_POS]);
//                stopData.setParentStation(tokens[PARENT_STATION_POS]);
//
//                stopsDao.create(stopData);
//            }
//
//        } catch (IOException e) {
//            Log.e(TAG, "IOException: " + e.getMessage());
//        } finally {
//            try {
//                in.close();
//            } catch (Exception e) {
//                // don't really care what happens here
//            }
//        }
    }

}
