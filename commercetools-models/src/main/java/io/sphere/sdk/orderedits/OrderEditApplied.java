package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.time.ZonedDateTime;

public final class OrderEditApplied extends Base implements OrderEditResult {

    private final ZonedDateTime appliedAt;

    private final OrderExcerpt excerptBeforeEdit;

    private final OrderExcerpt excerptAfterEdit;

    @JsonCreator
    OrderEditApplied(ZonedDateTime appliedAt, OrderExcerpt excerptBeforeEdit, OrderExcerpt excerptAfterEdit) {
        this.appliedAt = appliedAt;
        this.excerptBeforeEdit = excerptBeforeEdit;
        this.excerptAfterEdit = excerptAfterEdit;
    }

    public ZonedDateTime getAppliedAt() {
        return appliedAt;
    }

    public OrderExcerpt getExcerptBeforeEdit() {
        return excerptBeforeEdit;
    }

    public OrderExcerpt getExcerptAfterEdit() {
        return excerptAfterEdit;
    }
}