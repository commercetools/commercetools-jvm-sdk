package io.sphere.sdk.queries;

public final class Fixtures {
    private Fixtures() {
    }

    public static final class Product {

    }

    public static final QueryModel<String> emptyQueryModel = new QueryModel<String>() {
        @Override
        public String getPathSegment() {
            return null;
        }

        @Override
        public QueryModel<String> getParent() {
            return null;
        }
    };

    public static QueryModel<Product> fooQueryModel() {
        return new QueryModelImpl<Product>(null, "foo") {};
    }

    public static  QueryModel<Product> barQueryModel() {
        return new QueryModelImpl<Product>(fooQueryModel(), "bar") {
        };
    }

    public static QueryModel<Product> bazQueryModel() {
        return new QueryModelImpl<Product>(barQueryModel(), "baz") {};
    }
}
