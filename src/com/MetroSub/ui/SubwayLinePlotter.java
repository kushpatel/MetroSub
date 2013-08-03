package com.MetroSub.ui;

import com.MetroSub.database.QueryHelper;
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

    public static void plotLine(String line, QueryHelper queryHelper, GoogleMap map) {
//        List<ShapeData> shapePoints = queryHelper.queryForAllLineShapePoints(line);
//        SubwayLinePlotter.drawLine(line,shapePoints,map);
        String routeIdForLine = getRouteForLine(line);
        if (line == null) return;
        List<ShapeData> shapePoints = queryHelper.queryForAllShapePoints(routeIdForLine);
        SubwayLinePlotter.drawLine(line, shapePoints, map);
    }

    private static void drawLine(String line, List<ShapeData> linePointsList, GoogleMap map) {

        char lineName = line.charAt(0);
        int colorCode = UIUtils.getArgbColor(lineName);

        PolylineOptions polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);

        for (ShapeData linePoint : linePointsList) {

            double lat = Double.parseDouble(linePoint.getShapePtLat());
            double lng = Double.parseDouble(linePoint.getShapePtLon());

            LatLng coordinate = new LatLng(lat, lng);

            if (linePoint.getShapePtSequence().equals("0") && !polylineOptions.getPoints().isEmpty()) {
                PolylineOptions polylineOptionsSegment = polylineOptions;
                polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
                map.addPolyline(polylineOptionsSegment);
            }
            polylineOptions.add(coordinate);
        }

        map.addPolyline(polylineOptions);
    }

    private static String getRouteForLine(String line) {
        String route = null;
        switch (line.charAt(0)) {
            case('1'):   route = ("1..N04R"); break;
            case('2'):   route = ("2..N08R"); break;
            case('3'):   route = ("3..N01R"); break;
            case('4'):   route = ("4..S01R"); break;
            case('5'):   route = ("5..S04R"); break;
            case('6'):   route = ("6..S01R"); break;
            case('7'):   route = ("7..S17R"); break;
            case('A'):   route = ("A..N55R"); break;
            case('B'):   route = ("B..N45R"); break;
            case('C'):   route = ("C..N04R"); break;
            case('D'):   route = ("D..N07R"); break;
            case('E'):   route = ("E..N66R"); break;
            case('F'):   route = ("F..N75R"); break;
            case('G'):   route = ("G..N13R"); break;
            case('J'):   route = ("J..N15R"); break;
            case('L'):   route = ("L..N01R"); break;
            case('M'):   route = ("M..N20R"); break;
            case('N'):   route = ("N..N20R"); break;
            case('Q'):   route = ("Q..N55R"); break;
            case('R'):   route = ("R..N93R"); break;
            case('S'):   route = ("SI.N01R"); break;
        }
        return route;
    }


//    public static void drawLine(String line, List<ShapeData> linePointsList, GoogleMap map) {
//        char lineName = line.charAt(0);
//        int colorCode = UIUtils.getArgbColor(lineName);
//
//        PolylineOptions polylineOptions = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
//
//        // Plot the first sequence of shapeIds to get the basic shape plotted
//        HashSet<Double> coordinatesSet = new HashSet<Double>();
//        int pointIdx = 0;
//        do {
//            double lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
//            double lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());
//
//            LatLng coordinate = new LatLng(lat, lng);
//            polylineOptions.add(coordinate);
//            coordinatesSet.add(cantorPairFunctionMap(lat, lng));
//
//            pointIdx++;
//        } while(!linePointsList.get(pointIdx).getShapePtSequence().equals("0"));
//
//        map.addPolyline(polylineOptions);
//
//        // Plot the sub segments that do not exist in the already drawn line
//        for (int idx = pointIdx; idx < linePointsList.size(); idx++) {
//            double lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
//            double lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());
//            if (!coordinatesSet.contains(cantorPairFunctionMap(lat, lng))) {
//                // Plot starting from current point till shapePtSeq of zero is encountered
//                PolylineOptions polyLineOptionsSegment = new PolylineOptions().width(LINE_WIDTH).color(colorCode).geodesic(true);
//                while (idx < linePointsList.size() && !linePointsList.get(idx).getShapePtSequence().equals("0")) {
//                    lat = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLat());
//                    lng = Double.parseDouble(linePointsList.get(pointIdx).getShapePtLon());
//                    LatLng coordinate = new LatLng(lat, lng);
//                    polyLineOptionsSegment.add(coordinate);
//                    idx++;
//                }
//                map.addPolyline(polyLineOptionsSegment);
//            }
//        }
//    }
//
//    // Maps (x,y) -> z  s.t. z is unique for all (x,y) pairs
//    private static double cantorPairFunctionMap(double x, double y) {
//        double z = (0.5 * (x + y) * (x + y + 1)) + y;
//        return z;
//    }

}
