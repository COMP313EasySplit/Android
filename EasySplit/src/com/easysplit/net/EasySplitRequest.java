package com.easysplit.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.text.TextUtils;


public class EasySplitRequest {
	private final String serviceUrl = "http://54.191.15.241/EasySplitService/EasySplitService.svc/";

	protected Context mContext;

	private BaseRequest baseRequest;

	public EasySplitRequest(Context context) {
		baseRequest = new BaseRequest();
		mContext = context;
	}

	private String getUrl(String serviceName) {
		StringBuilder sb = new StringBuilder();
		sb.append(serviceUrl).append(serviceName);
		return sb.toString();
	}
	
	public String getEvent(int hostID) throws HttpException, IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/showHostEvents/1
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( hostID != 0) {
			//strParams.add(new BasicNameValuePair("hostid", Integer.toString(hostID)));
			url = getUrl("showHostEvents") + "/" + hostID;
		}else{
			return null;
		}
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	
	public String addEvent(String name, double budget, int hostID) throws HttpException, IOException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/addEvent?name=new%20event2&budget=450&hostid=1
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if ( !TextUtils.isEmpty(name) && budget!=0 && hostID != 0) {
			strParams.add(new BasicNameValuePair("name", name));
			strParams.add(new BasicNameValuePair("budget", Double.toString(budget)));
			strParams.add(new BasicNameValuePair("hostid", Integer.toString(hostID)));
		}else{
			return null;
		}
		return baseRequest.getRequestByHttpClient(strParams, getUrl("addEvent"));
	}
}
