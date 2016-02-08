package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

//NEGATIVE EXAMPLE
public class WrongBlockingWithGetAndSwallowing {
    public static <T> T demo(final SphereClient client, final SphereRequest<T> sphereRequest) {
        //WRONG!!! please don't do this !!!!! it swallows the exceptions
        try {
            final CompletionStage<T> completionStage = client.execute(sphereRequest);
            return completionStage.toCompletableFuture().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
