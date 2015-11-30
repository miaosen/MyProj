package com.main;


import com.okhttp.AsynActionInvorker;
import com.okhttp.AsynActionInvorker.GetTextListener;

public class Data {
	
	public void getInternetData(GetTextListener aa){
		AsynActionInvorker ai=new AsynActionInvorker("mpMpBaseDataAciton");
		ai.setGetTextListener(aa);
		ai.addParam("parent_id", "0");
		ai.invorkerForString("getCheckType");
	}

}
