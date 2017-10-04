package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.TestDoubleSphereClientFactory;
import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.queries.ProjectGet;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SimpleMetricsSphereClientTest {

    public static final int MINIMUM_WAIT_IN_MILLISECONDS = 23;
    public static final String CORRELATION_ID = "ID-123456";
    public static final HttpResponse HTTP_RESPONSE = HttpResponse.of(200, "{}", HttpHeaders.of("X-Correlation-ID", CORRELATION_ID));


    @Test
    public void testAll() {
        final SphereClient asyncClient = TestDoubleSphereClientFactory.createHttpTestDouble(intent -> {
            try {
                Thread.sleep(MINIMUM_WAIT_IN_MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return HTTP_RESPONSE;
        });
        final SimpleMetricsSphereClient simpleMetricsSphereClient = SimpleMetricsSphereClient.of(asyncClient);
        final BlockingSphereClient blockingSphereClient = BlockingSphereClient.of(simpleMetricsSphereClient, 10, TimeUnit.SECONDS);
        final Observable metricObservable = simpleMetricsSphereClient.getMetricObservable();
        final MetricObserver metricObserver = new MetricObserver();

        final SphereRequest<Project> sphereRequest = ProjectGet.of();

        blockingSphereClient.executeBlocking(sphereRequest);//warm up
        metricObservable.addObserver(metricObserver);
        blockingSphereClient.executeBlocking(sphereRequest);

        final String requestId = "2";
        final SoftAssertions softly = new SoftAssertions();
        final ObservedDeserializationDuration deserializationDuration = metricObserver.get(ObservedDeserializationDuration.class);
        softly.assertThat(deserializationDuration.getRequest()).as("deser request").isEqualTo(sphereRequest);
        softly.assertThat(deserializationDuration.getDurationInMilliseconds()).as("deser duration").isBetween(0L, 4L);
        softly.assertThat(deserializationDuration.getRequestId()).as("deser id").isEqualTo(requestId);
        softly.assertThat(deserializationDuration.getTopic()).as("deser topic").isEqualTo("ObservedDeserializationDuration");
        softly.assertThat(deserializationDuration.getCorrelationId()).as("total correlationId").isEqualTo(CORRELATION_ID);
        final ObservedTotalDuration totalDuration = metricObserver.get(ObservedTotalDuration.class);
        softly.assertThat(totalDuration.getRequest()).as("total request").isEqualTo(sphereRequest);
        softly.assertThat(totalDuration.getDurationInMilliseconds()).as("total duration").isBetween((long) MINIMUM_WAIT_IN_MILLISECONDS, MINIMUM_WAIT_IN_MILLISECONDS + 8L);
        softly.assertThat(totalDuration.getRequestId()).as("total id").isEqualTo(requestId);
        softly.assertThat(totalDuration.getTopic()).as("total topic").isEqualTo("ObservedTotalDuration");
        softly.assertThat(totalDuration.getCorrelationId()).as("total correlationId").isEqualTo(CORRELATION_ID);
        softly.assertThat(totalDuration.getSuccessResult()).as("getSuccessResult").isNotNull().isInstanceOf(Project.class);
        softly.assertThat(totalDuration.getErrorResult()).as("getErrorResult").isNull();
        final ObservedSerializationDuration serializationDuration = metricObserver.get(ObservedSerializationDuration.class);
        softly.assertThat(serializationDuration.getRequest()).as("ser request").isEqualTo(sphereRequest);
        softly.assertThat(serializationDuration.getDurationInMilliseconds()).as("ser duration").isBetween(0L, 4L);
        softly.assertThat(serializationDuration.getRequestId()).as("ser id").isEqualTo(requestId);
        softly.assertThat(serializationDuration.getTopic()).as("ser topic").isEqualTo("ObservedSerializationDuration");
        softly.assertAll();//important
    }

    private static class MetricObserver implements Observer {
        private final List<Object> messages = Collections.synchronizedList(new LinkedList<>());

        @Override
        public void update(final Observable o, final Object arg) {
            messages.add(arg);
        }

        @SuppressWarnings("unchecked")
        public <T> T get(final Class<T> clazz) {
            return (T) messages.stream().filter(o -> o.getClass().isAssignableFrom(clazz)).findFirst().get();
        }
    }
}