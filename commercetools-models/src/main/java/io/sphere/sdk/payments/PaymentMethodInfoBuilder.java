package io.sphere.sdk.payments;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

public final class PaymentMethodInfoBuilder extends Base implements Builder<PaymentMethodInfo> {
    @Nullable
    private String paymentInterface;
    @Nullable
    private String method;
    @Nullable
    private LocalizedString name;

    private PaymentMethodInfoBuilder() {
    }
    
    public static PaymentMethodInfoBuilder of() {
        return new PaymentMethodInfoBuilder();
    }

    public PaymentMethodInfoBuilder paymentInterface(@Nullable final String paymentInterface) {
        this.paymentInterface = paymentInterface;
        return this;
    }
    
    public PaymentMethodInfoBuilder method(@Nullable final String method) {
        this.method = method;
        return this;
    }
    
    public PaymentMethodInfoBuilder name(@Nullable final LocalizedString name) {
        this.name = name;
        return this;
    }

    @Override
    public PaymentMethodInfo build() {
        return new PaymentMethodInfoImpl(paymentInterface, method, name);
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
