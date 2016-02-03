package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

/**
 * Sets the identifier that is used by the interface that manages the payment (usually the PSP). Can not be changed once it has been set. The combination of interfaceId and paymentMethodInfo.paymentInterface must be unique.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#setInterfaceId()}
 *
 * @see Payment
 */
public final class SetInterfaceId extends UpdateActionImpl<Payment> {

    private final String interfaceId;

    private SetInterfaceId(final String interfaceId) {
        super("setInterfaceId");
        this.interfaceId = interfaceId;
    }

    public static SetInterfaceId of(final String interfaceId) {
        return new SetInterfaceId(interfaceId);
    }

    public String getInterfaceId() {
        return interfaceId;
    }
}
