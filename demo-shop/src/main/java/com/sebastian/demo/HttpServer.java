package com.sebastian.demo;


public interface HttpServer {
 void start() throws Exception;
 void start(int port) throws Exception;
 void stop()  throws Exception;
 
}
