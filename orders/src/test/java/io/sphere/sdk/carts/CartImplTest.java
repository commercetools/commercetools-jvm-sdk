package io.sphere.sdk.carts;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.models.DefaultCurrencyUnits.*;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class CartImplTest extends DefaultModelSubclassTest<Cart> {

    @Override
    public void example1ToStringContainsSubclassAttributes(final String example1String) {

    }

    @Override
    protected DefaultModelBuilder<Cart> newExample1Builder() {
        return CartBuilder.of(EUR).country(DE);
    }

    @Override
    protected DefaultModelBuilder<Cart> newExample2Builder() {
        return CartBuilder.of(EUR);
    }

    @Override
    public void testSubclassGettersOfExample1(final Cart model) {
        assertThat(model.getCountry()).isPresentAs(DE);
    }
}