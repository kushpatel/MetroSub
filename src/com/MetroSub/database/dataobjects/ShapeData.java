package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.ShapesDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/9/13
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */

@DatabaseTable(tableName = "ShapeData", daoClass = ShapesDao.class)
public class ShapeData {

    public static final String SHAPES_TABLE_KEY_SEPARATOR = ShapesDao.SHAPES_TABLE_KEY_SEPARATOR;

    // Primary key composed of shape_id[SEPARATOR]shape_pt_sequence for fast queries
    public static final String GENERATED_KEY_COL_NAME = "key";

    public static final String SHAPE_ID_COL_NAME = "shape_id";
    public static final String SHAPE_PT_SEQUENCE_COL_NAME = "shape_pt_sequence";
    public static final String SHAPE_PT_LAT_COL_NAME = "shape_pt_lat";
    public static final String SHAPE_PT_LON_COL_NAME = "shape_pt_lon";
    public static final String SHAPE_PT_LINE_COL_NAME = "shape_pt_line";

    @DatabaseField(generatedId = true, uniqueIndex = true, columnName = GENERATED_KEY_COL_NAME)
    protected int mGeneratedKey;

    @DatabaseField(canBeNull = false, columnName = SHAPE_ID_COL_NAME)
    protected String mShapeId;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_SEQUENCE_COL_NAME)
    protected String mShapePtSequence;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_LAT_COL_NAME)
    protected String mShapePtLat;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_LON_COL_NAME)
    protected String mShapePtLon;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_LINE_COL_NAME)
    protected char mShapePtLine;

     /* Accessor and Mutator methods
    ====================================================================================================================*/

//    public String getShapeId() {
//        String key = this.mGeneratedKey;
//        int separatorPos = key.indexOf(SHAPES_TABLE_KEY_SEPARATOR);
//        return key.substring(0,separatorPos);
//    }
//
//    public String getShapePtSequence() {
//        String key = this.mGeneratedKey;
//        int separatorPos = key.indexOf(SHAPES_TABLE_KEY_SEPARATOR);
//        return key.substring(separatorPos + 1);
//    }

    public String getShapeId() {
        return this.mShapeId;
    }

    public String getShapePtSequence() {
        return this.mShapePtSequence;
    }

    public String getShapePtLat() {
        return this.mShapePtLat;
    }

    public String getShapePtLon() {
        return this.mShapePtLon;
    }

    public char getShapePtLine() {
        return this.mShapePtLine;
    }

//    public void setGeneratedKey(String shapeId, String shape_pt_sequence) {
//        this.mGeneratedKey = shapeId + SHAPES_TABLE_KEY_SEPARATOR + shape_pt_sequence;
//    }

    public void setShapeId(String shapeId) {
        this.mShapeId = shapeId;
    }

    public void setShapePtSequence(String shapePtSequence) {
        this.mShapePtSequence = shapePtSequence;
    }

    public void setShapePtLat(String shapePtLat) {
        this.mShapePtLat = shapePtLat;
    }

    public void setShapePtLon(String shapePtLon) {
        this.mShapePtLon = shapePtLon;
    }

    public void setShapePtLine(char line) {
        this.mShapePtLine = line;
    }

}
