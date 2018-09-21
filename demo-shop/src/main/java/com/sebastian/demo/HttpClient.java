package com.sebastian.demo;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public  class HttpClient implements HttpCall {


	private String URL_PREFIX;
	
	private RestTemplate restTemplate=new RestTemplate();

	HttpClient(){
	
	}
	 
	public HttpClient(String urlPrefix ){
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		URL_PREFIX=urlPrefix;
		
	}
	
	public String getUrlPrefix(){
		return URL_PREFIX;
	}
	
	
	
	
	@Override
	public String get(HttpRequest request)  throws Exception{
		// TODO Auto-generated method stub
		
	
		RequestEntity<?> get;
		ResponseEntity<String> response;
		
			StringBuffer path =new StringBuffer();			
			path.append(request.path);
			
			if( request.params!=null && request.params.size()>0){
				path.append("?");
				
				for(Object key: request.params.keySet()){
					path.append(key+"="+URLEncoder.encode(request.params.get(key),Charset.defaultCharset().name())+"&");
				}
			}
			
		get  = RequestEntity.get(new URI(URL_PREFIX+path.toString())).accept(MediaType.APPLICATION_JSON).build();
		response =restTemplate.exchange(get, String.class);
		
		
		return  response.getBody();
	
	}
	
	@Override 
	public byte[] getBytes(HttpRequest request) throws Exception
	{

		RequestEntity<?> get;	
			StringBuffer path =new StringBuffer();			
			path.append(request.path);
			
			if( request.params!=null && request.params.size()>0){
				path.append("?");
				
				for(Object key: request.params.keySet()){
					path.append(key+"="+URLEncoder.encode(request.params.get(key),Charset.defaultCharset().name())+"&");
				}
			}
				
		return (byte[]) restTemplate.getForObject(new URI(URL_PREFIX+path.toString()), byte[].class);
	}
	
	
	
	@Override
	public String post(HttpRequest request) throws Exception{
		// TODO Auto-generated method stub
		RequestEntity<?> post;
		ResponseEntity<String> response = null;
		
		StringBuffer path =new StringBuffer();			
		path.append((String)request.path);
		
		if(request.params!=null && request.params.size()>0){
			path.append("?");
			for(String key: request.params.keySet()){
				path.append(key+"="+URLEncoder.encode(request.params.get(key),"UTF-8")+"&");
			}
		}
		
		
		Object data = request.data;
		post  = RequestEntity.post(new URI(URL_PREFIX+path)).accept(MediaType.APPLICATION_JSON).body(data);
			response =restTemplate.exchange(post, String.class);
							
		return response.getBody();
			
	}

}
