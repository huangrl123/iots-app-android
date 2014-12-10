package com.dahuangit.iots.app.android.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpUtils {
	/**
	 * 得到http服务端返回的字符（通过post方法）
	 * 
	 * @param host
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getHttpRequestContent(String host, Map<String, String> params) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(host);

		if (null != params) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator it = params.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		}

		HttpResponse response = httpclient.execute(httpost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(in, HTTP.UTF_8);
		BufferedReader read = new BufferedReader(inputStreamReader);
		String responseContent = "";
		String inputLine = "";
		while ((inputLine = read.readLine()) != null) {
			responseContent += inputLine;
		}
		inputStreamReader.close();
		read.close();
		httpclient.getConnectionManager().shutdown();
		return responseContent;
	}
}
