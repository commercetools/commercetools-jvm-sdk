package io.sphere.sdk.orderedits.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orderedits.OrderEdit;

import static io.sphere.sdk.http.HttpMethod.POST;

public final class OrderEditApplyCommand extends CommandImpl<OrderEdit> {

    private final String id;

    private final Long resourceVersion;

    private final Long editVersion;

    private OrderEditApplyCommand(final String id, final Long editVersion, final Long resourceVersion) {
        this.id = id;
        this.editVersion = editVersion;
        this.resourceVersion = resourceVersion;
    }

    /**
     * Creates a new instance of {@link OrderEditApplyCommand}
     * @param id of the {@link OrderEdit} that should be applied
     * @param editVersion - version of the {@link OrderEdit} that should be applied
     * @param resourceVersion - version of the {@link io.sphere.sdk.orders.Order} that is referenced from the {@link OrderEdit}
     * @return new instance of {@link OrderEditApplyCommand}
     */
    public static OrderEditApplyCommand of(final String id, final Long editVersion, final Long resourceVersion) {
        return new OrderEditApplyCommand(id, editVersion, resourceVersion);
    }

    /**
     * Creates a new instance of {@link OrderEditApplyCommand}
     * @param orderEditVersioned - versioned {@link OrderEdit} that should be applied
     * @param resourceVersion - version of the {@link io.sphere.sdk.orders.Order} that is referenced from the {@link OrderEdit}
     * @return new instance of {@link OrderEditApplyCommand}
     */
    public static OrderEditApplyCommand of(final Versioned<OrderEdit> orderEditVersioned, final Long resourceVersion) {
        return new OrderEditApplyCommand(orderEditVersioned.getId(), orderEditVersioned.getVersion(), resourceVersion);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(OrderEdit.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/orders/edits/" + id + "/apply", SphereJsonUtils.toJsonString(this));
    }

    public String getId() {
        return id;
    }

    public Long getResourceVersion() {
        return resourceVersion;
    }

    public Long getEditVersion() {
        return editVersion;
    }
}