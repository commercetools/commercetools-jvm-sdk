package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for implementing javapoet based generators - to generate multiple classes.
 */
abstract class AbstractMultipleFileGenerator extends BaseAbstractGenerator {

    public AbstractMultipleFileGenerator(final Elements elements, final Types types) {
        super(elements, types);
    }

    /**
     * Generates the classes for the given annotated type element.
     *
     * @param annotatedTypeElement the annotated type element
     * @return the list of java files to write
     */
    public final List<JavaFile> generate(final TypeElement annotatedTypeElement) {
        List<JavaFile> javaFiles = new ArrayList<>();
        final List<TypeSpec> typeSpecs = generateTypes(annotatedTypeElement);
        typeSpecs.forEach(typeSpec -> {
            final JavaFile javaFile = JavaFile.builder(getPackageName(annotatedTypeElement), typeSpec)
                    .build();
            javaFiles.add(javaFile);
        });
        return javaFiles;
    }

    protected abstract List<TypeSpec> generateTypes(final TypeElement annotatedTypeElement);

    protected abstract List<String> expectedClassNames(final TypeElement annotatedTypeElement);

}
