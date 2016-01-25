package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

final class TransactionDraftImpl extends Base implements TransactionDraft {
    @Nullable
    private final ZonedDateTime timestamp;
    private final TransactionType type;
    private final MonetaryAmount amount;
    @Nullable
    private final String interactionId;
    @Nullable
    private final TransactionState state;

    @JsonCreator
    TransactionDraftImpl(@Nullable final ZonedDateTime timestamp, final TransactionType type, final MonetaryAmount amount, @Nullable final String interactionId, @Nullable final TransactionState state) {
        this.state = state;
        this.timestamp = Optional.ofNullable(timestamp)
                .map(value -> value.withZoneSameInstant(ZoneOffset.UTC))
                .orElse(null);
        this.type = type;
        this.amount = amount;
        this.interactionId = interactionId;
    }

    @Override
    @Nullable
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public MonetaryAmount getAmount() {
        return amount;
    }

    @Override
    @Nullable
    public String getInteractionId() {
        return interactionId;
    }

    @Override
    @Nullable
    public TransactionState getState() {
        return state;
    }
}
