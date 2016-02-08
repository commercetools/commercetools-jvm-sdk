package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;

/**
 * Sets a new ID which can be used as additional identifier for external systems like CRM or ERP. If not defined, the external ID is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandTest#setExternalId()}
 *
 * @see Payment
 */
public final class SetExternalId extends UpdateActionImpl<Payment> {
    @Nullable
    private final String externalId;

    private SetExternalId(@Nullable final String externalId) {
        super("setExternalId");
        this.externalId = externalId;
    }

    public static SetExternalId of(@Nullable final String externalId) {
        return new SetExternalId(externalId);
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }
}
