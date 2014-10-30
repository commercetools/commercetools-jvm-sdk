package io.sphere.sdk.categories;

import java.util.Optional;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

/**
 * Template for a new category.
 *
 * <p>For construction see {@link io.sphere.sdk.categories.NewCategoryBuilder}.</p>
 */
public class NewCategory extends Base implements WithLocalizedSlug {
    private final LocalizedStrings name;
    private final LocalizedStrings slug;
    private final Optional<LocalizedStrings> description;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    private final Optional<String> externalId;

    NewCategory(final LocalizedStrings name, final LocalizedStrings slug,
                final Optional<LocalizedStrings> description, final Optional<Reference<Category>> parent,
                final Optional<String> orderHint, final Optional<String> externalId) {
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

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    public Optional<Reference<Category>> getParent() {
        return parent;
    }

    public Optional<String> getOrderHint() {
        return orderHint;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }
}
