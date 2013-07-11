package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.RouteData;
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
public class RoutesDao extends BaseDaoImpl<RouteData, String> {

    private static String TAG = "RoutesDao";

    public RoutesDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RouteData.class);
    }

    @Override
    public int create(RouteData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG, "Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<RouteData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG,"Query for all failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public RouteData queryForId(String routeId) {
        try {
            return super.queryForId(routeId);
        } catch(Exception e) {
            Log.e(TAG,"Query for route id " + routeId + " failed: " + e.getMessage());
            return null;
        }
    }

}
