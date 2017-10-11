package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartValueDraftImpl.class, name = "CartValue"),
        @JsonSubTypes.Type(value = CartClassificationDraftDsl.class, name = "CartClassification"),
        @JsonSubTypes.Type(value = CartScoreDraftImpl.class, name = "CartScore")
})
public interface ShippingRateInputTypeDraft  {
}
