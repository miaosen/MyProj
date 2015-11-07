package com.okhttp;

import java.io.IOException;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.comment.AppFactory;
import com.comment.Logger;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class AsynActionInvorker {

	private String host=AppFactory.getHOST();
	private OkHttpClient client;
	private String actionClass;
	// 表单提交方式
	private String way = "post";

	// 文本监听接口
	private AfterGetTextListener afterGetTextListener;
	
	public AsynActionInvorker(String actionClass) {
		super();
		this.actionClass = actionClass;
	}

	public void invorkerForString(final String method, Map<String, Object> map) {
		client = OkHttpFactory.getInstance();
		String url = host +"/"+actionClass+"?action=" + method;
		Logger.info("URL==="+url);
		if (way.equals("post")) {
			new getStringByPost(url,map).start();
		} else {// get方式
			new getStringByGet(url,map).start();
		}
	}

	/**
	 * 通过post方式获取数据线程
	 */
	class getStringByPost extends Thread {
		private String url;
		private Map<String, Object> map;
		final Message msg = new Message();

		public getStringByPost(String url, Map<String, Object> map) {
			this.url = url;
			this.map = map;
		}
		public void run() {
			try {
				FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
				if (map == null || map.size() == 0) {
				} else {
					for (String key : map.keySet()) {
						formEncodingBuilder.add(key.toString(), map.get(key)
								.toString());
						Logger.info("key==="+key+"   value==="+map.get(key)
								.toString());
					}
				}
				RequestBody formBody = formEncodingBuilder.build();
				Request request = new Request.Builder().url(url).post(formBody)
						.build();
				Response response = client.newCall(request).execute();
				if (response.isSuccessful()){
					//Logger.info("json==="+response.body().string());
					msg.obj = response.body().string();
					msg.what = 1;
					hanler.sendMessage(msg);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 通过get方式获取数据线程
	 */
	class getStringByGet extends Thread {
		private String url;
		private Map<String, Object> map;
		final Message msg = new Message();

		public getStringByGet(String url, Map<String, Object> map) {
			this.url = url;
			this.map = map;
		}

		public void run() {
			try {
				if (map == null || map.size() == 0) {
				} else {
					for (String key : map.keySet()) {
						url = url + "&" + key.toString() + "="
								+ map.get(key).toString();
					}
					Logger.info("URL===="+url);
				}
				Request request = new Request.Builder().url(url).build();
				Response response = client.newCall(request).execute();
				if(response.isSuccessful()){
					msg.obj = response.toString();
					msg.what = 1;
					hanler.sendMessage(msg);
				}

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 获取数据完成后执行
	 */
	private Handler hanler = new Handler() {
		public void handleMessage(Message msg) {
			int num = msg.what;
			switch (num) {
			case 1:// 获取字符串
				String jsonStr = (String) msg.obj;
				if(afterGetTextListener!=null){
					afterGetTextListener.onResult(jsonStr);
				}
				break;
			default:
				break;
			}
		};
	};

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}
	
	public void setAfterGetTextListener(AfterGetTextListener afterGetTextListener) {
		this.afterGetTextListener = afterGetTextListener;
	}

	public interface AfterGetTextListener {
		public void onResult(String text);
	}

}
