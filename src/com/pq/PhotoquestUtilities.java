package com.pq;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.utilsframework.android.view.Spinners;

/**
 * Created by CM on 2/20/2015.
 */
public class PhotoquestUtilities {
    public static void initGenderSpinner(Spinner spinner) {
        Spinners.initSpinnerFromStringArray(spinner, R.array.gender);
    }
}
