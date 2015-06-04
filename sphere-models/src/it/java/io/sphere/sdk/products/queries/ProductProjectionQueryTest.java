package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.products.*;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.products.ProductFixtures.PRICE;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductFixtures.withUpdateablePricedProduct;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static org.assertj.core.api.Assertions.*;

public class ProductProjectionQueryTest extends IntegrationTest {
    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                    .withPredicate(m -> m.id().is(product.getId()));
            final ProductProjection productProjection = execute(query).head().get();
            final VariantIdentifier identifier = productProjection.getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(VariantIdentifier.of(product.getId(), 1));
        });
    }

    @Test
    public void expandCustomerGroupInPrice() throws Exception {
        withCustomerGroup(client(), customerGroup ->
            withUpdateablePricedProduct(client(), PRICE.withCustomerGroup(customerGroup), product -> {
                final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                                .withPredicate(m -> m.id().is(product.getId()))
                                .withExpansionPath(m -> m.masterVariant().prices().customerGroup());
                final List<Price> prices = execute(query).head().get().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> price.getCustomerGroup().map(customerGroupReference -> customerGroupReference.getObj().isPresent()).orElse(false)))
                        .isTrue();
                return product;
            })
        );
    }

    @Test
    public void expandChannelInPrice() throws Exception {
        ChannelFixtures.withChannelOfRole(client(), ChannelRoles.INVENTORY_SUPPLY, channel -> {
            withUpdateablePricedProduct(client(), PRICE.withChannel(channel), product -> {
                final Query<ProductProjection> query = ProductProjectionQuery.of(STAGED)
                        .withPredicate(m -> m.id().is(product.getId()))
                        .withExpansionPath(m -> m.masterVariant().prices().channel());
                final List<Price> prices = execute(query).head().get().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> price.getChannel().map(channelRef -> channelRef.getObj().isPresent()).orElse(false)))
                        .isTrue();
                return product;
            });
        });
    }

}