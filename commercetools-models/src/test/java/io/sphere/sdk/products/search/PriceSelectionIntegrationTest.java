package io.sphere.sdk.products.search;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetPrices;
import io.sphere.sdk.products.queries.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Condition;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class PriceSelectionIntegrationTest extends IntegrationTest {
    public static final String PRODUCT_TYPE_KEY = PriceSelectionIntegrationTest.class.getSimpleName();
    private static ProductType productType;

    @AfterClass
    public static void delete() {
        CartDiscountFixtures.deleteDiscountCodesAndCartDiscounts(client());
        ProductFixtures.deleteProductsAndProductTypes(client());
        productType = null;
    }

    @BeforeClass
    public static void createData() {
        ProductFixtures.deleteProductsAndProductTypes(client());
        final ProductTypeDraft productTypeDraft =
                ProductTypeDraft.of(PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, PRODUCT_TYPE_KEY, emptyList());
        productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
    }


    @Ignore
    @Test
    public void selectAPriceByCurrency() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                    .withQueryFilters(m -> m.id().is(product.getId()))//to limit the test scope
                    .withPriceSelection(PriceSelection.of(EUR));//price selection config
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> result = client().executeBlocking(searchRequest);
                assertThat(result.getCount()).isEqualTo(1);
                final ProductVariant masterVariant = result.getResults().get(0).getMasterVariant();
                assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
            });
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductProjectionQuery() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductProjectionQuery request = ProductProjectionQuery.ofStaged()
                    .withPredicates(m -> m.id().is(product.getId()))//to limit the test scope
                    .withPriceSelection(PriceSelection.of(EUR));//price selection config
            final PagedQueryResult<ProductProjection> result = client().executeBlocking(request);
            assertThat(result.getCount()).isEqualTo(1);
            final ProductVariant masterVariant = result.getResults().get(0).getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductProjectionByIdGet() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductProjectionByIdGet request = ProductProjectionByIdGet.ofStaged(product)
                    .withPriceSelection(PriceSelection.of(EUR));//price selection config
            final ProductProjection result = client().executeBlocking(request);
            final ProductVariant masterVariant = result.getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductByIdGet() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductByIdGet request = ProductByIdGet.of(product)
                    .withPriceSelection(PriceSelection.of(EUR));//price selection config
            final Product result = client().executeBlocking(request);
            final ProductVariant masterVariant = result.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductUpdateCommand() {
        ProductFixtures.withProduct(client(), product -> {
            final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, SetPrices.of(1, prices))
                    .withPriceSelection(PriceSelection.of(EUR));
            final Product updatedProduct = client().executeBlocking(cmd);
            final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductQuery() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductQuery request = ProductQuery.of()
                    .withPredicates(m -> m.id().is(product.getId()))//to limit the test scope
                    .withPriceSelection(PriceSelection.of(EUR));//price selection config
            final PagedQueryResult<Product> result = client().executeBlocking(request);
            assertThat(result.getCount()).isEqualTo(1);
            final ProductVariant masterVariant = result.getResults().get(0).getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductProjectionByKeyGet() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final ProductProjection productProjection = client().executeBlocking(ProductProjectionByKeyGet.ofStaged(product.getKey()).withPriceSelection(PriceSelection.of(EUR)));
            final ProductVariant masterVariant = productProjection.getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void selectAPriceByCurrencyInProductByKeyGet() {
        final List<PriceDraft> prices = asList(PriceDraft.of(EURO_30), PriceDraft.of(USD_20));
        withProductOfPrices(prices, product -> {
            final Product loadedProduct = client().executeBlocking(ProductByKeyGet.of(product.getKey()).withPriceSelection(PriceSelection.of(EUR)));
            final ProductVariant masterVariant = loadedProduct.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getPrice()).isNotNull().has(price(PriceDraft.of(EURO_30)));
        });
    }

    @Ignore
    @Test
    public void verboseTest() {
        withResources((CustomerGroup b2c) -> (CustomerGroup b2b) -> (Channel channel) -> {
            final PriceDraftDsl simplePriceInEuro = PriceDraft.of(EURO_30);
            final PriceDraftDsl priceWithCountry = PriceDraft.of(EURO_40).withCountry(CountryCode.AU);
            final PriceDraftDsl b2bPrice = PriceDraft.of(EURO_10).withCustomerGroup(b2b);
            final PriceDraftDsl priceWithChannel = PriceDraft.of(EURO_20).withChannel(channel);
            final PriceDraftDsl priceAll = PriceDraft.of(EURO_5)
                    .withChannel(channel)
                    .withCustomerGroup(b2c)
                    .withCountry(DE);
            final List<PriceDraft> prices = asList(simplePriceInEuro, priceWithCountry,
                    b2bPrice, priceWithChannel, priceAll);
            withProductOfPrices(prices, product -> {
                assertEventually(() -> {
                    assertThat(searchWith(product, PriceSelection.of(EUR))).has(price(simplePriceInEuro));
                    assertThat(searchWith(product, PriceSelection.of(USD))).isNull();
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceCountry(CountryCode.AU)))
                            .has(price(priceWithCountry));
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceCountry(DE)))
                            .as("if the country is not present it falls back to the price without country")
                            .has(price(simplePriceInEuro));
                    final String aChannelId = "4698af68-fa24-49ab-8a3e-abb113d72c0a";
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceChannelId(aChannelId)))
                            .as("if a price with the channel is not present it falls back to the price without channel")
                            .has(price(simplePriceInEuro));
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceChannel(channel)))
                            .has(price(priceWithChannel));
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceCustomerGroup(b2b)))
                            .has(price(b2bPrice));
                    assertThat(searchWith(product, PriceSelection.of(EUR).withPriceCustomerGroup(b2c)))
                            .as("if a customer group does not have a special price the default price is used")
                            .has(price(simplePriceInEuro));
                    assertThat(searchWith(product,
                            PriceSelection.of(EUR).withPriceCountry(CountryCode.DE)
                                    .withPriceChannel(channel).withPriceCustomerGroup(b2c)))
                            .has(price(priceAll));
                    assertThat(searchWith(product,
                            PriceSelection.of(EUR).withPriceCountry(CountryCode.AU)
                                    .withPriceChannel(channel).withPriceCustomerGroup(b2c)))
                            .as("channel wins over country and customer group")
                            .has(price(priceWithChannel));
                    assertThat(searchWith(product,
                            PriceSelection.of(EUR).withPriceCountry(CountryCode.AU).withPriceCustomerGroup(b2c)))
                            .as("country wins over customer group")
                            .has(price(priceWithCountry));
                });
            });
        });
    }

    private Price searchWith(final Product product, final PriceSelection selection) {
        final ProductProjectionSearch searchRequest = ProductProjectionSearch.ofStaged()
                .withQueryFilters(m -> m.id().is(product.getId()))//to limit the test scope
                .withPriceSelection(selection);//price selection config
        final PagedSearchResult<ProductProjection> searchResult = client().executeBlocking(searchRequest);
        assertThat(searchResult.getCount()).isEqualTo(1);
        return searchResult.head().get().getMasterVariant().getPrice();
    }

    private void withResources(final Function<CustomerGroup, Function<CustomerGroup, Consumer<Channel>>> op) {
        withCustomerGroup(client(), customerGroupB2c -> {
            withCustomerGroup(client(), customerGroupB2b -> {
                withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
                    op.apply(customerGroupB2c).apply(customerGroupB2b).accept(channel);
                });
            });
        });
    }

    private Condition<Price> price(final PriceDraft draft) {
        return new Condition<Price>() {
            @Override
            public boolean matches(final Price value) {
                return PriceDraft.of(value).equals(draft);
            }
        }.describedAs("like %s", draft);
    }

    private void withProductOfPrices(final List<PriceDraft> priceDrafts, final Consumer<Product> productConsumer) {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .prices(priceDrafts)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType,  randomSlug(),  randomSlug(), masterVariant)
                .key(randomKey())
                .build();
        ProductFixtures.withProduct(client(), () -> productDraft, productConsumer);
    }
}
