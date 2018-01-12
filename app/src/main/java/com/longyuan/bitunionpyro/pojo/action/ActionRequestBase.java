package com.longyuan.bitunionpyro.pojo.action;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ActionRequestBase{

	@SerializedName("session")
	protected String session;

	@SerializedName("username")
	protected String username;

	public void setSession(String session){
		this.session = session;
	}

	public String getSession(){
		return session;
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
			",username = '" + username + '\'' +
			"}";
		}
}