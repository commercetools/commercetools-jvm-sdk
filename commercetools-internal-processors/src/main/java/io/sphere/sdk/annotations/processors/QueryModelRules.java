package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Resource;
import io.sphere.sdk.queries.ResourceQueryModel;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.getType;

final class QueryModelRules {
    private final TypeElement typeElement;
    private final ClassModelBuilder builder;
    private final LinkedList<InterfaceRule> interfaceRules = new LinkedList<>();
    private final LinkedList<BeanMethodRule> beanMethodRules = new LinkedList<>();

    QueryModelRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        this.typeElement = typeElement;
        this.builder = builder;
        interfaceRules.add(new ResourceRule());
        beanMethodRules.add(new GenerateMethodRule());
    }

    void execute() {
        builder.addImport("TODO");
        builder.addImport(typeElement.getQualifiedName().toString());
        typeElement.getInterfaces().forEach(i -> interfaceRules.stream()
                .filter(r -> r.accept((ReferenceType)i))
                .findFirst());
        createBeanGetterStream(typeElement).forEach(beanGetter -> beanMethodRules.stream()
                .filter(r -> r.accept(beanGetter))
                .findFirst());
    }

    private abstract class InterfaceRule {
        public abstract boolean accept(final ReferenceType i);
    }

    private class ResourceRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType typeMirror) {
            if (typeMirror.toString().startsWith(Resource.class.getCanonicalName() + "<")) {
                builder.addInterface(ResourceQueryModel.class.getSimpleName() + "<" + typeElement.getSimpleName() + ">");
                builder.addImport(ResourceQueryModel.class.getCanonicalName());
                beanMethodRules.addFirst(new IgnoreStandardResourceFields());
            }
            return true;
        }
    }

    private abstract class BeanMethodRule {
        public abstract boolean accept(final Element beanGetter);
    }

    public class GenerateMethodRule extends BeanMethodRule {
        @Override
        public boolean accept(final Element beanGetter) {
            final String fieldName = fieldNameFromGetter(beanGetter);
            final String type = getType(beanGetter);
            final MethodModel methodModel = new MethodModel();
            methodModel.setName(fieldName);
            if ("java.lang.String".equals(type)) {
                methodModel.setReturnType("String");
            } else {
                methodModel.setReturnType("void " + type);
                methodModel.setName(fieldName + "TODO");
            }
            builder.addMethod(methodModel);
            return true;
        }
    }

    private class IgnoreStandardResourceFields extends BeanMethodRule {

        private final List<String> methodNames;

        public IgnoreStandardResourceFields() {
            methodNames = Arrays.stream(Resource.class.getDeclaredMethods())
                    .map(m -> m.getName())
                    .collect(Collectors.toList());
        }

        @Override
        public boolean accept(final Element beanGetter) {
            return methodNames.contains(beanGetter.getSimpleName().toString());
        }
    }
}
