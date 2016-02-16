package io.sphere.sdk.meta;

import io.sphere.sdk.client.BlockingClientSphereExceptionDemo;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.TestDoubleSphereClientFactory;
import io.sphere.sdk.http.HttpResponse;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class SphereClientTuningDocumentationTest {
    @Test
    public void blockingClientAndValue() {
        final SphereClient httpTestDouble = TestDoubleSphereClientFactory.createHttpTestDouble(request -> HttpResponse.of(200, "{\"key\":\"foo\"}"));
        final BlockingSphereClient blockingSphereClient = BlockingSphereClient.of(httpTestDouble, 500, TimeUnit.MILLISECONDS);
        BlockingClientValueGetDemo.demo(blockingSphereClient, "foo");
    }

    @Test
    public void blockingClientAndSphereException() {
        final SphereClient httpTestDouble = TestDoubleSphereClientFactory.createHttpTestDouble(request -> HttpResponse.of(400, "{\"statusCode\":\"400\"}"));
        final BlockingSphereClient blockingSphereClient = BlockingSphereClient.of(httpTestDouble, 500, TimeUnit.MILLISECONDS);
        BlockingClientSphereExceptionDemo.demo(blockingSphereClient);
    }
}