package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.carts.TaxedPrice;
import org.javamoney.moneta.Money;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as = OrderExcerptImpl.class)
public interface OrderExcerpt {

    Money getTotalPrice();

    @Nullable
    TaxedPrice getTaxedPrice();

    Long getVersion();

}
