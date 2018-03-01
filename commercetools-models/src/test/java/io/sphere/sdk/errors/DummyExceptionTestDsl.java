package io.sphere.sdk.errors;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.TestDoubleSphereClientFactory;
import io.sphere.sdk.http.HttpResponse;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CompletionException;

public class DummyExceptionTestDsl {
        private final int responseCode;
        protected ExpectedException thrown;

        public DummyExceptionTestDsl(final int responseCode,ExpectedException thrown) {
            this.responseCode = responseCode;
            this.thrown = thrown;
        }

        public void resultsInA(final Class<? extends Throwable> type) throws Throwable {
            thrown.expect(type);
            try {
                TestDoubleSphereClientFactory.createHttpTestDouble(
                        request -> HttpResponse.of(responseCode)).execute(CategoryQuery.of()
                ).toCompletableFuture().join();
            } catch (final CompletionException e) {
                throw e.getCause();
            }
        }
    }