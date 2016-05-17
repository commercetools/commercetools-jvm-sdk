package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@JsonDeserialize(as = TransactionImpl.class)
public interface Transaction {
    @Nullable
    ZonedDateTime getTimestamp();

    TransactionType getType();

    MonetaryAmount getAmount();

    @Nullable
    String getInteractionId();

    String getId();

    TransactionState getState();
}
