package com.shoppingws.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public enum BeanValidator {

	Instance {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		@Override
		public Validator getValidator() {
			return this.factory.getValidator();
		}

	};

	public abstract Validator getValidator();

}
