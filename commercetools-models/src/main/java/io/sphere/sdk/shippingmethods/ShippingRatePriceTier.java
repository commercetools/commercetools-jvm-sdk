package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.models.WithType;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartValueImpl.class, name = "CartValue"),
        @JsonSubTypes.Type(value = CartClassificationImpl.class, name = "CartClassification"),
        @JsonSubTypes.Type(value = CartScoreImpl.class, name = "CartScore")
})
public interface ShippingRatePriceTier extends WithType{

    MonetaryAmount getPrice();

    @Nullable
    @JsonProperty("isMatching")
    Boolean isMatching();

}
