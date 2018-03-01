package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.annotations.HasQueryModel;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = ClassificationShippingRateInputImpl.class, name = "Classification"),
        @Type(value = ScoreShippingRateInputImpl.class, name = "Score")
})
public interface ShippingRateInput  {

}
