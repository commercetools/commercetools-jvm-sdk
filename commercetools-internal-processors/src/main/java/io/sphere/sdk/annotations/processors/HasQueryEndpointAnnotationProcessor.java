package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasQueryEndpoint;
import io.sphere.sdk.annotations.ResourceInfo;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasQueryEndpoint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasQueryEndpointAnnotationProcessor extends CommercetoolsAnnotationProcessor {

    @Override
    protected void generate(final TypeElement typeElement) {
        final String resourceName = getResourceName(typeElement);
        final String fullyQualifiedName = typeElement.getQualifiedName() + "Query";
        writeClass(typeElement, fullyQualifiedName, "queries/resourceQueryInterface", queryInterfaceClassValues(typeElement));
        //add ResourceQueryImpl class
        //add ResourceQueryBuilder class
    }

    private String getResourceName(final TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }

    private Map<String, Object> queryInterfaceClassValues(final TypeElement typeElement) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("package", ClassConfigurer.packageName(typeElement));
        map.put("resourceName", getResourceName(typeElement));
        final ResourceInfo resourceInfo = typeElement.getAnnotation(ResourceInfo.class);
        map.put("resourcePluralName", resourceInfo.pluralName());
        final HasQueryEndpoint queryInfo = typeElement.getAnnotation(HasQueryEndpoint.class);
        map.put("extras", asList(queryInfo.additionalContents()));
        return map;
    }

    protected Class<? extends Annotation> getAnnotationClass() {
        return HasQueryEndpoint.class;
    }
}