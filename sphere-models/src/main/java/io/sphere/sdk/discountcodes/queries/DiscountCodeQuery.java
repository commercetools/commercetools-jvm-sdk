package io.sphere.sdk.discountcodes.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.queries.CategoryQueryModel;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;

/**

 {@doc.gen summary discount codes}

 */
public class DiscountCodeQuery extends DefaultModelQuery<DiscountCode> {
    private DiscountCodeQuery(){
        super(DiscountCodeEndpoint.ENDPOINT.endpoint(), resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<DiscountCode>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<DiscountCode>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<DiscountCode>>";
            }
        };
    }

    public static DiscountCodeQuery of() {
        return new DiscountCodeQuery();
    }

    public static DiscountCodeQueryModel model() {
        return DiscountCodeQueryModel.get();
    }
}
