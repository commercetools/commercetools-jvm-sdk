package io.sphere.sdk.annotations.processors.generators;

import com.google.common.base.Charsets;
import com.google.testing.compile.CompilationRule;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.processors.generators.examples.ExampleResourceWithCustomUpdateAction;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CustomUpdateActionGeneratorTest  extends AbstractGeneratorTest{

    @Rule
    public CompilationRule compilationRule = new CompilationRule();

    protected AbstractGenerator generator;

    @Before
    public void setup() {
        generator = new UpdateActionGenerator(compilationRule.getElements(), compilationRule.getTypes(),messager);
    }

    @Test
    public void generateExampleUpdaupdateAction() throws Exception {
        final String content = generateAsString(ExampleResourceWithCustomUpdateAction.class,"getUserName");

        assertThat(content).isEqualTo(expectedContent(ExampleResourceWithCustomUpdateAction.class,"getUserName"));
    }




    protected String generateAsString(final Class<?> clazz,String methodName) throws Exception {
        final TypeElement typeElement = compilationRule.getElements().getTypeElement(clazz.getCanonicalName());
        Optional<ExecutableElement> elementOptional = ElementFilter.methodsIn(typeElement.getEnclosedElements()).stream().
                filter(elm ->elm.getSimpleName().toString().equals(methodName)).findAny();
        assertThat(elementOptional.isPresent()).as("No method "+methodName+ " was found in class "+clazz.getCanonicalName()).isTrue();

        final JavaFile javaFile = generator.generate(elementOptional.get());
        StringBuilder stringBuilder = new StringBuilder();
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
