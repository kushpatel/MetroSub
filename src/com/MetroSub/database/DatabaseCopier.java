package com.MetroSub.database;

import android.content.Context;
import android.util.Log;
import com.MetroSub.R;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/11/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseCopier {

    private static final String TAG = DatabaseCopier.class.getCanonicalName();

    private static final String DEVICE_DATABASE_PATH = "/data/data/com.MetroSub/databases/";
    private static final String DATABASE_NAME = DatabaseHelper.DATABASE_NAME;

    public static void createDatabaseFromFile(Context ctx) {

        Log.d(TAG,"Setting up database on device...");

        String databasePathName = DEVICE_DATABASE_PATH + DATABASE_NAME;
        File database = ctx.getDatabasePath(databasePathName);
        boolean databaseExists = database.exists();

        if (!databaseExists) {

            InputStream inputStream = ctx.getResources().openRawResource(R.raw.nyct_database);
            OutputStream databaseOutStream = null;

            try {
                File file = new File(databasePathName);
                file.getParentFile().mkdirs();
                databaseOutStream = new FileOutputStream(databasePathName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    databaseOutStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                Log.e(TAG,"IO Exception in reading .db file: " + e.getMessage());
            }

            try {
                databaseOutStream.flush();
                databaseOutStream.close();
                inputStream.close();
            } catch (Exception e) {
                // Don't care if unable to close the input stream
            }
            Log.d(TAG,"Loading database from file successful!");
        } else {
            Log.d(TAG,"Database already exists.");
        }
    }
}
