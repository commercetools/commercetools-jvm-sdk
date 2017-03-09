package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.ExampleResource;
import io.sphere.sdk.annotations.processors.generators.examples.ExampleResourceWithAbstractBaseClass;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Unit tests for {@link ResourceValueImplGenerator}.
 */
public class ResourceValueImplGeneratorTest extends AbstractGeneratorTest {

    @Before
    public void setup() {
        generator = new ResourceValueImplGenerator(compilationRule.getElements());
    }

    @Test
    public void generateExampleResource() throws Exception {
        final String content = generateAsString(ExampleResource.class);

        assertThat(content).isEqualTo(expectedContent(ExampleResource.class));
    }

    @Test
    public void generateExampleResourceWithAbstractBaseClass() throws Exception {
        final String content = generateAsString(ExampleResourceWithAbstractBaseClass.class);

        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithAbstractBaseClass.class));
    }
}