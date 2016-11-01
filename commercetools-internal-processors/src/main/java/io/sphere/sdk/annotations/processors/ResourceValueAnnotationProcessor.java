package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ResourceValueAnnotationProcessor extends CommercetoolsAnnotationProcessor {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return ResourceValue.class;
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        writeClass(typeElement, new ResourceValueImplClassModelFactory(typeElement).createClassModel());
    }

}