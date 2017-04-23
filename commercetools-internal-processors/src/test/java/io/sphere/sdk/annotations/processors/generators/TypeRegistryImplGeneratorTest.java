package io.sphere.sdk.annotations.processors.generators;

import com.google.common.base.Charsets;
import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.processors.generators.examples.ExampleTypes;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TypeRegistryImplGenerator}.
 */
public class TypeRegistryImplGeneratorTest {
    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    private TypeRegistryImplGenerator generator = new TypeRegistryImplGenerator();

    @Test
    public void generate() throws Exception {
        final List<TypeElement> typeElements = Stream.of(ExampleTypes.Type1.class, ExampleTypes.Type2.class)
                .map(c -> compilationRule.getElements().getTypeElement(c.getCanonicalName()))
                .collect(Collectors.toList());
        final String generatedSource = generateToString(typeElements);
        assertThat(generatedSource).isEqualTo(expectedContent(ExampleTypes.class));
    }

    private String generateToString(final List<TypeElement> typeElements) throws IOException {
        final JavaFile javaFile = generator.generate(typeElements);
        StringBuilder stringBuilder = new StringBuilder();
        javaFile.writeTo(stringBuilder);
        return stringBuilder.toString();
    }

    private String expectedContent(final Class<?> clazz) throws Exception {
        final String fixtureFile = "TypeRegistryImpl" + ".java.expected";
        try (InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile)) {
            return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
        } catch (NullPointerException e) {
            // this just makes it easier to diagnose missing files
            throw new IllegalStateException("Fixture file '" + fixtureFile + "' not found.", e);
        }
    }

}
