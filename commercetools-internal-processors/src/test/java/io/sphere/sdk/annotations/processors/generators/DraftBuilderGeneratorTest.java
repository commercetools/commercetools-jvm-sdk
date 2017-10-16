package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.annotations.processors.generators.examples.*;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DraftBuilderGeneratorTest extends AbstractGeneratorTest {

    @Before
    public void setup() {
        generator = new DraftBuilderGenerator(compilationRule.getElements(), compilationRule.getTypes(),messager);
    }

    @Test
    public void generateDraftBuilderClass() throws Exception {
        final String content = generateAsString(ExampleDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleDraft.class));
    }

    @Test
    public void generateDraftBuilderClassWithNoAttribute() throws Exception {
        final String content = generateAsString(ExampleDraftWithNoAttribute.class);
        assertThat(content).isEqualTo(expectedContent(ExampleDraftWithNoAttribute.class));
    }

    @Test
    public void generateDraftWithAbstractBaseClass() throws Exception {
        final String content = generateAsString(ExampleWithAbstractBaseClassDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithAbstractBaseClassDraft.class));
    }

    @Test
    public void generateDraftWithSuperInterface() throws Exception {
        final String content = generateAsString(ExampleWithSuperInterfaceDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithSuperInterfaceDraft.class));
    }

    @Test
    public void generateDraftWithLowerCasedBoolean() throws Exception {
        final String content = generateAsString(ExampleWithLowerCaseBooleanDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithLowerCaseBooleanDraft.class));
    }

    @Test
    public void generateDraftWithReference() throws Exception {
        final String content = generateAsString(ExampleWithReferenceDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithReferenceDraft.class));
    }

    @Test
    public void generateBuilderWhichReturnsDraftClass() throws Exception {
        final String content = generateAsString(ExampleWithBuilderReturnsInterfaceDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithBuilderReturnsInterfaceDraft.class));
    }


    @Test
    public void generateBuilderWithCopyFactoryMethod() throws Exception {
        final String content = generateAsString(ExampleDraftWithCopyFactoryMethod.class);

        assertThat(content).isEqualTo(expectedContent(ExampleDraftWithCopyFactoryMethod.class));
    }
}