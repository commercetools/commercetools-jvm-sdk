package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

final class TransactionDraftDsl extends TransactionDraftDslBase<TransactionDraftDsl> {
    @JsonCreator
    TransactionDraftDsl(final MonetaryAmount amount, final @Nullable String interactionId,
                        final @Nullable TransactionState state, final @Nullable ZonedDateTime timestamp, final TransactionType type) {
        super(amount, interactionId, state, Optional.ofNullable(timestamp)
                .map(value -> value.withZoneSameInstant(ZoneOffset.UTC))
                .orElse(null), type);
    }
}
