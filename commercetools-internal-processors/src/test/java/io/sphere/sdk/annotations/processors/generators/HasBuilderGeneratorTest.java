package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.ExampleResource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Unit tests for {@link HasBuilderGenerator}.
 */
public class HasBuilderGeneratorTest extends AbstractGeneratorTest {
    @Before
    public void setup() {
        generator = new HasBuilderGenerator(compilationRule.getElements(), compilationRule.getTypes(),messager);
    }

    @Test
    public void generateExampleResourceBuilder() throws Exception {
        final String content = generateAsString(ExampleResource.class);

        assertThat(content).isEqualTo(expectedContent(ExampleResource.class));
    }
}
