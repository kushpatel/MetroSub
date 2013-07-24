package com.MetroSub.ui;

import android.graphics.Color;
import com.MetroSub.database.dataobjects.ShapeData;
import com.MetroSub.utils.UIUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/23/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubwayLinePlotter {

    private static final String TAG = "SubwayLinePlotter";

    private static final int LINE_WIDTH = 5;

    public static void drawLine(String line, List<ShapeData> linePointsList, GoogleMap map) {

        char lineName = line.charAt(0);
        int colorCode = UIUtils.getArgbColor(lineName);

        //LatLng previousPoint = null;

        PolylineOptions polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(Color.BLUE).geodesic(true);

        for (ShapeData linePoint : linePointsList) {

            double lat = Double.parseDouble(linePoint.getShapePtLat());
            double lng = Double.parseDouble(linePoint.getShapePtLon());

            LatLng coordinate = new LatLng(lat, lng);

//            if (previousPoint != null) {
//                map.addPolyline(new PolylineOptions()
//                        .add(previousPoint, currentPoint)
//                        .width(5)
//                        .color(Color.BLUE));
//            }
//
//            previousPoint = currentPoint;

            polylineOptions.add(coordinate);
        }

        map.addPolyline(polylineOptions);
    }

}
