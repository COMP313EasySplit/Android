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
	
	public String getHostEvent(int hostID) throws HttpException, IOException, TimeoutException
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

	public String getParticipantEvent(int hostID) throws HttpException, IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/showParticipantEvents/1
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( hostID != 0) {
			//strParams.add(new BasicNameValuePair("hostid", Integer.toString(hostID)));
			url = getUrl("showParticipantEvents") + "/" + hostID;
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
	
	public String addEventParticipants(String eventId, String firstName, String lastName, String email) throws HttpException, IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/updateEventParticipants/{eventid}/{firstname}/{lastname}/{email}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url = "";
		
		if ( !TextUtils.isEmpty(eventId) 
				&& !TextUtils.isEmpty(firstName)
				&& !TextUtils.isEmpty(lastName)
				&& !TextUtils.isEmpty(email))
		{
			url = getUrl("updateEventParticipants") + "/" + eventId + "/" + firstName + "/" + lastName + "/" + email;
			url = url.replace(" ", "%20");
		}
		else
			return null;
		Log.v("Type 1", "post updateEventParticipants url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	public String addExpense(int eventId, String name, double amount,String place, int payerId ) throws IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/addExpense/{eventid}/{name}/{amount}/{place}/{originalpayer}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url = "";
		
		if(eventId!=0 && payerId!=0 && amount!=0
				&& !TextUtils.isEmpty(name)
				&& !TextUtils.isEmpty(place))
		{
			url = getUrl("addExpense") + "/" + eventId + "/" + name + "/" + amount + "/" + place + "/" + payerId;
			url = url.replace(" ", "%20");
		}
		else
			return null;
		Log.v("Type 1", "post addExpense url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	public String addExpenseParticipants(int expenseId, int userId, double amount ) throws IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/updateExpenseParticipants/{expenseid}/{userid}/{amount}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url = "";
		
		if(expenseId!=0 && userId!=0 )
		{
			url = getUrl("updateExpenseParticipants") + "/" + expenseId + "/" + userId + "/" + amount;
			url = url.replace(" ", "%20");
		}
		else
			return null;
		Log.v("Type 1", "post updateExpenseParticipants url:" + url);
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
	public String login(String email, String passcode) throws IOException, TimeoutException
	{
		//  http://localhost:12479/EasySplitService.svc/login/{email}/{passcode}
		String url = "";
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(passcode) )
		{
			url = getUrl("login") + "/" + email + "/" + passcode;
			url = url.replace(" ","%20");
		}
		else {return null;}
		Log.v("Type 1","post login url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
	
	public String getSummary(int eventId) throws IOException, TimeoutException
	{	//http://54.191.15.241/EasySplitService/EasySplitService.svc/showSummary/{eventid}
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		String url ="";
		if ( eventId != 0 ) {
			url = getUrl("showSummary") + "/" + eventId;
			url = url.replaceAll(" ","%20");
		
		}else{
			return null;
		}
		Log.v("Type 1", "post addEvent url:" + url);
		return baseRequest.postRequestByHttpClient(strParams, url);
	}
}
