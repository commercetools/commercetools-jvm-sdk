package io.sphere.sdk.test;

import io.sphere.sdk.client.*;
import io.sphere.sdk.http.ClientRequest;

import java.util.concurrent.ExecutionException;

public abstract class IntegrationTest {

    private static TestClient client;

    protected static TestClient client() {
        if (client == null) {
            final JavaClientFactory factory = JavaClientFactory.of();
            final String projectKey = System.getenv("JVM_SDK_IT_PROJECT_KEY");
            final String clientId = System.getenv("JVM_SDK_IT_CLIENT_ID");
            final String clientSecret = System.getenv("JVM_SDK_IT_CLIENT_SECRET");
            final String authUrl = System.getenv("JVM_SDK_IT_AUTH_URL");
            final String apiUrl = System.getenv("JVM_SDK_IT_SERVICE_URL");
            final JavaClient underlying = factory.createClient(projectKey, clientId, clientSecret, authUrl, apiUrl);
            client = new TestClient(underlying);
        }
        return client;
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest) {
        try {
            return client().execute(clientRequest);
        } catch (final TestClientException e) {
            if (e.getCause() instanceof ExecutionException && e.getCause().getCause() instanceof ConcurrentModificationException) {
                throw (ConcurrentModificationException) e.getCause().getCause();
            } else {
                throw e;
            }
        }
    }
}
