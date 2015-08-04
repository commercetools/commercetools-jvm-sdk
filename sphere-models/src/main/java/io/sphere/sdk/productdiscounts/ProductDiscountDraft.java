package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

public class ProductDiscountDraft extends Base {
    private final ProductDiscountPredicate predicate;
    private final LocalizedString name;
    private final String sortOrder;
    private final ProductDiscountValue value;
    private final LocalizedString description;
    private final Boolean active;

    private ProductDiscountDraft(final LocalizedString name, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        this.active = active;
        this.predicate = predicate;
        this.name = name;
        this.sortOrder = sortOrder;
        this.value = value;
        this.description = description;
    }

    @JsonProperty("isActive")
    public Boolean isActive() {
        return active;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public LocalizedString getName() {
        return name;
    }

    public String getPredicate() {
        return predicate.toSpherePredicate();
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public ProductDiscountValue getValue() {
        return value;
    }

    public static ProductDiscountDraft of(final LocalizedString name, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        return new ProductDiscountDraft(name, description, predicate, value, sortOrder, active);
    }
}
