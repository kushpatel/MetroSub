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
    private static final String GENERATED_KEY_COL_NAME = "key";

    private static final String SHAPE_PT_LAT_COL_NAME = "shape_pt_lat";
    private static final String SHAPE_PT_LON_COL_NAME = "shape_pt_lon";

    @DatabaseField(id = true, uniqueIndex = true, columnName = GENERATED_KEY_COL_NAME)
    protected String mGeneratedKey;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_LAT_COL_NAME)
    protected String mShapePtLat;

    @DatabaseField(canBeNull = false, columnName = SHAPE_PT_LON_COL_NAME)
    protected String mShapePtLon;

     /* Accessor and Mutator methods
    ====================================================================================================================*/

    public String getShapeId() {
        String key = this.mGeneratedKey;
        int separatorPos = key.indexOf(SHAPES_TABLE_KEY_SEPARATOR);
        return key.substring(0,separatorPos);
    }

    public String getShapePtSequence() {
        String key = this.mGeneratedKey;
        int separatorPos = key.indexOf(SHAPES_TABLE_KEY_SEPARATOR);
        return key.substring(separatorPos + 1);
    }

    public String getShapePtLat() {
        return this.mShapePtLat;
    }

    public String getShapePtLon() {
        return this.mShapePtLon;
    }

    public void setGeneratedKey(String shapeId, String shape_pt_sequence) {
        this.mGeneratedKey = shapeId + SHAPES_TABLE_KEY_SEPARATOR + shape_pt_sequence;
    }

    public void setShapePtLat(String shapePtLat) {
        this.mShapePtLat = shapePtLat;
    }

    public void setShapePtLon(String shapePtLon) {
        this.mShapePtLon = shapePtLon;
    }

}
