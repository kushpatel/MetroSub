package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/9/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class StopsDao extends BaseDaoImpl<StopData, String> {

    private static String TAG = "StopsDao";

    public StopsDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, StopData.class);
    }

    @Override
    public int create(StopData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG,"Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<StopData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG,"Query for all failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public StopData queryForId(String stopId) {
        try {
            return super.queryForId(stopId);
        } catch(Exception e) {
            Log.e(TAG,"Query for stop id " + stopId + " failed: " + e.getMessage());
            return null;
        }
    }

    public String queryForStopId(String lat, String lon) {
        String stopId = null;
        try {
            QueryBuilder<StopData, String> queryBuilder = queryBuilder();
            queryBuilder.where().eq(StopData.STOP_LAT_COL_NAME, lat).and()
                    .eq(StopData.STOP_LON_COL_NAME, lon);
            PreparedQuery<StopData> preparedQuery = queryBuilder.prepare();
            StopData data = queryForFirst(preparedQuery);
            stopId = (data.getParentStation() == null || data.getParentStation().equals("")) ?
                            data.getStopId() : data.getParentStation();
        } catch (SQLException e) {
            Log.e(TAG, "Query for station lat = " + lat + " lon = " + lon + " failed: " + e.getMessage());
        }
        return stopId;
    }

    public StopData queryForStation(String lat, String lon) {
        StopData stopData = null;
        try {
            QueryBuilder<StopData, String> queryBuilder = queryBuilder();
            queryBuilder.where().eq(StopData.STOP_LAT_COL_NAME, lat).and()
                    .eq(StopData.STOP_LON_COL_NAME, lon);
            PreparedQuery<StopData> preparedQuery = queryBuilder.prepare();
            stopData = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            Log.e(TAG, "Query for station lat = " + lat + " lon = " + lon + " failed: " + e.getMessage());
        }
        return stopData;
    }

}
