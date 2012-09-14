package de.commercetools.internal;

import de.commercetools.sphere.client.oauth.ClientCredentials;

/** Creates instances of {@link RequestHolder}. Allows for mocking in tests. */
public interface RequestFactory {
    <T> RequestHolder<T> createGet(String url, ClientCredentials credentials);
    <T> RequestHolder<T> createPost(String url, ClientCredentials credentials);
}
