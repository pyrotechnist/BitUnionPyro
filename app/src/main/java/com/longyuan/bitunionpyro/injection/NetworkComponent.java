package com.longyuan.bitunionpyro.injection;

import com.longyuan.bitunionpyro.LatestPostListActivity;
import com.longyuan.bitunionpyro.ReplayList.ReplyListActivity;
import com.longyuan.bitunionpyro.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by loxu on 10/01/2018.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    void inject(LatestPostListActivity latestPostListActivity);

    void inject(LoginActivity loginActivity);

    void inject(ReplyListActivity replyListActivity);



}
