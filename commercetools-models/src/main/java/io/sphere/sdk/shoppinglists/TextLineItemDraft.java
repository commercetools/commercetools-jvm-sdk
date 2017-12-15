package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * A draft for a new {@link TextLineItem}.
 *
 * @see TextLineItemDraftBuilder
 */
@JsonDeserialize(as = TextLineItemDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {"name", "quantity"})})
public interface TextLineItemDraft
{
    LocalizedString getName();

    Long getQuantity();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    ZonedDateTime getAddedAt();
}
