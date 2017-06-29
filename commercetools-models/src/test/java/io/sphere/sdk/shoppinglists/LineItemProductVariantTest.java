package io.sphere.sdk.shoppinglists;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static io.sphere.sdk.test.SphereTestUtils.asList;
import static org.assertj.core.api.Assertions.*;




public class LineItemProductVariantTest {

    private LineItem lineItem;
    private ProductVariant productVariant;


    @Before
    public void setUp() throws Exception {
        lineItem = SphereJsonUtils.readObjectFromResource("shoppingList/line-item-variant.json", LineItem.class);
        productVariant = lineItem.getVariant();
    }

    @Test
    public void checkProductVariantId() throws Exception {
        final String productId = "73f002a1-0c5a-4849-b985-fa1145a8c76d";
        assertThat(productId.equals(lineItem.getProductId()));
        assertThat(productId.equals(productVariant.getIdentifier().getProductId()));

    }

    @Test
    public void checkProductVaiantAtributes() throws Exception {

        assertThat(productVariant.getSku().equals("1f92d491-2180-489c-bcef-3966cdc68c0b"));
        assertThat(productVariant.getPrices())
                .as("comparing prices ")
                .isEqualTo(asList(Price.of(BigDecimal.valueOf(12.34), DefaultCurrencyUnits.EUR).withCountry(CountryCode.DE).withId("70a56289-bd20-4c47-8364-47b498ad993b")));

        assertThat(productVariant.getImages().isEmpty());
        assertThat(productVariant.getAssets().isEmpty());
        assertThat(productVariant.getAttributes().isEmpty());

    }
}
