package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.customergroups.CustomerGroupFixtures;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.suppliers.VariantsCottonTShirtProductDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.reviews.ReviewFixtures.withReview;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductQueryIntegrationTest extends IntegrationTest {

    @Test
    public void isPublished() throws Exception {
        withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();
            checkIsFoundByPublishedFlag(product, false);

            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            assertThat(publishedProduct.getMasterData().isPublished()).isTrue();
            checkIsFoundByPublishedFlag(product, true);


            final Product unpublishedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, Unpublish.of()));
            assertThat(unpublishedProduct.getMasterData().isPublished()).isFalse();
            return unpublishedProduct;
        });
    }

    private void checkIsFoundByPublishedFlag(final Product product, final boolean value) {
        final Optional<Product> productFromQuery = client().executeBlocking(ProductQuery.of()
                .withPredicates(m -> {
                    return m.masterData().isPublished().is(value);
                })
                .plusPredicates(m -> m.id().is(product.getId()))).head();
        assertThat(productFromQuery.get().getId()).isEqualTo(product.getId());
    }

    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final ByIdVariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(ByIdVariantIdentifier.of(product.getId(), 1));
        });
    }

    @Test
    public void canExpandItsCategories() throws Exception {
        withProductInCategory(client(), (product, category) -> {
            final Query<Product> query = query(product)
                    .withExpansionPaths(ProductExpansionModel.of().masterData().staged().categories());
            assertThat(client().executeBlocking(query).head().get().getMasterData().getStaged().getCategories().stream().anyMatch(reference -> reference.getObj() != null))
                    .isTrue();
        });
    }


    @Test
    public void canExpandCustomerGroupOfPrices() throws Exception {
        withCustomerGroup(client(), customerGroup -> {
                    withUpdateablePricedProduct(client(), PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE).withCustomerGroup(customerGroup), product -> {
                        final ExpansionPath<Product> expansionPath = ProductExpansionModel.of().masterData().staged().masterVariant().prices().customerGroup().expansionPaths().get(0);
                        final Query<Product> query = query(product).withExpansionPaths(expansionPath);
                        final List<Price> prices = client().executeBlocking(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
                        assertThat(prices
                                .stream()
                                .anyMatch(price -> Optional.ofNullable(price.getCustomerGroup()).map(customerGroupReference -> customerGroupReference.getObj() != null).orElse(false)))
                                .isTrue();
                        return product;
                    });
                }

        );
    }

    @Test
    public void canExpandChannelOfPrices() throws Exception {
        ChannelFixtures.withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withUpdateablePricedProduct(client(), PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE).withChannel(channel), product -> {
                final ExpansionPath<Product> expansionPath = ProductExpansionModel.of().masterData().staged().masterVariant().prices().channel().expansionPaths().get(0);
                final Query<Product> query = query(product).withExpansionPaths(expansionPath);
                final List<Price> prices = client().executeBlocking(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> Optional.ofNullable(price.getChannel()).map(channelRef -> channelRef.getObj() != null).orElse(false)))
                        .isTrue();
                return product;
            });
        });
    }


    @Test
    public void queryProductsWithAnyDiscount() throws Exception {
        withUpdateableProductDiscount(client(), (ProductDiscount productDiscount, Product product) -> {
            final ProductQuery query = ProductQuery.of()
                    .withPredicates(m -> m.id().is(product.getId())
                            .and(m.masterData().staged().masterVariant().prices().discounted().isPresent()));
            final Duration maxWaitTime = Duration.ofMinutes(2);
            final Duration waitBeforeRetry = Duration.ofMillis(500);
            assertEventually(maxWaitTime, waitBeforeRetry, () -> {
                final Optional<Product> loadedProduct = client().executeBlocking(query).head();
                assertThat(loadedProduct.isPresent()).isTrue();
                assertThat(loadedProduct.get().getId()).isEqualTo(product.getId());
            });
            return productDiscount;
        });
    }

    @Test
    public void expandVariants() {
        CustomerGroupFixtures.withB2cCustomerGroup(client(), customerGroup ->
            ProductFixtures.withProductType(client(), randomString(), productType ->
                withProduct(client(), new VariantsCottonTShirtProductDraftSupplier(productType, randomString(), customerGroup), product -> {
                    final PagedQueryResult<Product> result = client().executeBlocking(ProductQuery.of()
                            .withPredicates(m -> m.id().is(product.getId()))
                            .withExpansionPaths(m -> m.masterData().staged().variants().prices().customerGroup())
                            .withLimit(1L));
                    final Price priceWithCustomerGroup = result.head().get().getMasterData().getStaged().getVariants().get(0).getPrices().stream()
                            .filter(price -> Objects.equals(price.getCustomerGroup(), customerGroup.toReference()))
                            .findFirst().get();
                    assertThat(priceWithCustomerGroup.getCustomerGroup().getObj()).isNotNull().isEqualTo(customerGroup);
                })
            )
        );
    }

    @Test
    public void queryByReviewRating() {
        withProduct(client(), product -> {
            withReview(client(), b -> b.target(product).rating(1), review1 -> {
                withReview(client(), b -> b.target(product).rating(3), review2 -> {
                    assertEventually(() -> {
                        final ProductQuery query = ProductQuery.of()
                                .withPredicates(m -> m.reviewRatingStatistics().averageRating().is(2.0))
                                .plusPredicates(m -> m.reviewRatingStatistics().count().is(2))
                                .plusPredicates(m -> m.is(product));
                        final List<Product> results = client().executeBlocking(query).getResults();
                        assertThat(results).hasSize(1);
                        final Product loadedProduct = results.get(0);
                        assertThat(loadedProduct.getId()).isEqualTo(product.getId());
                        assertThat(loadedProduct.getReviewRatingStatistics().getCount()).isEqualTo(2);
                    });
                });
            });
        });
    }

    @Test
    public void queryByTiersWithMinimumQuantity() {
        withProduct(client(), product -> {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicates(m -> m.masterData().current().variants().prices().tiers().minimumQuantity().isGreaterThan(5))
                    .plusPredicates(m -> m.is(product));

            final List<Product> results = client().executeBlocking(productQuery).getResults();
            assertThat(results).hasSize(0);
        });
    }

    @Test
    public void queryByTiersWithValue() {
        withProduct(client(), product -> {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicates(m -> m.masterData().current().variants().prices().tiers().value().currencyCode().is("EUR"))
                    .plusPredicates(m -> m.is(product));

            final List<Product> results = client().executeBlocking(productQuery).getResults();
            assertThat(results).hasSize(0);
        });
    }

    private ProductQuery query(final Product product) {
        return ProductQuery.of().withPredicates(m -> m.id().is(product.getId()));
    }
}