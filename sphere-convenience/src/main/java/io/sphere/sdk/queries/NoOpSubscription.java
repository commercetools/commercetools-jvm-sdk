package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

class NoOpSubscription extends Base implements SubscriptionsState {
    @Override
    public void cancel() {

    }

    @Override
    public void request(final long n) {

    }
}