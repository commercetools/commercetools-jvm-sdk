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
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeIntegrationTest extends QueryIntegrationTest<ProductType> {


    public static final TextInputHint TEXT_INPUT_HINT = TextInputHint.MultiLine;
    public static final LocalizedString LABEL = en("label");

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

    static interface AttributeTestCase {
        String attributeName();

        AttributeDefinition getAttributeDefinition();

        Class<? extends AttributeType> getExpectedAttributeTypeClass();

        void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition);
    }

    Consumer<AttributeTestCase> attributeTestFunction = conf -> {
        final String productTypeName = "product type name";
        final String productTypeDescription = "product type description";
        final String attributeName = conf.attributeName();

        cleanUpByName(productTypeName);


        final AttributeDefinition textAttribute = conf.getAttributeDefinition();
        final List<AttributeDefinition> attributes = Arrays.asList(textAttribute);

        final ProductTypeCreateCommand command = new ProductTypeCreateCommand(NewProductType.of(productTypeName, productTypeDescription, attributes));
        final ProductType productType = client.execute(command);
        assertThat(productType.getName()).isEqualTo(productTypeName);
        assertThat(productType.getDescription()).isEqualTo(productTypeDescription);
        assertThat(productType.getAttributes()).hasSize(1);
        final AttributeDefinition attributeDefinition = productType.getAttributes().get(0);

        assertThat(attributeDefinition.getName()).isEqualTo(attributeName);
        assertThat(attributeDefinition.getLabel()).isEqualTo(en("label"));
        assertThat(attributeDefinition.getAttributeType()).isInstanceOf(conf.getExpectedAttributeTypeClass());

        cleanUpByName(productTypeName);
    };

    @Test
    public void createTextAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase() {
            @Override
            public String attributeName() {
                return "text-attribute";
            }

            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.
                        text(attributeName(), LABEL, TEXT_INPUT_HINT).
                        attributeConstraint(AttributeConstraint.CombinationUnique).
                        searchable(false).
                        required(true).
                        build();
            }

            @Override
            public Class<? extends AttributeType> getExpectedAttributeTypeClass() {
                return TextType.class;
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {
                assertThat(attributeDefinition.getIsRequired()).isTrue();
                assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.SameForAll);
                assertThat(attributeDefinition.getIsSearchable()).isFalse();
                assertThat(((TextAttributeDefinition) attributeDefinition).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
            }

        });
    }

    @Test
    public void createLocalizedTextAttribute() throws Exception {
        attributeTestFunction.accept(new AttributeTestCase() {
            @Override
            public String attributeName() {
                return "localized-text-attribute";
            }

            @Override
            public AttributeDefinition getAttributeDefinition() {
                return AttributeDefinitionBuilder.localizedText(attributeName(), LABEL, TEXT_INPUT_HINT).
                        attributeConstraint(AttributeConstraint.CombinationUnique).
                        searchable(false).
                        required(true).
                        build();
            }

            @Override
            public Class<? extends AttributeType> getExpectedAttributeTypeClass() {
                return LocalizedTextType.class;
            }

            @Override
            public void furtherAttributeDefinitionAssertions(final AttributeDefinition attributeDefinition) {
                assertThat(attributeDefinition.getIsRequired()).isTrue();
                assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.SameForAll);
                assertThat(attributeDefinition.getIsSearchable()).isFalse();
                assertThat(((TextAttributeDefinition) attributeDefinition).getTextInputHint()).isEqualTo(TEXT_INPUT_HINT);
            }
        });
    }
}
