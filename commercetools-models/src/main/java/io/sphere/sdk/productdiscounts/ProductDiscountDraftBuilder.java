package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class ProductDiscountDraftBuilder extends ProductDiscountDraftBuilderBase<ProductDiscountDraftBuilder> {
    ProductDiscountDraftBuilder(final Boolean active, @Nullable final LocalizedString description, final String key, final LocalizedString name, final String predicate, final String sortOrder, @Nullable ZonedDateTime validFrom,@Nullable ZonedDateTime validUntil, final ProductDiscountValue value) {
        super(active, description, key, name, predicate, sortOrder, validFrom,validUntil,value);
    }

    public ProductDiscountDraftBuilder predicate(final ProductDiscountPredicate predicate) {
        this.predicate = predicate.toSpherePredicate();
        return this;
    }
}
