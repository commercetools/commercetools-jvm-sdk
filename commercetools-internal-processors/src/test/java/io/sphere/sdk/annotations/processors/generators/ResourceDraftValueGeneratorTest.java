package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.ExampleDraft;
import org.junit.Before;
import org.junit.Test;

public class ResourceDraftValueGeneratorTest extends AbstractGeneratorTest {

    @Before
    public void setup() {
        generator = new ResourceDraftValueGenerator(compilationRule.getElements());
    }

    @Test
    public void generateExampleResource() throws Exception {
        final String content = generateAsString(ExampleDraft.class);

//        assertThat(content).isEqualTo(expectedContent(ExampleDraft.class));
    }

}