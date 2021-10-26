package com.acme.mytrader.price;

public class BuyPriceListenerImpl implements PriceListener {

	private final String security;
	private final double threshold;
	private final int volumeToPurchase;

	public BuyPriceListenerImpl(String security, double threshold, int volumeToPurchase) {
		this.security = security;
		this.threshold = threshold;
		this.volumeToPurchase = volumeToPurchase;
	}

	@Override
	public void priceUpdate(String security, double price) {
		if(security.equals(this.security) && price<threshold){
			printBuySuccessfull(security, price);
		}
	}
	
	public void printBuySuccessfull(String security, double price){
		System.out.println("Successfully executed Buy Trade for "+ "Security "+ security + " at a Price of " + price + 
				" for a Volume of " + this.volumeToPurchase);
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
