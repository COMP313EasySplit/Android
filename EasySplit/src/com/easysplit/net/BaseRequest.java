package com.easysplit.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

public class BaseRequest {
	private DefaultHttpClient client;
	private int connectTimeout;
	public BaseRequest(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public BaseRequest() {
		this.connectTimeout = 5000;
		// ------------------------------------------------------
		HttpParams params = new BasicHttpParams();

		ConnManagerParams.setTimeout(params, 1000);

		HttpConnectionParams.setConnectionTimeout(params, 50000);
	
		HttpConnectionParams.setSoTimeout(params, 20000);

		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
		client = new DefaultHttpClient(conMgr, params);
	}

	/**

	 * @param params
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String getRequestByHttpClient(List<NameValuePair> params, String url)
			throws HttpException, IOException {
		String result = null;

		String strURL;

		StringBuffer sb = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (NameValuePair nvp : params) {
				String value = URLEncoder.encode(nvp.getValue(), HTTP.UTF_8);
				if (nvp.getName().equals("oauth_verifier")) {
					value = nvp.getValue();
				}
				sb.append(nvp.getName()).append('=').append(value).append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		String paramsStr = sb.toString();
		if (paramsStr != null && !paramsStr.equals("")) {
			strURL = url + "?" + paramsStr;
		} else {
			strURL = url;

		}

		HttpGet request = new HttpGet(strURL);
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity());
		} else {
			throw new HttpException("Error Response:" + response.getStatusLine().toString());
		}

		return result;
	}
	
	
	/**

	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws HttpException
	 * @throws TimeoutException
	 */
	public String postRequestByHttpClient(List<NameValuePair> params, String url)
			throws IOException, TimeoutException {
		String result = null;
		HttpPost request = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		int httpStatusCode = response.getStatusLine().getStatusCode();
		if (httpStatusCode == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		} else {
			throw new TimeoutException();
		}
		return result;
	}
}
