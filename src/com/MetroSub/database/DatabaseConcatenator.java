package com.MetroSub.database;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
public class DatabaseConcatenator {

    private static final String TAG = DatabaseConcatenator.class.getCanonicalName();

    private static final String DEVICE_DATABASE_PATH = "/data/data/com.MetroSub/databases/";
    private static final String DATABASE_NAME = DatabaseHelper.DATABASE_NAME;

    public static void createDatabaseFromChunks(Context ctx) {

        Log.d(TAG,"Setting up database on device...");

        String databasePathName = DEVICE_DATABASE_PATH + DATABASE_NAME;
        File database = ctx.getDatabasePath(databasePathName);
        boolean databaseExists = database.exists();

        if (!databaseExists) {
            // Create database by concatenation of .db files in res/raw ... there's probably a better way to do this!
            InputStream dbStream1 = ctx.getResources().openRawResource(R.raw.database_chunk_01);
            InputStream dbStream2 = ctx.getResources().openRawResource(R.raw.database_chunk_02);
            InputStream dbStream3 = ctx.getResources().openRawResource(R.raw.database_chunk_03);
            InputStream dbStream4 = ctx.getResources().openRawResource(R.raw.database_chunk_04);
            InputStream dbStream5 = ctx.getResources().openRawResource(R.raw.database_chunk_05);
            InputStream dbStream6 = ctx.getResources().openRawResource(R.raw.database_chunk_06);
            InputStream dbStream7 = ctx.getResources().openRawResource(R.raw.database_chunk_07);
            InputStream dbStream8 = ctx.getResources().openRawResource(R.raw.database_chunk_08);
            InputStream dbStream9 = ctx.getResources().openRawResource(R.raw.database_chunk_09);
            InputStream dbStream10 = ctx.getResources().openRawResource(R.raw.database_chunk_10);
            InputStream dbStream11 = ctx.getResources().openRawResource(R.raw.database_chunk_11);
            InputStream dbStream12 = ctx.getResources().openRawResource(R.raw.database_chunk_12);
            InputStream dbStream13 = ctx.getResources().openRawResource(R.raw.database_chunk_13);

            // Recursively line up input streams for .db concatenations ... feels like programming in Scheme!
            SequenceInputStream sequenceInputStream = new SequenceInputStream(dbStream1,
                    new SequenceInputStream(dbStream2,
                        new SequenceInputStream(dbStream3,
                            new SequenceInputStream(dbStream4,
                                new SequenceInputStream(dbStream5,
                                    new SequenceInputStream(dbStream6,
                                        new SequenceInputStream(dbStream7,
                                            new SequenceInputStream(dbStream8,
                                                new SequenceInputStream(dbStream9,
                                                    new SequenceInputStream(dbStream10,
                                                        new SequenceInputStream(dbStream11,
                                                            new SequenceInputStream(dbStream12, dbStream13))))))))))));

            OutputStream databaseOutStream = null;

            try {
                File file = new File(databasePathName);
                file.getParentFile().mkdirs();
                databaseOutStream = new FileOutputStream(databasePathName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = sequenceInputStream.read(buffer)) > 0) {
                    databaseOutStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                Log.e(TAG,"IO Exception in reading .db chunks: " + e.getMessage());
            }

            try {
                databaseOutStream.flush();
                databaseOutStream.close();
                sequenceInputStream.close();
            } catch (Exception e) {
                // Don't care if unable to close the input stream
            }
            Log.d(TAG,"Loading database from chunks successful!");
        } else {
            Log.d(TAG,"Database already exists.");
        }
    }
}
