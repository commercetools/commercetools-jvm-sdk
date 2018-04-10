package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.assertThat;

public class MatchingProductDiscountGetIntegrationTest extends IntegrationTest {

    @Test
    public void executeValidQuery(){
        withUpdateableProductDiscount(client(),((productDiscount, product) -> {
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            assertThat(masterVariant.getPrices()).isNotEmpty();
            final Price masterVariantPrice = masterVariant.getPrices().get(0);
            final ProductDiscount queryedProductDiscount = client().executeBlocking(MatchingProductDiscountGet.of(product.getId(), masterVariant.getId(),true , masterVariantPrice));
            assertThat(productDiscount).isEqualTo(queryedProductDiscount);
            return productDiscount;
        }));
    }

    @Test(expected = NotFoundException.class)
    public void executeInvalidQuery(){
        withUpdateableProductDiscount(client(),((productDiscount, product) -> {
                final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
                final Price invalidPice = Price.of(MoneyImpl.of(0, DefaultCurrencyUnits.USD));
                final ProductDiscount queryedProductDiscount = client().executeBlocking(MatchingProductDiscountGet.of(product.getId(), masterVariant.getId(), true, invalidPice));
                return productDiscount;

        }));
    }

}
