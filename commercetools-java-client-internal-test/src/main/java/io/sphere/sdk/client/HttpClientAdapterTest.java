package io.sphere.sdk.client;

import io.sphere.sdk.http.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class HttpClientAdapterTest {

    protected abstract HttpClient createClient();
    protected abstract int port();

    @Test
    public final void testConnection() {
        final HttpClient client = createClient();
        final HttpResponse response = client.execute(HttpRequest.of(HttpMethod.GET, "https://api.europe-west1.gcp.commercetools.com")).toCompletableFuture().join();
        client.close();
        final String body = new String(response.getResponseBody());
        final Integer statusCode = response.getStatusCode();
        assertThat(statusCode).isLessThan(400);
        assertThat(body).containsIgnoringCase("commercetools");
    }

    @Test
    public final void stringBody() throws Exception {
        final String bodyData = "123456789";
        final HttpRequestBody requestBody = StringHttpRequestBody.of(bodyData);
        final int length = bodyData.length();
        checkBodyRequest(port(), requestBody, length);
    }

    @Test
    public final void fileBody() throws Exception {
        final String bodyData = "123456789";

        final File file = File.createTempFile(RandomStringUtils.randomAlphanumeric(32), "ext");
        FileUtils.writeStringToFile(file, bodyData, Charset.defaultCharset());
        file.deleteOnExit();

        final HttpRequestBody requestBody = FileHttpRequestBody.of(file);
        final int length = bodyData.length();
        checkBodyRequest(port() + 100, requestBody, length);
    }

    @Test
    public final void formEncodedBody() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");

        final HttpRequestBody requestBody = FormUrlEncodedHttpRequestBody.ofStringMap(map);
        final int length = 11;

        checkBodyRequest(port() + 200, requestBody, length);
    }

    @Test
    public final void userAgent() throws Exception {
        try (final HttpClient client = createClient()) {
            assertThat(client.getUserAgent()).matches("\\S+/\\S+");
        }
    }

    private void checkBodyRequest(final int port, final HttpRequestBody requestBody, final int length) throws Exception {
        final ServerSocket serverSocket = new ServerSocket(port);
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            final StringBuilder stringBuilder = new StringBuilder();
            try (final Socket socket = serverSocket.accept()){
                String inputLine;
                final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //important: the input stream does not contain data and is then closed
                //the tcp connection is still active and just contains no new data
                //with org.apache.commons.io.IOUtils.toString(java.io.InputStream) it would block forever
                while ((inputLine = in.readLine()) != null
                        //HTTP headers end with empty line
                        && !inputLine.isEmpty()) {
                    stringBuilder.append(inputLine).append("\n");
                }
                final OutputStream outputStream = socket.getOutputStream();
                IOUtils.write("HTTP/1.0 200 OK\r\n\r\n", outputStream, Charset.defaultCharset());
                outputStream.flush();
                outputStream.close();
                in.close();
                return stringBuilder.toString();
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        });
        Thread.sleep(100);
        final HttpClient client = createClient();
        final HttpRequest httpRequest = HttpRequest.of(HttpMethod.POST, "http://localhost:" + port, HttpHeaders.of("foo", "bar"), requestBody);
        final HttpResponse response = client.execute(httpRequest).toCompletableFuture().get(15, TimeUnit.SECONDS);
        client.close();
        final String receivedHttpHeadersAndBody = future.join();
        LoggerFactory.getLogger(HttpClientAdapterTest.class).info(receivedHttpHeadersAndBody);
        assertThat(receivedHttpHeadersAndBody).containsIgnoringCase("Content-Length: " + length);
    }
}
