package com.sebastian.demo;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.eclipse.jetty.server.SessionIdManager;


public class EmbeddedHttpServer implements HttpServer {
	private static final int DEFAULT_HTTP_PORT = 8080;
	private static  final String SPRING_WEB_CONFIG_PACKAGE = "com.sebastian.endpoint";
	   
    private   String CONTEXT_PATH = "/*";
    private   String SPRING_XML_CONFIG ="endpoint-beans.xml";
    
 	private  EmbeddedJetty server;
 	private boolean started =false;


	 public static void main(final  String[] args ) throws Exception {
		 EmbeddedHttpServer httpServer=    new EmbeddedHttpServer();
		 httpServer.start(args);
		 httpServer.stop();
		 httpServer.start(8090);
		 httpServer.stop();
		 httpServer.start();
		 httpServer.stop();	       
		}
	 
	 public EmbeddedHttpServer() throws Exception{
		 server =  new EmbeddedJetty();
			
	 }
	 
	 protected EmbeddedHttpServer(String CONTEXT_PATH,String SPRING_XML_CONFIG) throws Exception
	 {
		 this.CONTEXT_PATH = CONTEXT_PATH;
		 this.SPRING_XML_CONFIG = SPRING_XML_CONFIG;
		 server =  new EmbeddedJetty();
	 }
	
	 protected void addSessionSupport(){
		 server.addSessionSupport();
	 }
	 
	 protected void addRequestLog() {
		 server.addRequestLog();
	 }
	 
	 protected void addCorsSupport(String origins){
	     	server.addCorsSupport(origins);
	    }
	 
	 protected void addSslSupport(String sslPort, String kStoreDir, String kStorePasswd, String kManagerPasswd) {
		 server.addSslSupport(Integer.parseInt(sslPort), kStoreDir, kStorePasswd, kManagerPasswd);
	 }

	  WebApplicationContext getWebContext(){
		return server.ctx;
	 }
	 
	  ServletContextHandler getServletContextHandler(){
			return server.ctxHandler;
		 }
	 
	 public void start() throws Exception{
		 start(DEFAULT_HTTP_PORT);
	 }
	 
	 public void start(String[] args) throws Exception{
		 start(getPortFromArgs(args));
	 }

		private  int getPortFromArgs(String[] args) {
	        if (args.length > 0) {
	            try {
	                return Integer.valueOf(args[0]);
	            } catch (NumberFormatException ignore) {
	            }
	        }
	        return DEFAULT_HTTP_PORT;
	    }

	 
	 public void start(int port) throws Exception{
		 if(started)return;
		 server.start(port);
		 server.addShutdownHook();
		 started = true;
	 }
	

	 public void stop() throws Exception{
		 server.stop();
		 started = false;
	 }
	
	private  class EmbeddedJetty {
		
		private  Server server;
		private  WebApplicationContext ctx;
		private ServletContextHandler ctxHandler;
		private boolean sessionSupport = false;
		private boolean corsSupport = false;
		private boolean requestLog = false;
		private boolean sslSupport = false;
		
		private String origins;
		
		private int sslPort;
		private String kStorePasswd;
		private String kManagerPasswd;
		private String kStoreDir;
		
		public EmbeddedJetty() throws Exception{
			ctx = getContext();
			 ctxHandler = getServletContextHandler(ctx);
		
		}
		
		
	    public void start(int port) throws Exception {
	    	if( server!=null && server.isStarted())server.stop();
	    	
	     	HandlerCollection handlers = new HandlerCollection();
	    	handlers.addHandler(ctxHandler);
	    	handlers.addHandler(new DefaultHandler());
	    	
	        server = new Server(port);
	        if(sessionSupport) 
		    	   addSessionSupport(server);
	        if(corsSupport){
	           addCorsSupportImpl();	        }
	        if(requestLog) {
	        	RequestLogHandler requestLogHandler = addRequestLogImpl();
	        	handlers.addHandler(requestLogHandler);
	        }
	        if(sslSupport) 
	        	addSslSupport(server);
	        server.setHandler(handlers);
	    	server.start();
	      
	    	
	    }
	    
