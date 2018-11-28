package io.sphere.sdk.apiclient.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.apiclient.ApiClient;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.json.SphereJsonUtils;

final class ApiClientDeleteCommandImpl extends CommandImpl<ApiClient> implements ApiClientDeleteCommand{

    private final String id;

    ApiClientDeleteCommandImpl(final String id) {
        this.id = id;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(ApiClient.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.DELETE, String.format("/api-clients/%s", id));
    }
}
