package io.sphere.sdk.queries;

import java.util.Optional;

public final class Fixtures {
    private Fixtures() {
    }

    public static final class Product {

    }


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
