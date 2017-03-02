package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.generators.DraftBuilderGenerator;
import io.sphere.sdk.annotations.processors.validators.ResourceDraftValueValidator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceDraftValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class ResourceDraftValueAnnotationProcessor extends CommercetoolsAnnotationProcessor<ResourceDraftValue> {

    public ResourceDraftValueAnnotationProcessor() {
        super(ResourceDraftValue.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final ResourceDraftValueValidator validator = new ResourceDraftValueValidator(processingEnv);
        if (validator.validate(typeElement)) {

            writeClass(typeElement, new ResourceDraftDslClassModelFactory(typeElement).createClassModel());

            final JavaFile javaFile = new DraftBuilderGenerator(processingEnv.getElementUtils()).generate(typeElement);
            writeClass(javaFile);
        }
    }

}