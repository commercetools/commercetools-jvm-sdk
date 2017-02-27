package io.sphere.sdk.annotations.generator;

import com.google.common.base.Charsets;
import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.generator.examples.ExampleDraft;
import io.sphere.sdk.annotations.generator.examples.ExampleWithAbstractBaseClassDraft;
import io.sphere.sdk.annotations.generator.examples.ExampleWithSuperInterfaceDraft;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DraftBuilderGeneratorTest {
    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    @Test
    public void generateDraftBuilderClass() throws Exception {
        final String content = generateAsString(ExampleDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleDraft.class));
    }

    @Test
    public void generateDraftBuilderBaseClass() throws Exception {
        final String content = generateAsString(ExampleWithAbstractBaseClassDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithAbstractBaseClassDraft.class));
    }

    @Test
    public void generateDraftWithSuperInterfaceBuilderClass() throws Exception {
        final String content = generateAsString(ExampleWithSuperInterfaceDraft.class);

        assertThat(content).isEqualTo(expectedContent(ExampleWithSuperInterfaceDraft.class));
    }


    private String generateAsString(final Class<?> clazz) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());

        final JavaFile javaFile = new DraftBuilderGenerator(compilationRule.getElements()).generate(typeElement);
        StringBuilder stringBuilder = new StringBuilder();
        javaFile.writeTo(stringBuilder);
        return stringBuilder.toString();
    }

    private String expectedContent(final Class<?> clazz) throws Exception {
        final String fixtureFile = clazz.getSimpleName() + "Builder.java.expected";
        try (InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile)) {
            return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
        }
    }
}