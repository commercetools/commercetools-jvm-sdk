package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.producttypes.attributes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {


    @Override
    protected ClientRequest<ProductType> deleteCommand(Versioned item) {
        return new ProductTypeDeleteCommand(item);
    }

    @Override
    protected ClientRequest<ProductType> newCreateCommandForName(String name) {
        return new ProductTypeCreateCommand(NewProductType.of(name, "desc"));
    }

    @Override
    protected String extractName(ProductType instance) {
        return instance.getName();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryRequestForQueryAll() {
        return ProductTypes.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForName(String name) {
        return ProductTypes.query().byName(name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<ProductType>> queryObjectForNames(List<String> names) {
        return ProductTypes.query().withPredicate(ProductTypeQueryModel.get().name().isOneOf(names));
    }

    @Test
    public void createTextAttribute() throws Exception {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = "text-attribute";

        cleanUpByName(productTypeName);

        final LocalizedString attributeLabel = en("label");
        final AttributeDefinition textAttribute = TextAttributeDefinitionBuilder.
                of(attributeName, attributeLabel, TextInputHint.MultiLine).
                attributeConstraint(AttributeConstraint.CombinationUnique).
                searchable(false).
                required(true).
                build();
        final List<AttributeDefinition> attributes = Arrays.asList(textAttribute);

        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(NewProductType.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = client.execute(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition attributeDefinition = productType.getAttributes().get(0);

        assertThat(attributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(attributeDefinition.getLabel()).isEqualTo(attributeLabel);
        assertThat(attributeDefinition.getIsRequired()).isTrue();
        assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.CombinationUnique);
        assertThat(attributeDefinition.getIsSearchable()).isFalse();
        assertThat(attributeDefinition.getAttributeType()).isInstanceOf(TextType.class);

        cleanUpByName(productTypeName);
    }
}
