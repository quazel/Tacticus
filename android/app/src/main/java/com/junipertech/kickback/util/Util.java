package com.junipertech.kickback.util;

import android.content.res.Resources;

public class Util {

    public static int dpToPixels(Resources res, int dp) {
        final float scale = res.getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
