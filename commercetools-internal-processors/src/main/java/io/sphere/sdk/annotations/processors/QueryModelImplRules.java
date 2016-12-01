package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.util.LinkedList;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static java.util.Arrays.asList;

final class QueryModelImplRules extends GenerationRules {
    private final LinkedList<QueryModelMethodRule> queryModelSelectionRules = new LinkedList<>();

    QueryModelImplRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        interfaceRules.add(new ExtendCustomResourceQueryModelImplRule());
        beanMethodRules.add(new GenerateMethodRule());
    }

    private abstract class QueryModelMethodRule {
        public abstract boolean apply(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType);
    }

    @Override
    void execute() {
        addBaseClassAndConstructor();
        builder.addImport(typeElement.getQualifiedName().toString());
        typeElement.getInterfaces().forEach(i -> interfaceRules.stream()
                .filter(r -> r.accept((ReferenceType)i))
                .findFirst());
        createBeanGetterStream(typeElement).forEach(beanGetter -> beanMethodRules.stream()
                .filter(r -> r.accept((ExecutableElement)beanGetter))
                .findFirst());
    }

    private void addBaseClassAndConstructor() {
        builder.setBaseClassName("ResourceQueryModelImpl<" + getContextType() + ">");
        final String constructorBody = "super(parent, pathSegment);\n";
        final MethodModel constructor = new MethodModel();
        constructor.setName(builder.getName());
        constructor.setParameters(asList(
                MethodParameterModel.ofTypeAndName("QueryModel<" + getContextType() + ">", "parent"),
                MethodParameterModel.ofTypeAndName("String", "pathSegment")
        ));
        constructor.setBody(constructorBody);
        builder.addConstructor(constructor);
    }

    public class GenerateMethodRule extends BeanMethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
//            final String fieldName = fieldNameFromGetter(beanGetter);
//            final MethodModel methodModel = new MethodModel();
//            methodModel.setName(fieldName);
//
//            final String contextType = typeElement.getSimpleName().toString();
//
//            queryModelSelectionRules.stream()
//                    .filter(rule -> rule.apply(beanGetter, methodModel, contextType))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("no query model type for " + typeElement + " " + beanGetter));
//            builder.addMethod(methodModel);
//            return true;
            return false;
        }
    }

    private class ExtendCustomResourceQueryModelImplRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType referenceType) {
            if (referenceType.toString().startsWith("io.sphere.sdk.queries.ResourceQueryModel<")) {
                builder.addImport("io.sphere.sdk.types.queries.CustomResourceQueryModelImpl");
                final String contextType = getContextType();
                builder.setBaseClassName("CustomResourceQueryModelImpl<" + contextType + ">");
                beanMethodRules.addFirst(new IgnoreCustomFields());
            }
            return false;
        }
    }

    private class IgnoreCustomFields extends BeanMethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            return beanGetter.getSimpleName().toString().equals("getCustom");
        }
    }

    private String getContextType() {
        return builder.getName().replace("QueryModelImpl", "");
    }
}
