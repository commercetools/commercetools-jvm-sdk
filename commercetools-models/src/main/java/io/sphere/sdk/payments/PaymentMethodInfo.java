package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 @see PaymentMethodInfoBuilder
 */
@JsonDeserialize(as = PaymentMethodInfoImpl.class)
public interface PaymentMethodInfo {
    @Nullable
    String getPaymentInterface();

    @Nullable
    String getMethod();

    @Nullable
    LocalizedString getName();
}
