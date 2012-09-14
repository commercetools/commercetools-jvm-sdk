package de.commercetools.internal;

import com.ning.http.client.AsyncHttpClient;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import de.commercetools.sphere.client.util.*;

/** Boilerplate for setting up default RequestBuilder (consider using Guice). */
public class RequestBuilderFactoryImpl implements RequestBuilderFactory {
    private final AsyncHttpClient httpClient;

    public RequestBuilderFactoryImpl(AsyncHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public <T> RequestBuilder<T> create(
            String url, ClientCredentials credentials, TypeReference<T> jsonParserTypeRef) {
        return new RequestBuilderImpl<T>(
                new RequestHolderImpl<T>(
                        SetCredentials.forRequest(httpClient.prepareGet(url), credentials)),
                jsonParserTypeRef);
    }
}
