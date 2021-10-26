package com.acme.mytrader.strategy;

import com.acme.mytrader.price.BuyPriceListenerImpl;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.strategy.TradingStrategy.OpsIdentifier;
import com.acme.mytrader.strategy.TradingStrategy.SecuritiesEnum;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import com.acme.mytrader.price.SellPriceListenerImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class TradingStrategyTest{
	
	private TradingStrategy tradingStrategy;
	private ArgumentCaptor<String> securityCap;
	private ArgumentCaptor<Double> priceCap;
	private ArgumentCaptor<Double> thresholdCap;
	private ArgumentCaptor<Integer> volumeCap;
	private ArgumentCaptor<OpsIdentifier> opsIdentifierCap;
	
	@Before
	public void setup(){	

		tradingStrategy = Mockito.spy(new TradingStrategy());
		securityCap = ArgumentCaptor.forClass(String.class);
		priceCap = ArgumentCaptor.forClass(Double.class);
		thresholdCap = ArgumentCaptor.forClass(Double.class);
		volumeCap = ArgumentCaptor.forClass(Integer.class);
		opsIdentifierCap = ArgumentCaptor.forClass(OpsIdentifier.class);
	}

	@Test
	public void testAutoBuy_whenThresholdMetFor_IBMSecurity() {

		
		PriceListener priceListener = Mockito.mock(BuyPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());
		
		double threshold = 55.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(securityCap.capture(), priceCap.capture());
	    tradingStrategy.placeAutoBuyTrade(SecuritiesEnum.IBM.name(), threshold, 100);
	    
		verify(priceListener, atLeast(1)).priceUpdate(securityCap.capture(), priceCap.capture());
	    verify(tradingStrategy, times(0)).printMaximumPollReached();
		assertEquals(SecuritiesEnum.IBM.name(), securityCap.getValue());
		assertTrue(priceCap.getValue() < threshold);
		assertEquals(Integer.valueOf(100), volumeCap.getValue());
	}

	@Test
	public void testAutoBuy_whenThresholdMetFor_CognizantSecurity() {
		
		PriceListener priceListener = Mockito.mock(BuyPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());
		
		double threshold = 8000.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(anyString(), anyDouble());
	    tradingStrategy.placeAutoBuyTrade(SecuritiesEnum.COGNIZANT.name(), threshold, 758);
	    
		verify(priceListener, atLeast(1)).priceUpdate(securityCap.capture(), priceCap.capture());
	    verify(tradingStrategy, times(0)).printMaximumPollReached();
		assertEquals(SecuritiesEnum.COGNIZANT.name(), securityCap.getValue());
		assertTrue(priceCap.getValue() < threshold);
		assertEquals(Integer.valueOf(758), volumeCap.getValue());
	}
	
	@Test
	public void testAutoBuy_WhenInvalidSecurityPassed() {
		
		PriceListener priceListener = Mockito.mock(BuyPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());
		
		double threshold = 8000.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(anyString(), anyDouble());
	    tradingStrategy.placeAutoBuyTrade("INVALID_SECURITY_NAME", threshold, 758);
	    
	    verify(tradingStrategy, times(1)).printMaximumPollReached();
		verify(priceListener, atLeast(100)).priceUpdate(securityCap.capture(), priceCap.capture());
	}

	@Test
	public void testAutoSell_whenThresholdMetFor_ORACLESecurity() {

		PriceListener priceListener = Mockito.mock(SellPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());

		double threshold = 5000.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(anyString(), anyDouble());
	    tradingStrategy.placeAutoSellTrade(SecuritiesEnum.ORACLE.name(), threshold, 98);
	    
		verify(priceListener, atLeast(1)).priceUpdate(securityCap.capture(), priceCap.capture());
	    verify(tradingStrategy, times(0)).printMaximumPollReached();
		assertEquals(SecuritiesEnum.ORACLE.name(), securityCap.getValue());
		assertTrue(priceCap.getValue() > threshold);
		assertEquals(Integer.valueOf(98), volumeCap.getValue());
	}

	@Test
	public void testAutoSell_whenThresholdMetFor_BarclaysSecurity() {

		PriceListener priceListener = Mockito.mock(SellPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());

		double threshold = 10000.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(anyString(), anyDouble());
	    tradingStrategy.placeAutoSellTrade(SecuritiesEnum.BARCLAYS.name(), threshold, 248);
	    
		verify(priceListener, atLeast(1)).priceUpdate(securityCap.capture(), priceCap.capture());
	    verify(tradingStrategy, times(0)).printMaximumPollReached();
		assertEquals(SecuritiesEnum.BARCLAYS.name(), securityCap.getValue());
		assertTrue(priceCap.getValue() > threshold);
		assertEquals(Integer.valueOf(248), volumeCap.getValue());
	}
	
	@Test
	public void testAutoSell_WhenInvalidSecurityPassed() {
		
		PriceListener priceListener = Mockito.mock(SellPriceListenerImpl.class);	
		Mockito.doReturn(priceListener).when(tradingStrategy).getPriceListener(securityCap.capture(), thresholdCap.capture(), 
				volumeCap.capture(), opsIdentifierCap.capture());
		
		double threshold = 845.0;	
	    Mockito.doNothing().when(priceListener).priceUpdate(anyString(), anyDouble());
	    tradingStrategy.placeAutoBuyTrade("INVALID_SECURITY_NAME", threshold, 758);
	    
	    verify(tradingStrategy, times(1)).printMaximumPollReached();
		verify(priceListener, atLeast(100)).priceUpdate(securityCap.capture(), priceCap.capture());
	}
}
