package sphere.internal;

import io.sphere.client.shop.model.InventoryEntry;
import net.jcip.annotations.Immutable;
import sphere.FetchRequest;
import sphere.InventoryService;
import sphere.util.Async;

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
        return Async.adapt(service.byId(id));
    }

    @Override public FetchRequest<InventoryEntry> byProductVariant(String productId, String variantId) {
        return Async.adapt(service.byProductVariant(productId, variantId));
    }
}