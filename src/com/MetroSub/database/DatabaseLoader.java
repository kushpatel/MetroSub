package com.MetroSub.database;

import android.text.TextUtils;
import android.util.Log;
import com.MetroSub.database.dao.*;
import com.MetroSub.database.dataobjects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

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
    public static final int LOAD_STATION_ENTRANCES = 6;

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
            case LOAD_STATION_ENTRANCES:
                loadStationEntrances(databaseHelper, inputStream);
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
                String[] tokens = TextUtils.split(row, DELIMITER);

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

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        ShapesDao shapesDao = databaseHelper.getShapesDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row, DELIMITER);

                ShapeData shapeData = new ShapeData();
                shapeData.setShapeId(tokens[SHAPE_ID_POS]);
                //shapeData.setGeneratedKey(tokens[SHAPE_ID_POS], tokens[SHAPE_PT_SEQUENCE_POS]);
                shapeData.setShapePtLat(tokens[SHAPE_PT_LAT_POS]);
                shapeData.setShapePtLon(tokens[SHAPE_PT_LON_POS]);
                shapeData.setShapePtSequence(tokens[SHAPE_PT_SEQUENCE_POS]);
                shapeData.setShapePtLine(tokens[SHAPE_ID_POS].charAt(0));

                shapesDao.create(shapeData);
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

        StationEntrancesDao stationEntrancesDao = databaseHelper.getStationEntrancesDao();

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

                // Copy lines passing through each stop from station_entrances.csv data
                String lat = tokens[STOP_LAT_POS];
                String lon = tokens[STOP_LON_POS];
                StationEntranceData stationEntranceData = stationEntrancesDao.queryForStation(lat, lon);
                if (stationEntranceData != null) {
                    copyLinesDataFromStationsToStops(stationEntranceData, stopData);
                }

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

    private static void copyLinesDataFromStationsToStops(StationEntranceData station, StopData stop) {
        stop.setLine_1(station.getLine_1());
        stop.setLine_2(station.getLine_2());
        stop.setLine_3(station.getLine_3());
        stop.setLine_4(station.getLine_4());
        stop.setLine_5(station.getLine_5());
        stop.setLine_6(station.getLine_6());
        stop.setLine_7(station.getLine_7());
        stop.setLine_A(station.getLine_A());
        stop.setLine_B(station.getLine_B());
        stop.setLine_C(station.getLine_C());
        stop.setLine_D(station.getLine_D());
        stop.setLine_E(station.getLine_E());
        stop.setLine_F(station.getLine_F());
        stop.setLine_G(station.getLine_G());
        stop.setLine_J(station.getLine_J());
        stop.setLine_L(station.getLine_L());
        stop.setLine_M(station.getLine_M());
        stop.setLine_N(station.getLine_N());
        stop.setLine_Q(station.getLine_Q());
        stop.setLine_R(station.getLine_R());
        stop.setLine_S(station.getLine_S());
        stop.setLine_Z(station.getLine_Z());
    }

    /* Function for loading data from transfers.txt
    ====================================================================================================================*/

    private static void loadTransfers(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int FROM_STOP_ID_POS = 0;
        final int TO_STOP_ID_POS = 1;
        final int TRANSFER_TYPE_POS = 2;
        final int MIN_TRANSFER_TYPE_POS = 3;

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        TransfersDao transfersDao = databaseHelper.getTransfersDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row, DELIMITER);

                TransferData transferData = new TransferData();
                transferData.setFromStopId(tokens[FROM_STOP_ID_POS]);
                transferData.setToStopId(tokens[TO_STOP_ID_POS]);
                transferData.setTransferType(tokens[TRANSFER_TYPE_POS]);
                transferData.setMinTransferTime(tokens[MIN_TRANSFER_TYPE_POS]);

                transfersDao.create(transferData);
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

    /* Function for loading data from trips.txt
    ====================================================================================================================*/

    private static void loadTrips(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int ROUTE_ID_POS = 0;
        final int SERVICE_ID_POS = 1;
        final int TRIP_ID_POS = 2;
        final int TRIP_HEADSIGN_POS = 3;
        final int DIRECTION_ID_POS = 4;
        final int BLOCK_ID_POS = 5;
        final int SHAPE_ID_POS = 6;

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        TripsDao tripsDao = databaseHelper.getTripsDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row, DELIMITER);

                TripData tripData = new TripData();
                tripData.setTripId(tokens[TRIP_ID_POS]);
                tripData.setTripHeadsign(tokens[TRIP_HEADSIGN_POS]);
                tripData.setDirectionId(tokens[DIRECTION_ID_POS]);

                tripsDao.create(tripData);
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

    /* Function for loading data from station_entrances.csv
    ====================================================================================================================*/

    private static void loadStationEntrances(DatabaseHelper databaseHelper, InputStream inputStream) {

        final int DIVISION_POS = 0;
        final int LINE_POS = 1;
        final int STATION_NAME_POS = 2;
        final int STATION_LAT_POS = 3;
        final int STATION_LON_POS = 4;
        final int ROUTES_START_POS = 5;
        final int ROUTE_END_POS = 15;
        final int ENTRANCE_TYPE_POS = 16;
        final int ENTRY_POS = 17;
        final int EXIT_ONLY_POS = 18;
        final int VENDING_POS = 19;
        final int STAFFING_POS = 20;
        final int STAFFING_HOURS_POS = 21;
        final int ADA_POS = 22;
        final int ADA_NOTES_POS = 23;
        final int FREE_CROSSOVER_POS = 24;
        final int NORTH_SOUTH_STREET_POS = 25;
        final int EAST_WEST_STREET_POS = 26;
        final int CORNER_POS = 27;
        final int CORNER_LAT_POS = 28;
        final int CORNER_LON_POS = 29;

        final int NUM_ROUTE_LINES = ROUTE_END_POS - ROUTES_START_POS + 1;

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StationEntrancesDao stationEntrancesDao = databaseHelper.getStationEntrancesDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row, DELIMITER);

                StationEntranceData stationEntranceData = new StationEntranceData();
                stationEntranceData.setDivision(tokens[DIVISION_POS]);
                stationEntranceData.setLine(tokens[LINE_POS]);
                stationEntranceData.setStationName(tokens[STATION_NAME_POS]);
                stationEntranceData.setStationLat(tokens[STATION_LAT_POS]);
                stationEntranceData.setStationLon(tokens[STATION_LON_POS]);
                ArrayList<Character> routeLines = new ArrayList<Character>();
                for (int collectionCount = 0; collectionCount < NUM_ROUTE_LINES; collectionCount++) {
                    String routeLine = tokens[ROUTES_START_POS + collectionCount];
                    if (routeLine != null && !routeLine.equals("")) {
                        routeLines.add(routeLine.charAt(0));
                    } else {
                        // reached end of route lines list
                        break;
                    }
                }
                stationEntranceData = loadLinesPassingStation(routeLines, stationEntranceData);
                stationEntranceData.setRouteLines(routeLines);
                stationEntranceData.setNorthSouthStreet(tokens[NORTH_SOUTH_STREET_POS]);
                stationEntranceData.setEastWestStreet(tokens[EAST_WEST_STREET_POS]);
                stationEntranceData.setCorner(tokens[CORNER_POS]);
                stationEntranceData.setCornerLat(tokens[CORNER_LAT_POS]);
                stationEntranceData.setCornerLon(tokens[CORNER_LON_POS]);

                stationEntrancesDao.create(stationEntranceData);
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

    private static StationEntranceData loadLinesPassingStation(ArrayList<Character> routeLines, StationEntranceData stationEntranceData) {

        for (int routeLinePos = 0; routeLinePos < routeLines.size(); routeLinePos++) {
            switch (routeLines.get(routeLinePos)) {
                case '1':
                    stationEntranceData.setLine_1(true);
                    break;
                case '2':
                    stationEntranceData.setLine_2(true);
                    break;
                case '3':
                    stationEntranceData.setLine_3(true);
                    break;
                case '4':
                    stationEntranceData.setLine_4(true);
                    break;
                case '5':
                    stationEntranceData.setLine_5(true);
                    break;
                case '6':
                    stationEntranceData.setLine_6(true);
                    break;
                case '7':
                    stationEntranceData.setLine_7(true);
                    break;
                case 'A':
                    stationEntranceData.setLine_A(true);
                    break;
                case 'B':
                    stationEntranceData.setLine_B(true);
                    break;
                case 'C':
                    stationEntranceData.setLine_C(true);
                    break;
                case 'D':
                    stationEntranceData.setLine_D(true);
                    break;
                case 'E':
                    stationEntranceData.setLine_E(true);
                    break;
                case 'F':
                    stationEntranceData.setLine_F(true);
                    break;
                case 'G':
                    stationEntranceData.setLine_G(true);
                    break;
                case 'J':
                    stationEntranceData.setLine_J(true);
                    break;
                case 'L':
                    stationEntranceData.setLine_L(true);
                    break;
                case 'M':
                    stationEntranceData.setLine_M(true);
                    break;
                case 'N':
                    stationEntranceData.setLine_N(true);
                    break;
                case 'Q':
                    stationEntranceData.setLine_Q(true);
                    break;
                case 'R':
                    stationEntranceData.setLine_R(true);
                    break;
                case 'S':
                    stationEntranceData.setLine_S(true);
                    break;
                case 'Z':
                    stationEntranceData.setLine_Z(true);
                    break;
            }
        }
        return stationEntranceData;
    }

}
