package com.MetroSub.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.MetroSub.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/29/13
 * Time: 8:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubwayTimesListAdapter extends ArrayAdapter {

    private static final String TAG = "SubwayTimesListAdapter";
    private static final String HTML_BULLET = "&#8226;   ";

    private int mResourceId;
    private LayoutInflater mLayoutInflator;
    private Context mContext;

    public SubwayTimesListAdapter(Context context, int resourceId, List<Integer> subwayTimes) {
        super(context, resourceId, subwayTimes);
        mResourceId = resourceId;
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mLayoutInflator.inflate(mResourceId, null);
        int subwayTime = (Integer) getItem(position);
        String subwayTimeString = (subwayTime == 1) ? "In " + subwayTime + " minute" :
                                                      "In " + subwayTime + " minutes";

        TextView subwayTimeItem = (TextView) convertView.findViewById(R.id.subway_time);
        subwayTimeItem.setText(Html.fromHtml(HTML_BULLET + subwayTimeString));

        return convertView;
    }
}
