package de.commercetools.sphere.client.util;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.oauth.ClientCredentials;

/** Creates concrete implementations of {@link RequestBuilder}. */
public interface RequestBuilderFactory {
    <T> RequestBuilder<T> create(String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef);
}
