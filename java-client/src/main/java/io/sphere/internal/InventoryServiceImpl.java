package io.sphere.internal;

import com.google.common.base.Function;
import io.sphere.client.*;
import io.sphere.client.exceptions.DuplicateSkuException;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.InventoryService;
import io.sphere.client.shop.model.InventoryEntry;
import io.sphere.client.shop.model.InventoryEntryUpdate;
import io.sphere.internal.command.InventoryEntryCommands;
import io.sphere.internal.command.InventoryEntryCommands.CreateInventoryEntry;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.errors.ErrorHandling;
import io.sphere.internal.request.RequestFactory;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;

import static io.sphere.internal.request.FetchRequestBasedOnQuery.asFetchRequest;

public class InventoryServiceImpl extends ProjectScopedAPI<InventoryEntry> implements InventoryService {
    public InventoryServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<InventoryEntry>() {}, new TypeReference<QueryResult<InventoryEntry>>(){});
    }

    @Override
    public CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock) {
        return createInventoryEntry(sku, quantityOnStock, null, null);
    }

    @Override
    public CommandRequest<InventoryEntry> createInventoryEntry(final String sku, final long quantityOnStock,
                                                               final Long restockableInDays, final DateTime expectedDelivery) {
        return createInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery, null);
    }

    @Override
    public CommandRequest<InventoryEntry> createInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery, final String supplyChannelId) {
        final CreateInventoryEntry command = new CreateInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery, supplyChannelId);
        return createCommandRequest(endpoints.inventory.root(), command).withErrorHandling(handleDuplicateSku(sku));
    }

    private Function<SphereBackendException, SphereException> handleDuplicateSku(final String sku) {
        return ErrorHandling.handleDuplicateField("sku", new DuplicateSkuException(sku));
    }

    @Override
    public FetchRequest<InventoryEntry> bySku(final String sku) {
        return asFetchRequest(query().where("sku = \"" + sku + "\" and supplyChannel is not defined"));
    }

    @Override
    public QueryRequest<InventoryEntry> query() {
        return queryImpl(endpoints.inventory.root());
    }

    @Override
    public CommandRequest<InventoryEntry> updateInventoryEntry(VersionedId id, InventoryEntryUpdate update) {
        return createCommandRequest(
                endpoints.inventory.byId(id.getId()),
                new UpdateCommand<InventoryEntryCommands.InventoryEntryUpdateAction>(id.getVersion(), update));
    }
}
