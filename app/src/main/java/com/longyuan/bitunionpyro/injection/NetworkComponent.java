package com.longyuan.bitunionpyro.injection;

import com.longyuan.bitunionpyro.HomeActivity;
import com.longyuan.bitunionpyro.ReplayList.ReplyListActivity;
import com.longyuan.bitunionpyro.login.LoginActivity;
import com.longyuan.bitunionpyro.userdetails.UserDetailsFragment;
import com.longyuan.bitunionpyro.userdetails.UserdetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by loxu on 10/01/2018.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(ReplyListActivity replyListActivity);

    void inject(UserdetailsActivity userdetailsActivity);

    void inject(UserDetailsFragment userDetailsFragment);

}
