package com.longyuan.bitunionpyro.api;

import com.longyuan.bitunionpyro.pojo.action.ActionRequestBase;
import com.longyuan.bitunionpyro.pojo.action.LatestPostList;
import com.longyuan.bitunionpyro.pojo.action.PostList;
import com.longyuan.bitunionpyro.pojo.login.LoginRequest;
import com.longyuan.bitunionpyro.pojo.login.LoginResponse;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by loxu on 10/01/2018.
 */

public interface BUService {

    @POST("bu_logging.php")
    Observable<LoginResponse> getLogin(@Body LoginRequest request);


    @POST("bu_forum.php")
    Observable<PostList> getPosts(@Body LoginRequest request,@QueryMap Map<String, String> options);

    @POST("bu_home.php")
    Observable<LatestPostList> getHomePosts(@Body ActionRequestBase request);
}
