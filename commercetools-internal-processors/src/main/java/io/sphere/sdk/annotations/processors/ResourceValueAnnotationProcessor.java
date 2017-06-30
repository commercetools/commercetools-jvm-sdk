package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.annotations.processors.generators.ResourceValueImplGenerator;
import io.sphere.sdk.annotations.processors.validators.ResourceValueValidator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class ResourceValueAnnotationProcessor extends ClassLevelAnnotationProcessor<ResourceValue> {

    public ResourceValueAnnotationProcessor() {
        super(ResourceValue.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final ResourceValueValidator validator = new ResourceValueValidator(processingEnv);
        if (validator.isValid(typeElement)) {

            final JavaFile javaFile = new ResourceValueImplGenerator(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),processingEnv.getMessager()).generate(typeElement);

            writeClass(javaFile);
        }
    }
}