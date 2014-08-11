package io.sphere.sdk.categories;

import java.util.Optional;
import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Collections;
import java.util.List;

public final class CategoryBuilder extends DefaultModelFluentBuilder<CategoryBuilder, Category> {
    private LocalizedString name;
    private LocalizedString slug;
    private Optional<LocalizedString> description = Optional.empty();
    private List<Reference<Category>> ancestors = Collections.emptyList();
    private Optional<Reference<Category>> parent = Optional.empty();
    private Optional<String> orderHint = Optional.empty();
    private List<Category> children = Collections.emptyList();
    private List<Category> pathInTree = Collections.emptyList();

    public static CategoryBuilder of(final String id, final LocalizedString name, final LocalizedString slug) {
        return new CategoryBuilder(id, name, slug);
    }

    public static CategoryBuilder of(final Category category) {
        return new CategoryBuilder(category.getId(), category.getName(), category.getSlug()).
                version(category.getVersion()).createdAt(category.getCreatedAt()).
                lastModifiedAt(category.getLastModifiedAt()).
                name(category.getName()).slug(category.getSlug()).description(category.getDescription()).
                ancestors(category.getAncestors()).parent(category.getParent()).
                orderHint(category.getOrderHint()).children(category.getChildren()).
                pathInTree(category.getPathInTree());
    }

    private CategoryBuilder(final String id, final LocalizedString name, final LocalizedString slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public CategoryBuilder name(final LocalizedString name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder slug(final LocalizedString slug) {
        this.slug = slug;
        return this;
    }

    public CategoryBuilder description(final Optional<LocalizedString> description) {
        this.description = description;
        return this;
    }

    public CategoryBuilder description(final LocalizedString description) {
        this.description = Optional.ofNullable(description);
        return this;
    }

    public CategoryBuilder ancestors(final List<Reference<Category>> ancestors) {
        this.ancestors = ancestors;
        return this;
    }

    public CategoryBuilder parent(final Optional<Reference<Category>> parent) {
        this.parent = parent;
        return this;
    }

    public CategoryBuilder parent(final Referenceable<Category> parent) {
        this.parent = Optional.ofNullable(parent.toReference());
        return this;
    }

    public CategoryBuilder orderHint(final String orderHint) {
        return orderHint(Optional.ofNullable(orderHint));
    }

    public CategoryBuilder orderHint(final Optional<String> orderHint) {
        this.orderHint = orderHint;
        return this;
    }

    public CategoryBuilder children(final List<Category> children) {
        this.children = children;
        return this;
    }

    public CategoryBuilder pathInTree(final List<Category> pathInTree) {
        this.pathInTree = pathInTree;
        return this;
    }

    @Override
    public Category build() {
        return new CategoryImpl(id, version, createdAt, lastModifiedAt, name, slug, description, ancestors, parent, orderHint, children, pathInTree);
    }

    @Override
    protected CategoryBuilder getThis() {
        return this;
    }
}
