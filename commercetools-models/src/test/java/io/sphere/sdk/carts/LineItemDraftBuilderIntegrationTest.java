package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelFixtures;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraftBuilder;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.TypeFixtures;
import org.junit.Test;

import javax.money.MonetaryAmount;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LineItemDraftBuilder}
 */
public class LineItemDraftBuilderIntegrationTest extends IntegrationTest {

    @Test
    public void buildFromTemplate() {
        withCustomer(client(), customer -> {
            withUpdateableType(client(), type -> {
                final CustomFieldsDraft customFieldsDraft =
                        CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap(TypeFixtures.STRING_FIELD_NAME, "foo"));
                final long quantity = 3;
                final Channel supplyChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.INVENTORY_SUPPLY);
                final Channel distributionChannel = ChannelFixtures.persistentChannelOfRole(client(), ChannelRole.PRODUCT_DISTRIBUTION);
                final MonetaryAmount externalPrice = EURO_1;
                final ExternalTaxRateDraft externalTaxRate = ExternalTaxRateDraftBuilder.ofAmount(1.2, "tax rate name", CountryCode.AU).build();
                final ExternalLineItemTotalPrice externalTotalPrice = ExternalLineItemTotalPrice.ofPriceAndTotalPrice(EURO_5, EURO_15);
                final String sku = "sku";
                final String productId = "product-id";
                final int variantId = 1;
                final LineItemDraft lineItemDraft = LineItemDraft.of(productId, variantId, quantity)
                        .withCustom(customFieldsDraft)
                        .withDistributionChannel(distributionChannel)
                        .withExternalPrice(externalPrice)
                        .withExternalTaxRate(externalTaxRate)
                        .withExternalTotalPrice(externalTotalPrice)
                        .withSku(sku)
                        .withSupplyChannel(supplyChannel)
                        .newBuilder().build();

                final LineItemDraft builtLineItemDraft = LineItemDraftBuilder.of(lineItemDraft).build();

                assertThat(builtLineItemDraft.getProductId()).isEqualTo(productId);
                assertThat(builtLineItemDraft.getVariantId()).isEqualTo(variantId);
                assertThat(builtLineItemDraft.getQuantity()).isEqualTo(quantity);
                assertThat(builtLineItemDraft.getCustom()).isEqualTo(customFieldsDraft);
                assertThat(builtLineItemDraft.getDistributionChannel()).isEqualTo(distributionChannel.toResourceIdentifier());
                assertThat(builtLineItemDraft.getExternalPrice()).isEqualTo(externalPrice);
                assertThat(builtLineItemDraft.getExternalTaxRate()).isEqualTo(externalTaxRate);
                assertThat(builtLineItemDraft.getExternalTotalPrice()).isEqualTo(externalTotalPrice);
                assertThat(builtLineItemDraft.getSku()).isEqualTo(sku);
                assertThat(builtLineItemDraft.getSupplyChannel()).isEqualTo(supplyChannel.toResourceIdentifier());

                return type;
            });
        });
    }

}
