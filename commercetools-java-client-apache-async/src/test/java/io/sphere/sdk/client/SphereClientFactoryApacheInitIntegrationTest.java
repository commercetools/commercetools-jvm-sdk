package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import org.junit.Test;

import javax.annotation.Nullable;

import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SphereClientFactoryApacheInitIntegrationTest {
    @Test
    public void create() {
        //we cannot check this in TeamCity with sphere CI, but with travis
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientFactory clientFactory = SphereClientFactory.of();
            try (final SphereClient client = clientFactory.createClient("empty-for-test", "empty", "empty")) {

                final Throwable throwable = catchThrowable(() -> doRequest(client));

                assertThat(throwable)
                        .as("if the client works then we know that the credentials are wrong")
                        .isInstanceOf(CompletionException.class)
                        .hasCauseInstanceOf(InvalidClientCredentialsException.class);
            }
        }
    }

    private String doRequest(final SphereClient client) {
        return client.execute(new SphereRequest<String>() {
            @Nullable
            @Override
            public String deserialize(final HttpResponse httpResponse) {
                return null;
            }

            @Override
            public HttpRequestIntent httpRequestIntent() {
                return HttpRequestIntent.of(HttpMethod.GET, "/");
            }
        }).toCompletableFuture().join();
    }
}
