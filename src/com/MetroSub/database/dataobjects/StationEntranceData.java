package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.StationEntrancesDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/13/13
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "StationEntranceData", daoClass = StationEntrancesDao.class)
public class StationEntranceData {

    public static final String AUTO_GENERATED_ID_COL_NAME = "auto_generated_id";
    public static final String DIVISION_COL_NAME = "division";
    public static final String LINE_COL_NAME = "line";
    public static final String STATION_NAME_COL_NAME = "station_name";
    public static final String STATION_LAT_COL_NAME = "station_latitude";
    public static final String STATION_LON_COL_NAME = "station_longitude";
    public static final String ROUTE_LINES_COL_NAME = "route_lines";
    public static final String NORTH_SOUTH_STREET_COL_NAME = "north_south_street";
    public static final String EAST_WEST_STREET_COL_NAME = "east_west_street";
    public static final String CORNER_COL_NAME = "corner";
    public static final String CORNER_LAT_COL_NAME = "latitude";
    public static final String CORNER_LON_COL_NAME = "longitude";

    @DatabaseField(generatedId = true, columnName = AUTO_GENERATED_ID_COL_NAME)
    protected Integer mAutoId;

    @DatabaseField(canBeNull = false, columnName = DIVISION_COL_NAME)
    protected String mDivision;

    @DatabaseField(canBeNull = false, columnName = LINE_COL_NAME)
    protected String mLine;

    @DatabaseField(canBeNull = false, columnName = STATION_NAME_COL_NAME)
    protected String mStationName;

    @DatabaseField(canBeNull = false, columnName = STATION_LAT_COL_NAME)
    protected String mStationLat;

    @DatabaseField(canBeNull = false, columnName = STATION_LON_COL_NAME)
    protected String mStationLon;

    @DatabaseField(dataType = DataType.SERIALIZABLE, columnName = ROUTE_LINES_COL_NAME)
    protected ArrayList<String> mRouteLines;

    @DatabaseField(canBeNull = false, columnName = NORTH_SOUTH_STREET_COL_NAME)
    protected String mNorthSouthStreet;

    @DatabaseField(canBeNull = false, columnName = EAST_WEST_STREET_COL_NAME)
    protected String mEastWestStreet;

    @DatabaseField(canBeNull = false, columnName = CORNER_COL_NAME)
    protected String mCorner;

    @DatabaseField(canBeNull = false, columnName = CORNER_LAT_COL_NAME)
    protected String mCornerLat;

    @DatabaseField(canBeNull = false, columnName = CORNER_LON_COL_NAME)
    protected String mCornerLon;

        /* Accessor and Mutator methods
    ====================================================================================================================*/

    public int getAutoId() {
        return this.mAutoId;
    }

    public String getDivision() {
        return this.mDivision;
    }

    public String getLine() {
        return this.mLine;
    }

    public String getStationName() {
        return this.mStationName;
    }

    public String getStationLat() {
        return this.mStationLat;
    }

    public String getStationLon() {
        return this.mStationLon;
    }

    public ArrayList<String> getRouteLines() {
        return this.mRouteLines;
    }

    public String getNorthSouthStreet() {
        return this.mNorthSouthStreet;
    }

    public String getEastWestStreet() {
        return this.mEastWestStreet;
    }

    public String getCorner() {
        return this.mCorner;
    }

    public String getCornerLat() {
        return this.mCornerLat;
    }

    public String getCornerLon() {
        return this.mCornerLon;
    }

    public void setDivision(String division) {
        this.mDivision = division;
    }

    public void setLine(String line) {
        this.mLine = line;
    }

    public void setStationName(String stationName) {
        this.mStationName = stationName;
    }

    public void setStationLat(String stationLat) {
        this.mStationLat = stationLat;
    }

    public void setStationLon(String stationLon) {
        this.mStationLon = stationLon;
    }

    public void setRouteLines(ArrayList<String> routeLines) {
        this.mRouteLines = routeLines;
    }

    public void setNorthSouthStreet(String northSouthStreet) {
        this.mNorthSouthStreet = northSouthStreet;
    }

    public void setEastWestStreet(String eastWestStreet) {
        this.mEastWestStreet = eastWestStreet;
    }

    public void setCorner(String corner) {
        this.mCorner = corner;
    }

    public void setCornerLat(String cornerLat) {
        this.mCornerLat = cornerLat;
    }

    public void setCornerLon(String cornerLon) {
        this.mCornerLon = cornerLon;
    }

}
