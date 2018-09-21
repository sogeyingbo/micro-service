package com.sebastian.demo;

import java.util.List;

import com.sebastian.endpoint.ProductTestAppServer;

public class Main {
	public static void main(String[] args) throws Exception {

		HttpServer appServer = new ProductTestAppServer("demo-shop-beans.xml");
		 appServer.start(8080);
			 
	}
}
