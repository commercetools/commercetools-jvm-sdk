package io.sphere.sdk.products;

import java.util.List;

public class QueryResult {
        public final List<ProductProjection> productProjections;
        public final List<ProductProjection> sortedFromSearchForCategory1;
        public final List<ProductProjection> sortedFromSearchForCategory2;
        public final List<ProductProjection> sortedFromQueryForCategory1;
        public final List<ProductProjection> sortedFromQueryForCategory2;

        public QueryResult(List<ProductProjection> productProjections,
                           List<ProductProjection> sortedFromSearchForCategory1,
                           List<ProductProjection> sortedFromSearchForCategory2,
                           List<ProductProjection> sortedFromQueryForCategory1,
                           List<ProductProjection> sortedFromQueryForCategory2) {
            this.productProjections = productProjections;
            this.sortedFromSearchForCategory1 = sortedFromSearchForCategory1;
            this.sortedFromSearchForCategory2 = sortedFromSearchForCategory2;
            this.sortedFromQueryForCategory1 = sortedFromQueryForCategory1;
            this.sortedFromQueryForCategory2 = sortedFromQueryForCategory2;
        }
    }