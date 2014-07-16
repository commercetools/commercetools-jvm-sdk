package io.sphere.sdk.categories;

import java.util.Optional;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

public class NewCategoryBuilder implements Builder<NewCategory> {
    private LocalizedString name;
    private LocalizedString slug;
    private Optional<LocalizedString> description = Optional.empty();
    private Optional<Reference<Category>> parent = Optional.empty();
    private Optional<String> orderHint = Optional.empty();

    private NewCategoryBuilder(final LocalizedString name, final LocalizedString slug) {
        this.name = name;
        this.slug = slug;
    }

    public static NewCategoryBuilder create(final LocalizedString name, final LocalizedString slug) {
        return new NewCategoryBuilder(name, slug);
    }

    public NewCategoryBuilder description(final Optional<LocalizedString> description) {
        this.description = description;
        return this;
    }

    public NewCategoryBuilder description(final LocalizedString description) {
        return description(Optional.ofNullable(description));
    }

    public NewCategoryBuilder parent(final Optional<Reference<Category>> parent) {
        this.parent = parent;
        return this;
    }

    public NewCategoryBuilder parent(final Reference<Category> parent) {
        return parent(Optional.ofNullable(parent));
    }

    public NewCategoryBuilder orderHint(final Optional<String> orderHint) {
        this.orderHint = orderHint;
        return this;
    }

    public NewCategoryBuilder orderHint(final String orderHint) {
        return orderHint(Optional.ofNullable(orderHint));
    }

    public NewCategory build() {
        return new NewCategory(name, slug, description, parent, orderHint);
    }
}
