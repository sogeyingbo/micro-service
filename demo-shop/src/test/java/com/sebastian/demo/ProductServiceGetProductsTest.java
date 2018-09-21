package com.sebastian.demo;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sebastian.endpoint.ProductTestAppServer;

public class ProductServiceGetProductsTest {

	private static ProductService client;
	private static HttpServer server;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		 server = new ProductTestAppServer("demo-shop-test-beans.xml");
		 server.start(8080);
		 
		  client = new RestProductClient("http://localhost:8080");
	}
	
	@Test
	public void getProducts_GivenValidParams_ShouldPass() throws Exception {

		 List<DemoProduct> prods = client.getProducts(Color.BLUE.name(), 4);
		Assert.assertTrue(prods.size() == 4);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		if(server !=null) {
			server.stop();
		}
	}
}
