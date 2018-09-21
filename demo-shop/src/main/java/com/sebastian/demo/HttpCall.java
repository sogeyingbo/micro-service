package com.sebastian.demo;

import java.util.Map;

public interface HttpCall {
	public String getUrlPrefix();
	public String get(HttpRequest request) throws Exception ;
	public byte[] getBytes(HttpRequest request) throws Exception;
	public String post(HttpRequest payload) throws Exception;

	public static  class HttpRequest {

		public String path;
		public String sessionId;
		public Map<String,String>  params;
		public Object data;
		
	}
	
	
	

}
