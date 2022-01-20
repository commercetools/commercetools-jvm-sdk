package io.sphere.sdk.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class TransactionDraftDsl extends TransactionDraftDslBase<TransactionDraftDsl> {
    @JsonCreator
    TransactionDraftDsl(final MonetaryAmount amount, CustomFields custom, final @Nullable String interactionId,
                        final @Nullable TransactionState state, final @Nullable ZonedDateTime timestamp, final TransactionType type) {
        super(amount, custom, interactionId, state, Optional.ofNullable(timestamp)
                .map(value -> value.withZoneSameInstant(ZoneOffset.UTC))
                .orElse(null), type);
    }
}
