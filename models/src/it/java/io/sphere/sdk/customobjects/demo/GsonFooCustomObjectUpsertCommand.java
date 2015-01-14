package io.sphere.sdk.customobjects.demo;

import com.google.gson.Gson;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.commands.CustomObjectCustomJsonMappingUpsertCommand;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

public class GsonFooCustomObjectUpsertCommand extends CustomObjectCustomJsonMappingUpsertCommand<GsonFoo> {
    private final Gson gson = new Gson();
    private final GsonFooCustomObjectDraft draft;

    public GsonFooCustomObjectUpsertCommand(final GsonFooCustomObjectDraft draft) {
        this.draft = draft;
    }

    @Override
    protected String bodyAsJsonString() {
        return gson.toJson(draft);
    }

    @Override
    public Function<HttpResponse, CustomObject<GsonFoo>> resultMapper() {
        return httpResponse -> {
            final String jsonAsString = getBodyAsString(httpResponse);
            return gson.fromJson(jsonAsString, GsonFooCustomObject.class);
        };
    }
}
