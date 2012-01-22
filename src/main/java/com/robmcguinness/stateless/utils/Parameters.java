package com.robmcguinness.stateless.utils;

import java.util.List;

import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.string.StringValue;

public class Parameters<T> {
	
	public final PageParameters encodePageParameters(T bean, String...expressions) {
		PageParameters params = new PageParameters();		
		for(String expression : expressions) {
			Object value = PropertyResolver.getValue(expression, bean);
			params.set(expression, value);
		}
		return params;
	}
	
	/**
	 * Modified slight from ParameterSpec.java class provided by  <a href="http://www.55minutes.com)">55 Minutes</a>
	 * </p> 
	 * 
	 * Use this method in your page constructor to parse the PageParameters. 
	 * </p>
	 *
	 * @param params values will be taken from these PageParameters
	 * @param beanToPopulate Values will be set using appropriate setters on this bean
	 *
	 * @throws AbortWithHttpErrorCodeException with a 404 status code if {@code throw404OnParseError} is {@code true} and a parsing exception occurs. For example, this could happen
	 *             if the bean property for "id" is of type Long, but the parameter value being parsed is not numeric. If {@code throw404OnParseError} is {@code false}, skip past
	 *             properties with parsing errors.
	 */
	public final void parsePageParameters(PageParameters params, T bean, boolean throw404OnParseError) {
		
		List<NamedPair> pairs = params.getAllNamed();

		for (NamedPair pair : pairs) {
			String expression = pair.getKey();
			Object value = pair.getValue();

			if (value != null) {
				try {
					PropertyResolver.setValue(expression, bean, value, null);
				} catch (Exception e) {

					if (!throw404OnParseError)
						throw new AbortWithHttpErrorCodeException(404);
				}
			}

		}

	}
	
	public static String getParam(final PageParameters parameters, final String key) {
		return stringify(getParameter(parameters, key));
	}
	
	public static String getParam(final String key) {
		return stringify(getParameter(key));
	}
	
	public static String getPostParam(String key) {
		return stringify(getPostParameter(key));
	}
	
	public static String getQueryParam(String key) {
		return stringify(getQueryParameter(key));
	}
	
	private static String stringify(StringValue value) {
		if(value.isEmpty() || value.isNull())
			return null;
		return value.toString();
	}
	
	/**
	 * Gets a parameter from the request using the provided {@code key}
	 * 
	 * @param parameters
	 *          {@link PageParameters}
	 * @param key
	 *          the key to a value in the page parameters
	 * @return the parameter or null if key is not matching
	 */
	public static StringValue getParameter(final PageParameters parameters, final String key) {
		return parameters.get(key);
	}
	
	public static StringValue getQueryParameter(String key) {
		 return RequestCycle.get().getRequest().getQueryParameters().getParameterValue(key);
	}
	
	public static StringValue getPostParameter(String key) {
		return RequestCycle.get().getRequest().getPostParameters().getParameterValue(key);
	}
	 
	public static StringValue getParameter(String key) {
		return RequestCycle.get().getRequest().getRequestParameters().getParameterValue(key);
	}
	
	public static IRequestParameters getRequestParameters() {
		return RequestCycle.get().getRequest().getRequestParameters();
	}
	
	
}
