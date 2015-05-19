package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalizedStringsQuerySortingModelTest {
    private final LocalizedStringsQuerySortingModel<String> model = new LocalizedStringsQuerySortingModel<>(Optional.<QueryModel<String>>empty(), Optional.of("thepath"));

    @Test
    public void lang() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).is("bar").toSphereQuery()).isEqualTo("thepath(en=\"bar\")");
    }

    @Test
    public void isIn() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).isIn("foo", "bar").toSphereQuery()).isEqualTo("thepath(en in (\"foo\", \"bar\"))");
    }

    @Test
    public void isNot() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).isNot("bar").toSphereQuery()).isEqualTo("thepath(en <> \"bar\")");
    }
}
