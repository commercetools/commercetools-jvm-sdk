package io.sphere.sdk.categories;

import java.util.Optional;
import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import java.util.Collections;
import java.util.List;

/**
 * Creates a category for unit tests.
 *
 */
public final class CategoryBuilder extends DefaultModelFluentBuilder<CategoryBuilder, Category> {
    private LocalizedStrings name;
    private LocalizedStrings slug;
    private Optional<LocalizedStrings> description = Optional.empty();
    private List<Reference<Category>> ancestors = Collections.emptyList();
    private Optional<Reference<Category>> parent = Optional.empty();
    private Optional<String> orderHint = Optional.empty();
    private Optional<String> externalId = Optional.empty();
    private List<Category> children = Collections.emptyList();
    private List<Category> pathInTree = Collections.emptyList();
    private Optional<LocalizedStrings> metaTitle = Optional.empty();
    private Optional<LocalizedStrings> metaDescription = Optional.empty();
    private Optional<LocalizedStrings> metaKeywords = Optional.empty();

    public static CategoryBuilder of(final String id, final LocalizedStrings name, final LocalizedStrings slug) {
        return new CategoryBuilder(id, name, slug);
    }

    public static CategoryBuilder of(final Category category) {
        return new CategoryBuilder(category.getId(), category.getName(), category.getSlug()).
                version(category.getVersion()).createdAt(category.getCreatedAt()).
                lastModifiedAt(category.getLastModifiedAt()).
                name(category.getName()).slug(category.getSlug()).description(category.getDescription()).
                ancestors(category.getAncestors()).parent(category.getParent()).
                orderHint(category.getOrderHint()).externalId(category.getExternalId()).children(category.getChildren()).
                pathInTree(category.getPathInTree());
    }

    private CategoryBuilder(final String id, final LocalizedStrings name, final LocalizedStrings slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public CategoryBuilder name(final LocalizedStrings name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder slug(final LocalizedStrings slug) {
        this.slug = slug;
        return this;
    }

    public CategoryBuilder description(final Optional<LocalizedStrings> description) {
        this.description = description;
        return this;
    }

    public CategoryBuilder description(final LocalizedStrings description) {
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
    
    public CategoryBuilder externalId(final String externalId) {
        return externalId(Optional.ofNullable(externalId));
    }

    public CategoryBuilder externalId(final Optional<String> externalId) {
        this.externalId = externalId;
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

    public CategoryBuilder metaTitle(final Optional<LocalizedStrings> metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public CategoryBuilder metaTitle(final LocalizedStrings metaTitle) {
        return metaTitle(Optional.of(metaTitle));
    }

    public CategoryBuilder metaDescription(final Optional<LocalizedStrings> metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public CategoryBuilder metaDescription(final LocalizedStrings metaDescription) {
        return metaDescription(Optional.of(metaDescription));
    }

    public CategoryBuilder metaKeywords(final Optional<LocalizedStrings> metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    public CategoryBuilder metaKeywords(final LocalizedStrings metaKeywords) {
        return metaKeywords(Optional.of(metaKeywords));
    }

    @Override
    public Category build() {
        return new CategoryImpl(id, version, createdAt, lastModifiedAt, name, slug, description, ancestors, parent, orderHint, externalId, children, pathInTree, metaTitle, metaDescription, metaKeywords);
    }

    @Override
    protected CategoryBuilder getThis() {
        return this;
    }
}
