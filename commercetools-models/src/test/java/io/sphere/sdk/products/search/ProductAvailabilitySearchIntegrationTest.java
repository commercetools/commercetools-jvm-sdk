package io.sphere.sdk.products.search;

import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.http.StringHttpRequestBody;
import io.sphere.sdk.inventory.AvailabilityInfo;
import io.sphere.sdk.inventory.AvailabilityInfoBuilder;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.inventory.commands.InventoryEntryDeleteCommand;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariantAvailability;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeFacetExpression;
import io.sphere.sdk.search.RangeFacetResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductAvailabilitySearchIntegrationTest extends IntegrationTest {
    @Ignore
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

    @Ignore
    @Test
    public void searchForIsOnStockWithChannel() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 2, channel, product -> {
                final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().is(product.getId()))
                        .plusQueryFilters(m -> m.allVariants().availability()
                                .channels().channelId(channel.getId()).isOnStock().is(true));
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                    assertThat(res.getResults()).hasSize(1);
                });
            });
        });
    }

    @Ignore
    @Test
    public void searchForIsOnStockInChannels() {
        final String nonExistingChannel = "nonExistingChannelId";
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 2, channel, product -> {
                final ProductProjectionSearch isOnStockInAnyChannelRequest = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().is(product.getId()))
                        .plusQueryFilters(m -> m.allVariants().availability()
                                .onStockInChannels().containsAny(Arrays.asList(nonExistingChannel, channel.getId())));
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(isOnStockInAnyChannelRequest);
                    assertThat(res.getResults()).hasSize(1);
                });

                final ProductProjectionSearch isOnStockInAllChannelsRequest = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().is(product.getId()))
                        .plusQueryFilters(m -> m.allVariants().availability()
                                .onStockInChannels().containsAll(Arrays.asList(nonExistingChannel, channel.getId())));
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(isOnStockInAllChannelsRequest);
                    assertThat(res.getResults()).isEmpty();
                });
            });
        });
    }

    @Ignore
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

    @Ignore
    @Test
    public void searchForAvailableQuantityRanges() {
        withProductOfStock(client(), 10, productWith10Items -> {
            withProductOfStock(client(), 5, productWith5Items -> {
                final RangeFacetExpression<ProductProjection> productProjectionRangeFacetExpression
                        = ProductProjectionSearchModel.of()
                        .facet().allVariants().availability().availableQuantity().onlyGreaterThanOrEqualTo(new BigDecimal(1));
                final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().isIn(asList(productWith10Items.getId(), productWith5Items.getId())))
                        .plusQueryFilters(m -> m.allVariants().availability().availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(6)))
                        .plusFacets(productProjectionRangeFacetExpression);
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                    assertThat(res.getResults()).hasSize(1);
                    assertThat(res.getResults().get(0).getId())
                            .as("finds only the product with the sufficient amount stocked")
                            .isEqualTo(productWith10Items.getId());
                    final RangeFacetResult facetResult = res.getFacetResult(productProjectionRangeFacetExpression);
                    assertThat(facetResult.getRanges().get(0).getMax())
                            .isEqualTo("10.0");
                });
            });
        });
    }

    @Ignore
    @Test
    public void searchForAvailableQuantityRangesInChannels() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 10, channel, productWith10Items -> {
                withProductOfStockAndChannel(client(), 5, channel, productWith5Items -> {
                    final RangeFacetExpression<ProductProjection> facet = ProductProjectionSearchModel.of()
                            .facet().allVariants().availability().channels().channelId(channel.getId())
                            .availableQuantity().onlyGreaterThanOrEqualTo(new BigDecimal(1));
                    final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                            .plusQueryFilters(m -> m.id().isIn(asList(productWith10Items.getId(), productWith5Items.getId())))
                            .plusQueryFilters(m ->
                                    m.allVariants().availability().channels().channelId(channel.getId())
                                            .availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(6)))
                            .plusFacets(facet);
                    assertEventually(() -> {
                        final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                        assertThat(res.getResults()).hasSize(1);
                        assertThat(res.getResults().get(0).getId())
                                .as("finds only the product with the sufficient amount stocked")
                                .isEqualTo(productWith10Items.getId());
                        final RangeFacetResult facetResult =  res.getFacetResult(facet);
                        assertThat(facetResult.getRanges().get(0).getMax())
                                .isEqualTo("10.0");
                    });
                });
            });
        });
    }

    @Ignore
    @Test
    public void channelsFilterDsl() {
        final StringHttpRequestBody body = (StringHttpRequestBody) ProductProjectionSearch.ofStaged().plusQueryFilters(m ->
                m.allVariants().availability().channels().channelId("channel-id-12")
                        .availableQuantity().isGreaterThanOrEqualTo(new BigDecimal(3))).httpRequestIntent().getBody();
        assertThat(body.getString()).contains("filter.query=variants.availability.channels.channel-id-12.availableQuantity%3Arange%283+to+*%29");
    }

    @Ignore
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

    @Ignore
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

    @Ignore
    @Test
    public void sortByAvailableQuantity() {
        withProductOfStock(client(), 10, productWith10Items -> {
            withProductOfStock(client(), 5, productWith5Items -> {
                final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                        .plusQueryFilters(m -> m.id().isIn(asList(productWith5Items.getId(), productWith10Items.getId())))
                        .plusSort(m -> m.allVariants().availability().availableQuantity().asc());
                assertEventually(() -> {
                    final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                    assertThat(res.getResults()).hasSize(2);
                    assertThat(res.getResults().get(0).getId())
                            .isEqualTo(productWith5Items.getId());
                });
            });
        });
    }

    @Ignore
    @Test
    public void sortByAvailableQuantityWithSupplyChannel() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withProductOfStockAndChannel(client(), 10, channel, productWith10Items -> {
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

    @Ignore
    @Test
    public void channelsRestockableSortDsl() {
        final StringHttpRequestBody body = (StringHttpRequestBody) ProductProjectionSearch.ofStaged().
        plusSort(m -> m.allVariants().availability().channels().channelId("channel-id-500").restockableInDays().asc()).httpRequestIntent().getBody();
        assertThat(body.getString()).contains("sort=variants.availability.channels.channel-id-500.restockableInDays+asc");
    }

    @Ignore
    @Test
    public void newProductVariantFields() {
        withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel1 -> {
            withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel2 -> {
                final BlockingSphereClient client = client();
                ProductFixtures.withProduct(client, product -> {
                    final String sku = product.getMasterData().getStaged().getMasterVariant().getSku();
                    final InventoryEntry inventoryEntry1 = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, 4, null, 6, channel1)));
                    final InventoryEntry inventoryEntry2 = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, 2, ZonedDateTime.now().plusDays(3), 40, channel2)));
                    final InventoryEntry inventoryEntry3 = client.executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, 3, ZonedDateTime.now().plusDays(7), 80, null)));

                    final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                            .withQueryFilters(m -> m.id().is(product.getId()));
                    assertEventually(() -> {
                        final List<ProductProjection> results = client.executeBlocking(search).getResults();
                        assertThat(results).hasSize(1);
                        final ProductProjection productProjection = results.get(0);
                        final ProductVariantAvailability availability = productProjection.getMasterVariant().getAvailability();
                        assertThat(availability).isNotNull();
                        assertThat(availability.isOnStock()).as("isOnStock").isTrue();
                        assertThat(availability.getRestockableInDays()).as("getRestockableInDays").isEqualTo(80);
                        assertThat(availability.getAvailableQuantity()).as("getAvailableQuantity").isEqualTo(3);
                        final AvailabilityInfo availabilityInfo1 = AvailabilityInfoBuilder.of().availableQuantity(4L).isOnStock(true).restockableInDays(6).build();
                        final AvailabilityInfo availabilityInfo2 = AvailabilityInfoBuilder.of().availableQuantity(2L).isOnStock(true).restockableInDays(40).build();
                        final Map<String, AvailabilityInfo> channels = new HashMap<>();
                        channels.put(channel1.getId(), availabilityInfo1);
                        channels.put(channel2.getId(), availabilityInfo2);
                        assertThat(availability.getChannels()).as("channels").isEqualTo(channels);

                        client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry1));
                        client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry2));
                        client.executeBlocking(InventoryEntryDeleteCommand.of(inventoryEntry3));
                    });
                });
            });
        });
    }
}
