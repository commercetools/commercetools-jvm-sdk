package io.sphere.sdk.test;

import io.sphere.sdk.client.*;
import io.sphere.sdk.client.SphereRequest;

import java.util.concurrent.ExecutionException;

public abstract class IntegrationTest {

    private static TestClient client;

    protected static TestClient client() {
        if (client == null) {
            final SphereClientFactory factory = SphereClientFactory.of();
            final SphereClientConfig config = SphereClientConfig.of(projectKey(), clientId(), clientSecret(), authUrl(), apiUrl());
            final SphereClient underlying = factory.createClient(config);
            client = new TestClient(underlying);
        }
        return client;
    }

    protected static String apiUrl() {
        return System.getenv("JVM_SDK_IT_SERVICE_URL");
    }

    protected static String authUrl() {
        return System.getenv("JVM_SDK_IT_AUTH_URL");
    }

    protected static String clientSecret() {
        return System.getenv("JVM_SDK_IT_CLIENT_SECRET");
    }

    protected static String clientId() {
        return System.getenv("JVM_SDK_IT_CLIENT_ID");
    }

    protected static String projectKey() {
        return System.getenv("JVM_SDK_IT_PROJECT_KEY");
    }

    protected static <T> T execute(final SphereRequest<T> sphereRequest) {
        try {
            return client().execute(sphereRequest);
        } catch (final TestClientException e) {
            if (e.getCause() instanceof ExecutionException && e.getCause().getCause() instanceof ConcurrentModificationException) {
                throw (ConcurrentModificationException) e.getCause().getCause();
            } else {
                throw e;
            }
        }
    }
}
