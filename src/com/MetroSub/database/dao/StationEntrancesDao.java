package com.MetroSub.database.dao;

import android.util.Log;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
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
        } catch (SQLException e) {
            Log.e(TAG, "Creation of new row failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<StationEntranceData> queryForAll() {
        try {
            return super.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Query for all failed: " + e.getMessage());
            return null;
        }
    }

    // Returns only the first matching station. Database has duplicate station entries!
    public StationEntranceData queryForStation(String lat, String lon) {

        StationEntranceData data = null;
        try {
            QueryBuilder<StationEntranceData, Integer> queryBuilder = queryBuilder();
            queryBuilder.where().eq(StationEntranceData.STATION_LAT_COL_NAME, lat).and()
                    .eq(StationEntranceData.STATION_LON_COL_NAME, lon);
            PreparedQuery<StationEntranceData> preparedQuery = queryBuilder.prepare();
            data = queryForFirst(preparedQuery);
        } catch (SQLException e) {
            Log.e(TAG, "Query for station lat = " + lat + " lon = " + lon + " failed: " + e.getMessage());
        }
        return data;
    }

    // Data returned has duplicate station entries for all gates in the same parent station!
    public List<StationEntranceData> queryForStations(String line) {

        String lineColumn = StationEntranceData.LINE_COL_NAME_PREFIX + line;

        List<StationEntranceData> data = null;
        try {
            data = queryForEq(lineColumn, true);
        } catch (SQLException e) {
            Log.e(TAG, "Query for stations with line " + line + " failed: " + e.getMessage());
        }
        return data;
    }
}
