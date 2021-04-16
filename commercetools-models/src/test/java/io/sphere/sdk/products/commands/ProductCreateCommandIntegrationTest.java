package io.sphere.sdk.products.commands;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.productdiscounts.*;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.updateactions.SetDiscountedPrice;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.messages.ProductPriceExternalDiscountSetMessage;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SearchKeyword;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.search.tokenizer.CustomSuggestTokenizer;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.types.CustomFieldsDraft;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withProductDiscount;
import static io.sphere.sdk.products.ProductFixtures.withProductOfPrices;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withEmptyProductType;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withProductType;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PRODUCT_STATE;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.defaultTaxCategory;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductCreateCommandIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void cleanUp() throws Exception {
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
    }

    @Test
    public void createProductWithExternalImage() throws Exception {
        withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(PRODUCT_STATE), initialProductState -> {
            final ProductType productType = ProductTypeFixtures.defaultProductType(client());
            final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                    .images(image)
                    .build();
            final TaxCategory taxCategory = defaultTaxCategory(client());
            final SearchKeywords searchKeywords = SearchKeywords.of(Locale.ENGLISH, asList(SearchKeyword.of("foo bar baz", CustomSuggestTokenizer.of(asList("foo, baz")))));
            final ProductDraft productDraft = ProductDraftBuilder
                    .of(productType, en("product with external image"), randomSlug(), masterVariant)
                    .taxCategory(taxCategory)
                    .searchKeywords(searchKeywords)
                    .state(initialProductState)
                    .build();
            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
            final Image loadedImage = product.getMasterData().getStaged().getMasterVariant().getImages().get(0);
            assertThat(product.getMasterData().getCurrentUnsafe()).isNotNull();
            assertThat(loadedImage).isEqualTo(image);
            assertThat(product.getTaxCategory()).isEqualTo(taxCategory.toReference());
            assertThat(product.getMasterData().getStaged().getSearchKeywords()).isEqualTo(searchKeywords);
            assertThat(product.getState()).isEqualTo(initialProductState.toReference());

            //clean up test
            client().executeBlocking(ProductDeleteCommand.of(product));
        });
    }

    @Test
    public void createProductWithCustomPrice() {
        withEmptyProductType(client(), randomKey(), productType ->
                withUpdateableType(client(), type -> {
                    final String value = "foo";
                    final PriceDraft price = PriceDraft.of(EURO_1)
                            .withCustom(CustomFieldsDraft.ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, value)));
                    final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().price(price).build();
                    final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant).build();

                    final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
                    final Price loadedPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

                    assertThat(loadedPrice.getValue()).isEqualTo(EURO_1);
                    assertThat(loadedPrice.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo(value);

                    client().executeBlocking(ProductDeleteCommand.of(product));

                    return type;
                })
        );

    }

    @Test
    public void createProductWithDiscountedPrice() {
        final ProductDiscountPredicate predicate = ProductDiscountPredicate.of("1 = 1");//can be used for all products
        final ProductDiscountDraft productDiscountDraft = ProductDiscountDraft.of(randomSlug(), randomSlug(),
                predicate, ExternalProductDiscountValue.of(), randomSortOrder(), true);

        withProductDiscount(client(), productDiscountDraft, externalProductDiscount -> {
            withEmptyProductType(client(), randomKey(), productType ->
                    withUpdateableType(client(), type -> {
                        final DiscountedPrice discountedPrice = DiscountedPrice.of(EURO_5, externalProductDiscount.toReference());
                        final PriceDraft price = PriceDraft.of(EURO_10)
                                .withDiscounted(discountedPrice);

                        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().price(price).build();
                        final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant).build();

                        final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
                        final Price productPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

                        assertThat(productPrice.getValue()).isEqualTo(EURO_10);
                        assertThat(productPrice.getDiscounted()).isEqualTo(discountedPrice);

                        client().executeBlocking(ProductDeleteCommand.of(product));

                        return type;
                    })
            );
        });
    }

    @Test
    public void createProductWithTiers() {
        withEmptyProductType(client(), randomKey(), productType ->
                withUpdateableType(client(), type -> {
                    final List<PriceTier> tiers = Arrays.asList(PriceTierBuilder.of(10, EURO_5).build());
                    final PriceDraft priceWithTiers = PriceDraftBuilder.of(EURO_1)
                            .tiers(tiers)
                            .build();

                    final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                            .price(priceWithTiers)
                            .build();
                    final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant).build();

                    final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
                    final Price loadedPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

                    assertThat(loadedPrice.getValue()).isEqualTo(EURO_1);
                    assertThat(loadedPrice.getTiers()).containsExactlyElementsOf(tiers);

                    client().executeBlocking(ProductDeleteCommand.of(product));

                    return type;
                })
        );
    }

    @Test
    public void createProductWithTiersInPriceDraft() {
        withEmptyProductType(client(), randomKey(), productType ->
                withUpdateableType(client(), type -> {
                    final List<PriceTier> tiers = Arrays.asList(PriceTierBuilder.of(10, EURO_5).build());
                    final PriceDraft priceWithTiers = PriceDraftBuilder.of(EURO_1)
                            .plusTiers(PriceTierBuilder.of(10, EURO_5).build()).build();

                    final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                            .price(priceWithTiers)
                            .build();
                    final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), masterVariant).build();

                    final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
                    final Price loadedPrice = product.getMasterData().getStaged().getMasterVariant().getPrices().get(0);

                    assertThat(loadedPrice.getValue()).isEqualTo(EURO_1);
                    assertThat(loadedPrice.getTiers()).containsExactlyElementsOf(tiers);

                    client().executeBlocking(ProductDeleteCommand.of(product));

                    return type;
                })
        );
    }

    @Test
    public void createPublishedProduct() {
        withEmptyProductType(client(), randomKey(), productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final LocalizedString name = randomSlug();
            final LocalizedString slug = randomSlug();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, slug, masterVariant)
                    .publish(true)
                    .build();

            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
            assertThat(product.getMasterData().isPublished()).isTrue();
            assertThat(product.getMasterData().getCurrent().getSlug()).isEqualTo(slug);

            try {
                assertEventually(Duration.ofSeconds(120), Duration.ofMillis(200), () -> {
                    final ProductProjectionSearch search = ProductProjectionSearch.ofCurrent()
                            .withQueryFilters(m -> m.id().is(product.getId()));
                    final PagedSearchResult<ProductProjection> searchResult = client().executeBlocking(search);
                    assertThat(searchResult.getResults()).hasSize(1);
                });
            } catch (AssertionError e) {
                logger.error(e.getMessage());
            }

            unpublishAndDelete(product);
        });

    }

    @Test
    public void createProductByProductTypeKeyIdentifiable() {
        withEmptyProductType(client(), productType -> {
            final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of().build();
            final ResourceIdentifiable<ProductType> productTypeResourceIdentifier = productType;
            final ProductDraft productDraft = ProductDraftBuilder.of(productTypeResourceIdentifier, randomSlug(), randomSlug(), masterVariant).build();

            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));

            assertThat(product.getProductType()).isEqualTo(productType.toReference());

            client().executeBlocking(ProductDeleteCommand.of(product));
        });
    }

    @Test
    public void createProductByJsonDraft() throws Exception {
        final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
        withCategory(client(), category -> {
            withProductType(client(), randomKey(), productType -> {
                withTaxCategory(client(), taxCategory -> {
                    withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(StateType.PRODUCT_STATE), state -> {
                        referenceResolver.addResourceByKey("t-shirt-category", category);
                        referenceResolver.addResourceByKey("t-shirt-product-type", productType);
                        referenceResolver.addResourceByKey("standard-tax", taxCategory);
                        referenceResolver.addResourceByKey("initial-product-state", state);
                        final ProductDraft productDraft = draftFromJsonResource("drafts-tests/product.json", ProductDraft.class, referenceResolver);
                        final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));
                        assertThat(product.getProductType()).isEqualTo(productType.toReference());
                        assertThat(product.getTaxCategory()).isEqualTo(taxCategory.toReference());
                        assertThat(product.getState()).isEqualTo(state.toReference());
                        final ProductData productData = product.getMasterData().getStaged();
                        assertThat(productData.getName()).isEqualTo(LocalizedString.ofEnglish("red shirt"));
                        assertThat(productData.getCategories()).extracting("id").contains(category.getId());
                        client().executeBlocking(ProductDeleteCommand.of(product));
                    });
                });
            });
        });
    }

    private void unpublishAndDelete(final Product product) {
        client().executeBlocking(ProductDeleteCommand.of(client().executeBlocking(ProductUpdateCommand.of(product, Unpublish.of()))));
    }
}
