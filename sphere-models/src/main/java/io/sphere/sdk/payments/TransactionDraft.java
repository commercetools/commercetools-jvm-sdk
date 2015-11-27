package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class TransactionDraft extends Base {
    private final ZonedDateTime timestamp;
    private final TransactionType type;
    private final MonetaryAmount amount;
    @Nullable
    private final String interactionId;

    @JsonCreator
    TransactionDraft(final ZonedDateTime timestamp, final TransactionType type, final MonetaryAmount amount, final String interactionId) {
        this.timestamp = Optional.ofNullable(timestamp)
                .map(value -> value.withZoneSameInstant(ZoneOffset.UTC))
                .orElse(null);
        this.type = type;
        this.amount = amount;
        this.interactionId = interactionId;
    }

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
