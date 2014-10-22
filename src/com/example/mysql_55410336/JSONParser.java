package com.example.mysql_55410336;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.transform.Result;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	private static InputStream is = null;
	private static JSONObject jsonObject = null;
	private static String result = null;
	
	public JSONObject makeHttpRequest(String url, String method,
		List<NameValuePair> params){
		
		try {
			if (method == "POST"){
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = client.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				
			}else if(method == "GET"){
				
				DefaultHttpClient client = new DefaultHttpClient();
				String paraString = URLEncodedUtils.format(params, "UTF-8");
				url += "?" + paraString;
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = client.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(ClientProtocolException e){
		e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null){
				builder.append(line+"\n");
				
			}
				
			is.close();
			result = builder.toString();
			
		}catch (Exception e){
			Log.e("Buffer Erro Parson", "Error Converting result " + e.toString());
	}try{
		jsonObject = new JSONObject(result);
	}catch (JSONException e){
		Log.e("JSON Parser", "Error Parsing data" + e.toString());
	}
			return jsonObject;
	}

}
