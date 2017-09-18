package io.sphere.sdk.annotations.processors.generators;

import com.google.common.base.Charsets;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.processors.generators.examples.ExampleResourceWithUpdateAction;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class UpdateActionGeneratorTest  extends AbstractGeneratorTest {


    @Before
    public void setup() {
        generator = new UpdateActionGenerator(compilationRule.getElements(), compilationRule.getTypes(),messager);
    }

    @Test
    public void generateExampleUpdateActionNameActionForNonNullable() throws Exception {
        final String content = generateAsString(ExampleResourceWithUpdateAction.class,"getLocale");
        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithUpdateAction.class,"getLocale"));
    }

    @Test
    public void generateExampleUpdateActionForNullable() throws Exception {
        final String content = generateAsString(ExampleResourceWithUpdateAction.class,"getOptionalLocale");
        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithUpdateAction.class,"getOptionalLocale"));
    }

    @Test
    public void generateExampleUpdateActionNonNullableWithCustomName() throws Exception {
        final String content = generateAsString(ExampleResourceWithUpdateAction.class,"getUserName");
        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithUpdateAction.class,"getUserName"));
    }

    @Test
    public void generateExampleUpdateActionNullableWithCustomName() throws Exception {
        final String content = generateAsString(ExampleResourceWithUpdateAction.class,"getOptionalUserName");
        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithUpdateAction.class,"getOptionalUserName"));
    }

    @Test
    public void generateExampleUpdateActionNullableWithDeprecatedField() throws Exception {
        final String content = generateAsString(ExampleResourceWithUpdateAction.class,"getDeprecatedField");
        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithUpdateAction.class,"getDeprecatedField"));
    }

    protected String generateAsString(final Class<?> clazz,String methodName) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        Optional<ExecutableElement> elementOptional = ElementFilter.methodsIn(typeElement.getEnclosedElements()).stream().
                filter(elm ->elm.getSimpleName().toString().equals(methodName)).findAny();
        assertThat(elementOptional.isPresent()).as("No method "+methodName+ " was found in class "+clazz.getCanonicalName()).isTrue();

        final JavaFile javaFile = generator.generate(elementOptional.get());    StringBuilder stringBuilder = new StringBuilder();
        javaFile.writeTo(stringBuilder);
        return stringBuilder.toString();
    }


    protected String expectedContent(final Class<?> clazz,String methodName) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        Optional<ExecutableElement> elementOptional = ElementFilter.methodsIn(typeElement.getEnclosedElements()).stream().
                filter(elm ->elm.getSimpleName().toString().equals(methodName)).findAny();
        assertThat(elementOptional.isPresent()).as("No method ",methodName, " was found in class ",clazz.getCanonicalName()).isTrue();
        final String fixtureFile = generator.generateType(elementOptional.get()).name + ".java.expected";
        try (InputStream resourceAsStream = clazz.getResourceAsStream(fixtureFile)) {
            return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
        } catch (NullPointerException e) {
            // this just makes it easier to diagnose missing files
            throw new IllegalStateException("Fixture file '" + fixtureFile + "' not found.", e);
        }
    }
}
