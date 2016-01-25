package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class OrderFromCartDraftImpl extends Base implements OrderFromCartDraft {
    private final String id;
    private final Long version;
    @Nullable
    private final String orderNumber;
    @Nullable
    private final PaymentState  paymentState;

    @JsonCreator
    OrderFromCartDraftImpl(final String id, final Long version, @Nullable final String orderNumber, @Nullable final PaymentState paymentState) {
        this.id = id;
        this.version = version;
        this.orderNumber = orderNumber;
        this.paymentState = paymentState;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    @Nullable
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    @Nullable
    public PaymentState getPaymentState() {
        return paymentState;
    }
}
