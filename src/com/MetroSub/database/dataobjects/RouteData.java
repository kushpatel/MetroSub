package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.RoutesDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "RouteData", daoClass = RoutesDao.class)
public class RouteData {

    public static final String ROUTE_ID_COL_NAME = "route_id";
    public static final String ROUTE_SHORT_NAME_COL_NAME = "route_short_name";
    public static final String ROUTE_LONG_NAME_COL_NAME = "route_long_name";
    public static final String ROUTE_DESC_COL_NAME = "route_desc";
    public static final String ROUTE_TYPE_COL_NAME = "route_type";
    public static final String ROUTE_URL_COL_NAME = "route_url";
    public static final String ROUTE_COLOR_COL_NAME = "route_color";

    @DatabaseField(id = true, uniqueIndex = true, columnName = ROUTE_ID_COL_NAME)
    protected String mRouteId;

    @DatabaseField(canBeNull = false, columnName = ROUTE_SHORT_NAME_COL_NAME)
    protected String mShortName;

    @DatabaseField(canBeNull = false, columnName = ROUTE_LONG_NAME_COL_NAME)
    protected String mLongName;

    @DatabaseField(canBeNull = true, columnName = ROUTE_DESC_COL_NAME)
    protected String mRouteDesc;

    @DatabaseField(canBeNull = true, columnName = ROUTE_TYPE_COL_NAME)
    protected String mRouteType;

    @DatabaseField(canBeNull = true, columnName = ROUTE_URL_COL_NAME)
    protected String mRouteUrl;

    @DatabaseField(canBeNull = true, columnName = ROUTE_COLOR_COL_NAME)
    protected String mRouteColor;

    /* Accessor and Mutator methods
    ====================================================================================================================*/

    public String getRouteId() {
        return this.mRouteId;
    }

    public String getShortName() {
        return this.mShortName;
    }

    public String getLongName() {
        return this.mLongName;
    }

    public String getRouteDesc() {
        return this.mRouteDesc;
    }

    public String getRouteType() {
        return this.mRouteType;
    }

    public String getRouteUrl() {
        return this.mRouteUrl;
    }

    public String getRouteColor() {
        return this.mRouteColor;
    }

    public void setRouteId(String routeId) {
        this.mRouteId = routeId;
    }

    public void setShortName(String shortName) {
        this.mShortName = shortName;
    }

    public void setLongName(String longName) {
        this.mLongName = longName;
    }

    public void setRouteDesc(String routeDesc) {
        this.mRouteDesc = routeDesc;
    }

    public void setRouteType(String routeType) {
        this.mRouteType = routeType;
    }

    public void setRouteUrl(String routeUrl) {
        this.mRouteUrl = routeUrl;
    }

    public void setRouteColor(String routeColor) {
        this.mRouteColor = routeColor;
    }

}
