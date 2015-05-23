package io.sphere.sdk.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class StringUtilsTest {
    @Test
    public void slugifyRemovesSpaces() throws Exception {
        assertThat(StringUtils.slugify("a b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyLowercases() throws Exception {
        assertThat(StringUtils.slugify("ALpHA")).isEqualTo("alpha");
    }

    @Test
    public void slugifyReplacesSpecialChars() throws Exception {
        assertThat(StringUtils.slugify("&'()*")).isEqualTo("");
    }

    @Test
    public void slugifyReplacesMultipleMinusSigns() throws Exception {
        assertThat(StringUtils.slugify("a  b")).isEqualTo("a-b");
    }

    @Test
    public void slugifyForUnicodeLetters() throws Exception {
        assertThat(StringUtils.slugify("ÀÁÂÃÄ")).isEqualTo("aaaaa");
    }

    @Test
    public void slugifyUniqueAppendsANumeric() throws Exception {
        assertThat(StringUtils.slugifyUnique("alpha")).matches("alpha-\\d{6,14}");
    }
}