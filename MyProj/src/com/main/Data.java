package com.main;

import java.util.HashMap;
import java.util.Map;

import com.okhttp.AsynActionInvorker;
import com.okhttp.AsynActionInvorker.AfterGetTextListener;

public class Data {
	
	public void getInternetData(AfterGetTextListener aa){
		AsynActionInvorker ai=new AsynActionInvorker("mpMpBaseDataAciton");
		ai.setAfterGetTextListener(aa);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("parent_id", "0");
		ai.invorkerForString("getCheckType", map);
	}

}
