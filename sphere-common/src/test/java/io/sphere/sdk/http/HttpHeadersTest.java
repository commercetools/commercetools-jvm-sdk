package io.sphere.sdk.http;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {

    @Test
    public void testPlusForMissingValue() throws Exception {
        final HttpHeaders headers = HttpHeaders.of().plus("key", "value");
        assertThat(headers.getFlatHeader("key")).isEqualTo(Optional.of("value"));
        assertThat(headers.getHeader("key")).containsExactly("value");
    }

    @Test
    public void testPlusForAlreadyPresentValue() throws Exception {
        final HttpHeaders headers = HttpHeaders.of().plus("key", "value").plus("key", "value2");
        assertThat(headers.getFlatHeader("key")).isEqualTo(Optional.of("value"));
        assertThat(headers.getHeader("key")).containsExactly("value", "value2");
    }
}