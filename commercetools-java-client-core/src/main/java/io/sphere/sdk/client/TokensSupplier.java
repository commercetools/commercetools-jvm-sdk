package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

/**
 * Component that asynchronous retrieves tokens.
 */
public interface TokensSupplier extends AutoCloseable, Supplier<CompletionStage<Tokens>> {
    @Override
    CompletionStage<Tokens> get();

    @Override
    void close();

    static TokensSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return TokensSupplierImpl.of(config, httpClient, closeHttpClient);
    }

    static TokensSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient, final List<SolutionInfo> additionalSolutionInfos) {
        return TokensSupplierImpl.of(config, httpClient, closeHttpClient, additionalSolutionInfos);
    }

    static TokensSupplier ofCustomerPasswordFlowTokens(final SphereAuthConfig authConfig, final String email, final String password, final HttpClient httpClient, final boolean closeHttpClient) {
        return TokensSupplierImpl.ofCustomerPasswordFlowTokensImpl(authConfig, email, password, httpClient, closeHttpClient);
    }

    static TokensSupplier ofCustomerPasswordFlowTokens(final SphereAuthConfig authConfig, final String email, final String password, final HttpClient httpClient, final boolean closeHttpClient, final List<SolutionInfo> additionalSolutionInfos) {
        return TokensSupplierImpl.ofCustomerPasswordFlowTokensImpl(authConfig, email, password, httpClient, closeHttpClient, additionalSolutionInfos);
    }
}
