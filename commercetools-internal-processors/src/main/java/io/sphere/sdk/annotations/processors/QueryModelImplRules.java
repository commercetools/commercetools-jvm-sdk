package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasQueryModelImplementation;
import io.sphere.sdk.annotations.QueryModelFieldName;
import io.sphere.sdk.annotations.QueryModelHint;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.instanceMethodStream;
import static java.util.Arrays.asList;

final class QueryModelImplRules extends GenerationRules {
    private final LinkedList<QueryModelMethodRule> queryModelSelectionRules = new LinkedList<>();

    QueryModelImplRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        builder.addImport(builder.build().getPackageName().replace("queries", "") + getContextType());
        interfaceRules.add(new AnnotationBaseClassRule());
        interfaceRules.add(new ExtendCustomResourceQueryModelImplRule());
        beanMethodRules.add(new IgnoreQueryModelFields());
        beanMethodRules.add(new IgnoreResourceFields());
        beanMethodRules.add(new GenerateMethodRule());
        queryModelSelectionRules.add(new AnnotationHintRule());
        queryModelSelectionRules.add(new DefaultSelectionRule());
        queryModelSelectionRules.add(new ReviewRatingStatisticsQueryModelRule());
    }

    private abstract class QueryModelMethodRule {
        public abstract boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType);
    }

    @Override
    void execute() {
        addBaseClassAndConstructor();
        builder.addImport(typeElement.getQualifiedName().toString());
        builder.addImport(packageOfModels(typeElement));
        typeElement.getInterfaces().forEach(i -> interfaceRules.stream()
                .filter(r -> r.accept((ReferenceType)i))
                .findFirst());
        instanceMethodStream(typeElement)
                .forEach(beanGetter -> beanMethodRules.stream()
                .filter(r -> r.accept(beanGetter))
                .findFirst());
    }

    static String packageOfModels(final TypeElement typeElement) {
        final String fullName = typeElement.getQualifiedName().toString();
        final int i = fullName.indexOf(".queries.");
        return i == -1
                ? fullName.substring(0, fullName.lastIndexOf(".")) + ".*;"
                : fullName.substring(0, i) + ".*;";
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
            final String fieldName = method.getSimpleName().toString();//do not use obtainFieldName() here since it is implementing a base class method
            final MethodModel methodModel = new MethodModel();
            methodModel.setName(fieldName);
            methodModel.setReturnType(method.getReturnType().toString());
            methodModel.setModifiers(asList("public", "final"));
            queryModelSelectionRules.stream()
                    .filter(rule -> rule.apply(method, methodModel, getContextType()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cannot create method body for " + builder.getName() + "." + method + " with return type " + method.getReturnType() +" to create implementation class for " + typeElement + ". You have to fix it in " + QueryModelImplRules.class + " or annotate the resource class method of '" + getContextType() + "' with " + QueryModelHint.class.getCanonicalName() + "."));
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
                beanMethodRules.addFirst(new IgnoreFields("custom"));
            }
            return false;
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

    private class DefaultSelectionRule extends QueryModelMethodRule {
        @Override
        public boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType) {
            final String returnType = method.getReturnType().toString();
            final boolean standardQueryModel = returnType.matches("io.sphere.sdk.queries.(\\w+Query\\w*Model|TimestampSortingModel)[<].*");
            if (standardQueryModel) {
                final String intermediate = returnType.replace("io.sphere.sdk.queries.", "");
                final String type = intermediate.substring(0, intermediate.indexOf("<"));
                final String fieldName = obtainFieldName(method);
                methodModel.setBody("return " + StringUtils.uncapitalize(type) + "(\"" + fieldName + "\");");
                return true;
            }
            return false;
        }
    }

    private class ReviewRatingStatisticsQueryModelRule extends QueryModelMethodRule {
        @Override
        public boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType) {
            if (method.getReturnType().toString().contains("ReviewRatingStatisticsQueryModel")) {
                final String fieldName =  method.getSimpleName().toString();
                methodModel.setBody("return ReviewRatingStatisticsQueryModel.of(this, \"" + fieldName + "\");");
                builder.addImport("io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel");
                return true;
            }
            return false;
        }
    }

    private String obtainFieldName(final ExecutableElement method) {
        return Optional.ofNullable(method.getAnnotation(QueryModelFieldName.class))
                .map(QueryModelFieldName::value)
                .orElseGet(() -> method.getSimpleName().toString());
    }

    private class AnnotationHintRule extends QueryModelMethodRule {
        @Override
        public boolean apply(final ExecutableElement method, final MethodModel methodModel, final String contextType) {
            final QueryModelHint a = method.getAnnotation(QueryModelHint.class);
            if (a != null) {
                final String fieldName = obtainFieldName(method);
                final String body = "final String fieldName = \"" + fieldName + "\";\n" + a.impl();
                methodModel.setBody(body);
                return true;
            }
            return false;
        }
    }

    private class AnnotationBaseClassRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType i) {
            return Optional.ofNullable(typeElement.getAnnotation(HasQueryModelImplementation.class))
                    .map(HasQueryModelImplementation::implBaseClass)
                    .filter(StringUtils::isNotEmpty)
                    .map(baseClass -> {
                        builder.setBaseClassName(baseClass);
                        beanMethodRules.addFirst(new IgnoreFields("customerId", "customerEmail", "totalPrice", "taxedPrice", "country", "customerGroup", "lineItems", "customLineItems", "shippingAddress", "billingAddress", "shippingInfo", "discountCodes", "paymentInfo", "anonymousId", "locale", "custom"));
                        return true;
                    })
                    .orElse(false);
        }
    }

    private class IgnoreFields extends BeanMethodRule {
        private final Set<String> fields;

        public IgnoreFields(final String ... fields) {
            this.fields = new HashSet<>(asList(fields));
        }

        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            return fields.contains(beanGetter.getSimpleName().toString());
        }
    }

    private class IgnoreQueryModelFields extends IgnoreFields {
        public IgnoreQueryModelFields() {
            super("getParent", "getPathSegment");
        }
    }
}
