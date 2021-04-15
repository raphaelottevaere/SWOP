package com.swopteam07.tablr.test.validators;

import com.swopteam07.tablr.model.validator.Validator;
import com.swopteam07.tablr.test.facade.ValidatorTestFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validator for basic integers tests
 * @author rapha
 *
 */
public class IntValidatorTest
{
	public boolean testIntValidator(Object obj)
	{
		Validator intValidator = ValidatorTestFacade.createIntValidator();
		return intValidator.isValid(obj);
	}

	@Test
	public void TestIntCase1()
	{
		assertTrue(testIntValidator("0"));//0
	}

	@Test
	public void TestIntCase2()
	{
		assertTrue(testIntValidator("1"));//N
	}

	@Test
	public void TestIntCase4()
	{
		assertTrue(testIntValidator("-1"));//Z
	}

	@Test
	public void TestIntCase5()
	{
		assertFalse(testIntValidator(" -1 "));//space Z
	}

	@Test
	public void TestIntCase6()
	{
		assertFalse(testIntValidator("01"));
	}

	@Test
	public void TestIntCase7()
	{
		assertTrue(!testIntValidator("0.1"));
	}

	@Test
	public void TestIntCase8()
	{
		assertTrue(!testIntValidator("00.1"));
	}

	@Test
	public void TestIntCase9()
	{
		assertTrue(!testIntValidator("0,1"));
	}

	@Test
	public void TestIntCase10()
	{
		assertTrue(!testIntValidator("00,1"));
	}

	@Test
	public void TestIntCase11()
	{
		assertTrue(!testIntValidator(Integer.MAX_VALUE + "0"));
	}

	//objects

	@Test
	public void TestIntCase12()
	{
		assertTrue(testIntValidator(1));
	}

	@Test
	public void TestIntCase13()
	{
		assertTrue(testIntValidator(-1));
	}

	@Test
	public void TestIntCase14()
	{
		assertTrue(!testIntValidator(1.1));
	}

	@Test
	public void TestIntCase15()
	{
		assertTrue(!testIntValidator((char) 1680));
	}

	@Test
	public void TestIntCase16()
	{
		assertTrue(!testIntValidator((char) 2000));
	}

	@Test
	public void TestIntCase17()
	{
		assertTrue(!testIntValidator((char) 2001));
	}

	@Test
	public void TestIntCase18()
	{
		assertTrue(!testIntValidator((char) 2002));
	}

	@Test
	public void TestIntCase19()
	{
		assertTrue(!testIntValidator((char) 5760));
	}

	@Test
	public void TestIntCase20()
	{
		assertTrue(!testIntValidator((char) 8198));
	}

	@Test
	public void TestIntCase21()
	{
		assertTrue(!testIntValidator((char) 8287));
	}

	@Test
	public void TestIntCase22()
	{
		assertTrue(!testIntValidator((char) 12288));
	}

}
