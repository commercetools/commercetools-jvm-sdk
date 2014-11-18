package io.sphere.sdk.categories;

import java.util.Objects;
import java.util.Optional;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 * Creates templates for new categories.
 *
 */
public class CategoryDraftBuilder implements Builder<CategoryDraft> {
    private LocalizedStrings name;
    private LocalizedStrings slug;
    private Optional<LocalizedStrings> description = Optional.empty();
    private Optional<Reference<Category>> parent = Optional.empty();
    private Optional<String> orderHint = Optional.empty();
    private Optional<String> externalId = Optional.empty();

    private CategoryDraftBuilder(final LocalizedStrings name, final LocalizedStrings slug) {
        this.name = name;
        this.slug = slug;
    }

    public static CategoryDraftBuilder of(final LocalizedStrings name, final LocalizedStrings slug) {
        return new CategoryDraftBuilder(name, slug);
    }

    public CategoryDraftBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public CategoryDraftBuilder description(final LocalizedStrings description) {
        return description(Optional.ofNullable(description));
    }

    public CategoryDraftBuilder parent(final Optional<Reference<Category>> parent) {
        this.parent = parent;
        return this;
    }

    public CategoryDraftBuilder parent(final Referenceable<Category> parent) {
        return parent(Optional.ofNullable(parent.toReference()));
    }

    public CategoryDraftBuilder orderHint(final Optional<String> orderHint) {
        this.orderHint = orderHint;
        return this;
    }

    public CategoryDraftBuilder orderHint(final String orderHint) {
        Objects.requireNonNull(orderHint);
        return orderHint(Optional.of(orderHint));
    }
    
    public CategoryDraftBuilder externalId(final Optional<String> externalId) {
        this.externalId = externalId;
        return this;
    }

    public CategoryDraftBuilder externalId(final String externalId) {
        Objects.requireNonNull(externalId);
        return externalId(Optional.of(externalId));
    }

    public CategoryDraft build() {
        return new CategoryDraft(name, slug, description, parent, orderHint, externalId);
    }
}
