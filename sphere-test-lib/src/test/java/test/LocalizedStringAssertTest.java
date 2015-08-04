package test;

import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.test.LocalizedStringAssert.assertThat;

public class LocalizedStringAssertTest {
    final LocalizedString foo = LocalizedString.of(Locale.ENGLISH, "foo");
    final LocalizedString leFoo = LocalizedString.of(Locale.FRENCH, "le foo");

    @Test
    public void contains() throws Exception {
        assertThat(foo).contains(Locale.ENGLISH, "foo");
    }

    @Test
    public void doesNotContain() throws Exception {
        assertThat(leFoo).doesNotContain(Locale.ENGLISH, "foo");
    }
}
