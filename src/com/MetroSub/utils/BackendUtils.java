package com.MetroSub.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
