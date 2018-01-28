package com.longyuan.bitunionpyro.pojo.action.userDetails;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class UserDetailsResponse{

	@SerializedName("result")
	private String result;

	@SerializedName("memberinfo")
	private Memberinfo memberinfo;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMemberinfo(Memberinfo memberinfo){
		this.memberinfo = memberinfo;
	}

	public Memberinfo getMemberinfo(){
		return memberinfo;
	}

	@Override
 	public String toString(){
		return 
			"UserDetailsResponse{" + 
			"result = '" + result + '\'' + 
			",memberinfo = '" + memberinfo + '\'' + 
			"}";
		}
}