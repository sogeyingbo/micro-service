package com.sebastian.demo;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RestProductClient extends HttpClient implements ProductService {
	public static final String QUERY_PRODUCTS_URL = "/product/get/products";

	private Gson gson;
	
	public RestProductClient(String URL_PREFIX){
		super(URL_PREFIX);
		gson=new Gson();
		
	}
	
	@Override
	public List<DemoProduct> getProducts(String color, Integer howMany) throws Exception {
		// TODO Auto-generated method stub
		HttpCall.HttpRequest request = new HttpCall.HttpRequest();
		request.path = QUERY_PRODUCTS_URL;
		Map<String, String> params = new HashMap<>();
		params.put("color",color);
		params.put("howMany",howMany.toString());
		request.params = params;
		
		Exception error = null;
		String json = null;
		List<DemoProduct> prods = new ArrayList<>();
		
		try{
		 json = get (request);
		 prods  =  gson.fromJson(json, new TypeToken<List<DemoProductImpl>>(){}.getType());
		}catch(Exception e){
			if(e instanceof com.google.gson.JsonSyntaxException) {
				error = (new Gson()).fromJson(json, Exception.class);
			} else {
				error =e;
			}
		}
		
		if(prods == null){
			if(error == null) error = (new Gson()).fromJson(json, Exception.class);
			 if(error!=null) throw new Exception(error.getLocalizedMessage());
		}

		return prods;
	}

}
