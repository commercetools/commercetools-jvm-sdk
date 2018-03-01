package io.sphere.sdk.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class allows to build an url from a {@link UriTemplate} and a set
 * of uri parameters {@link #addUriParameter(String, Object)}.
 */
public final class UrlBuilder extends Base {
    private final UriTemplate uriTemplate;
    private final Map<String, Object> uriParameterValues = new HashMap<>();

    private UrlBuilder(final UriTemplate uriTemplate) {
        this.uriTemplate = uriTemplate;
    }

    /**
     * If the uri template just contains one parameter, the value can be set
     * without providing the parameters name.
     *
     * @param value the parameter value
     * @return this builder
     */
    public UrlBuilder addUriParameter(final Object value) {
        final Set<String> parameterNames = uriTemplate.getParameterNames();
        if (parameterNames.size() == 1) {
            addUriParameter(parameterNames.iterator().next(), value);
        } else {
            throw new IllegalArgumentException("Uri template has != 1 parameters:" + uriTemplate);
        }
        return this;
    }

    /**
     * Adds the given uri parameter value to this builder.
     *
     * @param name  the parameter name
     * @param value the parameter value
     * @return this builder
     */
    public UrlBuilder addUriParameter(final String name, final Object value) {
        if (uriTemplate.hasParameter(name)) {
            uriParameterValues.put(name, value);
        } else {
            throw new IllegalArgumentException(String.format("Parameter '%s' not definded in uri template '%s'", name, uriTemplate));
        }
        return this;
    }

    /**
     * Builds the url.
     *
     * @return the url
     */
    public String build() {
        return uriTemplate.render(uriParameterValues);
    }

    /**
     * Creates a new builder from the given parameter.
     *
     * @param uriTemplate the uri template
     * @return a new builder
     */
    public static UrlBuilder of(final UriTemplate uriTemplate) {
        return new UrlBuilder(uriTemplate);
    }
}
