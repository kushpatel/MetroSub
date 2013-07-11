package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.TripsDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "TripData", daoClass = TripsDao.class)
public class TripData {

    private static final String TRIP_ID_COL_NAME = "trip_id";
    private static final String TRIP_HEADSIGN_COL_NAME = "trip_headsign";
    private static final String DIRECTION_ID_COL_NAME = "direction_id";

    @DatabaseField(id = true, uniqueIndex = true, columnName = TRIP_ID_COL_NAME)
    protected String mTripId;

    @DatabaseField(canBeNull = false, columnName = TRIP_HEADSIGN_COL_NAME)
    protected String mTripHeadsign;

    @DatabaseField(canBeNull = false, columnName = DIRECTION_ID_COL_NAME)
    protected String mDirectionId;

    /* Accessor and Mutator methods
    ====================================================================================================================*/

    /** Save only trip_id to save memory
     *  If trip_id = A20121216_000800_1..S03R then: service_id = A20121216
     *                                              route_id = 1
     *                                              shape_id = 1..SO3R
     **/

    public static final String SERVICE_ID_SEPARATOR = "_";
    public static final String ROUTE_ID_SEPARATOR = "..";

    public String getTripId() {
        return this.mTripId;
    }

    public String getTripHeadSign() {
        return this.mTripHeadsign;
    }

    public String getDirectionId() {
        return this.mDirectionId;
    }

    public String getServiceId() {
        String tripId = this.getTripId();
        int separatorPos = tripId.indexOf(SERVICE_ID_SEPARATOR);
        return tripId.substring(0,separatorPos);
    }

    public String getRouteId() {
        String tripId = this.getTripId();
        // pre-separator will be the SECOND underscore aka SERVICE_ID_SEPARATOR
        int preSeparatorPos = tripId.indexOf(SERVICE_ID_SEPARATOR);
        preSeparatorPos = tripId.indexOf(SERVICE_ID_SEPARATOR, preSeparatorPos + 1);
        int postSeparatorPos = tripId.indexOf(ROUTE_ID_SEPARATOR);
        return tripId.substring(preSeparatorPos + 1, postSeparatorPos);
    }

    public String getShapeId() {
        String tripId = this.getTripId();
        // pre-separator will be the SECOND underscore aka SERVICE_ID_SEPARATOR
        int separatorPos = tripId.indexOf(SERVICE_ID_SEPARATOR);
        separatorPos = tripId.indexOf(SERVICE_ID_SEPARATOR, separatorPos + 1);
        return tripId.substring(separatorPos + 1);
    }

    public void setTripId(String tripId) {
        this.mTripId = tripId;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.mTripHeadsign = tripHeadsign;
    }

    public void setDirectionId(String directionId) {
        this.mDirectionId = directionId;
    }

}
