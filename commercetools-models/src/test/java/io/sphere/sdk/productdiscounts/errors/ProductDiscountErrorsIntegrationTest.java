package io.sphere.sdk.productdiscounts.errors;

import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.productdiscounts.queries.MatchingProductDiscountGet;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductDiscountErrorsIntegrationTest extends IntegrationTest {

    @Test
    public void noMatchingProductDiscountFound() {
        withUpdateableProductDiscount(client(),((productDiscount, product) -> {
            final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
            final Price invalidPice = Price.of(MoneyImpl.of(0, DefaultCurrencyUnits.USD));
            final Throwable throwable = catchThrowable(() -> client().executeBlocking(MatchingProductDiscountGet.of(product.getId(), masterVariant.getId(), true, invalidPice)));
            assertThat(throwable).isInstanceOf(NotFoundException.class);
            final NotFoundException e = (NotFoundException) throwable;
            assertThat((((NotFoundException) throwable).getJsonBody().get("errors").get(0).get("code")).textValue()).isEqualTo("NoMatchingProductDiscountFound");

            return productDiscount;
        }));
    }

}
