package com.robmcguinness.stateless.utils;

import java.util.regex.Pattern;

import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;

public class Validation extends PatternValidator {

	private String key;

	public Validation(MetaPattern pattern) {
		super(pattern);
	}

	public Validation(Pattern pattern) {
		super(pattern);
	}

	public Validation(String pattern, int flags) {
		super(pattern, flags);
	}

	public Validation(String pattern) {
		super(pattern);
	}

	public Validation setKey(String key) {
		this.key = key;
		return this;
	}

	public String getKey() {
		return key;
	}

	@Override
	protected ValidationError decorate(ValidationError error, IValidatable<String> validatable) {

		if (!Strings.isEmpty(getKey())) {

			error.addKey(getKey());
		}

		return error;
	}

}
