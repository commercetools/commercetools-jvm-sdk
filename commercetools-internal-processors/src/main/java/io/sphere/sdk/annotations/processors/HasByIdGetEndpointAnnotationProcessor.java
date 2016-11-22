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

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasByIdGetEndpoint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasByIdGetEndpointAnnotationProcessor extends HasByFieldGetEndpointAnnotationProcessor<HasByIdGetEndpoint> {

    public HasByIdGetEndpointAnnotationProcessor() {
        super(HasByIdGetEndpoint.class, "ByIdGet");
    }

    @Override
    protected void additionalValues(final TypeElement typeElement, final HashMap<String, Object> map) {
        final HasByIdGetEndpoint annotation = typeElement.getAnnotation(HasByIdGetEndpoint.class);
        final List<String> examples = Arrays.asList(annotation.includeExamples());
        map.put("includeExamples", examples);
        map.put("isForId", true);
        map.put("javadocSummary", annotation.javadocSummary());
    }
}