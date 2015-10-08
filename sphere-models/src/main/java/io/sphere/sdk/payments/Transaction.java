package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public final class Transaction extends Base {
    @Nullable
    private final ZonedDateTime timestamp;
    private final TransactionType type;
    private final MonetaryAmount amount;
    @Nullable
    private final String interactionId;

    @JsonCreator
    Transaction(final ZonedDateTime timestamp, final TransactionType type, final MonetaryAmount amount, final String interactionId) {
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.interactionId = interactionId;
    }

    @Nullable
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    @Nullable
    public String getInteractionId() {
        return interactionId;
    }
}
