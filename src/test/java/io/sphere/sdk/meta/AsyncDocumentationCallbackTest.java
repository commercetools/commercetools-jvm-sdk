package io.sphere.sdk.meta;

import org.junit.Test;

import java.util.concurrent.CompletionStage;

public class AsyncDocumentationCallbackTest extends AsyncDocumentationTest {
    private final Logger logger = new Logger();

    @Test
    public void loggingCallbackExample() throws Exception {
        final CompletionStage<String> stage = getFuture();

        //when completed, access only value if present
        stage.thenAcceptAsync(value -> logger.info("Fetched successfully " + value));

        //when completed, access value or error, one of them is null
        stage.whenCompleteAsync((nullableValue, nullableError) -> {
            if (nullableValue != null) {
                logger.info("Fetched successfully " + nullableValue);
            } else {
                logger.error("Did not fetch successfully.", nullableError);
            }
        });
    }


}
