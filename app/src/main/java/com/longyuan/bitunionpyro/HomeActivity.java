package com.longyuan.bitunionpyro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.longyuan.bitunionpyro.ReplayList.ReplyListActivity;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.login.LoginActivity;
import com.longyuan.bitunionpyro.pojo.action.ActionRequestBase;
import com.longyuan.bitunionpyro.pojo.action.LatestPostList;
import com.longyuan.bitunionpyro.pojo.action.NewlistItem;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyList;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginResponse;
import com.longyuan.bitunionpyro.utils.Constant;
import com.longyuan.bitunionpyro.utils.LastPostListAdapter;
import com.longyuan.bitunionpyro.utils.OnItemClickListener;
import com.longyuan.bitunionpyro.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.longyuan.bitunionpyro.ReplayList.ReplyListActivity.EXTRA_POST_ID;
import static com.longyuan.bitunionpyro.ReplayList.ReplyListActivity.EXTRA_SESSION_ID;
import static com.longyuan.bitunionpyro.utils.Constant.PREF_PASSWORD;
import static com.longyuan.bitunionpyro.utils.Constant.PREF_SESSION;
import static com.longyuan.bitunionpyro.utils.Constant.PREF_USER_NAME;
import static com.longyuan.bitunionpyro.utils.LogHelper.LogInfo;
import static com.longyuan.bitunionpyro.utils.SharedPreferencesHelper.getPrefValue;
import static com.longyuan.bitunionpyro.utils.SharedPreferencesHelper.setPrefValue;

public class HomeActivity extends AppCompatActivity {

    // Tags
    private final static String TAG = HomeActivity.class.getSimpleName();

    @Inject
    RequestQueue mRequestQueue;

    @Inject
    BUService mBUservice;

    @BindView(R.id.latest_post_list)
    RecyclerView mLatestPostList;

    @BindView(R.id.refresh_home)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LastPostListAdapter mLastPostListAdapter;

    private  List<NewlistItem> mHomePostList;

    private  String  mSession;

    private boolean mIsSwipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getApplicationContext()))
                .build().inject(this);

        setSwipeRefresh();
        setRecyclerView();

        String session = getPrefValue(this,PREF_SESSION);

        if( session == null || session.equals("")){

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        getPostList(session);
    }

    private void setSwipeRefresh() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsSwipeRefresh = true;
                getPostList(getPrefValue(getApplicationContext(),PREF_SESSION));
            }
        });

    }


    private void handleError(Throwable error ){

        Log.d("test",error.getLocalizedMessage());
    }

    private String checkSession(LoginResponse loginResponse){
        if(!loginResponse.getResult().equals("success" ))
        {
           LogInfo("test",loginResponse.toString());
            return null;
        }else
        {
            LogInfo("test",loginResponse.toString());

            setPrefValue(this,PREF_SESSION,loginResponse.getSession());
            return loginResponse.getSession();
        }

    }

    private void setRecyclerView(){

        List<NewlistItem> aPostList= new ArrayList();

        mLastPostListAdapter = new LastPostListAdapter(aPostList,this);

        mLatestPostList.setAdapter(mLastPostListAdapter);

        mLatestPostList.setLayoutManager(new LinearLayoutManager(mLatestPostList.getContext()));

        mLatestPostList.setNestedScrollingEnabled(false);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mLatestPostList.getContext(),
                DividerItemDecoration.VERTICAL);


        mLastPostListAdapter.setOnItemClickListener(new OnItemClickListener.OnPostItemClickListener() {
            @Override
            public void onItemClick(NewlistItem item) {
                //Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),ReplyListActivity.class);
                intent.putExtra(EXTRA_POST_ID, item.getTid());

                intent.putExtra(EXTRA_SESSION_ID, mSession);

                startActivity(intent);

            }

          /*  @Override
            public void onItemLongClick(Story item, int position) {

            }*/
        });

        //mStoryListAdapter.setOnItemClickListener(new OnItemClickListener.OnStoryItemClickListener() {
      /*      @Override
            public void onItemClick(StoryBase item) {
                Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),StoryDetailActivity.class);
                intent.putExtra(EXTRA_STORY_ID, item.getId());

                intent.putExtra(USE_VOLLEY, false);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(StoryBase item, int position) {

            }
        });*/

        mLatestPostList.addItemDecoration(horizontalDecoration);
    }


    private void getPostList(String session){

        getPostList(session,false);



    }

    private void getPostList(String session,boolean isSwipeRefresh){

        mSession = session;

        ActionRequestBase aActionRequestBase = new ActionRequestBase();

        aActionRequestBase.setSession(getPrefValue(this,PREF_SESSION));

        aActionRequestBase.setUsername("黄色潜水艇");

        mSwipeRefreshLayout.setRefreshing(false);

        mBUservice.getHomePosts(aActionRequestBase)
                .flatMap(data-> checkandUpdateData(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               // .map(data -> checkLatestPOstList(data))
                .subscribe(data ->  updateData(data),throwable -> LogInfo("",throwable.getLocalizedMessage()));
    }



    private Observable<LatestPostList> checkandUpdateData(LatestPostList data) {
        if(data.getResult().equals(Constant.RESULT_SUCCESS)) {

            LogInfo(TAG, "Session OK,  data loaded");
            return Observable.just(data);
            // mReplyListAdapter.updateData(data.getPostlist());

        }else
        {
            LogInfo(TAG, "Session expired, try to get session again");

            LoginRequest aLoginRequest = new LoginRequest();

            aLoginRequest.setAction("login");

            aLoginRequest.setUsername(SharedPreferencesHelper.getPrefValue(this,Constant.PREF_USER_NAME));

            aLoginRequest.setPassword(SharedPreferencesHelper.getPrefValue(this,Constant.PREF_PASSWORD));

            return mBUservice
                    .getLogin(aLoginRequest)
                    .map(loginResponse -> generateReplyRequest(loginResponse))
                    .flatMap(actionRequest -> mBUservice.getHomePosts(actionRequest));

            //return Observable.just(data);
            //loadData(true);

        }
    }

    private ActionRequestBase generateReplyRequest(LoginResponse loginResponse) {

        ActionRequestBase aActionRequestBase = new ActionRequestBase();

        aActionRequestBase.setSession(loginResponse.getSession());

        aActionRequestBase.setUsername("黄色潜水艇");

        setPrefValue(this, PREF_SESSION, loginResponse.getSession());

        return aActionRequestBase;
    }


    private void updateData(LatestPostList latestPostList){
        if(!latestPostList.getResult().equals("success"))
        {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            mLastPostListAdapter.updateData(latestPostList.getNewlist());

        }
    }


    private void setupToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_latest_post_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        else if(id == R.id.action_logout)
        {
            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        LoginRequest aLoginRequest = new LoginRequest();

        aLoginRequest.setAction("logout");
        aLoginRequest.setSession(getPrefValue(this,PREF_SESSION));
        aLoginRequest.setUsername(getPrefValue(this,PREF_USER_NAME));
        aLoginRequest.setPassword(getPrefValue(this,PREF_PASSWORD));

        mBUservice.getLogin(aLoginRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // .map(data -> checkLatestPOstList(data))
                .subscribe(data ->  handleLogoutData(data),
                        throwable -> LogInfo("",throwable.getLocalizedMessage()));

    }

    private void handleLogoutData(LoginResponse loginResponse){
        if(loginResponse.getResult().equals("success")){
            setPrefValue(this,PREF_SESSION,"");
            setPrefValue(this,PREF_PASSWORD,"");

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
