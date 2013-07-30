package com.MetroSub.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

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
}
