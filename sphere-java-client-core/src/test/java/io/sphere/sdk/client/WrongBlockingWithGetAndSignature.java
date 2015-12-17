package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

//NEGATIVE EXAMPLE
public class WrongBlockingWithGetAndSignature {
    //NOT a good idea
    public static <T> T demo(final SphereClient client, final SphereRequest<T> sphereRequest)
            throws ExecutionException, InterruptedException { //UNCOOL and contagious
        final CompletionStage<T> completionStage = client.execute(sphereRequest);
        return completionStage.toCompletableFuture().get();
    }
}
