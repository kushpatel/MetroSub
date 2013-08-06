package com.MetroSub.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.MetroSub.R;
import com.MetroSub.database.dataobjects.StationEntranceData;
import com.MetroSub.database.dataobjects.StopData;

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

    public StationListAdapter(Context context, int resourceId, List<StopData> stopsList, int imageResId) {
        super(context, resourceId, stopsList);
        mResourceId = resourceId;
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
        mImageResId = imageResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mLayoutInflator.inflate(mResourceId, null);
        final StopData stopData = (StopData) getItem(position);

        final TextView stationName = (TextView) convertView.findViewById(R.id.station_name);
        stationName.setText(stopData.getStopName());

        final ImageView lineImage = (ImageView) convertView.findViewById(R.id.line_image);
        lineImage.setImageDrawable(mContext.getResources().getDrawable(mImageResId));

        return convertView;
    }

}
