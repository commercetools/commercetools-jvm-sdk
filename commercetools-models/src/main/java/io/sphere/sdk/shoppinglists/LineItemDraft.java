package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * A draft for a new {@link LineItem}.
 *
 * @see LineItemDraftBuilder
 */
@JsonDeserialize(as = LineItemDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = {
                @FactoryMethod(methodName = "ofSku", parameterNames = {"sku", "quantity"}),
                @FactoryMethod(parameterNames = {"productId"})})
public interface LineItemDraft {
    String getProductId();

    @Nullable
    Integer getVariantId();

    String getSku();

    @Nullable
    Long getQuantity();

    @Nullable
    ZonedDateTime getAddedAt();

    @Nullable
    CustomFieldsDraft getCustom();
}
