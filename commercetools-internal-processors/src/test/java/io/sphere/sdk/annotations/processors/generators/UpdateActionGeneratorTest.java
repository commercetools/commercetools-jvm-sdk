package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.MyResource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateActionGeneratorTest extends AbstractGeneratorTest {

    @Before
    public void setup() {
        generator = new UpdateActionGenerator(compilationRule.getElements());
    }

    @Test
    public void generateUpdateActionOfString() throws Exception {
        final String content = generateAsString(MyResource.class);

        assertThat(content).isEqualTo(expectedContent(MyResource.class));
    }

}
