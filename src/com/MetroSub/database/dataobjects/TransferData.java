package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.TransfersDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "TransferData", daoClass = TransfersDao.class)
public class TransferData {

    private static final String FROM_STOP_ID_COL_NAME = "from_stop_id";
    private static final String TO_STOP_ID_COL_NAME = "to_stop_id";
    private static final String TRANSFER_TYPE_COL_NAME = "transfer_type";
    private static final String MIN_TRANSFER_TIME_COL_NAME = "min_transfer_time";

    // Did not optimize on handling duplicate keys since the static table was small
    @DatabaseField(id = true, uniqueIndex = false, columnName = FROM_STOP_ID_COL_NAME)
    protected String mFromStopId;

    @DatabaseField(canBeNull = false, columnName = TO_STOP_ID_COL_NAME)
    protected String mToStopId;

    @DatabaseField(canBeNull = false, columnName = TRANSFER_TYPE_COL_NAME)
    protected String mTransferType;

    @DatabaseField(canBeNull = false, columnName = MIN_TRANSFER_TIME_COL_NAME)
    protected String mMinTransferTime;

    /* Accessor and Mutator methods
    ====================================================================================================================*/

    public String getFromStopId() {
        return this.mFromStopId;
    }

    public String getToStopId() {
        return this.mToStopId;
    }

    public String getTransferType() {
        return this.mTransferType;
    }

    public String getMinTransferTime() {
        return this.mMinTransferTime;
    }

    public void setFromStopId(String fromStopId) {
        this.mFromStopId = fromStopId;
    }

    public void setToStopId(String toStopId) {
        this.mToStopId = toStopId;
    }

    public void setTransferType(String transferType) {
        this.mTransferType = transferType;
    }

    public void setMinTransferTime(String minTransferTime) {
        this.mMinTransferTime = minTransferTime;
    }

}
