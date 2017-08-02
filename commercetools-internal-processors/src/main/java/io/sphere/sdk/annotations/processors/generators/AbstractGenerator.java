package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Abstract base class for implementing javapoet based generators - to generate a single class.
 * The generic attribute T should be used to specify whether the annotation is used on the class level in which {@code T} would be {@link TypeElement} or method level,
 * in that case T would be {@link ExecutableElement}
 */
abstract class AbstractGenerator<T extends Element> extends BaseAbstractGenerator {

    AbstractGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types, messager);
    }

    /**
     * Generates code for the given annotated type element.
     *
     * @param annotatedElement the annotated type element
     * @return the java file to write
     */
    public final JavaFile generate(final T annotatedElement) {
        final TypeSpec typeSpec = generateType(annotatedElement);

        final JavaFile javaFile = JavaFile.builder(getPackageName(annotatedElement), typeSpec)
                .build();

        return javaFile;
    }

    public abstract TypeSpec generateType(final T annotatedElement);

}
