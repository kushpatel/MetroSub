package com.MetroSub.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockMapFragment;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created with IntelliJ IDEA.
 * User: Shenil
 * Date: 8/9/13
 * Time: 6:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapFragment extends SherlockMapFragment {
    private GoogleMap mMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        mMap = getMap();
        return root;
    }


}