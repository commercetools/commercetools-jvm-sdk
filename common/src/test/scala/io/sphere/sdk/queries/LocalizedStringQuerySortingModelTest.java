package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class LocalizedStringQuerySortingModelTest {
    private final LocalizedStringQuerySortingModel<String> model = new LocalizedStringQuerySortingModel<>(Optional.<QueryModel<String>>empty(), Optional.of("thepath"));

    @Test
    public void lang() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).is("bar").toSphereQuery()).isEqualTo("thepath(en=\"bar\")");
    }

    @Test
    public void isOneOf() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).isOneOf("foo", "bar").toSphereQuery()).isEqualTo("thepath(en in (\"foo\", \"bar\"))");
    }

    @Test
    public void isNot() throws Exception {
        assertThat(model.lang(Locale.ENGLISH).isNot("bar").toSphereQuery()).isEqualTo("thepath(en <> \"bar\")");
    }
}
