package com.MetroSub.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import com.MetroSub.R;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 6/30/13
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {

    //private String ACTION_BAR_TITLE = "MetroSub";
    protected ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBar = getActionBar();
        //mActionBar.setTitle(ACTION_BAR_TITLE);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setCustomView(R.layout.action_bar);
    }
}
