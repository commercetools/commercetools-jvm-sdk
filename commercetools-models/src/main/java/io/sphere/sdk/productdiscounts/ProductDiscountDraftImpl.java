package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

final class ProductDiscountDraftImpl extends Base implements ProductDiscountDraft {
    private final ProductDiscountPredicate predicate;
    private final LocalizedString name;
    private final String sortOrder;
    private final ProductDiscountValue value;
    private final LocalizedString description;
    private final Boolean active;

    @JsonCreator
    ProductDiscountDraftImpl(final LocalizedString name, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        this.active = active;
        this.predicate = predicate;
        this.name = name;
        this.sortOrder = sortOrder;
        this.value = value;
        this.description = description;
    }

    @Override
    @JsonProperty("isActive")
    public Boolean isActive() {
        return active;
    }

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public String getPredicate() {
        return predicate.toSpherePredicate();
    }

    @Override
    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public ProductDiscountValue getValue() {
        return value;
    }
}
