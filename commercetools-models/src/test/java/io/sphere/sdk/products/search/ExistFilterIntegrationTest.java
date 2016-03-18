package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.StringAttributeType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.search.model.ExistsAndMissingFilterSearchModelSupport;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class ExistFilterIntegrationTest extends IntegrationTest {
    public static final String PRODUCT_TYPE_KEY = ExistFilterIntegrationTest.class.getSimpleName();
    private static ProductType productType;

    @AfterClass
    public static void delete() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = null;
    }

    @BeforeClass
    public static void createData() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        final String stringattribute = "stringattribute";
        final List<AttributeDefinition> attributes = singletonList(AttributeDefinitionBuilder.of(stringattribute, randomSlug(), StringAttributeType.of()).build());
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, attributes);
        productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @Test
    public void categories() {
        withCategory(client(), category -> {
            checkFilter(builder -> builder.categories(singleton(category.toReference())), m -> m.categories());
        });
    }

    @Test
    public void state() {
        withState(client(), draft -> draft, state -> {
            checkFilter(builder -> builder.state(state), m -> m.state());
        });
    }

    @Test
    public void taxCategory() {
        withTaxCategory(client(), taxCategory -> {
            checkFilter(builder -> builder.taxCategory(taxCategory), m -> m.taxCategory());
        });
    }

    @Test
    public void sku() {
        checkFilter(builder -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of(builder.getMasterVariant())
                    .sku("sku-of-master")
                    .build();
            return builder.masterVariant(masterVariant);
        }, m -> m.allVariants().sku());
    }

    @Test
    public void prices() {
        checkFilter(builder -> {
            final ProductVariantDraft oldMaster = builder.getMasterVariant();
            final ProductVariantDraft masterWithPrices = ProductVariantDraftBuilder.of(oldMaster)
                    .prices(singletonList(PriceDraft.of(EURO_1)))
                    .build();
            return builder.masterVariant(masterWithPrices);
        }, m -> m.allVariants().prices());
    }

    @Test
    public void productAttribute() {
        checkFilter(builder -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of(builder.getMasterVariant())
                    .attributes(AttributeDraft.of("stringfield", "hello"))
                    .build();
            return builder.masterVariant(masterVariant);
        }, m -> m.allVariants().attribute().ofString("stringfield"));
    }

    private void checkFilter(final UnaryOperator<ProductDraftBuilder> productDraftBuilderUnaryOperator, final Function<ProductProjectionFilterSearchModel, ExistsAndMissingFilterSearchModelSupport<ProductProjection>> dsl) {
        withProduct(client(), productDraftBuilderUnaryOperator, productWith -> {
            withProduct(client(), productWithout -> {
                final ProductProjectionSearch baseRequest = ProductProjectionSearch.ofStaged()
                        .withQueryFilters(m -> {
                            final List<String> productIds = asList(productWith.getId(), productWithout.getId());
                            return m.id().isIn(productIds);
                        });
                final ProductProjectionSearch exists = baseRequest.plusQueryFilters(m -> dsl.apply(m).exists());
                final ProductProjectionSearch missing = baseRequest.plusQueryFilters(m -> dsl.apply(m).missing());
               assertEventually(() -> {
                   assertThat(client().executeBlocking(exists).getResults()).hasSize(1).extracting(s -> s.getId()).containsExactly(productWith.getId());
                   assertThat(client().executeBlocking(missing).getResults()).hasSize(1).extracting(s -> s.getId()).containsExactly(productWithout.getId());
               });
            });
        });
    }
}
