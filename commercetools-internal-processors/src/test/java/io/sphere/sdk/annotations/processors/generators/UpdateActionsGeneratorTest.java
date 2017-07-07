package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.MyResource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UpdateActionsGeneratorTest extends AbstractMultipleGeneratorTest {

    @Before
    public void setup() {
        generator = new UpdateActionsGenerator(compilationRule.getElements(), compilationRule.getTypes(),messager);
    }

    @Test
    public void createMultipleUpdateActions() throws Exception {
        final List<String> contentList = generateAsStrings(MyResource.class);
        final List<String> expectedContent = expectedContent(MyResource.class);

        assertThat(contentList).hasSameSizeAs(expectedContent);
        for (int index = 0; index < contentList.size(); index++) {
            assertThat(contentList.get(index)).isEqualTo(expectedContent.get(index));
        }
    }

}
