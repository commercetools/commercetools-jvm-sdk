package io.sphere.sdk.customers;

import io.sphere.sdk.models.Timestamped;

public interface CustomerToken extends Timestamped {
    String getId();

    String getCustomerId();

    String getValue();
}
