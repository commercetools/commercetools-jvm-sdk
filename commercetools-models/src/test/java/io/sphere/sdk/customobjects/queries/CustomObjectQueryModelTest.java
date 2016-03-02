package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.QueryPredicate;
import org.junit.Test;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class CustomObjectQueryModelTest {
    @Test
    public void objectNodeInValue() {
        assertResult(m -> m.value().ofJsonObject().ofString("s").is("s value"), "value(s=\"s value\")");
        assertResult(m -> m.value().ofJsonObject().ofJsonObject("sub").ofString("sub-s").is("sub s value"), "value(sub(sub-s=\"sub s value\"))");
        assertResult(m -> m.value().ofJsonObject().ofJsonObject("sub").ofString("sub-nullable").isNotPresent(), "value(sub(sub-nullable is not defined))");
        assertResult(m -> m.value().ofJsonObject().ofJsonObject("sub").ofBoolean("sub-boolean").is(true), "value(sub(sub-boolean=true))");
        assertResult(m -> m.value().ofJsonObject().ofJsonObject("sub").ofStringCollection("arrno").containsAll(asList("foo", "bar")), "value(sub(arrno contains all (\"foo\", \"bar\")))");
    }

    @Test
    public void valueInValue() {
        assertResult(m -> m.value().ofJsonValue().ofString().is("hello"), "value=\"hello\"");
    }

    private void assertResult(final Function<CustomObjectQueryModel<CustomObject<Void>>, QueryPredicate<CustomObject<Void>>> f, final String expected) {
        final CustomObjectQueryModel<CustomObject<Void>> m = CustomObjectQueryModel.<CustomObject<Void>>of();
        assertThat(f.apply(m).toSphereQuery()).isEqualTo(expected);
    }
}