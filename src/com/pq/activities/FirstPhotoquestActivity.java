package com.pq.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by CM on 2/20/2015.
 */
public class FirstPhotoquestActivity extends Activity {
    public static void start(Context context) {
        Intent intent = new Intent(context, FirstPhotoquestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
