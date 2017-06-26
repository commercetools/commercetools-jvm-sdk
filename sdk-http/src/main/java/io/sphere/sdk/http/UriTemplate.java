package io.sphere.sdk.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the simple string expansion of the
 * URI template specification (https://tools.ietf.org/html/rfc6570).
 */
public final class UriTemplate extends Base {
    private final static Pattern PARAMETER_PATTERN = Pattern.compile("\\{(.*)\\}");
    private final String uriTemplate;
    private final Set<String> parameterNames;

    private UriTemplate(final String uriTemplate) {
        this.uriTemplate = uriTemplate;
        this.parameterNames = extractParameterNames(uriTemplate);
    }

    private Set<String> extractParameterNames(final String uriTemplate) {
        final Matcher matcher = PARAMETER_PATTERN.matcher(uriTemplate);
        final Set<String> result = new HashSet<>();

        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    /**
     * Returns true if this uri template has the given parameter.
     *
     * @param name the parameter name
     * @return true iff. this uri template has the given parameter
     */
    public boolean hasParameter(final String name) {
        return parameterNames.contains(name);
    }

    /**
     * Returns the set of parameter names.
     *
     * @return the parameter names
     */
    public Set<String> getParameterNames() {
        return parameterNames;
    }

    /**
     * Render this uri template filled with the given uri parameter values.
     *
     * @param uriParameterValues map of parameter names and their value
     * @return the url rendered by replacing the parameters with the given values
     */
    public String render(final Map<String, Object> uriParameterValues) {
        final Matcher matcher = PARAMETER_PATTERN.matcher(uriTemplate);
        final StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            final String name = matcher.group(1);
            matcher.appendReplacement(buffer, urlEncode(uriParameterValues.get(name)));
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private static String urlEncode(final Object value) {
        try {
            return URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Creates a new uri template from the given parameter.
     *
     * @param uriTemplate the uri template
     * @return a new uri template
     */
    public static UriTemplate of(final String uriTemplate) {
        return new UriTemplate(uriTemplate);
    }

    @Override
    public String toString() {
        return uriTemplate;
    }
}
