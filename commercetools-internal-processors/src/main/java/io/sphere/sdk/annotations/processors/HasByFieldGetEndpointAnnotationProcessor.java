package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceInfo;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasByKeyGetEndpoint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public abstract class HasByFieldGetEndpointAnnotationProcessor<A extends Annotation> extends ClassLevelAnnotationProcessor<A> {

    private final String endpointSuffix;

    protected HasByFieldGetEndpointAnnotationProcessor(final Class<A> clazz, final String endpointSuffix) {
        super(clazz);
        this.endpointSuffix = endpointSuffix;
    }

    @Override
    protected final void generate(final TypeElement typeElement) {
        final String fullyQualifiedNameInterface = ClassConfigurer.packageName(typeElement) + ".queries." + typeElement.getSimpleName() + endpointSuffix;
        final Map<String, Object> values = values(typeElement);
        writeClass(typeElement, fullyQualifiedNameInterface, "queries/byFieldGetInterface", values);
        writeClass(typeElement, fullyQualifiedNameInterface + "Impl", "queries/byFieldGetImplementation", values);
    }

    protected final Map<String, Object> values(final TypeElement typeElement) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("package", ClassConfigurer.packageName(typeElement));
        map.put("resourceName", getResourceName(typeElement));
        map.put("endpointSuffix", endpointSuffix);
        final String fieldName = uncapitalize(removeStart(removeEnd(endpointSuffix, "Get"), "By"));
        map.put("fieldName", fieldName);
        final String identifier = "key".equals(fieldName) ? ("\"key=\" + urlEncode(key)") : fieldName;
        map.put("identifier", identifier);
        final ResourceInfo resourceInfo = typeElement.getAnnotation(ResourceInfo.class);
        map.put("resourcePluralName", resourceInfo.pluralName());
        map.put("pathElement", resourceInfo.pathElement());
        map.put("commonImports", Arrays.asList(resourceInfo.commonImports()));

        additionalValues(typeElement, map);
        return map;
    }

    protected abstract void additionalValues(final TypeElement typeElement, final HashMap<String, Object> map);
}
