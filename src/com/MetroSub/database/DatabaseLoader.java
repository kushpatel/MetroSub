package com.MetroSub.database;

import android.text.TextUtils;
import android.util.Log;
import com.MetroSub.database.dao.StopsDao;
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

    public static final int LOAD_STOPS = 0;

    public static void loadDatabase(DatabaseHelper databaseHelper, InputStream inputStream, int selector) {
        switch (selector) {
            case LOAD_STOPS:
                loadStops(databaseHelper, inputStream);
                break;
        }
    }

    /* Local variables and function for loading data from stops.txt
    ====================================================================================================================*/
    private static final int STOP_ID_POS = 0;
    private static final int STOP_CODE_POS = 1;
    private static final int STOP_NAME_POS = 2;
    private static final int STOP_DESC_POS = 3;
    private static final int STOP_LAT_POS = 4;
    private static final int STOP_LON_POS = 5;
    private static final int ZONE_ID_POS = 6;
    private static final int STOP_URL_POS = 7;
    private static final int LOCATION_TYPE_POS = 8;
    private static final int PARENT_STATION_POS = 9;

    private static void loadStops(DatabaseHelper databaseHelper, InputStream inputStream) {

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StopsDao stopsDao = databaseHelper.getStopsDao();

        try {
            // skip the first header line
            String row = in.readLine();

            while ((row = in.readLine()) != null) {
                String[] tokens = TextUtils.split(row,DELIMITER);

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

}
