package sphere.internal;

import io.sphere.client.shop.model.InventoryEntry;
import net.jcip.annotations.Immutable;
import sphere.FetchRequest;
import sphere.InventoryService;

import javax.annotation.Nonnull;

/** InventoryService with Play-specific async methods. */
@Immutable
public class InventoryServiceAdapter implements InventoryService {
    private final io.sphere.client.shop.InventoryService service;
    public InventoryServiceAdapter(@Nonnull io.sphere.client.shop.InventoryService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<InventoryEntry> byId(String id) {
        return new FetchRequestAdapter<InventoryEntry>(service.byId(id));
    }

    @Override public FetchRequest<InventoryEntry> byProductVariant(String productId, String variantId) {
        return new FetchRequestAdapter<InventoryEntry>(service.byProductVariant(productId, variantId));
    }
}