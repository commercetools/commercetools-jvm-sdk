package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;

public class SetStatusInterfaceCode extends UpdateActionImpl<Payment> {

    @Nullable
    private final String interfaceCode;

    private SetStatusInterfaceCode(@Nullable final String interfaceCode) {
        super("setStatusInterfaceCode");
        this.interfaceCode = interfaceCode;
    }

    public static SetStatusInterfaceCode of(@Nullable final String interfaceCode) {
        return new SetStatusInterfaceCode(interfaceCode);
    }

    @Nullable
    public String getInterfaceCode() {
        return interfaceCode;
    }
}
