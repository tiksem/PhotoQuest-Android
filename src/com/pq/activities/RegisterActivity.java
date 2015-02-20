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
import com.pq.data.User;
import com.pq.data.UserRegistration;
import com.pq.network.CaptchaUrlProvider;
import com.pq.network.RequestManager;
import com.pq.utils.Images;
import com.pq.view.CountryCityInput;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.json.OnSuccess;
import com.utilsframework.android.threading.OnComplete;
import com.utilsframework.android.view.Alerts;
import com.utilsframework.android.view.EditTextWithSuggestions;

import java.io.IOException;

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
    private EditTextWithSuggestions city;
    private EditTextWithSuggestions country;
    private long captchaId;
    private int cityId;
    private ProgressDialog progressDialog;
    private IOErrorListener ioErrorListener;
    private CountryCityInput countryCityInput;

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
        user.cityId = countryCityInput.getCityId();
        return user;
    }

    private void register() {
        UserRegistration user = getUserRegistration();
        requestManager.register(user, new OnSuccess<User>() {
            @Override
            public void onSuccess(User result) {
                FirstPhotoquestActivity.start(RegisterActivity.this);
            }
        });
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
        city = (EditTextWithSuggestions) findViewById(R.id.city);
        country = (EditTextWithSuggestions) findViewById(R.id.country);

        countryCityInput = new CountryCityInput(requestManager, country, city);

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

    private void onIOError(IOException e) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        Alerts.showOkButtonAlert(this, e.getMessage());
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

        ioErrorListener = new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                RegisterActivity.this.onIOError(error);
            }
        };
        requestManager.addIOErrorListener(ioErrorListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestManager.removeIOErrorListener(ioErrorListener);
    }

    private ProgressDialog showProgress() {
        return Alerts.showCircleProgressDialog(this, getString(R.string.please_wait));
    }
}
