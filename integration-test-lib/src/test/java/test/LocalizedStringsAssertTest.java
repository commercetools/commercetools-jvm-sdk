package test;

import io.sphere.sdk.models.LocalizedStrings;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.test.LocalizedStringsAssert.assertThat;

public class LocalizedStringsAssertTest {
    final LocalizedStrings foo = LocalizedStrings.of(Locale.ENGLISH, "foo");
    final LocalizedStrings leFoo = LocalizedStrings.of(Locale.FRENCH, "le foo");

    @Test
    public void contains() throws Exception {
        assertThat(foo).contains(Locale.ENGLISH, "foo");
    }

    @Test
    public void doesNotContain() throws Exception {
        assertThat(leFoo).doesNotContain(Locale.ENGLISH, "foo");
    }
}
