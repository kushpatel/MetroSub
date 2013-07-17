package com.MetroSub.database;

import com.MetroSub.database.dao.*;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryHelper {

    private DatabaseHelper mDatabaseHelper;
    private RoutesDao mRoutesDao;
    private ShapesDao mShapesDao;
    private StopsDao mStopsDao;
    private TransfersDao mTransfersDao;
    private TripsDao mTripsDao;
    private StationEntrancesDao mStationEntrancesDao;

    public QueryHelper(DatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
        mRoutesDao = databaseHelper.getRoutesDao();
        mShapesDao = databaseHelper.getShapesDao();
        mStopsDao = databaseHelper.getStopsDao();
        mTransfersDao = databaseHelper.getTransfersDao();
        mTripsDao = databaseHelper.getTripsDao();
        mStationEntrancesDao = databaseHelper.getStationEntrancesDao();
    }

    public ArrayList<Character> queryForStopLines(String stopId) {
        StopData stopData = mStopsDao.queryForId(stopId);
        StationEntranceData stationEntranceData = mStationEntrancesDao.
                queryForStation(stopData.getStopLat(),stopData.getStopLon());
        return stationEntranceData.getRouteLines();
    }

    public boolean sample(String stopId) {
        StopData stopData = mStopsDao.queryForId(stopId);
        StationEntranceData stationEntranceData = mStationEntrancesDao.
                queryForStation(stopData.getStopLat(),stopData.getStopLon());
        return stationEntranceData.getLine_1();
    }

}
