package com.longyuan.bitunionpyro.pojo.action;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PostList{

	@SerializedName("result")
	private String result;

	@SerializedName("postlist")
	private List<Post> postlist;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setPostlist(List<Post> postlist){
		this.postlist = postlist;
	}

	public List<Post> getPostlist(){
		return postlist;
	}

	@Override
 	public String toString(){
		return 
			"PostList{" + 
			"result = '" + result + '\'' + 
			",postlist = '" + postlist + '\'' + 
			"}";
		}
}