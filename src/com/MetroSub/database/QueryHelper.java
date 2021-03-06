package com.MetroSub.database;

import android.util.Log;
import com.MetroSub.database.dao.*;
import com.MetroSub.database.dataobjects.ShapeData;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;
import com.MetroSub.utils.BackendUtils;

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

//    public ArrayList<StationEntranceData> queryForLineStops(String subwayLine) {
//
//        List<StationEntranceData> dataWithDuplicateStations = mStationEntrancesDao.queryForStations(subwayLine);
//        ArrayList<StationEntranceData> uniqueStationsData = new ArrayList<StationEntranceData>();
//
//        // Keep only unique entries in the list of stations
//        HashSet<String> stationsHashSet = new HashSet<String>();
//        for (StationEntranceData entry : dataWithDuplicateStations) {
//            if (!stationsHashSet.contains(entry.getStationName())) {
//                uniqueStationsData.add(entry);
//                stationsHashSet.add(entry.getStationName());
//            }
//        }
//
//        uniqueStationsData = BackendUtils.mergeSort(uniqueStationsData);
//        return uniqueStationsData;
//    }

    public ArrayList<StopData> queryForLineStops(String subwayLine) {

        List<StopData> dataWithDuplicateStations = mStopsDao.queryForStations(subwayLine);
        ArrayList<StopData> uniqueStationsData = new ArrayList<StopData>();

        // Keep only unique entries in the list of stations
        HashSet<String> stationsHashSet = new HashSet<String>();
        for (StopData entry : dataWithDuplicateStations) {
            if (!stationsHashSet.contains(entry.getStopName())) {
                uniqueStationsData.add(entry);
                stationsHashSet.add(entry.getStopName());
            }
        }

        return uniqueStationsData;
    }

    public String queryForStopId(String lat, String lon) {
        return mStopsDao.queryForStopId(lat,lon);
    }

    public List<ShapeData> queryForAllShapePoints() {
        return mShapesDao.queryForAll();
    }

    public List<ShapeData> queryForAllLineShapePoints(String line) {
        return mShapesDao.queryForLinePoints(line.charAt(0));
    }

    public List<ShapeData> queryForAllShapePoints(String shapeId) {
        return mShapesDao.queryForShapeIdPoints(shapeId);
    }

}
