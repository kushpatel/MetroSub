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
    public static final String SHAPES_TABLE_KEY_SEPARATOR = "-";

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

    @Override
    public ShapeData queryForId(String generatedKey) {
        try {
            return super.queryForId(generatedKey);
        } catch(Exception e) {
            Log.e(TAG,"Query for generated key " + generatedKey + " failed: " + e.getMessage());
            return null;
        }
    }

    // Overload method to query with shapeId and shapePtSequence as arguments
    public ShapeData queryForId(String shapeId, String shapePtSequence) {
        return queryForId(shapeId + SHAPES_TABLE_KEY_SEPARATOR + shapePtSequence);
    }

    public List<ShapeData> queryForLinePoints(char line) {
        try {
            return queryForEq(ShapeData.SHAPE_PT_LINE_COL_NAME, line);
        } catch (SQLException e) {
            Log.e(TAG,"Query for points on line " + line + " failed: " + e.getMessage());
            return null;
        }
    }
}
