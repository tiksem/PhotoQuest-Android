package com.pq.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import com.pq.PhotoquestUtilities;
import com.pq.R;

/**
 * Created by CM on 2/20/2015.
 */
public class RegisterActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Spinner genderSpinner = (Spinner) findViewById(R.id.gender);
        PhotoquestUtilities.initGenderSpinner(genderSpinner);
    }
}
