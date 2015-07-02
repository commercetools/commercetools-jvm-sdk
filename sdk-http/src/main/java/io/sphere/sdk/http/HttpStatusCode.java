package io.sphere.sdk.http;

public final class HttpStatusCode {
    private HttpStatusCode() {
    }

    public static final int OK_200 = 200;
    public static final int CREATED_201 = 201;
    public static final int ACCEPTED_202 = 202;
    public static final int NO_CONTENT_204 = 204;
    public static final int PARTIAL_CONTENT_206 = 206;

    public static final int BAD_REQUEST_400 = 400;
    public static final int UNAUTHORIZED_401 = 401;
    public static final int FORBIDDEN_403 = 403;
    public static final int NOT_FOUND_404 = 404;
    public static final int CONFLICT_409 = 409;
    public static final int TOO_MANY_REQUESTS_429 = 429;

    public static final int INTERNAL_SERVER_ERROR_500 = 500;
    public static final int BAD_GATEWAY_502 = 502;
    public static final int SERVICE_UNAVAILABLE_503 = 503;
    public static final int GATEWAY_TIMEOUT_504 = 504;
}
