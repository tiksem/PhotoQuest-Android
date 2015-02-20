package com.pq.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.pq.PhotoquestUtilities;
import com.pq.R;
import com.pq.app.PhotoQuestApp;
import com.pq.data.UserRegistration;
import com.pq.network.CaptchaUrlProvider;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.Alerts;

/**
 * Created by CM on 2/20/2015.
 */
public class RegisterActivity extends Activity {
    private RequestManager requestManager;
    private TextView login;
    private TextView password;
    private TextView name;
    private TextView lastName;
    private Spinner gender;
    private ImageView captcha;
    private TextView captchaCode;
    private TextView city;
    private TextView country;
    private long captchaId;
    private int cityId;
    private ProgressDialog progressDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private UserRegistration getUserRegistration() {
        UserRegistration user = new UserRegistration();
        user.login = login.getText().toString();
        user.password = password.getText().toString();
        user.name = name.getText().toString();
        user.lastName = lastName.getText().toString();
        user.answer = captchaCode.getText().toString();
        user.captcha = captchaId;
        user.cityId = cityId;
        user.gender = gender.getSelectedItemPosition() == 0;
        return user;
    }

    private void register() {

    }

    private void initViews() {
        gender = (Spinner) findViewById(R.id.gender);
        PhotoquestUtilities.initGenderSpinner(gender);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        login = (TextView) findViewById(R.id.login);
        password = (TextView) findViewById(R.id.password);
        name = (TextView) findViewById(R.id.name);
        lastName = (TextView) findViewById(R.id.lastName);
        captcha = (ImageView) findViewById(R.id.captcha);
        captchaCode = (TextView) findViewById(R.id.captchaCode);
        city = (TextView) findViewById(R.id.city);
        country = (TextView) findViewById(R.id.country);

        updateCaptchaImage();
    }

    private void updateCaptchaImage() {
        Images.displayImage(requestManager.getCaptchaUrl(captchaId), captcha, new OnComplete() {
            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = PhotoQuestApp.getInstance().getRequestManager();

        progressDialog = showProgress();
        requestManager.getCaptcha(new OnSuccess<RequestManager.Captcha>() {
            @Override
            public void onSuccess(RequestManager.Captcha result) {
                captchaId = result.id;
                setContentView(R.layout.register);
                initViews();
            }
        });
    }

    private ProgressDialog showProgress() {
        return Alerts.showCircleProgressDialog(this, getString(R.string.please_wait));
    }
}
