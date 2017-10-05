package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.models.WithType;

import static com.fasterxml.jackson.annotation.JsonSubTypes.*;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = CartValueImpl.class, name = "CartValue"),
        @Type(value = CartClassificationImpl.class, name = "CartClassification"),
        @Type(value = CartScoreImpl.class, name = "CartScore")
})
public interface ShippingRateInputType extends WithType {
}
