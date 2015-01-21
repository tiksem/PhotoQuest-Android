package com.pq.app;

import com.utilsframework.android.time.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by CM on 1/21/2015.
 */
public class UiUtilities {
    public static String getDisplayDate(long value) {
        return TimeUtils.getAlternativeDisplayDate(value);
    }
}
