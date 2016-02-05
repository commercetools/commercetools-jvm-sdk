package io.sphere.sdk.meta;

import org.asynchttpclient.Response;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AsyncDocumentationCallbackTest extends AsyncDocumentationTest {
    private final Logger logger = new Logger();

    @Test
    public void loggingCallbackExample() throws Exception {
        final CompletionStage<String> stage = getFuture();

        //when completed, access only value if present
        stage.thenAcceptAsync(value -> logger.info("Fetched successfully " + value));

        //when completed, access value or error, one of them is null
        final CompletionStage<String> resultLikeOriginalStage = stage.whenCompleteAsync((nullableValue, nullableError) -> {
            if (nullableValue != null) {
                logger.info("Fetched successfully " + nullableValue);
            } else {
                logger.error("Did not fetch successfully.", nullableError);
            }
        });
    }

    @Test
    public void whenCompleteAsyncDemo() throws Exception {
        final CompletionStage<Response> stage = getResponse();
        final CompletionStage<String> contentTypeStage = stage
                .whenComplete((nullableResponse, e) -> logger.debug("response: " + nullableResponse))
                .thenApply(response -> response.getContentType());
    }

    private CompletionStage<Response> getResponse() {
        return new CompletableFuture<>();
    }
}
