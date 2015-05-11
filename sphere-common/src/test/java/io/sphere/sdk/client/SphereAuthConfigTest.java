package io.sphere.sdk.client;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SphereAuthConfigTest {
    @Test
    public void validateProjectKey() throws Exception {
        assertThatThrownBy(() -> SphereAuthConfig.of(null, "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
        assertThatThrownBy(() -> SphereAuthConfig.of("", "here", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("");
    }

    @Test
    public void validateClientId() throws Exception {
        assertThatThrownBy(() -> SphereAuthConfig.of("here", null, "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
        assertThatThrownBy(() -> SphereAuthConfig.of("here", "", "here", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientId");
    }

    @Test
    public void validateClientSecret() throws Exception {
        assertThatThrownBy(() -> SphereAuthConfig.of("here", "here", null, "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
        assertThatThrownBy(() -> SphereAuthConfig.of("here", "here", "", "here"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("clientSecret");
    }

    @Test
    public void validateAuthUrl() throws Exception {
        assertThatThrownBy(() -> SphereAuthConfig.of("here", "here", "here", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
        assertThatThrownBy(() -> SphereAuthConfig.of("here", "here", "here", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("authUrl");
    }
}