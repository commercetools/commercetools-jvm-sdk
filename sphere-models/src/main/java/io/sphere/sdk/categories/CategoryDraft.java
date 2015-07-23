package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import javax.annotation.Nullable;

/**
 * Template for a new category.
 *
 * <p>For construction see {@link CategoryDraftBuilder}.</p>
 */
public class CategoryDraft extends Base implements WithLocalizedSlug {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    @Nullable
    private final LocalizedStrings description;
    @Nullable
    private final Reference<Category> parent;
    @Nullable
    private final String orderHint;
    @Nullable
    private final String externalId;

    CategoryDraft(final LocalizedStrings name, final LocalizedStrings slug,
                  @Nullable final LocalizedStrings description, @Nullable final Reference<Category> parent,
                  @Nullable final String orderHint, @Nullable final String externalId) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.parent = parent;
        this.orderHint = orderHint;
        this.externalId = externalId;
    }

    public LocalizedStrings getName() {
        return name;
    }

    @Override
    public LocalizedStrings getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedStrings getDescription() {
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
