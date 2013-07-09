package com.MetroSub.database;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/8/13
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticDataParser {

    private static String TAG = "StaticDataParser";
    private static char DELIMITER = ',';

    public static ArrayList<String[]> parseStaticData(InputStream inputStream) {

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String[]> parsedData = new ArrayList<String[]>();

        try {
            // Get the number of columns to parse per row from the first line (header)
            String headerLine = in.readLine();
            int numColumns = 1;  // avoid zero-based index in counting!
            for(int startPos = 0; headerLine.indexOf(DELIMITER,startPos) != -1;
                    startPos = headerLine.indexOf(DELIMITER,startPos) + 1, numColumns++);

            // Parse the succeeding lines, storing data in the data structure
            String row;
            while((row = in.readLine()) != null) {
                String[] dataRow = new String[numColumns];
                int startPos = 0;
                for(int column = 0; column < numColumns; column++) {
                    int endPos = row.indexOf(DELIMITER,startPos);
                    if(endPos >= 0) {
                        dataRow[column] = row.substring(startPos,endPos);
                    } else {
                        dataRow[column] = row.substring(startPos);
                    }
                    startPos = endPos + 1;
                }
                parsedData.add(dataRow);
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }

        return parsedData;
    }

}
