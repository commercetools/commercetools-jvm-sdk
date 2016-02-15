package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

/**
 * Sets the interface that handles the payment (usually a PSP). Can not be changed once it has been set. The combination of interfaceId and paymentMethodInfo.paymentInterface must be unique.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#setMethodInfoInterface()}
 *
 *
 * @see Payment
 *
 *
 */
public final class SetMethodInfoInterface extends UpdateActionImpl<Payment> {

    private final String _interface;//sic! interface is keyword in Java

    private SetMethodInfoInterface(final String _interface) {
        super("setMethodInfoInterface");
        this._interface = _interface;
    }

    public static SetMethodInfoInterface of(final String _interface) {
        return new SetMethodInfoInterface(_interface);
    }

    public String getInterface() {
        return _interface;
    }
}
