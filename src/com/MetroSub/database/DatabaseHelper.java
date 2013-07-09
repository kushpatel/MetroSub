package com.MetroSub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.MetroSub.database.dao.StopsDao;
import com.MetroSub.database.dataobjects.StopData;
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
            TableUtils.createTable(connectionSource, StopData.class);
        } catch (SQLException e) {
            Log.e(TAG,"Could not create database table: " + e.getMessage());
        }

    }

    // Delete tables in the database
    private void dropSchema(ConnectionSource connectionSource) {

        try {
            TableUtils.dropTable(connectionSource, StopData.class, true);
        } catch(SQLException e) {
            Log.e(TAG,"Could not delete database table: " + e.getMessage());
        }
    }

    /* Database Accessor Objects (DAOs) methods
    ====================================================================================================================*/

    // The DAO objects used to access the table
    private StopsDao mStopDao = null;

    private void setupDao() {

        if(mStopDao == null) {
            try {
                mStopDao = new StopsDao(getConnectionSource());
            } catch(SQLException e) {
                Log.e(TAG,"StopsDao setup failed: " + e.getMessage());
            }
        }
    }

    public StopsDao getStopsDao() {
        if(mStopDao == null) {
            setupDao();
        }
        return mStopDao;
    }

    // Clear any cached DAOs
    private void releaseDao() {
        mStopDao = null;
    }
}
