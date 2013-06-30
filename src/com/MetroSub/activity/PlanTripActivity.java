package com.MetroSub.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.MetroSub.R;

/**
 * Created with IntelliJ IDEA.
 * User: Kush Patel
 * Date: 6/30/13
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlanTripActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_planner_screen);

        Button planTripLocationButton = (Button) findViewById(R.id.plan_trip_location_button);
        planTripLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTripByLocationActivity(PlanTripActivity.this);
            }
        });

        Button planTripSubwayButton = (Button) findViewById(R.id.plan_trip_subway_lines_button);
        planTripSubwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTripBySubwayLinesActivity(PlanTripActivity.this);
            }
        });

    }

    private void startTripByLocationActivity(Context ctx) {
        Intent intent = new Intent(ctx,TripByLocationActivity.class);
        startActivity(intent);
    }

    private void startTripBySubwayLinesActivity(Context ctx) {
        Intent intent = new Intent(ctx,TripBySubwayLinesActivity.class);
        startActivity(intent);
    }
}
