package io.sphere.sdk.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import java.util.Optional;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.utils.JsonUtils;

public abstract class FetchImpl<T> extends Base implements Fetch<T> {

    private final Identifiable identifiable;

    protected FetchImpl(final Identifiable identifiable) {
        this.identifiable = identifiable;
    }

    @Override
    public Function<HttpResponse, Optional<T>> resultMapper() {
        return httpResponse -> {
            final Optional<T> result;
            if (httpResponse.getStatusCode() == 404) {
                result = Optional.empty();
            } else {
                result = Optional.of(JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody()));
            }
            return result;
        };
    }

    @Override
    public HttpRequest httpRequest() {
        final String baseEndpointWithoutId = baseEndpointWithoutId();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequest.of(HttpMethod.GET, baseEndpointWithoutId + "/" + identifiable.getId());
    }

    protected abstract TypeReference<T> typeReference();

    protected abstract String baseEndpointWithoutId();
}
