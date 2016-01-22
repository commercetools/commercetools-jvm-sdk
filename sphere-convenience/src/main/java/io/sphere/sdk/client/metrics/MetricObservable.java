package io.sphere.sdk.client.metrics;

import java.util.Observable;

final class MetricObservable extends Observable {
    @Override
    public void notifyObservers(final Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
