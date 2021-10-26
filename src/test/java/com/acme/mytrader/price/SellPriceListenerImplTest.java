package com.acme.mytrader.price;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.acme.mytrader.strategy.TradingStrategy.SecuritiesEnum;
import org.junit.Test;
import org.mockito.Mockito;

public class SellPriceListenerImplTest {

	@Test
	public void testSellSuccess_whenThresholdMet() {

		String security=SecuritiesEnum.ORACLE.name();
		double currentPrice=1400.0, threshold=900.0;
		int volumeToPurchase=125;
		
		SellPriceListenerImpl sellPriceListener = Mockito.spy(new SellPriceListenerImpl(security, threshold, volumeToPurchase));
		sellPriceListener.priceUpdate(security, currentPrice);
		
	    verify(sellPriceListener, times(1)).printSellSuccessfull(security, currentPrice);
		assertEquals(security, sellPriceListener.getSecurity());
		assertEquals(volumeToPurchase, sellPriceListener.getVolumeToPurchase());
	}

	@Test
	public void testSellUnSuccess_whenThresholdDoesNotMet() {

		String security=SecuritiesEnum.ORACLE.name();
		double currentPrice=745.0, threshold=1420.0;
		int volumeToPurchase=94;
		
		SellPriceListenerImpl sellPriceListener = Mockito.spy(new SellPriceListenerImpl(security, threshold, volumeToPurchase));
		sellPriceListener.priceUpdate(security, currentPrice);
		
	    verify(sellPriceListener, times(0)).printSellSuccessfull(security, currentPrice);
		assertEquals(security, sellPriceListener.getSecurity());
		assertEquals(volumeToPurchase, sellPriceListener.getVolumeToPurchase());
	}

	@Test
	public void testSellUnSuccess_whenDifferentSecurity() {

		String security=SecuritiesEnum.ORACLE.name();
		double currentPrice=1200.0, threshold=845.0;
		int volumeToPurchase=42;
		
		SellPriceListenerImpl sellPriceListener = Mockito.spy(new SellPriceListenerImpl(security, threshold, volumeToPurchase));
		sellPriceListener.priceUpdate("SOME_OTHER_SECURITY", currentPrice);
		
	    verify(sellPriceListener, times(0)).printSellSuccessfull(security, currentPrice);
		assertEquals(security, sellPriceListener.getSecurity());
		assertEquals(volumeToPurchase, sellPriceListener.getVolumeToPurchase());
	}
}
