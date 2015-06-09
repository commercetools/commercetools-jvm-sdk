package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetAttribute;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductAttributeFlowTest extends IntegrationTest {

    public static final String LOCALIZED_ENUM_ATTR = "lenumattr";
    public static final LocalizedEnumValue A = LocalizedEnumValue.of("a", en("value a"));
    public static final LocalizedEnumValue B = LocalizedEnumValue.of("b", en("value b"));
    public static final LocalizedEnumValue C = LocalizedEnumValue.of("c", en("value c"));

    @Test
    public void localizedEnumValue() throws Exception {
        final String productTypeName = ProductAttributeFlowTest.class.getSimpleName() + "lenum";
        final List<LocalizedEnumValue> localizedEnumValues = asList(A, B, C);
        final ProductTypeDraft draft = ProductTypeDraft.of(productTypeName, "", asList(AttributeDefinitionBuilder.of(LOCALIZED_ENUM_ATTR, randomSlug(), LocalizedEnumType.of(localizedEnumValues)).build()));
        final QueryDsl<ProductType> query = ProductTypeQuery.of().byName(productTypeName);
        final ProductTypeCreateCommand createCommand = ProductTypeCreateCommand.of(draft);
        final ProductType productType = getOrCreate(createCommand, query);
        final NamedAttributeAccess<LocalizedEnumValue> namesAccess = AttributeAccess.ofLocalizedEnumValue().ofName(LOCALIZED_ENUM_ATTR);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().attributes(AttributeDraft.of(namesAccess, B)).build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant).build();
        final Product product = execute(ProductCreateCommand.of(productDraft));
        assertThat(extractAttribute(product, namesAccess)).contains(B);

        final Product updatedProduct = execute(ProductUpdateCommand.of(product, SetAttribute.of(1, namesAccess, C, ProductUpdateScope.ONLY_STAGED)));
        assertThat(extractAttribute(updatedProduct, namesAccess)).contains(C);


        final NamedAttributeAccess<LocalizedEnumValue> getterSetter = AttributeAccess.ofLocalizedEnumValue().ofName(LOCALIZED_ENUM_ATTR);
        final Attribute attribute = Attribute.of(getterSetter, C);

        final LocalizedEnumValue value = attribute.getValue(AttributeAccess.ofLocalizedEnumValue());
        assertThat(value).isEqualTo(C);
    }

    private Optional<LocalizedEnumValue> extractAttribute(final Product product, final NamedAttributeAccess<LocalizedEnumValue> namesAccess) {
        return product.getMasterData().getStaged().getMasterVariant().getAttribute(namesAccess);
    }


}