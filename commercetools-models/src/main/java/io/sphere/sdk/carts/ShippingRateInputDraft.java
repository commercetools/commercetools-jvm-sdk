package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClassificationShippingRateInputDraftDsl.class, name = "Classification"),
        @JsonSubTypes.Type(value = ScoreShippingRateInputDraftDsl.class, name = "Score")
})
public interface ShippingRateInputDraft  {

}
