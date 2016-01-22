package io.sphere.sdk.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SphereStringUtilsTest {
    @Test
    public void slugifyRemovesSpaces() throws Exception {
        assertThat(SphereStringUtils.slugify("a b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyLowercases() throws Exception {
        assertThat(SphereStringUtils.slugify("ALpHA")).isEqualTo("alpha");
    }

    @Test
    public void slugifyReplacesSpecialChars() throws Exception {
        assertThat(SphereStringUtils.slugify("&'()*")).isEqualTo("");
    }

    @Test
    public void slugifyReplacesMultipleMinusSigns() throws Exception {
        assertThat(SphereStringUtils.slugify("a  b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyForUnicodeLetters() throws Exception {
        assertThat(SphereStringUtils.slugify("ÀÁÂÃÄ")).isEqualTo("aaaaa");
    }

    @Test
    public void slugifyUniqueAppendsANumeric() throws Exception {
        assertThat(SphereStringUtils.slugifyUnique("alpha")).matches("alpha-\\d{6,14}");
    }
}