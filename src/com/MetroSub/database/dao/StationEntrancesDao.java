package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/13/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class StationEntrancesDao extends BaseDaoImpl<StationEntranceData, Integer> {

    private static String TAG = "StationEntrancesDao";

    public StationEntrancesDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, StationEntranceData.class);
    }

    @Override
    public int create(StationEntranceData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG,"Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<StationEntranceData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG, "Query for all failed: " + e.getMessage());
            return null;
        }
    }
}
