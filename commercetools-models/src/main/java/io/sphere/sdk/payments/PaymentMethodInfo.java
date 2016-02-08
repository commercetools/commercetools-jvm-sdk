package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 @see PaymentMethodInfoBuilder
 */
public final class PaymentMethodInfo extends Base {
    @Nullable
    private final String paymentInterface;
    @Nullable
    private final String method;
    @Nullable
    private final LocalizedString name;

    @JsonCreator
    PaymentMethodInfo(@Nullable final String paymentInterface, @Nullable final String method, @Nullable final LocalizedString name) {
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
