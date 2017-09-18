package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.search.PagedSearchResult;

public class TypeReferenceTestImpl extends TypeReference<PagedSearchResult<ProductProjection>>{

    private TypeReferenceTestImpl(){}

    public static TypeReferenceTestImpl of(){return new TypeReferenceTestImpl();}


}
