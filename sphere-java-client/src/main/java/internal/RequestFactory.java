package de.commercetools.internal;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.RequestHolder;

/** Creates instances of {@link RequestHolder}. Allows for mocking in tests. */
public interface RequestFactory {
    <T> RequestHolder<T> createGet(String url, ClientCredentials credentials);
    <T> RequestHolder<T> createPost(String url, ClientCredentials credentials);
}
