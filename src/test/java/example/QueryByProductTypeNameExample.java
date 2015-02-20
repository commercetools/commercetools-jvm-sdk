package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.attributes.EnumAttributeDefinition;
import io.sphere.sdk.attributes.EnumType;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class QueryByProductTypeNameExample {
    private final SphereClient client = null;//TODO

    public void queryByNameExample() {
        CompletableFuture<PagedQueryResult<ProductType>> queryResultFuture = client.execute(ProductTypeQuery.of().byName("t-shirt"));
        CompletableFuture<List<PlainEnumValue>> possibleValuesFuture = queryResultFuture.thenApply(
                queryResult -> extractPossibleEnumValuesForSize(queryResult));
    }

    private static List<PlainEnumValue> extractPossibleEnumValuesForSize(PagedQueryResult<ProductType> pagedQueryResult) {
        ProductType productType = pagedQueryResult.
                head().
                orElseThrow(() -> new MissingProductTypeException());
        EnumAttributeDefinition sizeAttribute = productType.
                getAttribute("size", EnumAttributeDefinition.class).
                orElseThrow(() -> new MissingAttributeException());
        EnumType attributeType = sizeAttribute.getAttributeType();
        return attributeType.getValues();
    }

    public static class MissingProductTypeException extends RuntimeException {
        private static final long serialVersionUID = 4954918890077093840L;
    }

    public static class MissingAttributeException extends RuntimeException {
        private static final long serialVersionUID = 4954918890077093841L;
    }
}
