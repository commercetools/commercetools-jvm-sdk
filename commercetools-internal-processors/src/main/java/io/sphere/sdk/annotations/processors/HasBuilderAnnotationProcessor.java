package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.processors.generators.HasBuilderGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasBuilder"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasBuilderAnnotationProcessor extends ClassLevelAnnotationProcessor<HasBuilder> {

    public HasBuilderAnnotationProcessor() {
        super(HasBuilder.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final JavaFile javaFile = new HasBuilderGenerator(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),processingEnv.getMessager()).generate(typeElement);
        writeClass(javaFile);
    }
}