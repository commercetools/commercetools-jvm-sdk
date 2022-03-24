package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.products.queries.ProductProjectionQueryModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.productselections.ProductSelectionAssignment;


import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

class ProductSelectionAssignmentInStoreQueryImpl extends MetaModelQueryDslImpl<ProductSelectionAssignment, ProductSelectionAssignmentInStoreQuery, ProductSelectionAssignmentQueryModel> implements ProductSelectionAssignmentInStoreQuery {

    ProductSelectionAssignmentInStoreQueryImpl(final String storeKey) {
        super("/in-store/key=" + urlEncode(storeKey) + "/product-selection-assignments", ProductSelectionAssignmentInStoreQuery.resultTypeReference(), ProductSelectionAssignmentQueryModel.of(), ProductSelectionAssignmentInStoreQueryImpl::new);
    }

}
