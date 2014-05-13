package io.sphere.client;

/** Specifies how products are sorted in {@link SearchRequest#sort}.
 * Use e.g. {@code ProductSort.price.asc}, {@code ProductSort.name.desc}. */
public abstract class ProductSort {
    private static abstract class DirectedProductSearch extends ProductSort {
        private final SortDirection direction;

        protected DirectedProductSearch(SortDirection direction) {
            this.direction = direction;
        }

        public SortDirection getDirection() { return direction; }

        @Override
        public String toString() {
            return getClass().getCanonicalName() + "{" +
                    "direction=" + direction +
                    '}';
        }
    }

    // ---------------------------
    // Relevance
    // ---------------------------
    /** Specifies sorting by descending relevance (always from the most relevant products). This is the default. */
    public static final Relevance relevance = new Relevance();
    /** Specifies sorting by descending relevance (always from the most relevant products). This is the default. */
    public static class Relevance extends ProductSort {
        private Relevance() {}
        @Override public String toString() {
            return "[Relevance]";
        }
    }
    // ---------------------------
    // Price
    // ---------------------------
    /** Specifies sorting by price, ascending or descending. */
    public static final PriceSorts price = new PriceSorts();
    /** Specifies sorting by price, ascending or descending. */
    public static class PriceSorts {
        /** Specifies sorting by price, descending. */
        public final Price desc = new Price(SortDirection.DESC);
        /** Specifies sorting by price, ascending. */
        public final Price asc = new Price(SortDirection.ASC);
    }
    /** Specifies sorting by price. */
    public static class Price extends DirectedProductSearch {
        private Price(SortDirection direction) {
            super(direction);
        }
    }

    // ---------------------------
    // Name
    // ---------------------------
    /** Specifies sorting by name, ascending or descending. */
    public static final NameSorts name = new NameSorts();
    /** Specifies sorting by name, ascending or descending. */
    public static class NameSorts {
        /** Specifies sorting by name, descending. */
        public final Name desc = new Name(SortDirection.DESC);
        /** Specifies sorting by name, ascending. */
        public final Name asc = new Name(SortDirection.ASC);
    }
    /** Specifies sorting by name. */
    public static class Name extends DirectedProductSearch {
        private Name(SortDirection direction) {
            super(direction);
        }
    }
}
