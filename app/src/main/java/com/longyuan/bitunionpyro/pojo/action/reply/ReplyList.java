package com.longyuan.bitunionpyro.pojo.action.reply;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ReplyList{

	@SerializedName("result")
	private String result;

	@SerializedName("postlist")
	private List<ReplyItem> postlist;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setPostlist(List<ReplyItem> postlist){
		this.postlist = postlist;
	}

	public List<ReplyItem> getPostlist(){
		return postlist;
	}

	@Override
 	public String toString(){
		return 
			"ReplyList{" + 
			"result = '" + result + '\'' + 
			",postlist = '" + postlist + '\'' + 
			"}";
		}
}