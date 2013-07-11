package com.MetroSub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.MetroSub.database.dao.*;
import com.MetroSub.database.dataobjects.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/9/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "NyctStaticData.db";

    // Increase database version when making changes to the database
    private static final int DATABASE_VERSION = 1;

    // Singleton instance of DatabaseHelper
    private static DatabaseHelper mDatabaseInstance = null;

    /* Database creation and upgrading methods
    ====================================================================================================================*/

    // return singleton instance of DatabaseHelper
    public static synchronized DatabaseHelper getInstance() {
        if(mDatabaseInstance == null) {
            Log.e(TAG,"DatabaseHelper has not been initialized.");
            return null;
        }
        return mDatabaseInstance;
    }

    public static synchronized DatabaseHelper init(Context context) {
        if(mDatabaseInstance == null) {
            mDatabaseInstance = new DatabaseHelper(context);
            mDatabaseInstance.setupDao();
        }
        return mDatabaseInstance;
    }

    protected DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createSchema(connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dropSchema(connectionSource);
        createSchema(connectionSource);
    }

    @Override
    public void close() {
        super.close();
        releaseDao();
    }

    // Create tables in the database
    private void createSchema(ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, RouteData.class);
            TableUtils.createTable(connectionSource, ShapeData.class);
            TableUtils.createTable(connectionSource, StopData.class);
            TableUtils.createTable(connectionSource, TransferData.class);
            TableUtils.createTable(connectionSource, TripData.class);
        } catch (SQLException e) {
            Log.e(TAG,"Could not create database table: " + e.getMessage());
        }

    }

    // Delete tables in the database
    private void dropSchema(ConnectionSource connectionSource) {

        try {
            TableUtils.dropTable(connectionSource, RouteData.class, true);
            TableUtils.dropTable(connectionSource, ShapeData.class, true);
            TableUtils.dropTable(connectionSource, StopData.class, true);
            TableUtils.dropTable(connectionSource, TransferData.class, true);
            TableUtils.dropTable(connectionSource, TripData.class, true);
        } catch(SQLException e) {
            Log.e(TAG,"Could not delete database table: " + e.getMessage());
        }
    }

    /* Database Accessor Objects (DAOs) methods
    ====================================================================================================================*/

    // The DAO objects used to access the table
    private RoutesDao mRoutesDao = null;
    private ShapesDao mShapesDao = null;
    private StopsDao mStopsDao = null;
    private TransfersDao mTransfersDao = null;
    private TripsDao mTripsDao = null;

    private void setupDao() {
        if(mRoutesDao == null) {
            try {
                mRoutesDao = new RoutesDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"RoutesDao setup failed: " + e.getMessage());
            }
        }
        if(mShapesDao == null) {
            try {
                mShapesDao = new ShapesDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"ShapesDao setup failed: " + e.getMessage());
            }
        }
        if(mStopsDao == null) {
            try {
                mStopsDao = new StopsDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"StopsDao setup failed: " + e.getMessage());
            }
        }
        if(mTransfersDao == null) {
            try {
                mTransfersDao = new TransfersDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"TransfersDao setup failed: " + e.getMessage());
            }
        }
        if(mTripsDao == null) {
            try {
                mTripsDao = new TripsDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"TripsDao setup failed: " + e.getMessage());
            }
        }
    }

    public RoutesDao getRoutesDao() {
        if(mRoutesDao == null) {
            setupDao();
        }
        return mRoutesDao;
    }

    public ShapesDao getShapesDao() {
        if(mShapesDao == null) {
            setupDao();
        }
        return mShapesDao;
    }

    public StopsDao getStopsDao() {
        if(mStopsDao == null) {
            setupDao();
        }
        return mStopsDao;
    }

    public TransfersDao getTransfersDao() {
        if(mTransfersDao == null) {
            setupDao();
        }
        return mTransfersDao;
    }

    public TripsDao getTripsDao() {
        if(mTripsDao == null) {
            setupDao();
        }
        return mTripsDao;
    }

    // Clear any cached DAOs
    private void releaseDao() {
        mRoutesDao = null;
        mShapesDao = null;
        mStopsDao = null;
        mTransfersDao = null;
        mTripsDao = null;
    }
}