	    private void addSessionSupport(){
	    	sessionSupport = true;
	    }
	    
	    private void addCorsSupport(String origins){
	     	corsSupport = true;
	     	this.origins = origins;
	    }

	    private void addSslSupport(Integer sslPort, String kStoreDir, String kStorePasswd, String kManagerPasswd) {
	    	sslSupport = true;
	    	this.sslPort = sslPort;
	    	this.kStoreDir = kStoreDir;
	    	this.kStorePasswd = kStorePasswd;
	    	this.kManagerPasswd = kManagerPasswd;
	    }
	    
	    private void addSessionSupport(Server server){
	    	
	    	SessionIdManager manager = new DefaultSessionIdManager(server);
	    	server.setSessionIdManager(manager);
	    	SessionHandler handler =  new SessionHandler();
	    	handler.setSessionIdManager(manager);
	    	ctxHandler.setSessionHandler(handler);
	    	
	    }
	    
	    private void addSslSupport(Server server){
	    	  HttpConfiguration https = new HttpConfiguration();
	    	  https.addCustomizer(new SecureRequestCustomizer());
	    	  SslContextFactory sslContextFactory = new SslContextFactory();
	    	 sslContextFactory.setKeyStorePath(kStoreDir+"/keystore.jks");
	    	 
	    	 sslContextFactory.setKeyStorePassword(kStorePasswd);
	    	 sslContextFactory.setKeyManagerPassword(kManagerPasswd);
	    	 ServerConnector sslConnector = new ServerConnector(server,
	    	         new SslConnectionFactory(sslContextFactory, "http/1.1"),
	    	         new HttpConnectionFactory(https));

	    	 sslConnector.setPort(sslPort);
	    	 server.addConnector(sslConnector);
	    }
	    
	    private void addCorsSupportImpl() {
	    	 FilterHolder holder = new FilterHolder(new CrossOriginFilter());
	            holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, origins);
	            holder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_CREDENTIALS_HEADER, "true");
	            holder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER, "OPTIONS,GET,POST,ALLOW");
	            holder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_MAX_AGE_HEADER, "4800");
	  	       ctxHandler.addFilter(holder, "/*", EnumSet.of(DispatcherType.REQUEST)); 
	        	//ctxHandler.setInitParameter("dispatchOptionsRequest", "true");

	    }
	    
	    private void addRequestLog() {
	    	requestLog = true;
	    }
	    
	    
	    private RequestLogHandler addRequestLogImpl() {
	    	RequestLogHandler requestLogHandler = new RequestLogHandler();
	    	NCSARequestLog requestLog = new NCSARequestLog("./logs/jetty-yyyy_mm_dd.request.log");
	    	requestLog.setRetainDays(90);
	    	requestLog.setAppend(true);
	    	requestLog.setExtended(false);
	    	requestLog.setLogTimeZone("GMT");
	    	requestLogHandler.setRequestLog(requestLog);
	    	
	    	return requestLogHandler;
	    }
	    
	    private void addShutdownHook(){
	    	   Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	                @Override
	                public void run() {
	                    try {
							
	                    	if(server!=null && server.isStarted())
	                    		stop();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }));
	    }
	    
	    public void stop() throws Exception{
	    	if(server!=null)
	    		server.stop();
	    	server=null;
	    }
	    
	
	    
	    private  ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
	        ServletContextHandler contextHandler = new ServletContextHandler();
	        contextHandler.setErrorHandler(null);
	        contextHandler.setContextPath(CONTEXT_PATH);
	        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)),CONTEXT_PATH);
	        
	       if(SPRING_XML_CONFIG!=null){
	    	   contextHandler.addEventListener(new ContextLoaderListener());
	    	   contextHandler.setInitParameter("contextConfigLocation", "classpath*:**/"+SPRING_XML_CONFIG);
	           contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
	       }

	        return contextHandler;
	    }

	    private  WebApplicationContext getContext() {
	    	
	    	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	    	context.setConfigLocation(SPRING_WEB_CONFIG_PACKAGE);
	    	return context;
	    	
	    }

	}

	}
