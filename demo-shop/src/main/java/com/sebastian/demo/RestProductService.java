package com.sebastian.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;


@RestController
@RequestMapping("/product")
public class RestProductService implements ProductService {

	private static Gson gson = new Gson();
	
	@Override
	 @ResponseBody 
	 @RequestMapping(value = "/get/products",method = RequestMethod.GET)
	 public List<DemoProduct> getProducts(@RequestParam String color, @RequestParam Integer howMany) throws Exception {
			ProductService factory = new ProductFactoryImpl();
		Color actualColor =Color.valueOf(color.toUpperCase());
		return factory.getProducts(actualColor.name(),  howMany);
		

 }
	
}
