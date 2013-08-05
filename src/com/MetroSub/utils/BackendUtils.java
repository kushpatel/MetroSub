package com.MetroSub.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/19/13
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BackendUtils {

    private static final String TAG = "BackendUtils";

    //code to troubleshoot if key for google maps api is incorrect
    public static void getShaKey(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.MetroSub.activity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v(TAG, "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

    }

    /* Quick sort implementation for a list of integers .. sorted in ascending order
    ====================================================================================================================*/

    public static ArrayList<Integer> quickSort(ArrayList<Integer> list) {
        Integer[] integerList = new Integer[list.size()];
        list.toArray(integerList);
        qSort(integerList, 0, list.size() - 1);
        return new ArrayList<Integer>(Arrays.asList(integerList));
    }


    private static void qSort(Integer[] a, int p, int r) {
        if (p < r) {
            int q = partition(a, p, r);
            qSort(a, p, q);
            qSort(a, q + 1, r);
        }
    }

    private static int partition(Integer[] a, int p, int r) {

        int x = a[p];
        int i = p - 1;
        int j = r + 1;

        while (true) {
            i++;
            while (i < r && a[i] < x)
                i++;
            j--;
            while (j > p && a[j] > x)
                j--;

            if (i < j)
                swap(a, i, j);
            else
                return j;
        }
    }

    private static void swap(Integer[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /* Merge sort implementation to sort StationsData list in lexicographic order on station name
    ====================================================================================================================*/

    public static void merge(StationEntranceData[] stations, int left, int mid, int right)
    {
        StationEntranceData[] temp = new StationEntranceData[stations.length];

        int leftEnd = (mid - 1);
        int tmpPos = left;
        int numElements = (right - left + 1);

        while ((left <= leftEnd) && (mid <= right))
        {
            String stationLeft = stations[left].getStationName();
            String stationMid = stations[mid].getStationName();
            //if (stations[left] <= stations[mid])
            if (stationLeft.compareTo(stationMid) < 0)
                temp[tmpPos++] = stations[left++];
            else
                temp[tmpPos++] = stations[mid++];
        }

        while (left <= leftEnd)
            temp[tmpPos++] = stations[left++];

        while (mid <= right)
            temp[tmpPos++] = stations[mid++];

        for (int idx = 0; idx < numElements; idx++)
        {
            stations[right] = temp[right];
            right--;
        }
    }

    private static void mSort(StationEntranceData[] stations, int left, int right)
    {
        int mid;

        if (right > left)
        {
            mid = (right + left) / 2;
            mSort(stations, left, mid);
            mSort(stations, (mid + 1), right);

            merge(stations, left, (mid+1), right);
        }
    }

    public static ArrayList<StationEntranceData> mergeSort(ArrayList<StationEntranceData> list) {
        StationEntranceData[] stationsList = new StationEntranceData[list.size()];
        list.toArray(stationsList);
        mSort(stationsList, 0, list.size() - 1);
        return new ArrayList<StationEntranceData>(Arrays.asList(stationsList));
    }

    /* Merge sort implementation to sort StationsData list on stopId found in StopData
    ====================================================================================================================*/

    public static void merge(StopData[] stops, int left, int mid, int right, StationEntranceData[] stations)
    {
        StopData[] tempStops = new StopData[stops.length];
        StationEntranceData[] tempStations = new StationEntranceData[stations.length];

        int leftEnd = (mid - 1);
        int tmpPos = left;
        int numElements = (right - left + 1);

        while ((left <= leftEnd) && (mid <= right))
        {
            String stopLeft = stops[left].getStopId();
            String stopMid = stops[mid].getStopId();
            //if (stops[left] <= stops[mid])
            if (stopLeft.compareTo(stopMid) < 0) {
                tempStops[tmpPos] = stops[left];
                tempStations[tmpPos] = stations[left];
                tmpPos++;
                left++;
            }
            else {
                tempStops[tmpPos] = stops[mid];
                tempStations[tmpPos++] = stations[mid];
                tmpPos++;
                mid++;
            }
        }

        while (left <= leftEnd) {
            tempStops[tmpPos] = stops[left];
            tempStations[tmpPos] = stations[left];
            tmpPos++;
            left++;
        }

        while (mid <= right) {
            tempStops[tmpPos] = stops[mid];
            tempStations[tmpPos] = stations[mid];
            tmpPos++;
            mid++;
        }

        for (int idx = 0; idx < numElements; idx++)
        {
            stations[right] = tempStations[right];
            stops[right] = tempStops[right];
            right--;
        }
    }

    private static void mSort(StopData[] stops, int left, int right, StationEntranceData[] stations)
    {
        int mid;

        if (right > left)
        {
            mid = (right + left) / 2;
            mSort(stops, left, mid, stations);
            mSort(stops, (mid + 1), right, stations);

            merge(stops, left, (mid+1), right, stations);
        }
    }

    public static ArrayList<StationEntranceData> mergeSort(ArrayList<StationEntranceData> stationsList,
                                                           ArrayList<StopData> stopsList) {
        StationEntranceData[] stationsArray = new StationEntranceData[stationsList.size()];
        StopData[] stopsArray = new StopData[stopsList.size()];
        stationsList.toArray(stationsArray);
        stopsList.toArray(stopsArray);
        mSort(stopsArray, 0, stopsList.size() - 1, stationsArray);
        return new ArrayList<StationEntranceData>(Arrays.asList(stationsArray));
    }
}
