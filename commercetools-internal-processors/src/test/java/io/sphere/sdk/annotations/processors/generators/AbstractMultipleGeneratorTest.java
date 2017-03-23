package io.sphere.sdk.annotations.processors.generators;

import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;

import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultipleGeneratorTest {

    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    protected AbstractMultipleGenerator generator;

    protected List<String> expectedContent(final Class<?> clazz) throws Exception {
        List<String> expectedContentList = new ArrayList<>();
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        final List<TypeSpec> typeSpecs = generator.generateTypes(typeElement);
        typeSpecs.forEach(typeSpec -> {
            final String typeName = typeSpec.name;
            final String fixtureFile = typeName + ".java.expected";
            InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile);
            try {
                expectedContentList.add(IOUtils.toString(resourceAsStream, Charsets.UTF_8));
            } catch (IOException e) {
                throw new IllegalStateException("Fixture file '" + fixtureFile + "' not found.", e);
            }
        });
        return expectedContentList;
    }

    protected List<String> generateAsStrings(final Class<?> clazz) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());

        final List<String> filesAsString = new ArrayList<>();
        final List<JavaFile> javaFiles = generator.generateMultiple(typeElement);
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
