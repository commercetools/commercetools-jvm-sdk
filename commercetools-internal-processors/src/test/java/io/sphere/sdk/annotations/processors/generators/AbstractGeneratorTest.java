package io.sphere.sdk.annotations.processors.generators;

import com.google.common.base.Charsets;
import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import java.io.InputStream;

public abstract class AbstractGeneratorTest {
    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    protected AbstractGenerator generator;

    protected final Messager messager = new FakeMessager();

    protected String expectedContent(final Class<?> clazz) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        final String typeName = generator.generateType(typeElement).name;
        final String fixtureFile = typeName + ".java.expected";
        try (InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile)) {
            return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
        } catch (NullPointerException e) {
            // this just makes it easier to diagnose missing files
            throw new IllegalStateException("Fixture file '" + fixtureFile + "' not found.", e);
        }
    }

    protected String generateAsString(final Class<?> clazz) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());

        final JavaFile javaFile = generator.generate(typeElement);
        StringBuilder stringBuilder = new StringBuilder();
        javaFile.writeTo(stringBuilder);
        return stringBuilder.toString();
    }



}
