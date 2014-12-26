package com.pq.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.data.User;
import com.pq.network.RequestManager;
import com.utilsframework.android.db.KeyValueDatabase;
import com.utilsframework.android.db.SQLiteKeyValueDatabase;
import com.utilsframework.android.json.ExceptionInfo;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.threading.Threading;

import java.io.IOException;

/**
 * Created by CM on 12/26/2014.
 */
public class LoginActivity extends Activity implements RequestManager.LoginListener {
    private KeyValueDatabase keyValueDatabase;
    private RequestManager requestManager;

    private void tryLoginFromCookies() {
        String login = keyValueDatabase.get("login");
        if(login != null){
            String password = keyValueDatabase.get("password");
            if(password != null){
                login(login, password);
                return;
            }
        }

        onNoCookies();
    }

    private void onNoCookies() {
        initLoginForm();
    }

    private void login(String login, String password) {
        requestManager.login(login, password, this);
    }

    private void initLoginForm() {
        setContentView(R.layout.login);

        final TextView login = (TextView) findViewById(R.id.login);
        final TextView password = (TextView) findViewById(R.id.password);

        findViewById(R.id.sing_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(login.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyValueDatabase = new SQLiteKeyValueDatabase(this);
        requestManager = PhotoQuestApp.getInstance().getRequestManager();

        tryLoginFromCookies();
    }

    @Override
    public void onLoginRequestFinished() {

    }

    @Override
    public void onLoginRequestError(IOException e, ExceptionInfo info) {
        throw new RuntimeException(e);
    }

    @Override
    public void onLoginSuccess(User user, final String login, final String password) {
        Threading.runOnBackground(new Runnable() {
            @Override
            public void run() {
                keyValueDatabase.set("login", login);
                keyValueDatabase.set("password", password);
            }
        }, new OnComplete() {
            @Override
            public void onFinish() {
                LoginActivity.this.finish();
                PeopleActivity.start(LoginActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyValueDatabase.close();
    }
}
