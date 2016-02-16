package io.sphere.sdk.payments.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.payments.Payment;

import javax.annotation.Nullable;

/**
 * Sets a human-readable, localizable name for the payment method, e.g. 'Credit Card'. If no name is provided, the name is unset.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.payments.commands.PaymentUpdateCommandIntegrationTest#setMethodInfoName()}
 *
 *
 * @see Payment
 *
 *
 */
public final class SetMethodInfoName extends UpdateActionImpl<Payment> {

    @Nullable
    private final LocalizedString name;

    private SetMethodInfoName(@Nullable final LocalizedString name) {
        super("setMethodInfoName");
        this.name = name;
    }

    public static SetMethodInfoName of(final LocalizedString name) {
        return new SetMethodInfoName(name);
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }
}
