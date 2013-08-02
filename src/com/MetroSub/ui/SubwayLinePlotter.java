package com.MetroSub.ui;

import com.MetroSub.database.dataobjects.ShapeData;
import com.MetroSub.utils.UIUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashSet;
import java.util.Hashtable;
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

//    public static void drawLine(String line, List<ShapeData> linePointsList, GoogleMap map) {
//
//        char lineName = line.charAt(0);
//        int colorCode = UIUtils.getArgbColor(lineName);
//
//        PolylineOptions polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
//
//        for (ShapeData linePoint : linePointsList) {
//
//            double lat = Double.parseDouble(linePoint.getShapePtLat());
//            double lng = Double.parseDouble(linePoint.getShapePtLon());
//
//            LatLng coordinate = new LatLng(lat, lng);
//
//            if (linePoint.getShapePtSequence().equals("0") && !polylineOptions.getPoints().isEmpty()) {
//                PolylineOptions polylineOptionsSegment = polylineOptions;
//                polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
//                map.addPolyline(polylineOptionsSegment);
//            }
//            polylineOptions.add(coordinate);
//        }
//
//        map.addPolyline(polylineOptions);
//    }


    public static void drawLine(String line, List<ShapeData> linePointsList, GoogleMap map) {
        char lineName = line.charAt(0);
        int colorCode = UIUtils.getArgbColor(lineName);

        PolylineOptions polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);

        // Plot the first sequence of shapeIds to get the basic shape plotted
        HashSet<Double> coordinatesSet = new HashSet<Double>();
        int pointIdx = 0;
        do {
            double lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
            double lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());

            LatLng coordinate = new LatLng(lat, lng);
            polylineOptions.add(coordinate);
            coordinatesSet.add(cantorPairFunctionMap(lat, lng));

            pointIdx++;
        } while(!linePointsList.get(pointIdx).getShapePtSequence().equals("0"));

        map.addPolyline(polylineOptions);

        // Plot the sub segments that do not exist in the already drawn line
        for (int idx = pointIdx; idx < linePointsList.size(); idx++) {
            double lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
            double lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());
            if (!coordinatesSet.contains(cantorPairFunctionMap(lat, lng))) {
                // Plot starting from current point till shapePtSeq of zero is encountered
                PolylineOptions polyLineOptionsSegment = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
                while (idx < linePointsList.size() && !linePointsList.get(idx).getShapePtSequence().equals("0")) {
                    lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
                    lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());
                    LatLng coordinate = new LatLng(lat, lng);
                    polyLineOptionsSegment.add(coordinate);
                    idx++;
                }
                map.addPolyline(polyLineOptionsSegment);
            }
        }
    }

    // Maps (x,y) -> z  s.t. z is unique for all (x,y) pairs
    private static double cantorPairFunctionMap(double x, double y) {
        double z = (0.5 * (x + y) * (x + y + 1)) + y;
        return z;
    }

}
