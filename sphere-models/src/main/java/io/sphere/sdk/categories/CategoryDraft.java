package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import javax.annotation.Nullable;

/**
 * Template for a new category.
 *
 * <p>For construction see {@link CategoryDraftBuilder}.</p>
 */
public class CategoryDraft extends Base implements WithLocalizedSlug {
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

    CategoryDraft(final LocalizedString name, final LocalizedString slug,
                  @Nullable final LocalizedString description, @Nullable final Reference<Category> parent,
                  @Nullable final String orderHint, @Nullable final String externalId) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.orderHint = orderHint;
        this.externalId = externalId;
    }

    public LocalizedString getName() {
        return name;
    }

    @Override
    public LocalizedString getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public Reference<Category> getParent() {
        return parent;
    }

    @Nullable
    public String getOrderHint() {
        return orderHint;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }
}
