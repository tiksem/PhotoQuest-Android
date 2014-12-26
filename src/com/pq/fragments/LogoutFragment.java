package com.pq.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import com.pq.R;
import com.pq.activities.LoginActivity;
import com.pq.app.PhotoQuestApp;
import com.pq.network.RequestManager;
import com.utilsframework.android.db.KeyValueDatabase;
import com.utilsframework.android.db.SQLiteKeyValueDatabase;
import com.utilsframework.android.json.OnFinished;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.threading.Threading;
import com.utilsframework.android.view.Alerts;
import com.utilsframework.android.view.GuiUtilities;

/**
 * Created by CM on 12/26/2014.
 */
public class LogoutFragment extends Fragment {
    private KeyValueDatabase keyValueDatabase;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        final ProgressDialog progressDialog =
                Alerts.showCircleProgressDialog(activity, getString(R.string.logout_progress_message));

        keyValueDatabase = new SQLiteKeyValueDatabase(activity);

        RequestManager requestManager = PhotoQuestApp.getInstance().getRequestManager();
        requestManager.logout(new OnFinished() {
            @Override
            public void onFinished() {
                Threading.runOnBackground(new Runnable() {
                    @Override
                    public void run() {
                        keyValueDatabase.set("login", null);
                        keyValueDatabase.set("password", null);
                    }
                }, new OnComplete() {
                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                        activity.finish();
                        LoginActivity.start(activity);
                    }
                });
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        keyValueDatabase.close();
    }
}
