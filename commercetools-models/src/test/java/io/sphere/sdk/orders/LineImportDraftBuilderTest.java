package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.awt.Color.yellow;
import static org.assertj.core.api.Assertions.assertThat;

public class LineImportDraftBuilderTest {

    @Test
    public void createsLineImportDraft() {
        final String productId = "product-id";
        final ProductVariantImportDraft variant = ProductVariantImportDraftBuilder.of(productId, 1)
                .attributes(
                        AttributeImportDraft.of("attributecolor", yellow),
                        AttributeImportDraft.of("attributerrp", EURO_30)
                ).build();
        final int quantity = 1;
        final Price price = Price.of(EURO_1);
        final LocalizedString name = en("line item name");
        final Set<ItemState> state = asSet(ItemState.of(State.referenceOfId("state-id"), 2));
        final Reference<Channel> supplyChannel = Channel.referenceOfId("channel-id");
        final CustomFieldsDraft custom = CustomFieldsDraftBuilder.ofTypeId("type-id").build();
        final LineItemImportDraftBuilder lineItemImportDraftBuilder = LineItemImportDraftBuilder.of(variant, quantity, price, name)
                .state(state)
                .supplyChannel(supplyChannel)
                .custom(custom);
        final LineItemImportDraft lineItemImportDraft = lineItemImportDraftBuilder.build();
        assertThat(lineItemImportDraft.getName()).isEqualTo(name);
        assertThat(lineItemImportDraft.getProductId()).isEqualTo(productId);
        assertThat(lineItemImportDraft.getVariant()).isEqualTo(variant);
        assertThat(lineItemImportDraft.getPrice()).isEqualTo(PriceDraft.of(price));
        assertThat(lineItemImportDraft.getQuantity()).isEqualTo(quantity);
        assertThat(lineItemImportDraft.getState()).isEqualTo(state);
        assertThat(lineItemImportDraft.getSupplyChannel()).isEqualTo(supplyChannel);
        assertThat(lineItemImportDraft.getCustom()).isEqualTo(custom);
    }
}