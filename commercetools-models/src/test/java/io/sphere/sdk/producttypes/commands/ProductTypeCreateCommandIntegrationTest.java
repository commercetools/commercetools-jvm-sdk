package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.*;

@NotThreadSafe
public class ProductTypeCreateCommandIntegrationTest extends IntegrationTest {
    @Before
    @After
    public void setUp() throws Exception {
        client().executeBlocking(ProductTypeQuery.of().byName(getName())).getResults().forEach(pt -> client().executeBlocking(ProductTypeDeleteCommand.of(pt)));
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
                .inputTip(LocalizedString.ofEnglish("size as enum"))
                .build();

        final String name = getName();
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(randomKey(), name, "a 'T' shaped cloth", asList(sizeAttributeDefinition));
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
        assertThat(productType.getName()).isEqualTo(name);
        assertThat(productType.getDescription()).isEqualTo("a 'T' shaped cloth");
        assertThat(productType.getAttributes()).contains(sizeAttributeDefinition);
        assertThat(productType.getAttributes()).hasSize(1);
        assertThat(productType.getAttributes().get(0).getInputTip())
                .isEqualTo(LocalizedString.ofEnglish("size as enum"));
    }

    @Test
    public void createByJson() {
        final ProductTypeDraft productTypeDraft = SphereJsonUtils.readObjectFromResource("drafts-tests/productType.json", ProductTypeDraft.class);
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
        assertThat(productType.getName()).isEqualTo(getName());
        assertThat(productType.getKey()).isEqualTo("product-type-from-json");
        assertThat(productType.getDescription()).isEqualTo("product type from json");
        final AttributeDefinition attributeDefinition = productType.getAttributes().get(0);
        assertThat(attributeDefinition.getAttributeType()).isEqualTo(StringAttributeType.of());
        assertThat(attributeDefinition.getName()).isEqualTo("string-attribute-from-json");
        final LocalizedString label = LocalizedString.of(ENGLISH, "an attribute from JSON", GERMAN, "ein Attribut von JSON");
        assertThat(attributeDefinition.getLabel()).isEqualTo(label);
        assertThat(attributeDefinition.isRequired()).isTrue();
        assertThat(attributeDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.NONE);
        assertThat(attributeDefinition.getInputHint()).isEqualTo(TextInputHint.SINGLE_LINE);
        assertThat(attributeDefinition.isSearchable()).isTrue();
    }

    private String getName() {
        return ProductTypeCreateCommandIntegrationTest.class.getSimpleName();
    }
}