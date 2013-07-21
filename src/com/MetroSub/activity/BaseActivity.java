package com.MetroSub.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.MetroSub.MainApp;
import com.MetroSub.R;
import com.MetroSub.database.QueryHelper;
import com.MetroSub.datamine.GtfsParser;
import com.MetroSub.datamine.RetrieveFeedTask;
import com.google.protobuf.ByteString;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 6/30/13
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    protected ActionBar mActionBar;
    private MainApp mainApp = MainApp.getAppInstance();

    protected QueryHelper mQueryHelper;
    protected GtfsParser mGtfsParser;

    protected final long RETRIEVE_FEED_TASK_DELAY = 60 * 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getMainApp().getNetworkConnectionStatus()) {
            showAlertDialog();
        }

        mQueryHelper = getMainApp().getQueryHelper();
        mGtfsParser = getMainApp().getGtfsParser();

        // Periodically fetch new GTFS feed in the background .. after every 1 minute
        //Timer retrieveFeedTimer = new Timer();
        //retrieveFeedTimer.scheduleAtFixedRate(new RetrieveFeedTimerTask(), RETRIEVE_FEED_TASK_DELAY, RETRIEVE_FEED_TASK_DELAY);

        mActionBar = getActionBar();
        //mActionBar.setTitle(ACTION_BAR_TITLE);
        mActionBar.setDisplayShowCustomEnabled(true);
        //mActionBar.setDisplayShowTitleEnabled(false);
        //mActionBar.setDisplayShowHomeEnabled(false);
        //mActionBar.setCustomView(R.layout.action_bar);
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    private class RetrieveFeedTimerTask extends TimerTask {
        public void run() {
            mGtfsParser = mainApp.fetchFeedData();
        }
    }


    public String getApplicationName() {
        final PackageManager packageManager = getApplicationContext().getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(this.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo).toString() : "(unknown)");
    }

    public void showAlertDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(getApplicationName() + " requires internet connection.");

        // set dialog message
        alertDialogBuilder
                .setMessage("Please restart the application after connecting to the internet.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // exit the activity/app
                        BaseActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
