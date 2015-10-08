package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

public class SetMethodInfoInterface extends UpdateActionImpl<Payment> {

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
