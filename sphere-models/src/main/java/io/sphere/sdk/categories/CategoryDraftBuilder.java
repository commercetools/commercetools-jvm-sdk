package io.sphere.sdk.categories;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Creates templates for new categories.
 *
 */
public class CategoryDraftBuilder implements Builder<CategoryDraft> {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    @Nullable
    private LocalizedStrings description;
    @Nullable
    private Reference<Category> parent;
    @Nullable
    private String orderHint;
    @Nullable
    private String externalId;

    private CategoryDraftBuilder(final LocalizedStrings name, final LocalizedStrings slug) {
        this.name = name;
        this.slug = slug;
    }

    public static CategoryDraftBuilder of(final LocalizedStrings name, final LocalizedStrings slug) {
        return new CategoryDraftBuilder(name, slug);
    }

    public CategoryDraftBuilder description(@Nullable final LocalizedStrings description) {
        this.description = description;
        return this;
    }

    public CategoryDraftBuilder parent(@Nullable final Referenceable<Category> parent) {
        this.parent = Optional.ofNullable(parent).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public CategoryDraftBuilder orderHint(@Nullable final String orderHint) {
        this.orderHint = orderHint;
        return this;
    }
    
    public CategoryDraftBuilder externalId(@Nullable final String externalId) {
        this.externalId = externalId;
        return this;
    }

    public CategoryDraft build() {
        return new CategoryDraft(name, slug, description, parent, orderHint, externalId);
    }
}
