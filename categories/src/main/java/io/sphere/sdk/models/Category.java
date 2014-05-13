package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

@Immutable
public class Category {
    private final String id;
    private final long version;
    private final DateTime createdAt;
    private final DateTime lastModifiedAt;
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final List<Reference<Category>> ancestors;
    private final Optional<Reference<Category>> parent;
    private final Optional<String> orderHint;
    @JsonIgnore
    private final List<Category> children;

    private Category(final Builder builder) {
        id = builder.id;
        version = builder.version;
        createdAt = builder.createdAt;
        lastModifiedAt = builder.lastModifiedAt;
        name = builder.name;
        slug = builder.slug;
        description = builder.description;
        ancestors = builder.ancestors;
        parent = builder.parent;
        orderHint = builder.orderHint;
        children = builder.children;
    }

    public static class Builder {
        private String id;
        private long version = 1;
        private DateTime createdAt = new DateTime();
        private DateTime lastModifiedAt = new DateTime();
        private LocalizedString name;
        private LocalizedString slug;
        private Optional<LocalizedString> description = Optional.absent();
        private List<Reference<Category>> ancestors = Collections.emptyList();
        private Optional<Reference<Category>> parent = Optional.absent();
        private Optional<String> orderHint = Optional.absent();
        private List<Category> children = Collections.emptyList();

        private Builder(final String id, final LocalizedString name, final LocalizedString slug) {
            this.id = id;
            this.name = name;
            this.slug = slug;
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder version(final long version) {
            this.version = version;
            return this;
        }

        public Builder createdAt(final DateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder lastModifiedAt(final DateTime lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
            return this;
        }

        public Builder name(final LocalizedString name) {
            this.name = name;
            return this;
        }

        public Builder slug(final LocalizedString slug) {
            this.slug = slug;
            return this;
        }

        public Builder description(final Optional<LocalizedString> description) {
            this.description = description;
            return this;
        }

        public Builder description(final LocalizedString description) {
            this.description = Optional.fromNullable(description);
            return this;
        }

        public Builder ancestors(final List<Reference<Category>> ancestors) {
            this.ancestors = ancestors;
            return this;
        }

        public Builder parent(final Optional<Reference<Category>> parent) {
            this.parent = parent;
            return this;
        }

        public Builder parent(final Reference<Category> parent) {
            this.parent = Optional.fromNullable(parent);
            return this;
        }

        public Builder orderHint(final String orderHint) {
            this.orderHint = Optional.fromNullable(orderHint);
            return this;
        }

        public Builder children(final List<Category> children) {
            this.children = children;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
