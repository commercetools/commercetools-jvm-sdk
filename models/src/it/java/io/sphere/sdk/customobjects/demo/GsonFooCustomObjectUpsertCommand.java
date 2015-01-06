package io.sphere.sdk.customobjects.demo;

import com.google.gson.Gson;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class GsonFooCustomObjectUpsertCommand implements ClientRequest<CustomObject<GsonFoo>> {
    private final Gson gson = new Gson();
    private final GsonFooCustomObjectDraft draft;

    public GsonFooCustomObjectUpsertCommand(final GsonFooCustomObjectDraft draft) {
        this.draft = draft;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.POST, "/custom-objects", gson.toJson(draft));
    }

    @Override
    public Function<HttpResponse, CustomObject<GsonFoo>> resultMapper() {
        return httpResponse -> {
            final String jsonAsString = new String(httpResponse.getResponseBody().get(), StandardCharsets.UTF_8);
            final CustomObject<GsonFoo> fooCustomObject = gson.fromJson(jsonAsString, GsonFooCustomObject.class);
            return fooCustomObject;
        };
    }
}
