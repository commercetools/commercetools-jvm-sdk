package io.sphere.sdk.client;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SphereApiConfigTest {
    @Test
    public void validateProjectKey() throws Exception {
        assertThatThrownBy(() -> SphereApiConfig.of(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> SphereApiConfig.of("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void validateApiUrl() throws Exception {
        assertThatThrownBy(() -> SphereApiConfig.of("foo", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
        assertThatThrownBy(() -> SphereApiConfig.of("foo", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
    }
}