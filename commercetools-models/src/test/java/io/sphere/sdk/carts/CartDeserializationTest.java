package io.sphere.sdk.carts;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the deserialization of {@link Cart} objects.
 */
public class CartDeserializationTest {

    @Test
    public void cartWithRefusedGifts() {
        final Cart cart = SphereJsonUtils.readObjectFromResource("carts/cart-with-refused-gifts.json", Cart.class);

        final List<Reference<CartDiscount>> refusedGifts = cart.getRefusedGifts();
        assertThat(refusedGifts).hasSize(1);

        final Reference<CartDiscount> refusedGift = refusedGifts.get(0);
        assertThat(refusedGift.getId()).isEqualTo("<my-cart-discount-id>");
    }

    @Test
    public void cartWithGiftLineItem() {
        final Cart cart = SphereJsonUtils.readObjectFromResource("carts/cart-with-gift-line-item.json", Cart.class);

        final List<LineItem> lineItems = cart.getLineItems();
        assertThat(lineItems).hasSize(1);

        final LineItem lineItem = lineItems.get(0);
        assertThat(lineItem.getLineItemMode()).isEqualTo(LineItemMode.GIFT_LINE_ITEM);
    }

    @Test
    public void cartWithStandardLineItem() {
        final Cart cart = SphereJsonUtils.readObjectFromResource("carts/cart-with-standard-line-item.json", Cart.class);

        final List<LineItem> lineItems = cart.getLineItems();
        assertThat(lineItems).hasSize(1);

        final LineItem lineItem = lineItems.get(0);
        assertThat(lineItem.getLineItemMode()).isEqualTo(LineItemMode.STANDARD);
    }
}
