package io.sphere.sdk.errors;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.errors.SphereError;
import org.junit.rules.ExpectedException;

import java.util.function.Supplier;

public class ExceptionTestDsl {
        private final Supplier<SphereRequest<?>> f;
        private ExpectedException thrown;
        private BlockingSphereClient client;
        private ExceptionTestDsl(final Supplier<SphereRequest<?>> f,ExpectedException thrown,BlockingSphereClient client) {
            this.f = f;
            this.thrown = thrown;
            this.client = client;
        }

        public static ExceptionTestDsl of(final Supplier<SphereRequest<?>> f,ExpectedException thrown,BlockingSphereClient client) {
                return new ExceptionTestDsl(f,thrown,client);
        }

        public void resultsInA(final Class<? extends Throwable> type) {
            thrown.expect(type);
            final SphereRequest<?> testSphereRequest = f.get();
            client.executeBlocking(testSphereRequest);
        }

        public void resultsInA(final Class<? extends ErrorResponseException> type, final Class<? extends SphereError> error) {
            thrown.expect(type);
            thrown.expect(ExceptionCodeMatches.of(error));
            final SphereRequest<?> testSphereRequest = f.get();
            client.executeBlocking(testSphereRequest);
        }
    }