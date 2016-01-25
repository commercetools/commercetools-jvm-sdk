package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@JsonDeserialize(as = TransactionDraftImpl.class)
public interface TransactionDraft {
    @Nullable
    ZonedDateTime getTimestamp();

    TransactionType getType();

    MonetaryAmount getAmount();

    @Nullable
    String getInteractionId();

    @Nullable
    TransactionState getState();
}
