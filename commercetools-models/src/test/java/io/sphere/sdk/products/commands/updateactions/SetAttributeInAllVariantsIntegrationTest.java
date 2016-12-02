package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class SetAttributeInAllVariantsIntegrationTest extends IntegrationTest {

    public static final String ATTR_NAME = "ProductFlags";
    public static final String KEY_1 = "key_1";
    public static final String KEY_2 = "key_2";
    private ProductType productType;
    private Product product;
    public static final LocalizedEnumValue ONE = LocalizedEnumValue.of(KEY_1, LocalizedString.of(ENGLISH, "one", GERMAN, "eins"));
    public static final LocalizedEnumValue TWO = LocalizedEnumValue.of(KEY_2, LocalizedString.of(ENGLISH, "two", GERMAN, "zwei"));
    public static final List<LocalizedEnumValue> ALL_ATTRIBUTE_VALUES = asList(ONE, TWO);

    @Before
    public void setUp() throws Exception {
        final String key = randomKey();
        productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft(key)));
        product = client().executeBlocking(ProductCreateCommand.of(productDraft(productType)));
    }

    @After
    public void tearDown() throws Exception {
        if (product != null) {
            client().executeBlocking(ProductDeleteCommand.of(product));
        }
        if (productType != null) {
            client().executeBlocking(ProductTypeDeleteCommand.of(productType));
        }
    }

    @Test
    @SuppressWarnings({"varargs", "unchecked"})
    public void ofUnsetAttribute() {
        assertThat(product.getMasterData().getStaged().getAllVariants())
                .extracting(variant -> variant.getAttribute(ATTR_NAME).getValueAsLocalizedEnumValueSet())
                .as("product attributes present")
                .contains(new HashSet<>(ALL_ATTRIBUTE_VALUES));

        final UpdateAction<Product> action = SetAttributeInAllVariants.ofUnsetAttribute("ProductFlags");
        product = client().executeBlocking(ProductUpdateCommand.of(product, action));

        assertThat(product.getMasterData().getStaged().getAllVariants())
                .extracting(variant -> variant.hasAttribute(ATTR_NAME))
                .as("attribute is deleted")
                .containsExactly(false, false);
    }

    private ProductDraft productDraft(final ProductType productType) {
        return ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), asList(variantDraft(), variantDraft()))
                .build();
    }

    private ProductVariantDraft variantDraft() {
        final AttributeDraft attributeDraft = AttributeDraft.of(ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet(), new HashSet<>(ALL_ATTRIBUTE_VALUES));
        return ProductVariantDraftBuilder.of()
                .attributes(attributeDraft)
                .sku(randomKey())
                .build();
    }

    private ProductTypeDraft productTypeDraft(final String key) {
        return ProductTypeDraft.of(key, key, key, singletonList(attributeDefinition()));
    }

    private AttributeDefinition attributeDefinition() {
        final LocalizedString label = LocalizedString.of(ENGLISH, "Product flag", GERMAN, "Produkt Flag");
        final AttributeType attributeType = SetAttributeType.of(LocalizedEnumAttributeType.of(ALL_ATTRIBUTE_VALUES));
        return AttributeDefinitionBuilder.
                of(ATTR_NAME, label, attributeType)
                .attributeConstraint(AttributeConstraint.SAME_FOR_ALL)
                .isRequired(false)
                .isSearchable(false)
                .inputHint(TextInputHint.SINGLE_LINE)
                .build();
    }

}