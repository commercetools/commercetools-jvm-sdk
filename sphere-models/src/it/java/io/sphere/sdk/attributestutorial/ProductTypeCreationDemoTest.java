package io.sphere.sdk.attributestutorial;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletionException;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.stream.Collectors.toList;

public class ProductTypeCreationDemoTest extends IntegrationTest {

    private static final String PRODUCT_TYPE_NAME = "tshirt-product-attribute-tutorial";

    public void demoCheckingIfProductTypeExist() {
        final ProductTypeQuery query = ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME);
        final boolean productTypeAlreadyExists = client().execute(query).head().isPresent();
    }

    @Test
    public void createProductType() throws Exception {
        final LocalizedEnumValue green = LocalizedEnumValue.of("green",
                LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün"));
        final LocalizedEnumValue red = LocalizedEnumValue.of("red",
                LocalizedStrings.of(ENGLISH, "red").plus(GERMAN, "rot"));
        final AttributeDefinition color = AttributeDefinitionBuilder
                .of("AttributeTutorialColor", en("color"), LocalizedEnumType.of(red, green))
                .required(true)
                .build();

        final PlainEnumValue s = PlainEnumValue.of("S", "S");
        final PlainEnumValue m = PlainEnumValue.of("M", "M");
        final PlainEnumValue x = PlainEnumValue.of("X", "X");
        final AttributeDefinition size = AttributeDefinitionBuilder
                .of("AttributeTutorialSize", en("size"), EnumType.of(s, m, x))
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
                .of("AttributeTutorialLaundrySymbols", en("washing labels"), laundryLabelType)
                .build();

        final AttributeDefinition matchingProducts = AttributeDefinitionBuilder
                .of("AttributeTutorialMatchingProducts", en("matching products"), SetType.of(ReferenceType.ofProduct()))
                .build();

        final AttributeDefinition rrp = AttributeDefinitionBuilder
                .of("AttributeTutorialRrp", en("recommended retail price"), MoneyType.of())
                .build();

        final AttributeDefinition availableSince = AttributeDefinitionBuilder
                .of("AttributeTutorialAvailableSince", en("available since"), DateType.of())
                .build();


        final List<AttributeDefinition> attributes = asList(color, size, laundrySymbols,
                matchingProducts, rrp, availableSince);
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "a 'T' shaped cloth", attributes);
        final ProductType productType = client().execute(ProductTypeCreateCommand.of(productTypeDraft));
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
}
