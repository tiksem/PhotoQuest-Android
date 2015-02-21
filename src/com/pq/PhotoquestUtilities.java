package com.pq;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.utilsframework.android.time.TimeUtils;
import com.utilsframework.android.view.Spinners;

/**
 * Created by CM on 2/20/2015.
 */
public class PhotoquestUtilities {
    public static void initGenderSpinner(Spinner spinner) {
        Spinners.initSpinnerFromStringArray(spinner, R.array.gender);
    }

    public static String getDisplayDate(long milliseconds) {
        return TimeUtils.getAlternativeDisplayDate(milliseconds);
    }
}
