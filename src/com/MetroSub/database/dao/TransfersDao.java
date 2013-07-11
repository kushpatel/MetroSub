package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.TransferData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransfersDao extends BaseDaoImpl<TransferData, String> {

    private static String TAG = "TransfersDao";

    public TransfersDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TransferData.class);
    }

    @Override
    public int create(TransferData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG, "Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<TransferData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG,"Query for all failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public TransferData queryForId(String fromStopId) {
        try {
            return super.queryForId(fromStopId);
        } catch(Exception e) {
            Log.e(TAG,"Query for from stop id " + fromStopId + " failed: " + e.getMessage());
            return null;
        }
    }
}
