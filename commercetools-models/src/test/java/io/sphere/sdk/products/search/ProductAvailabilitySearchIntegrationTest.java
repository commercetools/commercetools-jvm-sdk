package io.sphere.sdk.products.search;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.http.StringHttpRequestBody;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.math.BigDecimal;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAvailabilitySearchIntegrationTest extends IntegrationTest {
    @Test
    public void searchForIsOnStock() {
        withProductOfStock(client(), 2, product -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .plusQueryFilters(m -> m.id().is(product.getId()))
                    .plusQueryFilters(m -> m.allVariants().availability().isOnStock().is(true));
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                assertThat(res.getResults()).hasSize(1);
            });
        });
    }

    @Test
    public void searchForIsNotOnStock() {
        withProductOfStock(client(), 0, product -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .plusQueryFilters(m -> m.id().is(product.getId()))
                    .plusQueryFilters(m -> m.allVariants().availability().isOnStock().is(false));
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                assertThat(res.getResults()).hasSize(0);
            });
        });
    }

    @Test
    public void searchForAvailableQuantityRanges() {
        withProductOfStock(client(), 10, productWith10Items -> {
        withProductOfStock(client(), 5, productWith5Items -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .plusQueryFilters(m -> m.id().isIn(asList(productWith10Items.getId(), productWith5Items.getId())))
                    .plusQueryFilters(m -> m.allVariants().availability().availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(6)));
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                assertThat(res.getResults()).hasSize(1);
                assertThat(res.getResults().get(0).getId())
                        .as("finds only the product with the sufficient amount stocked")
                        .isEqualTo(productWith10Items.getId());
            });
        });
        });
    }

    @Test
    public void searchForAvailableQuantityRangesInChannels() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 10, channel, productWith10Items -> {
                withProductOfStockAndChannel(client(), 5, channel, productWith5Items -> {
                    final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                            .plusQueryFilters(m -> m.id().isIn(asList(productWith10Items.getId(), productWith5Items.getId())))
                            .plusQueryFilters(m ->
                                    m.allVariants().availability().channels().channelId(channel.getId())
                                            .availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(6)));
                    assertEventually(() -> {
                        final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                        assertThat(res.getResults()).hasSize(1);
                        assertThat(res.getResults().get(0).getId())
                                .as("finds only the product with the sufficient amount stocked")
                                .isEqualTo(productWith10Items.getId());
                    });
                });
            });
        });
    }

    @Test
    public void channelsFilterDsl() {
        final StringHttpRequestBody body = (StringHttpRequestBody) ProductProjectionSearch.ofStaged().plusQueryFilters(m ->
                m.allVariants().availability().channels().channelId("channel-id-12")
                        .availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(3))).httpRequestIntent().getBody();
        assertThat(body.getString()).contains("filter.query=variants.availability.channels.channel-id-12.availableQuantity%3Arange%283+to+*%29");
    }

    @Test
    public void sortByRestockableInDays() {
        withProductOfRestockableInDaysAndChannel(client(), 4, null, product4 -> {
            withProductOfRestockableInDaysAndChannel(client(), 9, null, product9 -> {
                final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().isIn(asList(product4.getId(), product9.getId())))
                        .plusSort(m -> m.allVariants().availability().restockableInDays().asc());
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                    assertThat(res.getResults()).hasSize(2);
                    assertThat(res.getResults().get(0).getId())
                            .isEqualTo(product4.getId());
                });
            });
        });
    }

    @Test
    public void sortByRestockableInDaysWithSupplyChannel() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfRestockableInDaysAndChannel(client(), 4, channel, product4 -> {
                withProductOfRestockableInDaysAndChannel(client(), 9, channel, product9 -> {
                    final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                            .plusQueryFilters(m -> m.id().isIn(asList(product4.getId(), product9.getId())))
                            .plusSort(m -> m.allVariants().availability().channels().channelId(channel.getId()).restockableInDays().asc());
                    assertEventually(() -> {
                        final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                        assertThat(res.getResults()).hasSize(2);
                        assertThat(res.getResults().get(0).getId())
                                .isEqualTo(product4.getId());
                    });
                });
            });
        });
    }

    @Test
    public void sortByAvailableQuantityWithSupplyChannel() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 10, channel, productWith10Items -> {*
                withProductOfStockAndChannel(client(), 5, channel, productWith5Items -> {
                    final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                            .plusQueryFilters(m -> m.id().isIn(asList(productWith5Items.getId(), productWith10Items.getId())))
                            .plusSort(m -> m.allVariants().availability().channels().channelId(channel.getId()).availableQuantity().asc());
                    assertEventually(() -> {
                        final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                        assertThat(res.getResults()).hasSize(2);
                        assertThat(res.getResults().get(0).getId())
                                .isEqualTo(productWith5Items.getId());
                    });
                });
            });
        });
    }

    @Test
    public void channelsRestockableSortDsl() {
        final StringHttpRequestBody body = (StringHttpRequestBody) ProductProjectionSearch.ofStaged().
        plusSort(m -> m.allVariants().availability().channels().channelId("channel-id-500").restockableInDays().asc()).httpRequestIntent().getBody();
        assertThat(body.getString()).contains("sort=variants.availability.channels.channel-id-500.restockableInDays+asc");
    }
}
