package com.sebastian.demo;

import java.util.ArrayList;
import java.util.List;

public class ProductFactoryImpl implements ProductService{

	@Override
	public List<DemoProduct> getProducts(String color, Integer howMany)  throws Exception {
		// TODO Auto-generated method stub
		assert(howMany != null);
		assert(color != null);
		assert(howMany.intValue() >0);
		List<DemoProduct> products = new ArrayList<>();
		
		for(int i =0; i< howMany.intValue(); i++) {
			String name = String.valueOf(System.currentTimeMillis());
			DemoProductImpl prod = new DemoProductImpl();
			prod.setName(name);
			prod.setColor(Color.valueOf(color));
			prod.setPrice(Double.valueOf(System.currentTimeMillis()%1000));
			products.add(prod);
		}
		return products;
	}

}
