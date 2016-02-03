package io.sphere.sdk.http;

import java.io.File;

public final class FileHttpRequestBody extends Base implements HttpRequestBody {
    private final File body;

    private FileHttpRequestBody(final File body) {
        this.body = body;
    }

    public static FileHttpRequestBody of(final File body) {
        return new FileHttpRequestBody(body);
    }

    public File getFile() {
        return body;
    }
}