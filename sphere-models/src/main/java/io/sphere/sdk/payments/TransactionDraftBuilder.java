package io.sphere.sdk.payments;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public final class TransactionDraftBuilder extends Base implements Builder<TransactionDraft> {
    private ZonedDateTime timestamp;
    private TransactionType type;
    private MonetaryAmount amount;
    @Nullable
    private String interactionId;

    private TransactionDraftBuilder(final TransactionType type, final MonetaryAmount amount, final ZonedDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public static TransactionDraftBuilder of(final TransactionType type, final MonetaryAmount amount, final ZonedDateTime timestamp) {
        return new TransactionDraftBuilder(type, amount, timestamp);
    }

    public TransactionDraftBuilder timestamp(final ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TransactionDraftBuilder interactionId(@Nullable final String interactionId) {
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
    public TransactionDraft build() {
        return new TransactionDraft(timestamp, type, amount, interactionId);
    }
}
