package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

class ProductDiscountImpl extends DefaultModelImpl<ProductDiscount> implements ProductDiscount {
    private final LocalizedStrings name;
    private final Optional<LocalizedStrings> description;
    private final ProductDiscountValue value;
    private final String predicate;
    private final String sortOrder;
    private final boolean isActive;
    private final List<Reference<Object>> references;

    ProductDiscountImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                        final LocalizedStrings name, final Optional<LocalizedStrings> description,
                        final ProductDiscountValue value, final String predicate, final String sortOrder,
                        final boolean isActive, final List<Reference<Object>> references) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.description = description;
        this.value = value;
        this.predicate = predicate;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.references = references;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    public ProductDiscountValue getValue() {
        return value;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Reference<Object>> getReferences() {
        return references;
    }

    @Override
    public Reference<ProductDiscount> toReference() {
        return Reference.of(ProductDiscount.typeId(), this);
    }
}
