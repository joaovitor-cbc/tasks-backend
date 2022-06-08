package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void deveRetornarTrueParaDataFutura() {
		LocalDate data = LocalDate.of(2100, 1, 1);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarTrueParaDataPresente() {
		LocalDate data = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarFalseParaDataPassada() {
		LocalDate data = LocalDate.of(2000, 1, 1);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(data));
	}
}
