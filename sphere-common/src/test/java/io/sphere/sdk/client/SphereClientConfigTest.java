package io.sphere.sdk.client;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SphereClientConfigTest {
    @Test
    public void validateProjectKey() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of(null, "here", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
        assertThatThrownBy(() -> SphereClientConfig.of("", "here", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
    }

    @Test
    public void validateClientId() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", null, "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
    }

    @Test
    public void validateClientSecret() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", null, "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
    }

    @Test
    public void validateAuthUrl() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", null, "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
    }

    @Test
    public void validateApiUrl() throws Exception {
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "here", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
        assertThatThrownBy(() -> SphereClientConfig.of("here", "here", "here", "here", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("apiUrl");
    }
}