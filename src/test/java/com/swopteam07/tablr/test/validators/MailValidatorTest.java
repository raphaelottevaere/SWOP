package com.swopteam07.tablr.test.validators;

import com.swopteam07.tablr.model.validator.Validator;
import com.swopteam07.tablr.test.facade.ValidatorTestFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validator for basic mails tests
 * @author rapha
 *
 */
public class MailValidatorTest
{

	public boolean testMailValidator(Object obj)
	{
		//!! http://www.ietf.org/rfc/rfc2822.txt !! WARN
		//https://blogs.msdn.microsoft.com/testing123/2009/02/06/email-address-test-cases/
		Validator mailValidator = ValidatorTestFacade.createMailValidator();
		return mailValidator.isValid(obj);
	}

	@Test
	public void TestMailCase1()
	{
		assertTrue(testMailValidator("email@domain.com"));
	}

	@Test
	public void TestMailCase2()
	{
		assertTrue(testMailValidator("firstname.lastname@domain.com"));
	}

	@Test
	public void TestMailCase3()
	{
		assertTrue(testMailValidator("email@subdomain.domain.com"));
	}

	@Test
	public void TestMailCase4()
	{
		assertTrue(testMailValidator("firstname+lastname@domain.com"));
	}

	@Test
	public void TestMailCase5()
	{
		assertTrue(testMailValidator("email@123.123.123.123"));
	}

	@Test
	public void TestMailCase6()
	{
		assertTrue(testMailValidator("email@[123.123.123.123]"));
	}

	@Test
	public void TestMailCase7()
	{
		assertTrue(testMailValidator("\"email\"@domain.com"));
	}

	@Test
	public void TestMailCase8()
	{
		assertTrue(testMailValidator("1234567890@domain.com"));
	}

	@Test
	public void TestMailCase9()
	{
		assertTrue(testMailValidator("email@domain-one.com"));
	}

	@Test
	public void TestMailCase10()
	{
		assertTrue(testMailValidator("_______@domain.com"));
	}

	@Test
	public void TestMailCase11()
	{
		assertTrue(testMailValidator("email@domain.name"));
	}

	@Test
	public void TestMailCase12()
	{
		assertTrue(testMailValidator("email@domain.co.jp"));
	}

	@Test
	public void TestMailCase13()
	{
		assertTrue(testMailValidator("firstname-lastname@domain.com"));
	}

	@Test
	public void TestMailCase14()
	{
		assertTrue(!testMailValidator("plainaddress"));
	}

	@Test
	public void TestMailCase15()
	{
		assertTrue(!testMailValidator("#@%^%#$@#$@#.com"));
	}

	@Test
	public void TestMailCase16()
	{
        assertTrue(testMailValidator("@domain.com"));
	}

	@Test
	public void TestMailCase17()
	{
        assertTrue(testMailValidator("Joe Smith <email@domain.com>"));
	}

	@Test
	public void TestMailCase18()
	{
		assertTrue(!testMailValidator("email.domain.com"));
	}

	@Test
	public void TestMailCase19()
	{
		assertTrue(!testMailValidator("email@domain@domain.com"));
	}

	@Test
	public void TestMailCase20()
	{
        assertTrue(testMailValidator(".email@domain.com"));
	}

	@Test
	public void TestMailCase21()
	{
        assertTrue(testMailValidator("email.@domain.com"));
	}

	@Test
	public void TestMailCase22()
	{
        assertTrue(testMailValidator("email..email@domain.com "));
	}

	@Test
	public void TestMailCase23()
	{
        assertTrue(testMailValidator("あいうえお@domain.com"));
	}

	@Test
	public void TestMailCase24()
	{
        assertTrue(testMailValidator("email@domain.com (Joe Smith)"));
	}

	@Test
	public void TestMailCase25()
	{
        assertTrue(testMailValidator("email@domain"));
	}

	@Test
	public void TestMailCase26()
	{
        assertTrue(testMailValidator("email@-domain.com"));
	}

	@Test
	public void TestMailCase27()
	{
        assertTrue(testMailValidator("email@domain.web")); //not valid tld
	}

	@Test
	public void TestMailCase28()
	{
        assertTrue(testMailValidator("email@111.222.333.44444"));
	}

	@Test
	public void TestMailCase29()
	{
        assertTrue(testMailValidator("email@domain..com"));
	}

}
