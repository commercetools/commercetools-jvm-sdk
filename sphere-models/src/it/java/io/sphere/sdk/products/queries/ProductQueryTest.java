package io.sphere.sdk.products.queries;

import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withCustomerGroup;
import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
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
                    .withExpansionPaths(ProductExpansionModel.of().masterData().staged().categories());
            assertThat(execute(query).head().get().getMasterData().getStaged().getCategories().stream().anyMatch(reference -> reference.getObj() != null))
                    .isTrue();
        });
    }


    @Test
    public void canExpandCustomerGroupOfPrices() throws Exception {
        withCustomerGroup(client(), customerGroup ->
                        withUpdateablePricedProduct(client(), PRICE.withCustomerGroup(customerGroup), product -> {
                            final ExpansionPath<Product> expansionPath = ProductExpansionModel.of().masterData().staged().masterVariant().prices().customerGroup();
                            final Query<Product> query = query(product).withExpansionPaths(expansionPath);
                            final List<Price> prices = execute(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
                            assertThat(prices
                                    .stream()
                                    .anyMatch(price -> Optional.ofNullable(price.getCustomerGroup()).map(customerGroupReference -> customerGroupReference.getObj() != null).orElse(false)))
                                    .isTrue();
                            return product;
                        })
        );
    }

    @Test
    public void canExpandChannelOfPrices() throws Exception {
        ChannelFixtures.withChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY, channel -> {
            withUpdateablePricedProduct(client(), PRICE.withChannel(channel), product -> {
                final ExpansionPath<Product> expansionPath = ProductExpansionModel.of().masterData().staged().masterVariant().prices().channel();
                final Query<Product> query = query(product).withExpansionPaths(expansionPath);
                final List<Price> prices = execute(query).head().get().getMasterData().getStaged().getMasterVariant().getPrices();
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
            final Product loadedProduct = execute(query).head().get();
            assertThat(loadedProduct.getId()).isEqualTo(product.getId());
            return productDiscount;
        });
    }

    private ProductQuery query(final Product product) {
        return ProductQuery.of().withPredicates(m -> m.id().is(product.getId()));
    }
}