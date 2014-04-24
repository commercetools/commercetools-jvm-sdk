package io.sphere.client;

import com.ning.http.client.Cookie;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.List;

/** Fake HTTP response with preset status and body. */
public class MockHttpResponse implements Response {
    int statusCode;
    String body;

    public MockHttpResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
    @Override
    public int getStatusCode() {
        return statusCode;
    }
    @Override
    public String getResponseBody(String charset) throws IOException {
        return body;
    }
    @Override
    public String getResponseBody() throws IOException {
        return body;
    }

    // everything else is unimplemented

    @Override
    public ByteBuffer getResponseBodyAsByteBuffer() throws IOException {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getStatusText() {
        throw new UnsupportedOperationException();
    }
    @Override
    public byte[] getResponseBodyAsBytes() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getResponseBodyAsStream() throws IOException {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getResponseBodyExcerpt(int maxLength, String charset) throws IOException {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getResponseBodyExcerpt(int maxLength) throws IOException {
        throw new UnsupportedOperationException();
    }
    @Override
    public URI getUri() throws MalformedURLException {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getContentType() {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getHeader(String name) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<String> getHeaders(String name) {
        throw new UnsupportedOperationException();
    }
    @Override
    public FluentCaseInsensitiveStringsMap getHeaders() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean isRedirected() {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<Cookie> getCookies() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasResponseStatus() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasResponseHeaders() {
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasResponseBody() {
        throw new UnsupportedOperationException();
    }
}
