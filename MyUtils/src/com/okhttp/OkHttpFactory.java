package com.okhttp;

import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;

public class OkHttpFactory {
	
	private static OkHttpClient client=null;
	//单例加线程锁
	public static synchronized OkHttpClient getInstance(){
		if(client==null){
			client=getOkHttp();
		}
		return client;
	}
	
	/**
	 * OKhttp 设置
	 * @return
	 */
	public static OkHttpClient getOkHttp(){
		client = new OkHttpClient();
		//连接超时  10秒
		client.setConnectTimeout(10, TimeUnit.SECONDS);
		return client;
	}

}
