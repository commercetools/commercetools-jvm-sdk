package io.sphere.sdk.attributestutorial;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeCreationDemoTest extends IntegrationTest {

    private static final String PRODUCT_TYPE_NAME = "tshirt-product-attribute-tutorial";
    private static final String COLOR_ATTR_NAME = "AttributeTutorialColor";
    private static final String SIZE_ATTR_NAME = "AttributeTutorialSize";
    private static final String MATCHING_PRODUCTS_ATTR_NAME = "AttributeTutorialMatchingProducts";
    private static final String LAUNDRY_SYMBOLS_ATTR_NAME = "AttributeTutorialLaundrySymbols";
    private static final String RRP_ATTR_NAME = "AttributeTutorialRrp";
    private static final String AVAILABLE_SINCE_ATTR_NAME = "AttributeTutorialAvailableSince";

    public void demoCheckingIfProductTypeExist() {
        final ProductTypeQuery query = ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME);
        final boolean productTypeAlreadyExists = client().execute(query).head().isPresent();
    }

    public ProductType fetchProductTypeByName() {
        final Optional<ProductType> productTypeOptional =
                client().execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head();
        final ProductType productType = productTypeOptional
                .orElseThrow(() -> new RuntimeException("product type " + PRODUCT_TYPE_NAME + " is not present."));
        //end example parsing here
        return productType;
    }

    @AfterClass
    @BeforeClass
    public static void deleteProductsAndProductType() {
        final List<ProductType> productTypes = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicate(m -> m.productType().isIn(productTypes))
                    .withLimit(500);
            execute(productQuery).getResults().forEach(p -> execute(ProductDeleteCommand.of(p)));
            productTypes.forEach(p -> execute(ProductTypeDeleteCommand.of(p)));
        }
    }

    @BeforeClass
    public static void createProductType() throws Exception {
        final LocalizedEnumValue green = LocalizedEnumValue.of("green",
                LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün"));
        final LocalizedEnumValue red = LocalizedEnumValue.of("red",
                LocalizedStrings.of(ENGLISH, "red").plus(GERMAN, "rot"));
        final AttributeDefinition color = AttributeDefinitionBuilder
                .of(COLOR_ATTR_NAME, en("color"), LocalizedEnumType.of(red, green))
                .required(true)
                .build();

        final PlainEnumValue s = PlainEnumValue.of("S", "S");
        final PlainEnumValue m = PlainEnumValue.of("M", "M");
        final PlainEnumValue x = PlainEnumValue.of("X", "X");
        final AttributeDefinition size = AttributeDefinitionBuilder
                .of(SIZE_ATTR_NAME, en("size"), EnumType.of(s, m, x))
                .required(true)
                .build();

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue hot = LocalizedEnumValue.of("hot",
                LocalizedStrings.of(ENGLISH, "Wash at or below 60°C ").plus(GERMAN, "60°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedStrings.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        final LocalizedEnumValue noTumbleDrying = LocalizedEnumValue.of("noTumbleDrying",
                LocalizedStrings.of(ENGLISH, "no tumble drying").plus(GERMAN, "Nicht im Trommeltrockner trocknen"));
        final SetType laundryLabelType = SetType.of(LocalizedEnumType.of(cold, hot, tumbleDrying, noTumbleDrying));
        final AttributeDefinition laundrySymbols = AttributeDefinitionBuilder
                .of(LAUNDRY_SYMBOLS_ATTR_NAME, en("washing labels"), laundryLabelType)
                .build();

        final AttributeDefinition matchingProducts = AttributeDefinitionBuilder
                .of(MATCHING_PRODUCTS_ATTR_NAME, en("matching products"), SetType.of(ReferenceType.ofProduct()))
                .build();

        final AttributeDefinition rrp = AttributeDefinitionBuilder
                .of(RRP_ATTR_NAME, en("recommended retail price"), MoneyType.of())
                .build();

        final AttributeDefinition availableSince = AttributeDefinitionBuilder
                .of(AVAILABLE_SINCE_ATTR_NAME, en("available since"), DateType.of())
                .build();


        final List<AttributeDefinition> attributes = asList(color, size, laundrySymbols,
                matchingProducts, rrp, availableSince);
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "a 'T' shaped cloth", attributes);
        final ProductType productType = client().execute(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @Test
    public void productCreation() throws Exception {
        final Reference<Product> productReference = ProductFixtures.referenceableProduct(client()).toReference();
        final ProductType productType = fetchProductTypeByName();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
                .plusAttribute(COLOR_ATTR_NAME, "green")//special case: enums are set with key (String)
                .plusAttribute(SIZE_ATTR_NAME, "S")
                .plusAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, asSet("cold", "tumbleDrying"))//special case: localized enums set Set of keys (String)
                .plusAttribute(MATCHING_PRODUCTS_ATTR_NAME, asSet(productReference))
                .plusAttribute(RRP_ATTR_NAME, MoneyImpl.of(300, EUR))
                .plusAttribute(AVAILABLE_SINCE_ATTR_NAME, LocalDate.of(2015, 2, 2))
                .build();
        final ProductDraft draft = ProductDraftBuilder
                .of(productType, en("basic shirt"), randomSlug(), masterVariantDraft)
                .build();

        final Product product = client().execute(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.getAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue()))
                .contains(LocalizedEnumValue.of("green", LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün")));
        assertThat(masterVariant.getAttribute(SIZE_ATTR_NAME, AttributeAccess.ofPlainEnumValue()))
                .contains(PlainEnumValue.of("S", "S"));

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedStrings.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        assertThat(masterVariant.getAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet()))
                .contains(asSet(cold, tumbleDrying));
        assertThat(masterVariant.getAttribute(MATCHING_PRODUCTS_ATTR_NAME, AttributeAccess.ofProductReferenceSet()))
                .contains(asSet(productReference));
        assertThat(masterVariant.getAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney()))
                .contains(MoneyImpl.of(300, EUR));
        assertThat(masterVariant.getAttribute(AVAILABLE_SINCE_ATTR_NAME, AttributeAccess.ofDate()))
                .contains(LocalDate.of(2015, 2, 2));
    }

    @Test
    public void productCreationWithGetterSetter() throws Exception {
        final NamedAttributeAccess<PlainEnumValue> size = AttributeAccess.ofPlainEnumValue().ofName(SIZE_ATTR_NAME);
        final NamedAttributeAccess<LocalizedEnumValue> color = AttributeAccess.ofLocalizedEnumValue().ofName(COLOR_ATTR_NAME);

    }
}
