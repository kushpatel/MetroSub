package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.StopsDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/9/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "StopData", daoClass = StopsDao.class)
public class StopData {

    private static final String STOP_ID_COL_NAME = "stop_id";
    private static final String STOP_NAME_COL_NAME = "stop_name";
    private static final String STOP_LAT_COL_NAME = "stop_lat";
    private static final String STOP_LON_COL_NAME = "stop_lon";
    private static final String STOP_LOCATION_TYPE_COL_NAME = "location_type";
    private static final String STOP_PARENT_STATION_COL_NAME = "parent_station";


    @DatabaseField(id = true, uniqueIndex = true, columnName = STOP_ID_COL_NAME)
    protected String mStopId;

    @DatabaseField(canBeNull = false, columnName = STOP_NAME_COL_NAME)
    protected String mStopName;

    @DatabaseField(canBeNull = false, columnName = STOP_LAT_COL_NAME)
    protected String mStopLat;

    @DatabaseField(canBeNull = false, columnName = STOP_LON_COL_NAME)
    protected String mStopLon;

    @DatabaseField(canBeNull = true, columnName = STOP_LOCATION_TYPE_COL_NAME)
    protected String mLocationType;

    @DatabaseField(canBeNull = true, columnName = STOP_PARENT_STATION_COL_NAME)
    protected String mParentStation;

    /* Accessor and Mutator methods
    ====================================================================================================================*/

    public String getStopId() {
        return this.mStopId;
    }

    public String getStopName() {
        return this.mStopName;
    }

    public String getStopLat() {
        return this.mStopLat;
    }

    public String getStopLon() {
        return this.mStopLon;
    }

    public String getLocationType() {
        return this.mLocationType;
    }

    public String getParentStation() {
        return this.mParentStation;
    }

    public void setStopId(String stopId) {
        this.mStopId = stopId;
    }

    public void setStopName(String stopName) {
        this.mStopName = stopName;
    }

    public void setStopLat(String stopLat) {
        this.mStopLat = stopLat;
    }

    public void setStopLon(String stopLon) {
        this.mStopLon = stopLon;
    }

    public void setLocationType(String locationType) {
        this.mLocationType = locationType;
    }

    public void setParentStation(String parentStation) {
        this.mParentStation = parentStation;
    }

}
