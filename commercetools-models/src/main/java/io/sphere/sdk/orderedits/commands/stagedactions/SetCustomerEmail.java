package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.commands.StagedUpdateActionImpl;
import io.sphere.sdk.orderedits.OrderEdit;

public final class SetCustomerEmail extends StagedUpdateActionImpl<OrderEdit> {

    private final String email;

    private SetCustomerEmail(final String email) {
        super("setCustomerEmail");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public static SetCustomerEmail of(final String email){
        return new SetCustomerEmail(email);
    }
}
