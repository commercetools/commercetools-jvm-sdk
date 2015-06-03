package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRoles;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.VariantIdentifier;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.products.ProductFixtures.*;
import static org.assertj.core.api.Assertions.*;

public class ProductQueryTest extends IntegrationTest {
    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final VariantIdentifier identifier = product.getMasterData().getStaged().getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(VariantIdentifier.of(product.getId(), 1));
        });
    }

    @Test
    public void canExpandItsCategories() throws Exception {
        withProductInCategory(client(), (product, category) -> {
            final Query<Product> query = query(product)
                    .withExpansionPath(ProductQuery.expansionPath().masterData().staged().categories());
            assertThat(execute(query).head().get().getMasterData().getStaged().getCategories().stream().anyMatch(reference -> reference.getObj().isPresent()))
                    .isTrue();
        });
    }


    @Test
    public void canExpandCustomerGroupOfPrices() throws Exception {
        withCustomerGroup(client(), customerGroup ->
            withUpdateablePricedProduct(client(), PRICE.withCustomerGroup(customerGroup), product -> {
                final ExpansionPath<Product> expansionPath = ProductQuery.expansionPath().masterData().staged().masterVariant().prices().customerGroup();
                final Query<Product> query = query(product).withExpansionPath(expansionPath);
                final List<Price> prices = execute(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> price.getCustomerGroup().map(customerGroupReference -> customerGroupReference.getObj().isPresent()).orElse(false)))
                        .isTrue();
                return product;
            })
        );
    }

    @Test
    public void canExpandChannelOfPrices() throws Exception {
        ChannelFixtures.withChannelOfRole(client(), ChannelRoles.INVENTORY_SUPPLY, channel -> {
            withUpdateablePricedProduct(client(), PRICE.withChannel(channel), product -> {
                final ExpansionPath<Product> expansionPath = ProductQuery.expansionPath().masterData().staged().masterVariant().prices().channel();
                final Query<Product> query = query(product).withExpansionPath(expansionPath);
                final List<Price> prices = execute(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
                assertThat(prices
                        .stream()
                        .anyMatch(price -> price.getChannel().map(channelRef -> channelRef.getObj().isPresent()).orElse(false)))
                        .isTrue();
                return product;
            });
        });
    }

    private QueryDsl<Product> query(final Product product) {
        return ProductQuery.of().withPredicate(ProductQuery.model().id().is(product.getId()));
    }
}