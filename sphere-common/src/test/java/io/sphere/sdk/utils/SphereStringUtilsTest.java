package io.sphere.sdk.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SphereStringUtilsTest {
    @Test
    public void slugifyRemovesSpaces() throws Exception {
        assertThat(SphereInternalUtils.slugify("a b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyLowercases() throws Exception {
        assertThat(SphereInternalUtils.slugify("ALpHA")).isEqualTo("alpha");
    }

    @Test
    public void slugifyReplacesSpecialChars() throws Exception {
        assertThat(SphereInternalUtils.slugify("&'()*")).isEqualTo("");
    }

    @Test
    public void slugifyReplacesMultipleMinusSigns() throws Exception {
        assertThat(SphereInternalUtils.slugify("a  b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyForUnicodeLetters() throws Exception {
        assertThat(SphereInternalUtils.slugify("ÀÁÂÃÄ")).isEqualTo("aaaaa");
    }

    @Test
    public void slugifyUniqueAppendsANumeric() throws Exception {
        assertThat(SphereInternalUtils.slugifyUnique("alpha")).matches("alpha-\\d{6,14}");
    }
}