package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasByIdGetEndpoint;
import io.sphere.sdk.annotations.HasByKeyGetEndpoint;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasByKeyGetEndpoint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasByKeyGetEndpointAnnotationProcessor extends HasByFieldGetEndpointAnnotationProcessor<HasByKeyGetEndpoint> {

    public HasByKeyGetEndpointAnnotationProcessor() {
        super(HasByKeyGetEndpoint.class, "ByKeyGet");
    }

    @Override
    protected void additionalValues(final TypeElement typeElement, final HashMap<String, Object> map) {
        final HasByKeyGetEndpoint annotation = typeElement.getAnnotation(HasByKeyGetEndpoint.class);
        final List<String> examples = Arrays.asList(annotation.includeExamples());
        map.put("includeExamples", examples);
        map.put("javadocSummary", annotation.javadocSummary());
    }
}