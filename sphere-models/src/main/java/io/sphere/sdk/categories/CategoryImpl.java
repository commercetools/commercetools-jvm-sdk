package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

class CategoryImpl extends DefaultModelImpl<Category> implements Category {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    @Nullable
    private final LocalizedStrings description;
    private final List<Reference<Category>> ancestors;
    @Nullable
    private final Reference<Category> parent;
    @Nullable
    private final String orderHint;
    @Nullable
    private final String externalId;
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
                 @Nullable final LocalizedStrings description,
                 final List<Reference<Category>> ancestors,
                 @Nullable final Reference<Category> parent,
                 @Nullable final String orderHint, @Nullable final String externalId,
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

    @Nullable
    @Override
    public LocalizedStrings getDescription() {
        return description;
    }

    @Nullable
    @Override
    public List<Reference<Category>> getAncestors() {
        return ancestors;
    }

    @Nullable
    @Override
    public Reference<Category> getParent() {
        return parent;
    }

    @Nullable
    @Override
    public String getOrderHint() {
        return orderHint;
    }

    @Nullable
    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public String toString() {
        return Category.toString(this);
    }

    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }

    @Nullable
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }
}
