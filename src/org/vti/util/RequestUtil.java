package org.vti.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RequestUtil {
	
	private static RequestUtil instance = new RequestUtil();

	protected RequestUtil() {
	}

	public static RequestUtil getInstance() {
		return instance;
	}
	
	public String doRequest(String uri) throws Exception{
		
		URL url = new URL(uri);
		
		InputStream inputStream=null;
		
		if (uri.startsWith("http://")) {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.connect();
			connection.setConnectTimeout(10000);
			inputStream=connection.getInputStream();
		
		}else {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		    SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
					}
				}
			}, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier() {
				@Override
				public boolean verify(String string,SSLSession ssls) {
					return true;
				}
			});
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.connect();
			inputStream=connection.getInputStream();
		}
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buf=new StringBuffer();
		String content = "";
		while ((content = reader.readLine()) != null) {
			buf.append(content);
		}
		reader.close();
		
		return buf.toString();
		
	}
	
	
	public InputStream getInputStream(String uri) throws Exception{
		
		URL url = new URL(uri);
		
		InputStream inputStream=null;
		
		if (uri.startsWith("http://")) {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.connect();
			inputStream=connection.getInputStream();
		
		}else {
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		    SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] {
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs, String authType) {
					}
				}
			}, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier() {
				@Override
				public boolean verify(String string,SSLSession ssls) {
					return true;
				}
			});
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);
			connection.connect();
			inputStream=connection.getInputStream();
		}
		
		return inputStream;
		
	}

}
