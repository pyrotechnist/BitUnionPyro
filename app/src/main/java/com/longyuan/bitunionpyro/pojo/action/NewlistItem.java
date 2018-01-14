package com.longyuan.bitunionpyro.pojo.action;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class NewlistItem{

	@SerializedName("fid")
	private String fid;

	@SerializedName("fname")
	private String fname;

	@SerializedName("lastreply")
	private Lastreply lastreply;

	@SerializedName("pname")
	private String pname;

	@SerializedName("author")
	private String author;

	@SerializedName("tid_sum")
	private String tidSum;

	@SerializedName("fid_sum")
	private String fidSum;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("tid")
	private String tid;

	public void setFid(String fid){
		this.fid = fid;
	}

	public String getFid(){
		return fid;
	}

	public void setFname(String fname){
		this.fname = fname;
	}

	public String getFname(){
		return fname;
	}

	public void setLastreply(Lastreply lastreply){
		this.lastreply = lastreply;
	}

	public Lastreply getLastreply(){
		return lastreply;
	}

	public void setPname(String pname){
		this.pname = pname;
	}

	public String getPname(){
		return pname;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setTidSum(String tidSum){
		this.tidSum = tidSum;
	}

	public String getTidSum(){
		return tidSum;
	}

	public void setFidSum(String fidSum){
		this.fidSum = fidSum;
	}

	public String getFidSum(){
		return fidSum;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setTid(String tid){
		this.tid = tid;
	}

	public String getTid(){
		return tid;
	}

	@Override
 	public String toString(){
		return 
			"NewlistItem{" + 
			"fid = '" + fid + '\'' + 
			",fname = '" + fname + '\'' + 
			",lastreply = '" + lastreply + '\'' + 
			",pname = '" + pname + '\'' + 
			",author = '" + author + '\'' + 
			",tid_sum = '" + tidSum + '\'' + 
			",fid_sum = '" + fidSum + '\'' + 
			",avatar = '" + avatar + '\'' + 
			",tid = '" + tid + '\'' + 
			"}";
		}
}