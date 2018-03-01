package io.sphere.sdk.errors;

import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RetryInvalidTokenHttpClient implements HttpClient {
        private final ExecutorService executorService = Executors.newFixedThreadPool(1);

        private volatile boolean tokenValid = true;
        private volatile boolean tokenNew = false;

        private static final Logger logger = LoggerFactory.getLogger(RetryInvalidTokenHttpClient.class);

        private SphereClientConfig config;

        public RetryInvalidTokenHttpClient(SphereClientConfig config) {
            this.config = config;
        }

        public boolean isTokenValid() {
            return tokenValid;
        }

        @Override
        public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
            executorService.execute(() -> {
                final HttpResponse httpResponse = executeSync(httpRequest);
                logger.info("request: {} response: {} tokenIsValid: {}", httpRequest, httpResponse, tokenValid);
                future.complete(httpResponse);
            });
            return future;
        }

        private HttpResponse executeSync(final HttpRequest httpRequest) {
            if (httpRequest.getUrl().contains("oauth")) {
                if (tokenValid && !tokenNew) {
                    return HttpResponse.of(200, String.format("{\"access_token\":\"first-token\",\"token_type\":\"Bearer\",\"expires_in\":172800,\"scope\":\"manage_project:%s\"}", config.getProjectKey()));
                } else {
                    tokenValid = true;
                    tokenNew = true;
                    return HttpResponse.of(200, String.format("{\"access_token\":\"second-token\",\"token_type\":\"Bearer\",\"expires_in\":172800,\"scope\":\"manage_project:%s\"}", config.getProjectKey()));
                }
            }
            if (httpRequest.getUrl().contains("cat-id")) {
                tokenValid = false;//after that, the token expires
                return HttpResponse.of(404);
            }
            if (httpRequest.getUrl().contains("channel-id")) {
                if (tokenValid && httpRequest.getHeaders().getHeader(HttpHeaders.AUTHORIZATION).get(0).equals("Bearer second-token")) {
                    return HttpResponse.of(404);
                } else {
                    return HttpResponse.of(401, "{\"statusCode\":401,\"message\":\"invalid_token\",\"errors\":[{\"code\":\"invalid_token\",\"message\":\"invalid_token\"}],\"error\":\"invalid_token\"}");
                }
            }
            return HttpResponse.of(500);
        }

        @Override
        public void close() {
            executorService.shutdownNow();
        }
    }