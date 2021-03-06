package com.robmcguinness.stateless;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.UrlEncoder;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralize algorithms that are shared.
 * 
 * @author jfk
 * 
 */
final class StatelessEncoder {

	private static final Logger logger = LoggerFactory.getLogger(StatelessEncoder.class);

	/**
	 * Merges the query parameters of the url with the named parameters from the {@link PageParameters}. The page parameters override the query
	 * parameters.
	 * 
	 * @param url
	 *          the url with the original parameters
	 * @param params
	 *          the page parameters to merge
	 * @return an Url with merged parameters
	 */
	static Url mergeParameters(final Url url, final PageParameters params) {

		if (params == null) {
			return url;
		}

		Charset charset = url.getCharset();

		logger.debug("merging url[{}] with parameters[{}]", url.toString(), params);

		Url mergedUrl = Url.parse(url.toString(), charset);

		UrlEncoder urlEncoder = UrlEncoder.QUERY_INSTANCE;

		Set<String> setParameters = new HashSet<String>();

		for (NamedPair pair : params.getAllNamed()) {
			String key = urlEncoder.encode(pair.getKey(), charset);
			String value = urlEncoder.encode(pair.getValue(), charset);

			if (setParameters.contains(key)) {
				mergedUrl.addQueryParameter(key, value);
			} else {
				mergedUrl.setQueryParameter(key, value);
				setParameters.add(key);
			}
		}

		return mergedUrl;
	}

	private StatelessEncoder() {
		// forbid instantiation
	}
}
