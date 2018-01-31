package com.longyuan.bitunionpyro.replayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.longyuan.bitunionpyro.R;
import com.longyuan.bitunionpyro.api.BUService;
import com.longyuan.bitunionpyro.injection.DaggerNetworkComponent;
import com.longyuan.bitunionpyro.injection.NetworkModule;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyItem;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyList;
import com.longyuan.bitunionpyro.pojo.action.reply.ReplyRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginResponse;
import com.longyuan.bitunionpyro.utils.Constant;
import com.longyuan.bitunionpyro.utils.ReplyListAdapter;
import com.longyuan.bitunionpyro.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.longyuan.bitunionpyro.utils.Constant.PREF_SESSION;
import static com.longyuan.bitunionpyro.utils.LogHelper.LogInfo;
import static com.longyuan.bitunionpyro.utils.SharedPreferencesHelper.setPrefValue;

public class ReplyListActivity extends AppCompatActivity {

    // Tags
    private final static String TAG = ReplyListActivity.class.getSimpleName();

    public static final String EXTRA_POST_ID = "POST_ID";

    public static final String EXTRA_SESSION_ID = "SESSION_ID";

    private String mPostId;

    private String mSession;

    @Inject
    BUService mBUservice;

    @BindView(R.id.reply_list)
    RecyclerView mLatestPostList;

    private ReplyListAdapter mReplyListAdapter;

    private  List<ReplyItem> mReplyItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_list);

        mPostId = getIntent().getStringExtra(EXTRA_POST_ID);

        mSession= getIntent().getStringExtra(EXTRA_SESSION_ID);

        // View Injection
        ButterKnife.bind(this);

        // Dependency Injection
        DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://out.bitunion.org/open_api/",getApplicationContext()))
                .build().inject(this);

        setRecyclerView();
        loadData();

    }
    private void loadData(){
        loadData(false);
    }

    private void loadData(boolean isGetNewSession){
        ReplyRequest replyRequest = new ReplyRequest();


        replyRequest.setAction("post");

        replyRequest.setFrom(0);

        replyRequest.setTid(mPostId);
        replyRequest.setTo(20);
        replyRequest.setSession(mSession);

        replyRequest.setUsername("黄色潜水艇");

        mBUservice.getReplies(replyRequest)
                .flatMap(data-> checkandUpdateData(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> mReplyListAdapter.updateData(data.getPostlist()),
                        throwable -> handleError(throwable));

    }



    private Observable<ReplyList> checkandUpdateData(ReplyList data) {
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
                    .flatMap(replyRequest -> mBUservice.getReplies(replyRequest));

            //return Observable.just(data);
            //loadData(true);

        }
    }

    private ReplyRequest generateReplyRequest(LoginResponse loginResponse) {

        ReplyRequest replyRequest = new ReplyRequest();


        replyRequest.setAction("post");

        replyRequest.setFrom(0);

        replyRequest.setTid(mPostId);
        replyRequest.setTo(20);
        replyRequest.setSession(loginResponse.getSession());

        setPrefValue(this, PREF_SESSION, loginResponse.getSession());

        replyRequest.setUsername("黄色潜水艇");

        return replyRequest;
    }


    private void handleError(Throwable error ){

        Log.d("test",error.getLocalizedMessage());
    }

    private void setRecyclerView(){

        List<ReplyItem> aReplyList= new ArrayList();

        mReplyListAdapter = new ReplyListAdapter(aReplyList,this);

        mLatestPostList.setAdapter(mReplyListAdapter);

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
}
