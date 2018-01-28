package com.longyuan.bitunionpyro.pojo.action.userDetails;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class UserDetailsRequest{

	@SerializedName("uid")
	private String uid;

	@SerializedName("session")
	private String session;

	@SerializedName("action")
	private String action;

	@SerializedName("queryusername")
	private String queryusername;

	@SerializedName("username")
	private String username;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

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

	public void setQueryusername(String queryusername){
		this.queryusername = queryusername;
	}

	public String getQueryusername(){
		return queryusername;
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
			"UserDetailsRequest{" + 
			"uid = '" + uid + '\'' + 
			",session = '" + session + '\'' + 
			",action = '" + action + '\'' + 
			",queryusername = '" + queryusername + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}