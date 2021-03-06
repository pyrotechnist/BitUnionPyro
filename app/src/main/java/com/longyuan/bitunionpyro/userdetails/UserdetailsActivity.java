package com.longyuan.bitunionpyro.userdetails;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsRequest;
import com.longyuan.bitunionpyro.pojo.action.userDetails.UserDetailsResponse;
import com.longyuan.bitunionpyro.utils.Constant;
import com.longyuan.bitunionpyro.utils.GlideImageGetter;
import com.longyuan.bitunionpyro.utils.HtmlHelper;
import com.longyuan.bitunionpyro.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.longyuan.bitunionpyro.utils.HtmlHelper.avatarUrlUpdate;

public class UserdetailsActivity extends AppCompatActivity implements UserDetailsFragment.OnFragmentInteractionListener {

    private final static String TAG = UserdetailsActivity.class.getSimpleName();

    public static final String EXTRA_USER_NAME = "USER_NAME";

    public static final String EXTRA_USER_AVATAR = "USER_AVATAR";


    @Inject
    BUService mBUservice;


    @BindView(R.id.textView_signature)
    TextView mTextView_signature;

    @BindView(R.id.imageView_logo)
    ImageView mImageView_logo;

/*    @BindView(R.id.webView_signature)
    WebView mWebView_signature;*/

    private  String mUserName;

    private String mUserAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getApplicationContext()))
                .build().inject(this);

        mUserName = getIntent().getStringExtra(EXTRA_USER_NAME);

        mUserAvatar = getIntent().getStringExtra(EXTRA_USER_AVATAR);

        //getUserDetails(mUserName);


        //mWebView_signature.getSettings().setJavaScriptEnabled(true);


        Glide.with(getApplicationContext()).load(mUserAvatar).into(mImageView_logo);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());

        UserDetailsFragment userDetailsFragment = UserDetailsFragment.newInstance(mUserName);


        //promotionsFragment1.setPromotionsRepository(mPromotionsRepository);
        //promotionsFragment1.setCategory(Category.Better);
        adapter.addFragment(userDetailsFragment, "User Details");




        UserActionsFragment userActionsFragment = UserActionsFragment.newInstance();


        //promotionsFragment1.setPromotionsRepository(mPromotionsRepository);
        //promotionsFragment1.setCategory(Category.Better);
      /*  promotionsFragment2.setPromotionsRepository(mPromotionsRepository);
        promotionsFragment2.setCategory(Category.Good);*/
        adapter.addFragment(userActionsFragment, "User Actions");

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
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

    }

    @Override
    public void onFragmentInteraction(String url, String signature) {


        GlideImageGetter imageGetter = new GlideImageGetter(getApplicationContext(),mTextView_signature);

        String htmlString = HtmlHelper.urlDecode(signature);
        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml(htmlString, imageGetter, null);
        }

        mTextView_signature.setText(html);

        //mWebView_signature.loadData(HtmlHelper.urlDecode(signature),"text/html", "UTF-8");

        String logoUrl = avatarUrlUpdate(HtmlHelper.urlDecode(url));

        Log.d(TAG,logoUrl);

    }
}
