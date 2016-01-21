package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereClient;

import java.util.Observable;
import java.util.Observer;

public class SimpleMetricsSphereClientDemo {
    public static SphereClient demo(final SphereClient asyncClient) {
        final SimpleMetricsSphereClient simpleMetricsSphereClient = SimpleMetricsSphereClient.of(asyncClient);
        final Observable observable = simpleMetricsSphereClient.getMetricObservable();
        observable.addObserver(new StdOutObserver());//register once observer
        return simpleMetricsSphereClient;//use metric client wrapper instead of async client directly
/*
    prints something like:
    observed: ObservedSerializationDuration[requestId=1,durationInMilliseconds=0,request=ProjectGet[]]
    observed: ObservedDeserializationDuration[requestId=1,durationInMilliseconds=0,request=ProjectGet[]]
    observed: ObservedTotalDuration[requestId=1,durationInMilliseconds=19,request=ProjectGet[]]
*/
    }

    public static class StdOutObserver implements Observer {
        @Override
        public void update(final Observable o, final Object arg) {
            System.out.println("observed: " + arg);
        }
    }
}
