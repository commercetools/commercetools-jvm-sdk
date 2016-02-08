package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

@JsonDeserialize(as = ProductDiscountDraftImpl.class)
public interface ProductDiscountDraft {
    @JsonProperty("isActive")
    Boolean isActive();

    @Nullable
    LocalizedString getDescription();

    LocalizedString getName();

    String getPredicate();

    String getSortOrder();

    ProductDiscountValue getValue();

    static ProductDiscountDraft of(final LocalizedString name, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        return new ProductDiscountDraftImpl(name, description, predicate, value, sortOrder, active);
    }
}
