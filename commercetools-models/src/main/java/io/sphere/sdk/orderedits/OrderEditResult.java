package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
        )
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderEditPreviewSuccess.class, name = "PreviewSuccess"),
        @JsonSubTypes.Type(value = OrderEditPreviewFailure.class, name = "PreviewFailure"),
        @JsonSubTypes.Type(value = OrderEditNotProcessed.class, name = "NotProcessed"),
        @JsonSubTypes.Type(value = OrderEditApplied.class, name = "Applied")
})
public interface OrderEditResult {

}
