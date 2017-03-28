package io.sphere.sdk.annotations.processors;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.processors.generators.TypeRegistryImplGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * This annotation processor generates a type registry for all interfaces annotated with {@link JsonTypeName}.
 */
@SupportedAnnotationTypes({"com.fasterxml.jackson.annotation.JsonTypeName"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TypeNameAnnotationProcessor extends AggregatorAnnotationProcessor<JsonTypeName> {
    public TypeNameAnnotationProcessor() {
        super(JsonTypeName.class);
    }

    @Override
    protected void generate(final List<TypeElement> typeElements) {
        final JavaFile javaFile = new TypeRegistryImplGenerator().generate(typeElements);
        writeClass(javaFile);
    }
}
