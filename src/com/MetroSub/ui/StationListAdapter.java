package com.MetroSub.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MetroSub.R;
import com.MetroSub.activity.MapActivity;
import com.MetroSub.database.dataobjects.StationEntranceData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/18/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class StationListAdapter extends ArrayAdapter {

    private static final String TAG = "StationListAdapter";

    private int mResourceId;
    private LayoutInflater mLayoutInflator;
    private Context mContext;
    private int mImageResId;
    private String mLine;

    public StationListAdapter(Context context, int resourceId, List<StationEntranceData> stationsList,
                              int imageResId, String line) {
        super(context, resourceId, stationsList);
        mResourceId = resourceId;
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
        mImageResId = imageResId;
        mLine = line;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mLayoutInflator.inflate(mResourceId, null);
        final StationEntranceData stationEntranceData = (StationEntranceData) getItem(position);

        final TextView stationName = (TextView) convertView.findViewById(R.id.station_name);
        stationName.setText(stationEntranceData.getStationName());

        final ImageView lineImage = (ImageView) convertView.findViewById(R.id.line_image);
        lineImage.setImageDrawable(mContext.getResources().getDrawable(mImageResId));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reload MapActivity passing marker point information in Bundle extras
                Intent intent = new Intent(mContext, MapActivity.class);
                intent.putExtra(MapActivity.EXTRA_PLAN_TRIP_BY_LINE, true);
                intent.putExtra(MapActivity.EXTRA_LINE, mLine);
                intent.putExtra(MapActivity.EXTRA_STATION_LAT, stationEntranceData.getStationLat());
                intent.putExtra(MapActivity.EXTRA_STATION_LON, stationEntranceData.getStationLon());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

}
