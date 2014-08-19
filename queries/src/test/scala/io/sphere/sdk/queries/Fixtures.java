package io.sphere.sdk.queries;

import java.util.Optional;

public final class Fixtures {
    private Fixtures() {
    }

    public static final class Product {

    }

    public static final QueryModel<String> emptyQueryModel = new QueryModel<String>() {
        @Override
        public Optional<String> getPathSegment() {
            return Optional.empty();
        }

        @Override
        public Optional<? extends QueryModel<String>> getParent() {
            return Optional.empty();
        }
    };

    public static QueryModel<Product> fooQueryModel() {
        return new QueryModelImpl<Product>(Optional.<QueryModel<Product>>empty(), "foo") {};
    }

    public static  QueryModel<Product> barQueryModel() {
        return new QueryModelImpl<Product>(Optional.of(fooQueryModel()), "bar") {
        };
    }

    public static QueryModel<Product> bazQueryModel() {
        return new QueryModelImpl<Product>(Optional.of(barQueryModel()), "baz") {};
    }
}
