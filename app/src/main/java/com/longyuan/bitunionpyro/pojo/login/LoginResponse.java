package com.longyuan.bitunionpyro.pojo.login;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LoginResponse{

	@SerializedName("result")
	private String result;

	@SerializedName("lastactivity")
	private String lastactivity;

	@SerializedName("uid")
	private String uid;

	@SerializedName("session")
	private String session;

	@SerializedName("credit")
	private String credit;

	@SerializedName("username")
	private String username;

	@SerializedName("status")
	private String status;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setLastactivity(String lastactivity){
		this.lastactivity = lastactivity;
	}

	public String getLastactivity(){
		return lastactivity;
	}

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

	public void setCredit(String credit){
		this.credit = credit;
	}

	public String getCredit(){
		return credit;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"result = '" + result + '\'' + 
			",lastactivity = '" + lastactivity + '\'' + 
			",uid = '" + uid + '\'' + 
			",session = '" + session + '\'' + 
			",credit = '" + credit + '\'' + 
			",username = '" + username + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}