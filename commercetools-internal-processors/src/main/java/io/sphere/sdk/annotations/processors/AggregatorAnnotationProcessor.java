package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base class for annotation processor that generate a single java file from multiple type elements
 * annotated with the same annotation.
 */
abstract class AggregatorAnnotationProcessor<A extends Annotation> extends AbstractProcessor {
    protected final Class<A> annotationClass;

    protected AggregatorAnnotationProcessor(final Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final List<TypeElement> typeElements = roundEnv.getElementsAnnotatedWith(getAnnotationClass()).stream()
                .map(TypeElement.class::cast)
                .collect(Collectors.toList());

        if (!typeElements.isEmpty()) {
            generate(typeElements);
        }

        return true;
    }

    protected final Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    protected abstract void generate(List<TypeElement> typeElements);

    protected final void writeClass(final JavaFile javaFile) {
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }
}
