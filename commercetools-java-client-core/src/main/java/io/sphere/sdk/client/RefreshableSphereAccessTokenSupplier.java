package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;

interface RefreshableSphereAccessTokenSupplier extends SphereAccessTokenSupplier {
    /**
        Triggers a token refresh and gives a future which is fulfilled if a new token is fetched.

     @return token future
     */
    CompletionStage<String> getNewToken();
}
