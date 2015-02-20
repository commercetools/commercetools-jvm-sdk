package io.sphere.sdk.test;

import io.sphere.sdk.client.*;
import io.sphere.sdk.client.SphereRequest;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public abstract class IntegrationTest {

    private static final String JVM_SDK_IT_SERVICE_URL = "JVM_SDK_IT_SERVICE_URL";
    private static final String JVM_SDK_IT_AUTH_URL = "JVM_SDK_IT_AUTH_URL";
    private static final String JVM_SDK_IT_CLIENT_SECRET = "JVM_SDK_IT_CLIENT_SECRET";
    private static final String JVM_SDK_IT_CLIENT_ID = "JVM_SDK_IT_CLIENT_ID";
    private static final String JVM_SDK_IT_PROJECT_KEY = "JVM_SDK_IT_PROJECT_KEY";
    private static TestClient client;

    protected static TestClient client() {
        if (client == null) {
            Objects.requireNonNull(projectKey());
            final SphereClientFactory factory = SphereClientFactory.of();
            final SphereClientConfig config = SphereClientConfig.of(projectKey(), clientId(), clientSecret(), authUrl(), apiUrl());
            final SphereClient underlying = factory.createClient(config);
            client = new TestClient(underlying);
        }
        return client;
    }

    private static String getValueForEnvVar(final String key) {
        return Optional.ofNullable(System.getenv(key))
                .orElseThrow(() -> new RuntimeException(
                        "Missing environment variable " + key + ", please provide the following environment variables from a NEW TEST PROJECT (you can keep it for further tests):\n" +
                        "export JVM_SDK_IT_SERVICE_URL=\"https://api.sphere.io\"\n" +
                        "export JVM_SDK_IT_AUTH_URL=\"https://auth.sphere.io\"\n" +
                        "export JVM_SDK_IT_PROJECT_KEY=\"YOUR project key\"\n" +
                        "export JVM_SDK_IT_CLIENT_ID=\"YOUR client id\"\n" +
                        "export JVM_SDK_IT_CLIENT_SECRET=\"YOUR client secret\""));
    }

    protected static String apiUrl() {
        return getValueForEnvVar(JVM_SDK_IT_SERVICE_URL);
    }

    protected static String authUrl() {
        return getValueForEnvVar(JVM_SDK_IT_AUTH_URL);
    }

    protected static String clientSecret() {
        return getValueForEnvVar(JVM_SDK_IT_CLIENT_SECRET);
    }

    protected static String clientId() {
        return getValueForEnvVar(JVM_SDK_IT_CLIENT_ID);
    }

    protected static String projectKey() {
        return getValueForEnvVar(JVM_SDK_IT_PROJECT_KEY);
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
