package sphere.internal;

import net.jcip.annotations.Immutable;
import sphere.InventoryService;
import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.client.shop.model.InventoryEntryUpdate;
import play.libs.F;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.util.Async;

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

    @Override
    public FetchRequest<InventoryEntry> bySku(String sku) {
        return Async.adapt(service.bySku(sku));
    }

    @Override
    public QueryRequest<InventoryEntry> query() {
        return Async.adapt(service.query());
    }

    @Override
    public InventoryEntry updateInventoryEntry(VersionedId id, InventoryEntryUpdate update) {
        return service.updateInventoryEntry(id, update).execute();
    }

    @Override
    public F.Promise<SphereResult<InventoryEntry>> updateInventoryEntryAsync(VersionedId id, InventoryEntryUpdate update) {
        return Async.execute(service.updateInventoryEntry(id, update));
    }
}