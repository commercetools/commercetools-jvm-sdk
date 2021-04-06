package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.List;

/**
 * Defines cart discount type with the corresponding value. The type can be relative or absolute.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RelativeCartDiscountValue.class, name = "relative"),
        @JsonSubTypes.Type(value = AbsoluteCartDiscountValue.class, name = "absolute"),
        @JsonSubTypes.Type(value = FixedCartDiscountValue.class, name = "fixed"),
        @JsonSubTypes.Type(value = GiftLineItemCartDiscountValue.class, name = "giftLineItem")})
public interface CartDiscountValue {
    static AbsoluteCartDiscountValue ofAbsolute(final List<MonetaryAmount> money) {
        return AbsoluteCartDiscountValue.of(money);
    }

    static AbsoluteCartDiscountValue ofAbsolute(final MonetaryAmount money) {
        return AbsoluteCartDiscountValue.of(Collections.singletonList(money));
    }

    static RelativeCartDiscountValue ofRelative(final int permyriad) {
        return RelativeCartDiscountValue.of(permyriad);
    }

    static FixedCartDiscountValue ofFixed(final MonetaryAmount money) {
        return FixedCartDiscountValue.of(Collections.singletonList(money));
    }

    static GiftLineItemCartDiscountValue ofGiftLineItem(final ByIdVariantIdentifier variantIdentifier,
                                                        @Nullable final ResourceIdentifier<Channel> supplyChannel,
                                                        @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        return ofGiftLineItem(ResourceIdentifier.ofId(variantIdentifier.getProductId(),Product.resourceTypeId()), variantIdentifier.getVariantId(), supplyChannel, distributionChannel);
    }

    static GiftLineItemCartDiscountValue ofGiftLineItem(final ResourceIdentifier<Product> product,
                                                        final Integer variantId,
                                                        @Nullable final ResourceIdentifier<Channel> supplyChannel,
                                                        @Nullable final ResourceIdentifier<Channel> distributionChannel) {
        return GiftLineItemCartDiscountValue.of(product, variantId, supplyChannel, distributionChannel);
    }
}
