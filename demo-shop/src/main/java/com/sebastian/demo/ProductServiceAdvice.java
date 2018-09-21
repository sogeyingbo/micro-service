package com.sebastian.demo;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

@ControllerAdvice
public class ProductServiceAdvice {
private static Gson gson = new Gson();

@ExceptionHandler({ Exception.class })
@ResponseBody String handlProductError(Exception ex){
		 return gson.toJson(ex);
}


}
