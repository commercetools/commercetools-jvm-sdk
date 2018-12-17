package io.sphere.sdk.inventory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@JsonDeserialize(as = InventoryEntryDraftDsl.class)
@ResourceDraftValue(
        copyFactoryMethods = @CopyFactoryMethod(InventoryEntry.class),
        factoryMethods = {
                @FactoryMethod(parameterNames = {"sku", "quantityOnStock"}),
                @FactoryMethod(parameterNames = {"sku", "quantityOnStock", "expectedDelivery"}),
                @FactoryMethod(parameterNames = {"sku", "quantityOnStock", "expectedDelivery", "restockableInDays", "supplyChannel"
                })
        },
        abstractResourceDraftValueClass = true
)
public interface InventoryEntryDraft extends CustomDraft {
    @Nullable
    ZonedDateTime getExpectedDelivery();

    /**
     * Overall amount of stock. (available + reserved).
     *
     * @see InventoryEntryDraft#getQuantityOnStock()
     * @return the overall amount of stock
     */
    Long getQuantityOnStock();

    @Nullable
    Integer getRestockableInDays();

    String getSku();

    @Nullable
    ResourceIdentifier<Channel> getSupplyChannel();

    @Nullable
    @Override
    CustomFieldsDraft getCustom();

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock) {
        return InventoryEntryDraftDsl.of(sku, quantityOnStock);
    }

    static InventoryEntryDraftDsl of(final String sku, final long quantityOnStock, @Nullable final ZonedDateTime expectedDelivery, @Nullable final Integer restockableInDays, final Referenceable<Channel> supplyChannel) {
        final Reference<Channel> channel =
                Optional.ofNullable(supplyChannel).map(Referenceable::toReference).orElse(null);
        return InventoryEntryDraftDsl.of(sku, quantityOnStock, expectedDelivery, restockableInDays, channel);
    }
}
