package com.longyuan.bitunionpyro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.action.ActionRequestBase;
import com.longyuan.bitunionpyro.pojo.action.Post;
import com.longyuan.bitunionpyro.pojo.login.LoginRequest;
import com.longyuan.bitunionpyro.utils.LastPostListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LatestPostListActivity extends AppCompatActivity {

    @Inject
    RequestQueue mRequestQueue;

    @Inject
    BUService mBUservice;

    @BindView(R.id.test_text)
    TextView mTextView;

    @BindView(R.id.latest_post_list)
    RecyclerView mLatestPostList;

    private LastPostListAdapter mLastPostListAdapter;

    private  List<Post> mHomePostList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_post_list);

        setupToolbar();

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getApplicationContext()))
                .build().inject(this);

        setRecyclerView();

        LoginRequest aLoginRequest = new LoginRequest();

        aLoginRequest.setAction("login");

        aLoginRequest.setUsername("黄色潜水艇");
        aLoginRequest.setPassword("barcainiesta");

        mBUservice.getLogin(aLoginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  getPostList(data.getSession()));






        //mTextView.setText("Hello BU");
    }

    private void setRecyclerView(){

        List<Post> aPostList= new ArrayList();

        mLastPostListAdapter = new LastPostListAdapter(aPostList,this);

        mLatestPostList.setAdapter(mLastPostListAdapter);

        mLatestPostList.setLayoutManager(new LinearLayoutManager(mLatestPostList.getContext()));

        mLatestPostList.setNestedScrollingEnabled(false);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mLatestPostList.getContext(),
                DividerItemDecoration.VERTICAL);

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

        ActionRequestBase aActionRequestBase = new ActionRequestBase();

        aActionRequestBase.setSession(session);

        aActionRequestBase.setUsername("黄色潜水艇");

        mBUservice.getHomePosts(aActionRequestBase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data ->  getPostList(data.getSession()));

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

        return super.onOptionsItemSelected(item);
    }
}
