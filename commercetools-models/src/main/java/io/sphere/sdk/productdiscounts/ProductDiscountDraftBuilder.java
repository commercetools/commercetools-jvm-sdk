package io.sphere.sdk.productdiscounts;

import javax.annotation.Nullable;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

public class ProductDiscountDraftBuilder extends Base implements Builder<ProductDiscountDraft> {

    private LocalizedString name;
    @Nullable
    private LocalizedString description;
    private ProductDiscountValue value;
    private ProductDiscountPredicate predicate;
    private String sortOrder;
    private Boolean isActive;

    private ProductDiscountDraftBuilder() {
    }

    public static ProductDiscountDraftBuilder of(final ProductDiscountDraft productDiscountDraft) {
        return of()
                .name(productDiscountDraft.getName())
                .description(productDiscountDraft.getDescription())
                .value(productDiscountDraft.getValue())
                .predicate(ProductDiscountPredicate.of(productDiscountDraft.getPredicate()))
                .sortOrder(productDiscountDraft.getSortOrder())
                .isActive(productDiscountDraft.isActive());
    }

    public static ProductDiscountDraftBuilder of() {
        return new ProductDiscountDraftBuilder();
    }

    public ProductDiscountDraftBuilder name(final LocalizedString name) {
        this.name = name;
        return this;
    }

    public ProductDiscountDraftBuilder description(final LocalizedString description) {
        this.description = description;
        return this;
    }

    public ProductDiscountDraftBuilder value(ProductDiscountValue value) {
        this.value = value;
        return this;
    }
    
    public ProductDiscountDraftBuilder predicate(final ProductDiscountPredicate predicate) {
        this.predicate = predicate;
        return this;
    }
    

    public ProductDiscountDraftBuilder sortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public ProductDiscountDraftBuilder isActive(final Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @Override
    public ProductDiscountDraft build() {
        return new ProductDiscountDraftImpl(name, description, predicate, value, sortOrder, isActive);
    }
}
