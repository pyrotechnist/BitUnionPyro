package com.longyuan.bitunionpyro.pojo.action;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class UserRequest extends ActionRequestBase {

    @SerializedName("uid")
    private String uid;

    @SerializedName("queryusername")
    private String queryusername;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setQueryusername(String queryusername) {
        this.queryusername = queryusername;
    }

    public String getQueryusername() {
        return queryusername;
    }

    @Override
    public String toString() {
        return
                "UserRequest{" +
                        "session = '" + session + '\'' +
                        ",action = '" + action + '\'' +
                        ",username = '" + username + '\'' +
                        "uid = '" + uid + '\'' +
                        ",queryusername = '" + queryusername + '\'' +
                        "}";
    }
}