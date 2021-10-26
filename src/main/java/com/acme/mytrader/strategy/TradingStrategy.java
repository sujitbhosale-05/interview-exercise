package com.acme.mytrader.strategy;

import java.util.Random;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.BuyPriceListenerImpl;
import com.acme.mytrader.price.SellPriceListenerImpl;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */

public class TradingStrategy {

	public enum SecuritiesEnum {
		DEUTSCHE, COGNIZANT, IBM, ORACLE, BARCLAYS
	}

	public enum OpsIdentifier {
		BUY, SELL
	}

	public void placeAutoBuyTrade(String security, double priceThreshold, int volume) {
		PriceListener priceListener = this.getPriceListener(security, priceThreshold, volume, OpsIdentifier.BUY);
		checkTradeOperation(priceListener, security, priceThreshold, OpsIdentifier.BUY);
	}

	public void placeAutoSellTrade(String security, double priceThreshold, int volume) {
		PriceListener priceListener = getPriceListener(security, priceThreshold, volume, OpsIdentifier.SELL);
		checkTradeOperation(priceListener, security, priceThreshold, OpsIdentifier.SELL);
	}

	public void checkTradeOperation(PriceListener priceListener, String security, 
			double priceThreshold, OpsIdentifier odpId) {

		boolean isTradeExecuted = false;
		int pollCounter = 0;
		int pollIntervalLimit = 100;
		
		while (!isTradeExecuted && pollCounter < pollIntervalLimit) {
			for (SecuritiesEnum sec : SecuritiesEnum.values()) {
				double currentPrice = getCurrentTradePrice(priceThreshold);
				//Uncomment To see Dynamically getting generated current price of security 
				//System.out.println("Current Price for Security: "+sec.name()+" is: "+currentPrice);
				priceListener.priceUpdate(sec.name(), currentPrice);

				boolean opsCheck = false;
				if(odpId.equals(OpsIdentifier.BUY))
					opsCheck = currentPrice < priceThreshold;
				else if(odpId.equals(OpsIdentifier.SELL))
					opsCheck = currentPrice > priceThreshold;

				if (sec.name().equals(security) && opsCheck) {
					isTradeExecuted = true;
					break;
				}
			}
			++pollCounter;
		}

		if (pollCounter >= pollIntervalLimit) {
			printMaximumPollReached();
		}
	}

	public PriceListener getPriceListener(String security, double priceThreshold, int volume, OpsIdentifier opsId) {
		
		if(OpsIdentifier.BUY.equals(opsId)){
			return new BuyPriceListenerImpl(security, priceThreshold, volume);
		}else if(OpsIdentifier.SELL.equals(opsId)){
			return new SellPriceListenerImpl(security, priceThreshold, volume);
		}
		return null;
	}

	public double getCurrentTradePrice(double priceThreshold) {
		double minRange=priceThreshold>100?priceThreshold-200 : 0.0;
		double maxRange=priceThreshold+200.0;
		return new Random().doubles(minRange, maxRange).findFirst().getAsDouble();
	}
	
	public void printMaximumPollReached(){
		System.out.println("Maximum Poll interval limit reched, No threshold condition reached with Max poll interval limit");
	}
	
	public static void main(String[] args) {
		// Creating Buy Listener for IBM Security
		new TradingStrategy().placeAutoBuyTrade(SecuritiesEnum.IBM.name(), 845.0, 20);

		// Creating Sell Listener for COGNIZANT Security
		new TradingStrategy().placeAutoSellTrade(SecuritiesEnum.COGNIZANT.name(), 984.0, 30);
	}
}
