package io.sphere.sdk.products.attributes;

import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.products.attributes.AttributeConstraint.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;

public class AttributeConstraintTest {

    @Test
    public void demoExportUpperCase() {
        final List<AttributeConstraint> javaValues =
                asList(NONE, UNIQUE, COMBINATION_UNIQUE, SAME_FOR_ALL);
        final List<String> enumValueNames = javaValues.stream()
                .map(value -> value.name())
                .collect(toList());
        assertThat(enumValueNames)
                .as("use name() for upper case values")
                .containsExactly("NONE", "UNIQUE", "COMBINATION_UNIQUE", "SAME_FOR_ALL");
    }

    @Test
    public void demoExportSphereValue() {
        final List<AttributeConstraint> javaValues =
                asList(NONE, UNIQUE, COMBINATION_UNIQUE, SAME_FOR_ALL);
        final List<String> enumValueNames = javaValues.stream()
                .map(value -> value.toSphereName())
                .collect(toList());
        assertThat(enumValueNames)
                .as("use toSphereName() for constants used in the commercetools platform")
                .containsExactly("None", "Unique", "CombinationUnique", "SameForAll");
    }

    @Test
    public void demoImportUpperCase() {
        final List<String> javaValues =
                asList("NONE", "UNIQUE", "COMBINATION_UNIQUE", "SAME_FOR_ALL");
        final List<AttributeConstraint> enumValueNames = javaValues.stream()
                .map(value -> AttributeConstraint.valueOf(value))
                .collect(toList());
        assertThat(enumValueNames)
                .as("use valueOf(value) for importing the upper case values")
                .containsExactly(NONE, UNIQUE, COMBINATION_UNIQUE, SAME_FOR_ALL);
    }

    @Test
    public void demoImportSphereValue() {
        final List<String> javaValues =
                asList("None", "Unique", "CombinationUnique", "SameForAll");
        final List<AttributeConstraint> enumValueNames = javaValues.stream()
                .map(value -> AttributeConstraint.ofSphereValue(value))
                .collect(toList());
        assertThat(enumValueNames)
                .as("use ofSphereValue(value) for importing from commercetools platform constants")
                .containsExactly(NONE, UNIQUE, COMBINATION_UNIQUE, SAME_FOR_ALL);
    }
}