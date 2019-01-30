package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;

public final class SetCustomerEmail extends OrderEditStagedUpdateActionImpl {

    private final String email;

    @JsonCreator
    SetCustomerEmail(final String email) {
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
