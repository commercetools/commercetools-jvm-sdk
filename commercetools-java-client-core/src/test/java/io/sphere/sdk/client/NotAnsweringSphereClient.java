package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public final class NotAnsweringSphereClient extends Base implements SphereClient {
    public NotAnsweringSphereClient() {
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return new CompletableFuture<>();
    }

    @Override
    public void close() {

    }

    @Override
    public SphereApiConfig getConfig() {
        return null;
    }
}
