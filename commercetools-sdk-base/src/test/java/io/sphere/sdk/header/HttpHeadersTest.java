package io.sphere.sdk.header;

import io.sphere.sdk.http.HttpHeaders;
import org.junit.Test;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {

    @Test
    public void testPlusForMissingValue() throws Exception {
        final HttpHeaders headers = HttpHeaders.of().plus("key", "value");
        assertThat(headers.findFlatHeader("key")).isEqualTo(Optional.of("value"));
        assertThat(headers.getHeader("key")).isEqualTo(asList("value"));
    }

    @Test
    public void testPlusForAlreadyPresentValue() throws Exception {
        final HttpHeaders headers = HttpHeaders.of().plus("key", "value").plus("key", "value2");
        assertThat(headers.findFlatHeader("key")).isEqualTo(Optional.of("value"));
        assertThat(headers.getHeader("key")).isEqualTo(asList("value", "value2"));
    }
}