package io.sphere.sdk.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link UrlBuilder}.
 */
public class UrlBuilderTest {
    private final UriTemplate uriTemplate = UriTemplate.of("/password-token={token}");

    @Test
    public void buildUri() {
        final UrlBuilder builder = UrlBuilder.of(uriTemplate)
                .addUriParameter("token", "myToken");

        final String url = builder.build();
        assertThat(url).isEqualTo("/password-token=myToken");
    }

    @Test
    public void buildUriEcoded() {
        final UrlBuilder builder = UrlBuilder.of(uriTemplate)
                .addUriParameter("token", "öüä");

        final String url = builder.build();
        assertThat(url).isEqualTo("/password-token=%C3%B6%C3%BC%C3%A4");
    }

    @Test
    public void buildUriWithOneParameter() {
        final UrlBuilder builder = UrlBuilder.of(uriTemplate)
                .addUriParameter("myToken");

        final String url = builder.build();
        assertThat(url).isEqualTo("/password-token=myToken");
    }
}
