package example;

import java.util.Optional;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;
import io.sphere.sdk.producttypes.attributes.EnumAttributeDefinition;
import io.sphere.sdk.producttypes.attributes.EnumType;
import io.sphere.sdk.producttypes.attributes.PlainEnumValue;
import io.sphere.sdk.queries.PagedQueryResult;
import play.libs.F;

import java.util.Collections;
import java.util.List;

public class QueryByProductTypeNameExample extends ExampleWithClient {
    public static void queryByNameExample() {
        F.Promise<PagedQueryResult<ProductType>> queryResultPromise = client.execute(ProductType.query().byName("t-shirt"));
        F.Promise<List<PlainEnumValue>> possibleValuesPromise = queryResultPromise.map(
                queryResult -> extractPossibleEnumValuesForSize(queryResult));
    }

    private static List<PlainEnumValue> extractPossibleEnumValuesForSize(PagedQueryResult<ProductType> pagedQueryResult) {
        Optional<ProductType> productTypeOption = pagedQueryResult.head();
        final Optional<List<PlainEnumValue>> valuesOption = productTypeOption.map(productType -> extractSizeEnumValues(productType));
        return valuesOption.orElse(Collections.<PlainEnumValue>emptyList());
    }

    private static List<PlainEnumValue> extractSizeEnumValues(ProductType productType) {
        List<AttributeDefinition> attributes = productType.getAttributes();
        java.util.Optional<EnumAttributeDefinition> sizeAttribute =
                AttributeDefinition.findByName(attributes, "size", EnumAttributeDefinition.class);
        java.util.Optional<List<PlainEnumValue>> valuesOptional = sizeAttribute.map(enumAttribute -> {
            EnumType attributeType = enumAttribute.getAttributeType();
            List<PlainEnumValue> values = attributeType.getValues();
            return values;
        });
        return valuesOptional.orElse(Collections.<PlainEnumValue>emptyList());
    }
}
