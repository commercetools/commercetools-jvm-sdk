package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

class ProductDiscountImpl extends ResourceImpl<ProductDiscount> implements ProductDiscount {
    private final LocalizedString name;
    private final LocalizedString description;
    private final ProductDiscountValue value;
    private final String predicate;
    private final String sortOrder;
    private final Boolean isActive;
    private final List<Reference<JsonNode>> references;

    @JsonCreator
    ProductDiscountImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                        final LocalizedString name, final LocalizedString description,
                        final ProductDiscountValue value, final String predicate, final String sortOrder,
                        final boolean isActive, final List<Reference<JsonNode>> references) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.description = description;
        this.value = value;
        this.predicate = predicate;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.references = references;
    }

    public LocalizedString getName() {
        return name;
    }

    @Nullable
    public LocalizedString getDescription() {
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

    public Boolean isActive() {
        return isActive;
    }

    public List<Reference<JsonNode>> getReferences() {
        return references;
    }

    @Override
    public Reference<ProductDiscount> toReference() {
        return Reference.of(ProductDiscount.referenceTypeId(), this);
    }
}
