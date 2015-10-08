package io.sphere.sdk.payments;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

public class PaymentStatusBuilder extends Base implements Builder<PaymentStatus> {
    @Nullable
    private String interfaceCode;
    @Nullable
    private String interfaceText;
    @Nullable
    private Reference<State> state;

    private PaymentStatusBuilder() {
    }
    
    public static PaymentStatusBuilder of() {
        return new PaymentStatusBuilder();
    }

    public PaymentStatusBuilder interfaceCode(@Nullable final String interfaceCode) {
        this.interfaceCode = interfaceCode;
        return this;
    }

    public PaymentStatusBuilder interfaceText(@Nullable final String interfaceText) {
        this.interfaceText = interfaceText;
        return this;
    }

    public PaymentStatusBuilder state(@Nullable final Reference<State> state) {
        this.state = state;
        return this;
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

    @Override
    public PaymentStatus build() {
        return new PaymentStatus(interfaceCode, interfaceText, state);
    }
}
