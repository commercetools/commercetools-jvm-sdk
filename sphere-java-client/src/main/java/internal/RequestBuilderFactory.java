package de.commercetools.internal;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestBuilder;

/** Creates concrete implementations of {@link RequestBuilder}. */
public interface RequestBuilderFactory {
    <T> RequestBuilder<T> create(String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef);
}
