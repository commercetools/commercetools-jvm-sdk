package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;

@JsonDeserialize(as = TextLineItemImpl.class)
@ResourceValue
public interface TextLineItem extends Custom
{
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Long getQuantity();
}
