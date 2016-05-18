package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**
 @see PaymentStatusBuilder
 */
@JsonDeserialize(as = PaymentStatusImpl.class)
public interface PaymentStatus {
    @Nullable
    String getInterfaceCode();

    @Nullable
    String getInterfaceText();

    @Nullable
    Reference<State> getState();
}
