package com.acme.mytrader.price;

public class SellPriceListenerImpl implements PriceListener {

	private final String security;
	private final double threshold;
	private final int volumeToPurchase;

	public SellPriceListenerImpl(String security, double threshold, int volumeToPurchase) {
		this.security = security;
		this.threshold = threshold;
		this.volumeToPurchase = volumeToPurchase;
	}

	@Override
	public void priceUpdate(String security, double price) {
		if(security.equals(this.security) && price>threshold){
			printSellSuccessfull(security, price);
		}
	}
	
	public void printSellSuccessfull(String security, double price){
		System.out.println("Successfully executed Sell Trade for "+ "Security "+ security + " at a Price of " + price + 
				" for a Volume of " + volumeToPurchase);
	}

	public String getSecurity() {
		return security;
	}

	public double getThreshold() {
		return threshold;
	}

	public int getVolumeToPurchase() {
		return volumeToPurchase;
	}
}
