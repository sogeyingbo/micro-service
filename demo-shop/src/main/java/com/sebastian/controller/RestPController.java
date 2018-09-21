package com.sebastian.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sebastian.demo.DemoProduct;
import com.sebastian.demo.ProductService;
import com.sebastian.demo.RestProductClient;


@RestController
@RequestMapping("/controller")
public class RestPController {

	private static Gson gson = new Gson();
	
	 @ResponseBody 
	 @RequestMapping(value = "/get/products/{color}/{howmany}",method = RequestMethod.GET)
	 public String getProducts(@PathVariable String color, @PathVariable Integer howmany) {
		 ProductPage result = new ProductPage();
		 try{
			 ProductService client = new RestProductClient("http://localhost:8080");
			 List<DemoProduct> products = client.getProducts(color,  howmany);
			 result.products = products;
			 result.status = ControllerResult.Success; 
		}catch(Exception ex) {

			 result.status = ControllerResult.Error;
			 result.errorMessage = ex.getLocalizedMessage();
		}
		
		return gson.toJson(result);
 }
	 
class ProductPage {
	ControllerResult status;
	List<DemoProduct> products;
	String errorMessage;
	
}
	
}
