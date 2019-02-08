package io.sphere.sdk.orderedits.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.orderedits.OrderEdit;

import static io.sphere.sdk.http.HttpMethod.POST;

public final class OrderEditApplyCommand extends CommandImpl<OrderEdit> {

    private final String id;

    private final Long resourceVersion;

    private final Long editVersion;

    private OrderEditApplyCommand(final String id, final Long resourceVersion, final Long editVersion) {
        this.id = id;
        this.resourceVersion = resourceVersion;
        this.editVersion = editVersion;
    }

    public static OrderEditApplyCommand of(final String id, final Long resourceVersion, final Long editVersion) {
        return new OrderEditApplyCommand(id, resourceVersion, editVersion);
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