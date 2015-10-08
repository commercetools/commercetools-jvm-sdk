package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 @see PaymentMethodInfoBuilder
 */
public class PaymentMethodInfo {
    @Nullable
    private final String paymentInterface;
    @Nullable
    private final String method;
    @Nullable
    private final LocalizedString name;

    @JsonCreator
    PaymentMethodInfo(final String paymentInterface, final String method, final LocalizedString name) {
        this.paymentInterface = paymentInterface;
        this.method = method;
        this.name = name;
    }

    @Nullable
    public String getPaymentInterface() {
        return paymentInterface;
    }

    @Nullable
    public String getMethod() {
        return method;
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }
}
