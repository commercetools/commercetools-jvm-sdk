package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;

public class SetMethodInfoMethod extends UpdateActionImpl<Payment> {

    @Nullable
    private final String method;

    private SetMethodInfoMethod(@Nullable final String method) {
        super("setMethodInfoMethod");
        this.method = method;
    }

    public static SetMethodInfoMethod of(@Nullable final String method) {
        return new SetMethodInfoMethod(method);
    }

    @Nullable
    public String getMethod() {
        return method;
    }
}
