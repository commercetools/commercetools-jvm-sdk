package io.sphere.sdk.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SphereExceptionTest {
    @Test
    public void getVersionForJavadoc() throws Exception {
        check("1.0.0-M1-SNAPSHOT", "1.0.0-M1-SNAPSHOT");
        check("category-parent-predicate", "category-parent-predicate");
        check("1.0.0-M4", "v1.0.0-M4");
        check("1.2.4", "v1.2.4");
    }

    private void check(final String version, final String expected) {
        final String versionForJavadoc = SphereException.getVersionForJavadoc(version);
        assertThat(versionForJavadoc).isEqualTo(expected);
    }
}