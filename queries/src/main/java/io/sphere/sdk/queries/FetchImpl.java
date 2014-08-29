package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import java.util.Optional;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.utils.UrlQueryBuilder;

public abstract class FetchImpl<T> extends Base implements Fetch<T> {

    private final Identifiable<T> identifiable;

    protected FetchImpl(final Identifiable<T> identifiable) {
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
        final String queryParameters = additionalQueryParameters().toStringWithOptionalQuestionMark();
        final String path = baseEndpointWithoutId + "/" + identifiable.getId() + queryParameters;
        return HttpRequest.of(HttpMethod.GET, path);
    }

    protected abstract TypeReference<T> typeReference();

    protected abstract String baseEndpointWithoutId();

    protected UrlQueryBuilder additionalQueryParameters() {
        return new UrlQueryBuilder();
    }

    @Override
    public boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == 404;
    }
}
