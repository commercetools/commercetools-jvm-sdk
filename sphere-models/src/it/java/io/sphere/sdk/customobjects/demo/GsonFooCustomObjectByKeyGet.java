package io.sphere.sdk.customobjects.demo;

import com.google.gson.Gson;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingByKeyGet;
import io.sphere.sdk.http.HttpResponse;

public class GsonFooCustomObjectByKeyGet extends CustomObjectCustomJsonMappingByKeyGet<GsonFoo> {

    public GsonFooCustomObjectByKeyGet(final String container, final String key) {
        super(container, key);
    }

    @Override
    protected CustomObject<GsonFoo> deserializeCustomObject(final HttpResponse httpResponse) {
        final String jsonAsString = SphereRequestUtils.getBodyAsString(httpResponse);
        return new Gson().fromJson(jsonAsString, GsonFooCustomObject.class);
    }
}
