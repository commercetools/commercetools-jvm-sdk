package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

class CategoryImpl extends DefaultModelImpl<Category> implements Category {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    private final Optional<LocalizedStrings> description;
    private final List<Reference<Category>> ancestors;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    private final Optional<String> externalId;
    @Nullable
    private final LocalizedStrings metaTitle;
    @Nullable
    private final LocalizedStrings metaDescription;
    @Nullable
    private final LocalizedStrings metaKeywords;

    @JsonCreator
    CategoryImpl(final String id,
                 final long version,
                 final ZonedDateTime createdAt,
                 final ZonedDateTime lastModifiedAt,
                 final LocalizedStrings name,
                 final LocalizedStrings slug,
                 final Optional<LocalizedStrings> description,
                 final List<Reference<Category>> ancestors,
                 final Optional<Reference<Category>> parent,
                 final Optional<String> orderHint, final Optional<String> externalId,
                 final LocalizedStrings metaTitle, final LocalizedStrings metaDescription, final LocalizedStrings metaKeywords) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.ancestors = ancestors;
        this.parent = parent;
        this.orderHint = orderHint;
        this.externalId = externalId;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
    }

    @Override
    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Override
    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    @Override
    public List<Reference<Category>> getAncestors() {
        return ancestors;
    }

    @Override
    public Optional<Reference<Category>> getParent() {
        return parent;
    }

    @Override
    public Optional<String> getOrderHint() {
        return orderHint;
    }

    @Override
    public Optional<String> getExternalId() {
        return externalId;
    }

    @Override
    public String toString() {
        return Category.toString(this);
    }

    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }

    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }
}
