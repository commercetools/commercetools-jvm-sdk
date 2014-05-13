package io.sphere.sdk.client;

class HttpResponse {
    private final int statusCode;
    private final String responseBody;

    HttpResponse(final int statusCode, final String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public static HttpResponse of(final int status, final String responseBody) {
        return new HttpResponse(status, responseBody);
    }
}
