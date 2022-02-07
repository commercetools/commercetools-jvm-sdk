package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

/**
 * @see TransactionDraftBuilder
 */
@ResourceDraftValue(
        abstractResourceDraftValueClass = true,
        factoryMethods = {
            @FactoryMethod(parameterNames = {"type", "amount"}),
            @FactoryMethod(parameterNames = {"type", "amount", "timestamp"}),
})
@JsonDeserialize(as = TransactionDraftDsl.class)
public interface TransactionDraft {
    @Nullable
    ZonedDateTime getTimestamp();

    TransactionType getType();

    MonetaryAmount getAmount();

    @Nullable
    String getInteractionId();

    /**
     * The state of this transaction. If not set, currently defaults to Pending. As part of a Beta improvement initiative,
     * we will switch the default to Initial. In the mean time, please do not depend on the default value but provide the state field explicitly.
     */
    @Nullable
    TransactionState getState();

    @Nullable
    CustomFields getCustom();
}
