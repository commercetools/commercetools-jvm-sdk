package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.MyResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UpdateActionsGeneratorTest extends AbstractMultipleGeneratorTest {

    @Before
    public void setup() {
        generator = new UpdateActionsGenerator(compilationRule.getElements());
    }

    @Test
    public void createMultipleUpdateActions() throws Exception {
        final List<String> contentList = generateAsStrings(MyResource.class);
        final List<String> expectedContent = expectedContent(MyResource.class);
        int index = 0;
        for (String content : contentList) {
            assertThat(content).isEqualTo(expectedContent.get(index++));
        }
    }

}
