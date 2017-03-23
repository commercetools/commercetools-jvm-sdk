package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.MyResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MultipleUpdateActionsGeneratorTest extends AbstractMultipleGeneratorTest {

    @Before
    public void setup() {
        generator = new MultipleUpdateActionsGenerator(compilationRule.getElements());
    }

    @Test
    public void createMultipleUpdateActions() throws Exception {
        final List<String> contentList = generateAsStrings(MyResource.class);

        assertThat(contentList).containsExactlyElementsOf(expectedContent(MyResource.class));
    }

}
