package com.MetroSub.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    private int mResourceId;
    private LayoutInflater mLayoutInflator;
    private Context mContext;

    public StationListAdapter(Context context, int resourceId, List<StationEntranceData> stationsList) {
        super(context, resourceId,stationsList);
        mResourceId = resourceId;
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflator.inflate(mResourceId, null);

        return convertView;
    }

}
