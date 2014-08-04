package example;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeQuery;
import io.sphere.sdk.producttypes.attributes.EnumAttributeDefinition;
import io.sphere.sdk.producttypes.attributes.EnumType;
import io.sphere.sdk.producttypes.attributes.PlainEnumValue;
import io.sphere.sdk.queries.PagedQueryResult;
import play.libs.F;

import java.util.List;

public class QueryByProductTypeNameExample extends ExampleWithClient {
    public static void queryByNameExample() {
        F.Promise<PagedQueryResult<ProductType>> queryResultPromise = client.execute(new ProductTypeQuery().byName("t-shirt"));
        F.Promise<List<PlainEnumValue>> possibleValuesPromise = queryResultPromise.map(
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
