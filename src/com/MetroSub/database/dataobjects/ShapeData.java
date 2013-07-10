package com.MetroSub.database.dataobjects;

import com.MetroSub.database.dao.ShapesDao;
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

    private static final String SHAPE_ID_COL_NAME = "shape_id";
    private static final String SHAPE_PT_LAT_COL_NAME = "shape_pt_lat";
    private static final String SHAPE_PT_LON_COL_NAME = "shape_pt_lon";
    private static final String SHAPE_PT_SEQUENCE_COL_NAME = "shape_pt_sequence";



}
