package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.attributes.AttributeConstraint;
import io.sphere.sdk.attributes.AttributeDefinition;
import io.sphere.sdk.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.attributes.EnumType;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.*;

public class ProductTypeCreateCommandTest extends IntegrationTest {
    @Before
    @After
    public void setUp() throws Exception {
        execute(ProductTypeQuery.of().byName(getName())).getResults().forEach(pt -> execute(ProductTypeDeleteCommand.of(pt)));
    }

    @Test
    public void execution() throws Exception {
        //size attribute stuff
        final PlainEnumValue S = PlainEnumValue.of("S", "S");
        final PlainEnumValue M = PlainEnumValue.of("M", "M");
        final PlainEnumValue X = PlainEnumValue.of("X", "X");
        final List<PlainEnumValue> values = asList(S, M, X);
        final LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        final AttributeDefinition sizeAttributeDefinition =
                AttributeDefinitionBuilder.of("size", sizeAttributeLabel, EnumType.of(values))
                .required(true)
                .attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE)
                .build();

        final String name = getName();
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(name, "a 'T' shaped cloth", asList(sizeAttributeDefinition));
        final ProductType productType = execute(ProductTypeCreateCommand.of(productTypeDraft));
        assertThat(productType.getName()).isEqualTo(name);
        assertThat(productType.getDescription()).isEqualTo("a 'T' shaped cloth");
        assertThat(productType.getAttributes()).contains(sizeAttributeDefinition);
    }

    private String getName() {
        return ProductTypeCreateCommandTest.class.getSimpleName();
    }
}