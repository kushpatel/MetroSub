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

    public static final String STOP_ID_COL_NAME = "stop_id";
    public static final String STOP_NAME_COL_NAME = "stop_name";
    public static final String STOP_LAT_COL_NAME = "stop_lat";
    public static final String STOP_LON_COL_NAME = "stop_lon";
    public static final String STOP_LOCATION_TYPE_COL_NAME = "location_type";
    public static final String STOP_PARENT_STATION_COL_NAME = "parent_station";

    // Useful constant in doing queries on columns selected by lines
    public static final String LINE_COL_NAME_PREFIX = "line_";

    public static final String LINE_1_COL_NAME = "line_1";
    public static final String LINE_2_COL_NAME = "line_2";
    public static final String LINE_3_COL_NAME = "line_3";
    public static final String LINE_4_COL_NAME = "line_4";
    public static final String LINE_5_COL_NAME = "line_5";
    public static final String LINE_6_COL_NAME = "line_6";
    public static final String LINE_7_COL_NAME = "line_7";
    public static final String LINE_A_COL_NAME = "line_A";
    public static final String LINE_B_COL_NAME = "line_B";
    public static final String LINE_C_COL_NAME = "line_C";
    public static final String LINE_D_COL_NAME = "line_D";
    public static final String LINE_E_COL_NAME = "line_E";
    public static final String LINE_F_COL_NAME = "line_F";
    public static final String LINE_G_COL_NAME = "line_G";
    public static final String LINE_J_COL_NAME = "line_J";
    public static final String LINE_L_COL_NAME = "line_L";
    public static final String LINE_M_COL_NAME = "line_M";
    public static final String LINE_N_COL_NAME = "line_N";
    public static final String LINE_Q_COL_NAME = "line_Q";
    public static final String LINE_R_COL_NAME = "line_R";
    public static final String LINE_S_COL_NAME = "line_S";
    public static final String LINE_Z_COL_NAME = "line_Z";


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

    @DatabaseField(columnName = LINE_1_COL_NAME)
    protected boolean mLine_1;

    @DatabaseField(columnName = LINE_2_COL_NAME)
    protected boolean mLine_2;

    @DatabaseField(columnName = LINE_3_COL_NAME)
    protected boolean mLine_3;

    @DatabaseField(columnName = LINE_4_COL_NAME)
    protected boolean mLine_4;

    @DatabaseField(columnName = LINE_5_COL_NAME)
    protected boolean mLine_5;

    @DatabaseField(columnName = LINE_6_COL_NAME)
    protected boolean mLine_6;

    @DatabaseField(columnName = LINE_7_COL_NAME)
    protected boolean mLine_7;

    @DatabaseField(columnName = LINE_A_COL_NAME)
    protected boolean mLine_A;

    @DatabaseField(columnName = LINE_B_COL_NAME)
    protected boolean mLine_B;

    @DatabaseField(columnName = LINE_C_COL_NAME)
    protected boolean mLine_C;

    @DatabaseField(columnName = LINE_D_COL_NAME)
    protected boolean mLine_D;

    @DatabaseField(columnName = LINE_E_COL_NAME)
    protected boolean mLine_E;

    @DatabaseField(columnName = LINE_F_COL_NAME)
    protected boolean mLine_F;

    @DatabaseField(columnName = LINE_G_COL_NAME)
    protected boolean mLine_G;

    @DatabaseField(columnName = LINE_J_COL_NAME)
    protected boolean mLine_J;

    @DatabaseField(columnName = LINE_L_COL_NAME)
    protected boolean mLine_L;

    @DatabaseField(columnName = LINE_M_COL_NAME)
    protected boolean mLine_M;

    @DatabaseField(columnName = LINE_N_COL_NAME)
    protected boolean mLine_N;

    @DatabaseField(columnName = LINE_Q_COL_NAME)
    protected boolean mLine_Q;

    @DatabaseField(columnName = LINE_R_COL_NAME)
    protected boolean mLine_R;

    @DatabaseField(columnName = LINE_S_COL_NAME)
    protected boolean mLine_S;

    @DatabaseField(columnName = LINE_Z_COL_NAME)
    protected boolean mLine_Z;

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

    public boolean getLine_1() {
        return this.mLine_1;
    }

    public boolean getLine_2() {
        return this.mLine_2;
    }

    public boolean getLine_3() {
        return this.mLine_3;
    }

    public boolean getLine_4() {
        return this.mLine_4;
    }

    public boolean getLine_5() {
        return this.mLine_5;
    }

    public boolean getLine_6() {
        return this.mLine_6;
    }

    public boolean getLine_7() {
        return this.mLine_7;
    }

    public boolean getLine_A() {
        return this.mLine_A;
    }

    public boolean getLine_B() {
        return this.mLine_B;
    }

    public boolean getLine_C() {
        return this.mLine_C;
    }

    public boolean getLine_D() {
        return this.mLine_D;
    }

    public boolean getLine_E() {
        return this.mLine_E;
    }

    public boolean getLine_F() {
        return this.mLine_F;
    }

    public boolean getLine_G() {
        return this.mLine_G;
    }

    public boolean getLine_J() {
        return this.mLine_J;
    }

    public boolean getLine_L() {
        return this.mLine_L;
    }

    public boolean getLine_M() {
        return this.mLine_M;
    }

    public boolean getLine_N() {
        return this.mLine_N;
    }

    public boolean getLine_Q() {
        return this.mLine_Q;
    }

    public boolean getLine_R() {
        return this.mLine_R;
    }

    public boolean getLine_S() {
        return this.mLine_S;
    }

    public boolean getLine_Z() {
        return this.mLine_Z;
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

    public void setLine_1(boolean passesAtStation) {
        this.mLine_1 = passesAtStation;
    }

    public void setLine_2(boolean passesAtStation) {
        this.mLine_2 = passesAtStation;
    }

    public void setLine_3(boolean passesAtStation) {
        this.mLine_3 = passesAtStation;
    }

    public void setLine_4(boolean passesAtStation) {
        this.mLine_4 = passesAtStation;
    }

    public void setLine_5(boolean passesAtStation) {
        this.mLine_5 = passesAtStation;
    }

    public void setLine_6(boolean passesAtStation) {
        this.mLine_6 = passesAtStation;
    }

    public void setLine_7(boolean passesAtStation) {
        this.mLine_7 = passesAtStation;
    }

    public void setLine_A(boolean passesAtStation) {
        this.mLine_A = passesAtStation;
    }

    public void setLine_B(boolean passesAtStation) {
        this.mLine_B = passesAtStation;
    }

    public void setLine_C(boolean passesAtStation) {
        this.mLine_C = passesAtStation;
    }

    public void setLine_D(boolean passesAtStation) {
        this.mLine_D = passesAtStation;
    }

    public void setLine_E(boolean passesAtStation) {
        this.mLine_E = passesAtStation;
    }

    public void setLine_F(boolean passesAtStation) {
        this.mLine_F = passesAtStation;
    }

    public void setLine_G(boolean passesAtStation) {
        this.mLine_G = passesAtStation;
    }

    public void setLine_J(boolean passesAtStation) {
        this.mLine_J = passesAtStation;
    }

    public void setLine_L(boolean passesAtStation) {
        this.mLine_L = passesAtStation;
    }

    public void setLine_M(boolean passesAtStation) {
        this.mLine_M = passesAtStation;
    }

    public void setLine_N(boolean passesAtStation) {
        this.mLine_N = passesAtStation;
    }

    public void setLine_Q(boolean passesAtStation) {
        this.mLine_Q = passesAtStation;
    }

    public void setLine_R(boolean passesAtStation) {
        this.mLine_R = passesAtStation;
    }

    public void setLine_S(boolean passesAtStation) {
        this.mLine_S = passesAtStation;
    }

    public void setLine_Z(boolean passesAtStation) {
        this.mLine_Z = passesAtStation;
    }

}
