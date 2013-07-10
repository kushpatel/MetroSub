package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.TripData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TripsDao extends BaseDaoImpl<TripData, String> {

    private static String TAG = "TripsDao";

    public TripsDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TripData.class);
    }

    @Override
    public int create(TripData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG, "Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<TripData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG,"Query for all failed: " + e.getMessage());
            return null;
        }
    }
}
