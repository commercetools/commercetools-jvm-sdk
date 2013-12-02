package sphere.internal;

import net.jcip.annotations.Immutable;
import sphere.InventoryService;

import javax.annotation.Nonnull;

/**
 * InventoryService with Play-specific async methods.
 */
@Immutable
public class InventoryServiceAdapter implements InventoryService {
    private final io.sphere.client.shop.InventoryService service;

    public InventoryServiceAdapter(@Nonnull io.sphere.client.shop.InventoryService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }
}