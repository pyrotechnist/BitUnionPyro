package com.longyuan.bitunionpyro.pojo.action.reply;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ReplyRequest{

	@SerializedName("session")
	private String session;

	@SerializedName("action")
	private String action;

	@SerializedName("from")
	private int from;

	@SerializedName("to")
	private int to;

	@SerializedName("tid")
	private String tid;

	@SerializedName("username")
	private String username;

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
	}

	public void setTid(String tid){
		this.tid = tid;
	}

	public String getTid(){
		return tid;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"{" +
			"session = '" + session + '\'' + 
			",action = '" + action + '\'' + 
			",from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",tid = '" + tid + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}