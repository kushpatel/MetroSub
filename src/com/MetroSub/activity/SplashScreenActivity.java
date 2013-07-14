package com.MetroSub.activity;

import android.content.Intent;
import android.os.Bundle;
import com.MetroSub.R;
import android.os.Handler;

public class SplashScreenActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */

    private final int SPLASH_SCREEN_DELAY = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Hide action bar in splash screen
        mActionBar.hide();

        /* New Handler to start the MapActivity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the MapActivity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this,MapActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
