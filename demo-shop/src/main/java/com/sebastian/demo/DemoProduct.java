package com.sebastian.demo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as=DemoProductImpl.class)
public interface DemoProduct {
	String getName();
	Color getColor();
	Double getPrice();
	
}
