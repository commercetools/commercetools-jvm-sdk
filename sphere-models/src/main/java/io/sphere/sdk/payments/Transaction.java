package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class Transaction extends Base {
    private final String id;
    @Nullable
    private final ZonedDateTime timestamp;
    private final TransactionType type;
    private final MonetaryAmount amount;
    @Nullable
    private final String interactionId;
    private final TransactionState state;

    @JsonCreator
    Transaction(@Nullable final ZonedDateTime timestamp, final String id, final TransactionType type, final MonetaryAmount amount, @Nullable final String interactionId, final TransactionState state) {
        this.id = id;
        this.state = state;
        this.timestamp = Optional.ofNullable(timestamp)
                .map(value -> value.withZoneSameInstant(ZoneOffset.UTC))
                .orElse(null);
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

    public String getId() {
        return id;
    }

    public TransactionState getState() {
        return state;
    }
}
