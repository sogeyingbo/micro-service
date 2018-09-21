package com.sebastian.demo;

import java.util.List;

public interface ProductService {
	List<DemoProduct> getProducts(String color, Integer howMany) throws Exception;
}