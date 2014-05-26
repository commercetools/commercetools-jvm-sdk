package io.sphere.sdk.categories;

import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

public class NewCategoryBuilder {
    private LocalizedString name;
    private LocalizedString slug;
    private Optional<LocalizedString> description = Optional.absent();
    private Optional<Reference<Category>> parent = Optional.absent();
    private Optional<String> orderHint = Optional.absent();

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
        return description(Optional.fromNullable(description));
    }

    public NewCategoryBuilder parent(final Optional<Reference<Category>> parent) {
        this.parent = parent;
        return this;
    }

    public NewCategoryBuilder parent(final Reference<Category> parent) {
        return parent(Optional.fromNullable(parent));
    }

    public NewCategoryBuilder orderHint(final Optional<String> orderHint) {
        this.orderHint = orderHint;
        return this;
    }

    public NewCategoryBuilder orderHint(final String orderHint) {
        return orderHint(Optional.fromNullable(orderHint));
    }

    public NewCategory build() {
        return new NewCategory(name, slug, description, parent, orderHint);
    }
}
