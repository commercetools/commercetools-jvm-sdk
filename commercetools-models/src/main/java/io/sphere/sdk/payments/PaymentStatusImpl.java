package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

final class PaymentStatusImpl extends Base implements PaymentStatus {
    @Nullable
    private final String interfaceCode;
    @Nullable
    private final String interfaceText;
    @Nullable
    private final Reference<State> state;

    @JsonCreator
    PaymentStatusImpl(@Nullable final String interfaceCode, @Nullable final String interfaceText, @Nullable final Reference<State> state) {
        this.interfaceCode = interfaceCode;
        this.interfaceText = interfaceText;
        this.state = state;
    }

    @Nullable
    public String getInterfaceCode() {
        return interfaceCode;
    }

    @Nullable
    public String getInterfaceText() {
        return interfaceText;
    }

    @Nullable
    public Reference<State> getState() {
        return state;
    }
}
