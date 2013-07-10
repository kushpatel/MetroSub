package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.ShapeData;
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
public class ShapesDao extends BaseDaoImpl<ShapeData, String> {

    private static String TAG = "ShapesDao";

    public ShapesDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ShapeData.class);
    }

    @Override
    public int create(ShapeData data) {
        try {
            return super.create(data);
        } catch(SQLException e) {
            Log.e(TAG, "Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<ShapeData> queryForAll() {
        try {
            return super.queryForAll();
        } catch(SQLException e) {
            Log.e(TAG,"Query for all failed: " + e.getMessage());
            return null;
        }
    }
}
