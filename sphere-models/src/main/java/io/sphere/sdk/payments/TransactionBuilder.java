package io.sphere.sdk.payments;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public final class TransactionBuilder extends Base implements Builder<Transaction> {
    private ZonedDateTime timestamp;
    private TransactionType type;
    private MonetaryAmount amount;
    @Nullable
    private String interactionId;

    private TransactionBuilder(final TransactionType type, final MonetaryAmount amount, final ZonedDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public static TransactionBuilder of(final TransactionType type, final MonetaryAmount amount, final ZonedDateTime timestamp) {
        return new TransactionBuilder(type, amount, timestamp);
    }

    public TransactionBuilder timestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TransactionBuilder interactionId(@Nullable final String interactionId) {
        this.interactionId = interactionId;
        return this;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    @Nullable
    public String getInteractionId() {
        return interactionId;
    }

    @Nullable
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public Transaction build() {
        return new Transaction(timestamp, type, amount, interactionId);
    }
}
