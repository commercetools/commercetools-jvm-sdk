package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletionStage;

final class RetryOutputImpl<P, R> extends Base implements RetryOutput<P, R> {
    private final CompletionStage<R> stage;

    private final P parameterObject;

    RetryOutputImpl(final CompletionStage<R> stage, final P parameterObject) {
        this.stage = stage;
        this.parameterObject = parameterObject;
    }

    @Override
    public P getParameterObject() {
        return parameterObject;
    }

    @Override
    public CompletionStage<R> getStage() {
        return stage;
    }
}
