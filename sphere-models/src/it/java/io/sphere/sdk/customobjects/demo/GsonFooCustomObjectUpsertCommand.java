package io.sphere.sdk.customobjects.demo;

import com.google.gson.Gson;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.customobjects.commands.CustomObjectCustomJsonMappingUpsertCommand;
import io.sphere.sdk.http.HttpResponse;

public class GsonFooCustomObjectUpsertCommand extends CustomObjectCustomJsonMappingUpsertCommand<GsonFoo> {
    private final Gson gson = new Gson();
    private final GsonFooCustomObjectDraft draft;

    public GsonFooCustomObjectUpsertCommand(final GsonFooCustomObjectDraft draft) {
        this.draft = draft;
    }

    //used to send the value of the object
    @Override
    protected String bodyAsJsonString() {
        return gson.toJson(draft);
    }

    //used to deserialize the response
    @Override
    public GsonFooCustomObject deserialize(final HttpResponse httpResponse) {
        final String jsonAsString = SphereRequestUtils.getBodyAsString(httpResponse);
        return gson.fromJson(jsonAsString, GsonFooCustomObject.class);
    }
}
