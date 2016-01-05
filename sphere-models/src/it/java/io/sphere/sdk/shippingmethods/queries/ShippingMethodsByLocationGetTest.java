package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.models.DefaultCurrencyUnits.USD;

public class ShippingMethodsByLocationGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final List<ShippingMethod> result =
                client().executeBlocking(ShippingMethodsByLocationGet.of(CountryCode.US, "Kansas", USD));
    }
}