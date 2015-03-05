package com.pq;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.mulya.enums.Case;
import com.mulya.enums.Gender;
import com.mulya.enums.NamePart;
import com.pq.app.PhotoQuestApp;
import com.utilsframework.android.resources.StringUtilities;
import com.utilsframework.android.time.TimeUtils;
import com.utilsframework.android.view.Spinners;

import java.util.Locale;

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

    public static String englishOf(String who, int what) {
        String whatString = PhotoQuestApp.getInstance().getEnStringResources().getString(what);
        return who + "'s " + whatString;
    }

    public static String of(String who, int what) {
        PhotoQuestApp app = PhotoQuestApp.getInstance();
        if(!StringUtilities.getLocale(app).equals(new Locale("ru"))){
            return englishOf(who, what);
        }

        String modifiedWho = app.getPetrovich().make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, who);
        if (!modifiedWho.equals(who)) {
            return app.getString(what) + " " + who;
        } else {
            return englishOf(who, what);
        }
    }
}
