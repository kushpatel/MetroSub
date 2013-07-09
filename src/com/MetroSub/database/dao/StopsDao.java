package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.StopData;
import com.j256.ormlite.dao.BaseDaoImpl;
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

}
