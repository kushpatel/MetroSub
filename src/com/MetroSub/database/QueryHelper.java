package com.MetroSub.database;

import com.MetroSub.database.dao.*;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public List<StationEntranceData> queryForLineStops(String subwayLine) {

        List<StationEntranceData> dataWithDuplicateStations = mStationEntrancesDao.queryForStations(subwayLine);
        List<StationEntranceData> uniqueStationsData = new ArrayList<StationEntranceData>();

        // Keep only unique entries in the list of stations
        HashSet<String> stationsHashSet = new HashSet<String>();
        for (StationEntranceData entry : dataWithDuplicateStations) {
            if (!stationsHashSet.contains(entry.getStationName())) {
                uniqueStationsData.add(entry);
                stationsHashSet.add(entry.getStationName());
            }
        }

        return uniqueStationsData;
    }

}
