package com.longyuan.bitunionpyro.userdetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsRequest;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsResponse;
import com.longyuan.bitunionpyro.utils.Constant;
import com.longyuan.bitunionpyro.utils.SharedPreferencesHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserdetailsActivity extends AppCompatActivity {


    public static final String EXTRA_USER_NAME = "USER_NAME";

    @Inject
    BUService mBUservice;


    @BindView(R.id.textView_user_id)
    TextView mTextView_UserId;

    String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getApplicationContext()))
                .build().inject(this);

        mUserName = getIntent().getStringExtra(EXTRA_USER_NAME);

        getUserDetails(mUserName);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getUserDetails(String userId) {

        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();

        userDetailsRequest.setSession(SharedPreferencesHelper.getPrefValue(this, Constant.PREF_SESSION));
        userDetailsRequest.setQueryusername(mUserName);
        userDetailsRequest.setAction("profile");
        userDetailsRequest.setUsername(SharedPreferencesHelper.getPrefValue(this, Constant.PREF_USER_NAME));


        mBUservice.getUserDetails(userDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> updateData(data),
                        throwable -> handleError(throwable));

    }

    private void handleError(Throwable throwable) {
    }

    private void updateData(UserDetailsResponse data) {
        mTextView_UserId.setText(data.getMemberinfo().getUid());
    }


}
