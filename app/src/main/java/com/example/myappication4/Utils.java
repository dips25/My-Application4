package com.example.myappication4;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {

    public static int getDpFromPixels(Context context , float pixels){

        return (int)(pixels/context.getResources().getDisplayMetrics().density);

    }

    public static int getPixelsFromDp(Context context , float dp){

        return (int)(dp * context.getResources().getDisplayMetrics().density);

    }
}
