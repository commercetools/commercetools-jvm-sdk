package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.products.attributes.AttributeConstraint;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.products.attributes.EnumAttributeType;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
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
        final EnumValue S = EnumValue.of("S", "S");
        final EnumValue M = EnumValue.of("M", "M");
        final EnumValue X = EnumValue.of("X", "X");
        final List<EnumValue> values = asList(S, M, X);
        final LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        final AttributeDefinition sizeAttributeDefinition =
                AttributeDefinitionBuilder.of("size", sizeAttributeLabel, EnumAttributeType.of(values))
                .required(true)
                .attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE)
                .build();

        final String name = getName();
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(randomKey(), name, "a 'T' shaped cloth", asList(sizeAttributeDefinition));
        final ProductType productType = execute(ProductTypeCreateCommand.of(productTypeDraft));
        assertThat(productType.getName()).isEqualTo(name);
        assertThat(productType.getDescription()).isEqualTo("a 'T' shaped cloth");
        assertThat(productType.getAttributes()).contains(sizeAttributeDefinition);
    }

    private String getName() {
        return ProductTypeCreateCommandTest.class.getSimpleName();
    }
}