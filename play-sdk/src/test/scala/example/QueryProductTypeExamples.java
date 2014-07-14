package example;

import com.google.common.base.Function;
import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;
import io.sphere.sdk.producttypes.attributes.EnumAttributeDefinition;
import io.sphere.sdk.producttypes.attributes.PlainEnumValue;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import play.libs.F;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QueryProductTypeExamples {
    private PlayJavaClient client;
    private ProductType productType;

    public void queryAll() {
        final F.Promise<PagedQueryResult<ProductType>> result = client.execute(ProductType.query());
    }

    public void queryByName() {
        //this example loads the t-shirt attributes and extracts possible size values
        F.Promise<PagedQueryResult<ProductType>> result = client.execute(ProductType.query().byName("t-shirt"));
        Function<ProductType, List<PlainEnumValue>> function = productType -> {
            Optional<EnumAttributeDefinition> sizeAttribute = AttributeDefinition.findByName(productType.getAttributes(), "size", EnumAttributeDefinition.class);
            return sizeAttribute.
                    map(attrib -> attrib.getAttributeType().getValues()).
                    orElse(Collections.<PlainEnumValue>emptyList());

        };
        F.Promise<List<PlainEnumValue>> possibleSizeValues = result.
                map(pagedResult -> pagedResult.head().transform(function).or(Collections.emptyList()));
    }

    public void queryByAttributeName() {
        Predicate<ProductTypeQueryModel<ProductType>> hasSizeAttribute = ProductTypeQueryModel.get().attributes().name().is("size");
        F.Promise<PagedQueryResult<ProductType>> result = client.execute(ProductType.query().withPredicate(hasSizeAttribute));
    }

    public void delete() {
        final ProductTypeDeleteByIdCommand command = new ProductTypeDeleteByIdCommand(productType);
        final F.Promise<ProductType> deletedProductType = client.execute(command);
    }
}
