package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;

//NEGATIVE EXAMPLE
public class WrongBlockingWithJoin {
    public static <T> T demo(final SphereClient client, final SphereRequest<T> sphereRequest) {
        //WRONG please don't do this
        final CompletionStage<T> completionStage = client.execute(sphereRequest);
        return completionStage.toCompletableFuture().join();
    }
}
