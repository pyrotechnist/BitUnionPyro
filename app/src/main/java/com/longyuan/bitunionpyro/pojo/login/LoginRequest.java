package com.longyuan.bitunionpyro.pojo.login;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginRequest{

	@SerializedName("password")
	private String password;

	@SerializedName("action")
	private String action;

	@SerializedName("username")
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
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
			"password = '" + password + '\'' + 
			",action = '" + action + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}