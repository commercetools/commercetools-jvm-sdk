package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.errors.ErrorResponse;
import java.util.List;

@JsonDeserialize(as = OrderEditPreviewFailureImpl.class)
@ResourceValue
public interface OrderEditPreviewFailure extends OrderEditResult {

    List<ErrorResponse> getErrors();
}
