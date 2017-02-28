package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

public final class ProductDiscountDraftBuilder extends ProductDiscountDraftBuilderBase<ProductDiscountDraftBuilder> {
    ProductDiscountDraftBuilder(final Boolean active, @Nullable final LocalizedString description, final LocalizedString name, final String predicate, final String sortOrder, final ProductDiscountValue value) {
        super(active, description, name, predicate, sortOrder, value);
    }

    public static ProductDiscountDraftBuilder of() {
        return new ProductDiscountDraftBuilder(null, null, null, null, null, null);
    }

    public static ProductDiscountDraftBuilder of(final Boolean active,
                                                 final LocalizedString description, final LocalizedString name,
                                                 final String predicate, final String sortOrder, final ProductDiscountValue value) {
        return new ProductDiscountDraftBuilder(active, description, name, predicate, sortOrder, value);
    }

    public static ProductDiscountDraftBuilder of(final ProductDiscountDraft template) {
        return new ProductDiscountDraftBuilder(template.isActive(), template.getDescription(), template.getName(), template.getPredicate(), template.getSortOrder(), template.getValue());
    }

    public ProductDiscountDraftBuilder predicate(final ProductDiscountPredicate predicate) {
        this.predicate = predicate.toSpherePredicate();
        return this;
    }
}
