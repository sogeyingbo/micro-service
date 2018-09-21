package com.sebastian.endpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sebastian.demo.EmbeddedHttpServer;


public final class ProductTestAppServer extends EmbeddedHttpServer {
	
	public static final int DEFAULT_HTTP_PORT = 8080;
    private static String SPRING_XML_CONFIG ="demo-shop-beans.xml";
   
    public ProductTestAppServer() throws Exception{
    	super("/",SPRING_XML_CONFIG);
     	
    }
    public ProductTestAppServer(String SPRING_XML_CONF) throws Exception{
    	super("/",SPRING_XML_CONF);
    	SPRING_XML_CONFIG = SPRING_XML_CONF;
    }
    
	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start(DEFAULT_HTTP_PORT);
		ApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_XML_CONFIG);

	}

	@Override
	public void start(int port) throws Exception {
		// TODO Auto-generated method stub
		super.start(port);
		ApplicationContext ctx = new ClassPathXmlApplicationContext(SPRING_XML_CONFIG);
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}



	 public static void main( final String[] args ) throws Exception {
		 ProductTestAppServer server = new ProductTestAppServer("product-test-beans.xml");
		 server.start();
			       
		}
	}
