package com.MetroSub.utils;

import android.graphics.Color;
import com.MetroSub.R;
import com.MetroSub.database.QueryHelper;
import com.MetroSub.ui.SubwayLinePlotter;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/19/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class UIUtils {

    public static final int NUMBER_SUBWAY_LINES = 22;
    public static final int LINE_1_POS = 0;
    public static final int LINE_2_POS = 1;
    public static final int LINE_3_POS = 2;
    public static final int LINE_4_POS = 3;
    public static final int LINE_5_POS = 4;
    public static final int LINE_6_POS = 5;
    public static final int LINE_7_POS = 6;
    public static final int LINE_A_POS = 7;
    public static final int LINE_B_POS = 8;
    public static final int LINE_C_POS = 9;
    public static final int LINE_D_POS = 10;
    public static final int LINE_E_POS = 11;
    public static final int LINE_F_POS = 12;
    public static final int LINE_G_POS = 13;
    public static final int LINE_J_POS = 14;
    public static final int LINE_L_POS = 15;
    public static final int LINE_M_POS = 16;
    public static final int LINE_N_POS = 17;
    public static final int LINE_Q_POS = 18;
    public static final int LINE_R_POS = 19;
    public static final int LINE_S_POS = 20;
    public static final int LINE_Z_POS = 21;

    private static String[] lineNames = {"1", "2", "3", "4", "5", "6", "7", "A", "B", "C", "D",
                                         "E", "F", "G", "J", "L", "M", "N", "Q", "R", "S", "Z"};

    public static void plotSelectedLines(boolean[] selectedCheckBoxes, QueryHelper queryHelper, GoogleMap map) {
        for (int selectedIdx = 0; selectedIdx < selectedCheckBoxes.length; selectedIdx++) {
            if (selectedCheckBoxes[selectedIdx]) {
                SubwayLinePlotter.plotLine(lineNames[selectedIdx],queryHelper,map);
            }
        }
    }

    public static int getIconForLine(char line) {
        int iconResId = 0;
        switch (line) {
            case '1':
                iconResId = R.drawable.icon1;
                break;
            case '2':
                iconResId = R.drawable.icon2;
                break;
            case '3':
                iconResId = R.drawable.icon3;
                break;
            case '4':
                iconResId = R.drawable.icon4;
                break;
            case '5':
                iconResId = R.drawable.icon5;
                break;
            case '6':
                iconResId = R.drawable.icon6;
                break;
            case '7':
                iconResId = R.drawable.icon7;
                break;
            case 'A':
                iconResId = R.drawable.icona;
                break;
            case 'B':
                iconResId = R.drawable.iconb;
                break;
            case 'C':
                iconResId = R.drawable.iconc;
                break;
            case 'D':
                iconResId = R.drawable.icond;
                break;
            case 'E':
                iconResId = R.drawable.icone;
                break;
            case 'F':
                iconResId = R.drawable.iconf;
                break;
            case 'G':                            // no G line exists?
                iconResId = R.drawable.letter_g;
                break;
            case 'J':
                iconResId = R.drawable.iconj;
                break;
            case 'L':
                iconResId = R.drawable.iconl;
                break;
            case 'M':
                iconResId = R.drawable.iconm;
                break;
            case 'N':
                iconResId = R.drawable.iconn;
                break;
            case 'Q':
                iconResId = R.drawable.iconq;
                break;
            case 'R':
                iconResId = R.drawable.iconr;
                break;
            case 'S':
                iconResId = R.drawable.icons;
                break;
            case 'Z':
                iconResId = R.drawable.iconz;
                break;
        }
        return iconResId;
    }

    public static int getArgbColor(char line) {
        int colorCode = 0;
        try {
            String lineHexColor = getLineHexColor(line);
            colorCode = Color.parseColor(lineHexColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorCode;
    }

    public static String getLineHexColor(char line) throws Exception {
        String color;
        switch (line) {
            case('1'):   color = ("#EE352E"); break;
            case('2'):   color = ("#EE352E"); break;
            case('3'):   color = ("#EE352E"); break;
            case('4'):   color = ("#00933C"); break;
            case('5'):   color = ("#00933C"); break;
            case('6'):   color = ("#00933C"); break;
            case('7'):   color = ("#B933AD"); break;
            case('A'):   color = ("#2850AD"); break;
            case('B'):   color = ("#FF6319"); break;
            case('C'):   color = ("#2850AD"); break;
            case('D'):   color = ("#FF6319"); break;
            case('E'):   color = ("#2850AD"); break;
            case('F'):   color = ("#FF6319"); break;
            case('G'):   color = ("#6CBE45"); break;
            case('J'):   color = ("#996633"); break;
            case('L'):   color = ("#A7A9AC"); break;
            case('M'):   color = ("#FF6319"); break;
            case('N'):   color = ("#FCCC0A"); break;
            case('Q'):   color = ("#FCCC0A"); break;
            case('R'):   color = ("#FCCC0A"); break;
            case('S'):   color = ("#808183"); break;
            case('Z'):   color = ("#996633"); break;
            default:     throw new Exception("Unknown line " + line);
        }
        return color;
    }

    public static long getNotificationTime(String startAlertsAfter) {
        long duration = -1;
        int minutesMultiplier = 60 * 1000;
        if (startAlertsAfter.equals("15 minutes")) {
            duration = 15 * minutesMultiplier;
        } else if (startAlertsAfter.equals("30 minutes")) {
            duration = 30 * minutesMultiplier;
        } else if (startAlertsAfter.equals("45 minutes")) {
            duration = 45 * minutesMultiplier;
        } else if (startAlertsAfter.equals("1 hour")) {
            duration = 60 * minutesMultiplier;
        } else if (startAlertsAfter.equals("1.5 hours")) {
            duration = 90 * minutesMultiplier;
        } else if (startAlertsAfter.equals("2 hours")) {
            duration = 120 * minutesMultiplier;
        }
        return duration;
    }
}
