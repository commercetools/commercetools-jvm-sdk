package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Resource;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.methodStream;
import static java.util.Arrays.asList;

final class QueryModelImplRules extends GenerationRules {
    private final LinkedList<QueryModelMethodRule> queryModelSelectionRules = new LinkedList<>();

    QueryModelImplRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        builder.addImport(builder.build().getPackageName().replace("queries", "") + getContextType());
        interfaceRules.add(new ExtendCustomResourceQueryModelImplRule());
        beanMethodRules.add(new IgnoreResourceFields());
        beanMethodRules.add(new GenerateMethodRule());
        queryModelSelectionRules.add(new XSelectionRule());
        queryModelSelectionRules.add(new ReviewRatingStatisticsQueryModelRule());
    }

    private abstract class QueryModelMethodRule {
        public abstract boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType);
    }

    @Override
    void execute() {
        addBaseClassAndConstructor();
        builder.addImport(typeElement.getQualifiedName().toString());
        typeElement.getInterfaces().forEach(i -> interfaceRules.stream()
                .filter(r -> r.accept((ReferenceType)i))
                .findFirst());
        methodStream(typeElement)
                .forEach(beanGetter -> beanMethodRules.stream()
                .filter(r -> r.accept(beanGetter))
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
        public boolean accept(final ExecutableElement method) {
            final String fieldName = method.getSimpleName().toString();
            final MethodModel methodModel = new MethodModel();
            methodModel.setName(fieldName);
            methodModel.setReturnType(method.getReturnType().toString());
            queryModelSelectionRules.stream()
                    .filter(rule -> rule.apply(method, methodModel, getContextType()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("no query model impl type for " + typeElement + " " + method));
            builder.addMethod(methodModel);
            return true;
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
            return beanGetter.getSimpleName().toString().equals("custom");
        }
    }

    private class IgnoreResourceFields extends BeanMethodRule {
        private final List<String> methodNames;

        private IgnoreResourceFields() {
            methodNames = Arrays.stream(ResourceQueryModelImpl.class.getDeclaredMethods())
                    .map(Method::getName)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean accept(final ExecutableElement method) {
            return methodNames.contains(method.getSimpleName().toString());
        }
    }

    private String getContextType() {
        final String name = builder.getName();
        return name.substring(0, name.indexOf("QueryModel"));
    }

    private class XSelectionRule extends QueryModelMethodRule {
        @Override
        public boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType) {
            final String returnType = method.getReturnType().toString();
            final boolean standardQueryModel = returnType.matches("io.sphere.sdk.queries.\\w+Query\\w*Model[<].*");
            if (standardQueryModel) {
                final String intermediate = returnType.replace("io.sphere.sdk.queries.", "");
                final String type = intermediate.substring(0, intermediate.indexOf("<"));
                methodModel.setBody("return " + StringUtils.uncapitalize(type) + "(\"" + method.getSimpleName().toString() + "\");");
                return true;
            }
            return false;
        }
    }

    private class ReviewRatingStatisticsQueryModelRule extends QueryModelMethodRule {
        @Override
        public boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType) {
            if (method.getReturnType().toString().contains("ReviewRatingStatisticsQueryModel")) {
                final String fieldName = method.getSimpleName().toString();
                methodModel.setBody("return ReviewRatingStatisticsQueryModel.of(this, \"" + fieldName + "\");");
                builder.addImport("io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel");
                return true;
            }
            return false;
        }
    }
}
