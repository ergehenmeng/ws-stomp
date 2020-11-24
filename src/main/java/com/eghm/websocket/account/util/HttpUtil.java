package com.eghm.websocket.account.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class HttpUtil{
	
	
	public static String HtmlFrom(String Url,Map<String,String> Parms){
		if(Parms.isEmpty()){
			return  "参数不能为空！";
		}
		String FormString = "<body onLoad=\"document.actform.submit()\">正在处理请稍候.....................<form  id=\"actform\" name=\"actform\" method=\"post\" action=\"" + Url + "\">";
		for (String key : Parms.keySet()){
			//Log.Write("HTML:"+key +":"+Parms.get(key));
			FormString +="<input name=\"" + key + "\" type=\"hidden\" value='" + Parms.get(key) + "'>";
		}
		
        FormString +="</form></body>";
        return FormString; 
	}
	
	public static String RequestForm(String Url,Map<String,String> Parms,String ContentType){		
		if(Parms.isEmpty()){
			return  "参数不能为空！";
		}
		String PostParms = "";
		int PostItemTotal = Parms.keySet().size();
		int Itemp=0;
		for (String key : Parms.keySet()){
			PostParms += key + "="+Parms.get(key);
			Itemp++;
			if(Itemp<PostItemTotal){
				PostParms +="&";
			}
		}
		HttpSendModel httpSendModel = new HttpSendModel(Url + "?" + PostParms);
		Log.Write("【请求全部参数】：" + Url + "?" + PostParms);
		httpSendModel.setMethod(HttpMethod.POST);
		httpSendModel.setContentTypeStr(ContentType);//
		SimpleHttpResponse response = null;
		try {
			response = doRequest(httpSendModel, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return response.getEntityString();

	}
	
	public static SimpleHttpResponse doRequest(HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		// 创建默认的httpClient客户端端
		SimpleHttpClient simpleHttpclient = new SimpleHttpClient();
		try {
			return doRequest(simpleHttpclient, httpSendModel, getCharSet);
		} finally {
			simpleHttpclient.getHttpclient().getConnectionManager().shutdown();
		}

	}

	/**
	 * @param httpclient
	 * @param httpSendModel
	 * @param getCharSet
	 * @return
	 * @throws Exception 
	 */
	public static SimpleHttpResponse doRequest(
			SimpleHttpClient simpleHttpclient, HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		HttpRequestBase httpRequest = buildHttpRequest(httpSendModel);
		
		if (httpSendModel.getUrl().startsWith("https://")) {
			simpleHttpclient.enableSSL();
		}
		try {
			
			HttpResponse response = simpleHttpclient.getHttpclient().execute(
					httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (isRequestSuccess(statusCode)) {
				return new SimpleHttpResponse(statusCode, EntityUtils.toString(
						response.getEntity(), getCharSet), null);
			} else {
				return new SimpleHttpResponse(statusCode, null, response
						.getStatusLine().getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("http请求异常", e);
		}

	}

	/**
	 * @param httpSendModel
	 * @return
	 * @throws Exception 
	 */
	protected static HttpRequestBase buildHttpRequest(
			HttpSendModel httpSendModel) throws Exception {
		HttpRequestBase httpRequest;
		if (httpSendModel.getMethod() == null) {
			throw new Exception("请求方式未设定");
		} else if (httpSendModel.getMethod() == HttpMethod.POST) {

			String url = httpSendModel.getUrl();
			String sendCharSet = httpSendModel.getCharSet();
			List<HttpFormParameter> params = httpSendModel.getParams();			
			Map<String,String> data  = new HashMap<String,String>();
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null && params.size() != 0) {
				for (HttpFormParameter param : params) {
					data.put(param.getName(), param.getValue());//json方式请求
					qparams.add(new BasicNameValuePair(param.getName(), param.getValue()));//key/value方式请求
				}
			}
			
			HttpPost httppost = new HttpPost(url);
			
			try {
				if(httpSendModel.getContentTypeStr().isEmpty()){					
					UrlEncodedFormEntity Str = new UrlEncodedFormEntity(qparams,sendCharSet);
					httppost.setEntity(Str);
				}else{
					StringEntity Str = new StringEntity(JSONObject.fromObject(data).toString(),sendCharSet);
					Str.setContentEncoding(sendCharSet);
					Str.setContentType("application/json");
					httppost.setEntity(Str);
				}
				
			} catch (UnsupportedEncodingException e) {
				throw new Exception("构建post请求参数失败", e);
			}

			httpRequest = httppost;
		} else if (httpSendModel.getMethod() == HttpMethod.GET) {
			HttpGet httpget = new HttpGet(httpSendModel.buildGetRequestUrl());
			httpRequest = httpget;
		} else {
			throw new Exception("请求方式不支持：" + httpSendModel.getMethod());
		}

		return httpRequest;
	}
	
	/**
	 * 请求是否成功
	 * 
	 * @param statusCode
	 * @return
	 */
	public static boolean isRequestSuccess(int statusCode) {
		return statusCode == 200;
	}
	
	
	
	
}