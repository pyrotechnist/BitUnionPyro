package com.longyuan.bitunionpyro.pojo.action;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PostRequest extends ActionRequestBase {

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("tid")
    private String tid;

    @SerializedName("action")
    protected String action;

    public void setAction(String action){
        this.action = action;
    }

    public String getAction(){
        return action;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTid() {
        return tid;
    }

    @Override
    public String toString() {
        return
                "{" +
                        "session = '" + session + '\'' +
                        ",action = '" + action + '\'' +
                        ",username = '" + username + '\'' +
                        "from = '" + from + '\'' +
                        ",to = '" + to + '\'' +
                        ",tid = '" + tid + '\'' +
                        "}";
    }
}