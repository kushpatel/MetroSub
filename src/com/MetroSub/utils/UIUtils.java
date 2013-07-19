package com.MetroSub.utils;

import com.MetroSub.R;

/**
 * Created with IntelliJ IDEA.
 * User: kpatel
 * Date: 7/19/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class UIUtils {

    public static int getIconForLine(char line) {
        int iconResId = 0;
        switch (line) {
            case '1':
                iconResId = R.drawable.number_1;
                break;
            case '2':
                iconResId = R.drawable.number_2;
                break;
            case '3':
                iconResId = R.drawable.number_3;
                break;
            case '4':
                iconResId = R.drawable.number_4;
                break;
            case '5':
                iconResId = R.drawable.number_5;
                break;
            case '6':
                iconResId = R.drawable.number_6;
                break;
            case '7':
                iconResId = R.drawable.number_7;
                break;
            case 'A':
                iconResId = R.drawable.letter_a;
                break;
            case 'B':
                iconResId = R.drawable.letter_b;
                break;
            case 'C':
                iconResId = R.drawable.letter_c;
                break;
            case 'D':
                iconResId = R.drawable.letter_d;
                break;
            case 'E':
                iconResId = R.drawable.letter_e;
                break;
            case 'F':
                iconResId = R.drawable.letter_f;
                break;
            case 'G':
                iconResId = R.drawable.letter_g;
                break;
            case 'J':
                iconResId = R.drawable.letter_j;
                break;
            case 'L':
                iconResId = R.drawable.letter_l;
                break;
            case 'M':
                iconResId = R.drawable.letter_m;
                break;
            case 'N':
                iconResId = R.drawable.letter_n;
                break;
            case 'Q':
                iconResId = R.drawable.letter_q;
                break;
            case 'R':
                iconResId = R.drawable.letter_r;
                break;
            case 'S':
                iconResId = R.drawable.letter_s;
                break;
            case 'Z':
                iconResId = R.drawable.letter_z;
                break;
        }
        return iconResId;
    }
}
