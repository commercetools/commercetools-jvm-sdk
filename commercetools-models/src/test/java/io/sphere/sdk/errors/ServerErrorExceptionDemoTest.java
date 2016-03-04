package io.sphere.sdk.errors;

import io.sphere.sdk.client.*;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

public class ServerErrorExceptionDemoTest {
    @Test
    public void httpCodesAndTheCorrespondingExceptions() {
        httpCodeExpectedResult(500, InternalServerErrorException.class);
        httpCodeExpectedResult(502, BadGatewayException.class);
        httpCodeExpectedResult(503, ServiceUnavailableException.class);
        httpCodeExpectedResult(504, GatewayTimeoutException.class);
    }

    public static BlockingSphereClient clientForStatusCode(final int statusCode) {
        final SphereClient sphereClient = TestDoubleSphereClientFactory
                .createHttpTestDouble(intent -> HttpResponse.of(statusCode));
        return BlockingSphereClient.of(sphereClient, Duration.ofMillis(100));
    }

    private void httpCodeExpectedResult(final int statusCode, final Class<? extends ServerErrorException> type) {
        final Throwable throwable = catchThrowable(() ->
                clientForStatusCode(statusCode).executeBlocking(ProjectGet.of()));
        assertThat(throwable).isInstanceOf(type);
    }
}
