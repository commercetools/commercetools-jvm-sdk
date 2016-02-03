package io.sphere.sdk.http;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class StringHttpRequestBody extends Base implements HttpRequestBody {
    private final String body;

    private StringHttpRequestBody(final String body) {
        this.body = body;
    }

    public static StringHttpRequestBody of(final String body) {
        return new StringHttpRequestBody(body);
    }

    public String getString() {
        return body;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("body", getSecuredBody())
                .toString();
    }

    /**
     * internal method
     * @return body without passwords
     */
    public String getSecuredBody() {
        return tryToFilter(this.body);
    }

    static String tryToFilter(final String input) {
        return input.replaceAll("(\"\\w*([Pp]ass|access_token)\\w*\"):\"[^\"]*\"", "$1:\"**removed from output**\"");
    }
}