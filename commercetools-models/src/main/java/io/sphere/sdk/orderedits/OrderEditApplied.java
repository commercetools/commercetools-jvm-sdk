package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderEditAppliedImpl.class)
@ResourceValue
public interface OrderEditApplied extends OrderEditResult {

    ZonedDateTime getAppliedAt();

    OrderExcerpt getExcerptBeforeEdit();

    OrderExcerpt getExcerptAfterEdit();
}