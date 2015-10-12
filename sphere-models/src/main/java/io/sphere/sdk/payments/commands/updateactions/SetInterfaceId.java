package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

/**
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#setInterfaceId()}
 */
public class SetInterfaceId extends UpdateActionImpl<Payment> {

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
