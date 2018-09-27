package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.models.errors.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.sphere.sdk.client.HttpResponseBodyUtils.bytesToString;

final class ExceptionFactory {
    private final List<HttpResponseExceptionResponsibility> responsibilities = new ArrayList<>();

    private ExceptionFactory when(final Predicate<HttpResponse> predicate, final Function<HttpResponse, SphereException> exceptionCreator) {
        return addResponsibility(predicate, exceptionCreator);
    }

    private ExceptionFactory addResponsibility(final Predicate<HttpResponse> predicate, final Function<HttpResponse, SphereException> exceptionCreator) {
        final HttpResponseExceptionResponsibility r = new HttpResponseExceptionResponsibility(predicate, exceptionCreator);
        responsibilities.add(r);
        return this;
    }

    private ExceptionFactory whenStatus(final int status, final Function<HttpResponse, SphereException> exceptionCreator) {
        return addResponsibility(response -> response.getStatusCode() == status, response -> exceptionCreator.apply(response));
    }

    private static String extractBody(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse.getResponseBody()).map(b -> bytesToString(b)).orElse("");
    }

    public static ExceptionFactory of() {
        final ExceptionFactory exceptionFactory = new ExceptionFactory()
                .when(r -> isServiceNotAvailable(r), r -> new ServiceUnavailableException(extractBody(r)))
                .whenStatus(401, r -> {
                    final String body = extractBody(r);
                    return body.contains("invalid_token") ? new InvalidTokenException() : new UnauthorizedException(body,401);
                })
                .whenStatus(403, r -> new ForbiddenException(extractBody(r)))
                .whenStatus(500, r -> new InternalServerErrorException(extractBody(r)))
                .whenStatus(502, r -> new BadGatewayException(extractBody(r)))
                .whenStatus(503, r -> new ServiceUnavailableException(extractBody(r)))
                .whenStatus(504, r -> new GatewayTimeoutException(extractBody(r)))
                .whenStatus(409, r -> {
                    final ErrorResponse errorResponse = SphereJsonUtils.readObject(r.getResponseBody(), ErrorResponse.typeReference());
                    return new ConcurrentModificationException(errorResponse);
                })
                .whenStatus(413, r -> new RequestEntityTooLargeException())
                .whenStatus(400, r -> {
                    final String body = extractBody(r);
                    if (body.contains("invalid_scope")) {
                        return new InvalidTokenException();
                    }
                    final ErrorResponse errorResponse = SphereJsonUtils.readObject(r.getResponseBody(), ErrorResponse.typeReference());
                    return new ErrorResponseException(errorResponse);
                })
                .whenStatus(404, r -> new NotFoundException())
                //default
                .when(response -> true, r -> new SphereException("Can't parse backend response."));
        return exceptionFactory;
    }

    //hack since backend returns in same error conditions response code 500 but with the message Service unavailable
    private static boolean isServiceNotAvailable(final HttpResponse httpResponse) {
        return httpResponse.getStatusCode() == 503 || Optional.ofNullable(httpResponse.getResponseBody()).map(b -> bytesToString(b)).map(s -> s.contains("<h2>Service Unavailable</h2>")).orElse(false);
    }

    public <T> SphereException createException(final HttpResponse httpResponse, final SphereRequest<T> sphereRequest, final ObjectMapper objectMapper) {
        final HttpResponseExceptionResponsibility responsibility = responsibilities
                .stream()
                .filter(x -> x.getPredicate().test(httpResponse))
                .findFirst().get();
        return responsibility.getExceptionCreator().apply(httpResponse);
    }
}
