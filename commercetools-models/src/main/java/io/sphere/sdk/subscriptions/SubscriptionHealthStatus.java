package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum SubscriptionHealthStatus implements SphereEnumeration {

    /**
     * The subscription is delivering messages as expected
     */
    HEALTHY,

    /**
     * Messages can not be delivered with the current configuration. If the configuration is fixed, all messages that haven't been delivered yet will still be delivered.
     */
    CONFIGURATION_ERROR,

    /**
     * Messages can not be delivered with the current configuration. Delivery is not attempted anymore. Undelivered messages are not retained.
     */
    CONFIGURATION_ERROR_DELIVERY_STOPPED,


    /**
     * Messages can not be delivered currently, but not due to the configuration. E.g. the destination has a temporary outage.
     */
    TEMPORARY_ERROR;


    @JsonCreator
    public static SubscriptionHealthStatus ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
