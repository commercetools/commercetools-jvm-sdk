package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@ResourceValue
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

    @Nullable
    CustomFields getCustom();

    static String referenceTypeId() {
        return "transaction";
    }
}
