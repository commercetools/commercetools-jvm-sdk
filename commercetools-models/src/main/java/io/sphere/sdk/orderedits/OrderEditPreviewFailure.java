package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.errors.ErrorResponse;

import java.util.List;

public final class OrderEditPreviewFailure extends Base implements OrderEditResult {

    private final List<ErrorResponse> errors;

    @JsonCreator
    public OrderEditPreviewFailure(final List<ErrorResponse> errors) {
        this.errors = errors;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }
}
