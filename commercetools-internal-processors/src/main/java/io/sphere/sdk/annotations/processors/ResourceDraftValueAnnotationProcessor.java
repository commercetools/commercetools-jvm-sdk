package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.generators.DraftBuilderGenerator;
import io.sphere.sdk.annotations.processors.generators.ResourceDraftValueGenerator;
import io.sphere.sdk.annotations.processors.validators.ResourceDraftValueValidator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceDraftValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class ResourceDraftValueAnnotationProcessor extends ClassLevelAnnotationProcessor<ResourceDraftValue> {

    public ResourceDraftValueAnnotationProcessor() {
        super(ResourceDraftValue.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final ResourceDraftValueValidator validator = new ResourceDraftValueValidator(processingEnv);
        if (validator.isValid(typeElement)) {
            final JavaFile javaFile = new ResourceDraftValueGenerator(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),processingEnv.getMessager()).generate(typeElement);
            writeClass(javaFile);

            final JavaFile javaFileBuilder = new DraftBuilderGenerator(processingEnv.getElementUtils(),processingEnv.getTypeUtils(),processingEnv.getMessager()).generate(typeElement);
            writeClass(javaFileBuilder);
        }
    }
}