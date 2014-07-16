package example;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.attributes.EnumAttributeDefinition;
import io.sphere.sdk.producttypes.attributes.EnumType;
import io.sphere.sdk.producttypes.attributes.PlainEnumValue;
import io.sphere.sdk.queries.PagedQueryResult;
import play.libs.F;

import java.util.List;

public class QueryByProductTypeNameExample extends ExampleWithClient {
    public static void queryByNameExample() {
        F.Promise<PagedQueryResult<ProductType>> queryResultPromise = client.execute(ProductType.query().byName("t-shirt"));
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

    public static class MissingProductTypeException extends RuntimeException { }
    public static class MissingAttributeException extends RuntimeException { }
}
