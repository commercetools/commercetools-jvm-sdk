package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = LineItemDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"productId"}),
})
public interface LineItemDraft {
    String getProductId();

    @Nullable
    Integer getVariantId();

    @Nullable
    Long getQuantity();

    @Nullable
    ZonedDateTime getAddedAt();

    @Nullable
    CustomFields getCustom();
}
