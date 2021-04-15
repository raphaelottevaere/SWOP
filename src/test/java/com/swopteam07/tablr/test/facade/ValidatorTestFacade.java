package com.swopteam07.tablr.test.facade;

import com.swopteam07.tablr.model.validator.IntValidator;
import com.swopteam07.tablr.model.validator.MailValidator;
import com.swopteam07.tablr.model.validator.StringValidator;
import com.swopteam07.tablr.model.validator.Validator;

/**
 * Interface for easier validator tests
 * @author rapha
 *
 */
public interface ValidatorTestFacade
{
	static Validator createMailValidator()
	{
		return new MailValidator();
	}

	static Validator createListValidator()
	{
		return new StringValidator();
	}

	static Validator createIntValidator()
	{
		return new IntValidator();
	}
}
