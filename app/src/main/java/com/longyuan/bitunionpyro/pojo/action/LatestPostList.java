package com.longyuan.bitunionpyro.pojo.action;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LatestPostList{

	@SerializedName("result")
	private String result;

	@SerializedName("newlist")
	private List<NewlistItem> newlist;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setNewlist(List<NewlistItem> newlist){
		this.newlist = newlist;
	}

	public List<NewlistItem> getNewlist(){
		return newlist;
	}

	@Override
 	public String toString(){
		return 
			"LatestPostList{" + 
			"result = '" + result + '\'' + 
			",newlist = '" + newlist + '\'' + 
			"}";
		}
}