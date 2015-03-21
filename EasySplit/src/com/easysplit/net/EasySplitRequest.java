package com.easysplit.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


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
			url = url.replaceAll(" ","%20");
		}else{
			return null;
		}
		Log.v("Type 1", "post getEvent url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	
	public String addEvent(String name, double budget, int hostID) throws HttpException, IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/addEvent/{name}/{budget}/{hostid}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( !TextUtils.isEmpty(name) && budget!=0 && hostID != 0) {
			url = getUrl("addEvent") + "/" + name  + "/" + budget + "/" + hostID;
			url = url.replaceAll(" ","%20");
			
			//strParams.add(new BasicNameValuePair("name", name));
			//strParams.add(new BasicNameValuePair("budget", Double.toString(budget)));
			//strParams.add(new BasicNameValuePair("hostid", Integer.toString(hostID)));
		}else{
			return null;
		}
		Log.v("Type 1", "post addEvent url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	
	public String getEventExpenses(int eventId) throws IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/getExpense/{eventid}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( eventId != 0 ) {
			url = getUrl("getExpense") + "/" + eventId;
			url = url.replaceAll(" ","%20");
		
		}else{
			return null;
		}
		Log.v("Type 1", "post addEvent url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	
	}

	public String getEventParticipants(int eventId) throws IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/getParticipants/{eventid}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( eventId != 0 ) {
			url = getUrl("getParticipants") + "/" + eventId;
			url = url.replaceAll(" ","%20");
		
		}else{
			return null;
		}
		Log.v("Type 1", "post addEvent url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
}
