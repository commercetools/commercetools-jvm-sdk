package io.sphere.sdk.orders.errors;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomShippingMethod;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxCategoryDraftBuilder;
import io.sphere.sdk.taxcategories.TaxRateDraft;
import io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.neovisionaries.i18n.CountryCode.AT;
import static io.sphere.sdk.carts.CartFixtures.createCartWithShippingAddress;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class OrderErrorIntegrationTest extends IntegrationTest {
    @Test
    public void matchingPriceNotFound() {
        withProduct(client(), product -> {
            final int quantity = 5;
            final int variantId = 1;
            Product productPublished = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final List<LineItemDraft> lineItemDrafts = asList(LineItemDraft.of(productPublished, variantId, quantity));
            final CartDraftDsl cartDraft = CartDraft.of(EUR)
                    .withTaxMode(TaxMode.EXTERNAL)
                    .withCountry(CountryCode.DE)
                    .withShippingAddress(Address.of(CountryCode.DE))
                    .withLineItems(lineItemDrafts);
            final CartCreateCommand cartCreateCommand = CartCreateCommand.of(cartDraft);
            final Throwable throwable = catchThrowable(() -> client().executeBlocking(cartCreateCommand));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(MatchingPriceNotFoundError.CODE)).isTrue();
            final MatchingPriceNotFoundError error = e.getErrors().get(0).as(MatchingPriceNotFoundError.class);
            assertThat(error.getProductId()).isEqualTo(productPublished.getId());
        });
    }

    @Test
    public void missingTaxRateForCountry() {
        final TaxCategory taxCategory = createTaxCategory();
        final Cart cart = createCartWithShippingAddress(client());
        assertThat(cart.getShippingInfo()).isNull();
        final MonetaryAmount price = MoneyImpl.of(new BigDecimal("23.50"), EUR);
        final ShippingRate shippingRate = ShippingRate.of(price, null, Collections.emptyList());
        final String shippingMethodName = "custom-shipping";
        final SetCustomShippingMethod action = SetCustomShippingMethod.of(shippingMethodName, shippingRate, taxCategory);
        final Throwable throwable = catchThrowable(() -> client().executeBlocking(CartUpdateCommand.of(cart, action)));
        assertThat(throwable).isInstanceOf(ErrorResponseException.class);
        final ErrorResponseException e = (ErrorResponseException) throwable;
        assertThat(e.hasErrorCode(MissingTaxRateForCountryError.CODE)).isTrue();
        final MissingTaxRateForCountryError error = e.getErrors().get(0).as(MissingTaxRateForCountryError.class);
        assertThat(error.getTaxCategoryId()).isEqualTo(taxCategory.getId());

        client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory));
        client().executeBlocking(CartDeleteCommand.of(cart));
    }


    private TaxCategory createTaxCategory() {
        final TaxRateDraft taxRate = TaxRateDraft.of("Austria tax", 0.19, false, AT);
        final TaxCategoryDraft taxCategoryDraft = TaxCategoryDraftBuilder.of("Austria tax", singletonList(taxRate), "Normal-Steuersatz").key(randomKey()).build();
        final TaxCategory taxCategory = client().executeBlocking(TaxCategoryCreateCommand.of(taxCategoryDraft));
        return taxCategory;
    }

}
