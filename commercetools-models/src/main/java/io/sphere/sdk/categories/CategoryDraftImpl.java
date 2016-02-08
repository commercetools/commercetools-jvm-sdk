package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

final class CategoryDraftImpl extends Base implements CategoryDraft {
    private final LocalizedString name;
    private final LocalizedString slug;
    @Nullable
    private final LocalizedString description;
    @Nullable
    private final Reference<Category> parent;
    @Nullable
    private final String orderHint;
    @Nullable
    private final String externalId;
    @Nullable
    private final CustomFieldsDraft custom;

    CategoryDraftImpl(final LocalizedString name, final LocalizedString slug,
                      @Nullable final LocalizedString description, @Nullable final Reference<Category> parent,
                      @Nullable final String orderHint, @Nullable final String externalId,
                      @Nullable final CustomFieldsDraft custom) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.orderHint = orderHint;
        this.externalId = externalId;
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

    @Override
    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Override
    @Nullable
    public Reference<Category> getParent() {
        return parent;
    }

    @Override
    @Nullable
    public String getOrderHint() {
        return orderHint;
    }

    @Override
    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }
}
