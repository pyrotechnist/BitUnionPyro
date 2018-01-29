package com.longyuan.bitunionpyro.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.longyuan.bitunionpyro.HomeActivity;
import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.login.LoginRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.longyuan.bitunionpyro.utils.Constant.PREF_PASSWORD;
import static com.longyuan.bitunionpyro.utils.Constant.PREF_SESSION;
import static com.longyuan.bitunionpyro.utils.Constant.PREF_USER_NAME;
import static com.longyuan.bitunionpyro.utils.LogHelper.LogInfo;
import static com.longyuan.bitunionpyro.utils.SharedPreferencesHelper.getPrefValue;
import static com.longyuan.bitunionpyro.utils.SharedPreferencesHelper.setPrefValue;

public class LoginActivity extends AppCompatActivity {


    @Inject
    BUService mBUservice;

    @BindView(R.id.editText_name)
    EditText mEditText_name;

    @BindView(R.id.editText_password)
    EditText mEditText_password;

    String mUserName;

    String mPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/", getApplicationContext())).build().inject(this);

        mUserName = getPrefValue(this,PREF_USER_NAME);

        mPassword = getPrefValue(this,PREF_PASSWORD);

        if(mUserName != null &&  !mUserName.equals(""))
        {
            mEditText_name.setText(mUserName);
        }

        if(mPassword != null &&  !mPassword.equals(""))
        {
            mEditText_password.setText(mPassword);
        }

    }

    private void handleError(Throwable error) {

        Log.d("test", error.getLocalizedMessage());
    }

    private void checkSession(LoginResponse loginResponse) {
        if (!loginResponse.getResult().equals("success")) {
            LogInfo("test", loginResponse.toString());

        } else {
            LogInfo("test", loginResponse.toString());
            setPrefValue(this, PREF_USER_NAME, mUserName);
            setPrefValue(this, PREF_SESSION, loginResponse.getSession());
            setPrefValue(this, PREF_PASSWORD, mPassword);

            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);

            startActivity(intent);
            finish();
        }

    }


    @OnClick(R.id.button_login)
    public void login() {
        LoginRequest aLoginRequest = new LoginRequest();

        aLoginRequest.setAction("login");

        mUserName = mEditText_name.getText().toString();

        mPassword = mEditText_password.getText().toString();

        aLoginRequest.setUsername(mEditText_name.getText().toString());
        aLoginRequest.setPassword(mEditText_password.getText().toString());

        mBUservice.getLogin(aLoginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> checkSession(data),
                        throwable -> handleError(throwable));
    }

}

