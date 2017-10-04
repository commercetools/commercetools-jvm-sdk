package io.sphere.sdk.shoppinglists;

import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class LineItemDraftBuilder extends LineItemDraftBuilderBase<LineItemDraftBuilder> {
    LineItemDraftBuilder(@Nullable ZonedDateTime addedAt, @Nullable CustomFieldsDraft custom, String productId, @Nullable Long quantity, @Nullable Integer variantId) {
        super(addedAt, custom, productId, quantity, variantId);
    }

    /**
     * Sets the {@code custom} property of this builder.
     *
     * @param custom the value for {@link LineItemDraft#getCustom()}
     * @return this builder
     *
     * @deprecated This method will be removed be removed with the next major SDK update.
     * Please use the {@link #custom(CustomFieldsDraft)} method instead.
     */
    @Deprecated
    public LineItemDraftBuilder custom(@Nullable final CustomFields custom) {
        return super.custom(CustomFieldsDraftBuilder.of(custom).build());
    }
}
