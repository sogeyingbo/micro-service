package com.sebastian.demo;

class DemoProductImpl implements DemoProduct {
	public DemoProductImpl() {}
	public DemoProductImpl(DemoProduct prod) {
		this.price = prod.getPrice();
		this.color = prod.getColor();
		this.name  = prod.getName();
	}
	
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
	private Color color;
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	private Double price;
	@Override
	public Double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
		
	}
}
