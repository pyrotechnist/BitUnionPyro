package com.longyuan.bitunionpyro.pojo.action;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Lastreply{

	@SerializedName("what")
	private String what;

	@SerializedName("when")
	private String when;

	@SerializedName("who")
	private String who;

	public void setWhat(String what){
		this.what = what;
	}

	public String getWhat(){
		return what;
	}

	public void setWhen(String when){
		this.when = when;
	}

	public String getWhen(){
		return when;
	}

	public void setWho(String who){
		this.who = who;
	}

	public String getWho(){
		return who;
	}

	@Override
 	public String toString(){
		return 
			"Lastreply{" + 
			"what = '" + what + '\'' + 
			",when = '" + when + '\'' + 
			",who = '" + who + '\'' + 
			"}";
		}
}