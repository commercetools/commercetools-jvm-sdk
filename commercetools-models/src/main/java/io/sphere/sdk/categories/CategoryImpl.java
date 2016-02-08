package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

class CategoryImpl extends ResourceImpl<Category> implements Category {
    private final LocalizedString name;
    private final LocalizedString slug;
    @Nullable
    private final LocalizedString description;
    private final List<Reference<Category>> ancestors;
    @Nullable
    private final Reference<Category> parent;
    @Nullable
    private final String orderHint;
    @Nullable
    private final String externalId;
    @Nullable
    private final LocalizedString metaTitle;
    @Nullable
    private final LocalizedString metaDescription;
    @Nullable
    private final LocalizedString metaKeywords;
    @Nullable
    private final CustomFields custom;

    @JsonCreator
    CategoryImpl(final String id,
                 final Long version,
                 final ZonedDateTime createdAt,
                 final ZonedDateTime lastModifiedAt,
                 final LocalizedString name,
                 final LocalizedString slug,
                 @Nullable final LocalizedString description,
                 final List<Reference<Category>> ancestors,
                 @Nullable final Reference<Category> parent,
                 @Nullable final String orderHint, @Nullable final String externalId,
                 @Nullable final LocalizedString metaTitle, @Nullable final LocalizedString metaDescription,
                 @Nullable final LocalizedString metaKeywords, @Nullable final CustomFields custom) {
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
        this.custom = custom;
    }

    @Override
    public LocalizedString getName() {
        return name;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Nullable
    @Override
    public LocalizedString getDescription() {
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
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }

    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }
}
