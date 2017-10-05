package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.models.WithType;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = ClassificationShippingRateInputDraftImpl.class, name = "Classification"),
        @Type(value = ScoreShippingRateInputDraftImpl.class, name = "Score")
})
public interface ShippingRateInputDraft extends WithType {


}
