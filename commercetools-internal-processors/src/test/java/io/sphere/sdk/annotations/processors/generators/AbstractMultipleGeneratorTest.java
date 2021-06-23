package io.sphere.sdk.annotations.processors.generators;

import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultipleGeneratorTest {

    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    protected final Messager messager = new FakeMessager();

    protected AbstractMultipleFileGenerator generator;

    protected List<String> expectedContent(final Class<?> clazz) throws Exception {
        List<String> expectedContentList = new ArrayList<>();
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        final List<String> classNames = generator.expectedClassNames(typeElement);
        classNames.forEach(className -> {
            final String fixtureFile = className + ".java.expected";
            InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile);
            try {
                expectedContentList.add(IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new IllegalStateException("Fixture file '" + fixtureFile + "' not found.", e);
            }
        });
        return expectedContentList;
    }

    protected List<String> generateAsStrings(final Class<?> clazz) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());

        final List<String> filesAsString = new ArrayList<>();
        final List<JavaFile> javaFiles = generator.generate(typeElement);
        javaFiles.forEach(javaFile -> {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                javaFile.writeTo(stringBuilder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            filesAsString.add(stringBuilder.toString());
        });
        return filesAsString;
    }
}
