package com.acme.mytrader.price;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.acme.mytrader.price.BuyPriceListenerImpl;
import com.acme.mytrader.strategy.TradingStrategy.SecuritiesEnum;
import org.junit.Test;
import org.mockito.Mockito;

public class BuyPriceListenerImplTest {

	@Test
	public void testBuySuccess_whenThresholdMet() {

		String security=SecuritiesEnum.IBM.name();
		double currentPrice=400.0, threshold=845.0;
		int volumeToPurchase=42;
		
		BuyPriceListenerImpl buyPriceListener = Mockito.spy(new BuyPriceListenerImpl(security, threshold, volumeToPurchase));
		buyPriceListener.priceUpdate(security, currentPrice);
		
	    verify(buyPriceListener, times(1)).printBuySuccessfull(security, currentPrice);
		assertEquals(security, buyPriceListener.getSecurity());
		assertEquals(volumeToPurchase, buyPriceListener.getVolumeToPurchase());
	}

	@Test
	public void testBuyUnSuccess_whenThresholdDoesNotMet() {

		String security=SecuritiesEnum.IBM.name();
		double currentPrice=1200.0, threshold=845.0;
		int volumeToPurchase=42;
		
		BuyPriceListenerImpl buyPriceListener = Mockito.spy(new BuyPriceListenerImpl(security, threshold, volumeToPurchase));
		buyPriceListener.priceUpdate(security, currentPrice);
		
	    verify(buyPriceListener, times(0)).printBuySuccessfull(security, currentPrice);
		assertEquals(security, buyPriceListener.getSecurity());
		assertEquals(volumeToPurchase, buyPriceListener.getVolumeToPurchase());
	}

	@Test
	public void testBuyUnSuccess_whenDifferentSecurity() {

		String security=SecuritiesEnum.IBM.name();
		double currentPrice=1200.0, threshold=845.0;
		int volumeToPurchase=42;
		
		BuyPriceListenerImpl buyPriceListener = Mockito.spy(new BuyPriceListenerImpl(security, threshold, volumeToPurchase));
		buyPriceListener.priceUpdate("SOME_OTHER_SECURITY", currentPrice);
		
	    verify(buyPriceListener, times(0)).printBuySuccessfull(security, currentPrice);
		assertEquals(security, buyPriceListener.getSecurity());
		assertEquals(volumeToPurchase, buyPriceListener.getVolumeToPurchase());
	}
}
