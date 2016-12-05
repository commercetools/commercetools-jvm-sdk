package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.IgnoreInQueryModel;
import io.sphere.sdk.annotations.QueryModelHint;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.SphereEnumeration;
import io.sphere.sdk.queries.ResourceQueryModel;

import javax.annotation.Nullable;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static java.util.Arrays.asList;

final class QueryModelRules extends GenerationRules {
    private final LinkedList<QueryModelSelectionRule> queryModelSelectionRules = new LinkedList<>();

    QueryModelRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        interfaceRules.add(new ResourceRule());
        interfaceRules.add(new CustomFieldsRule());
        beanMethodRules.add(new IgnoreAnnotatedElements());
        beanMethodRules.add(new GenerateMethodRule());
        queryModelSelectionRules.add(new AnnotationSelectionRule());
        queryModelSelectionRules.add(new LocalizedStringQueryModelSelectionRule());
        queryModelSelectionRules.add(new ReferenceQueryModelSelectionRule());
        queryModelSelectionRules.add(new SetOfSphereEnumerationQueryModelSelectionRule());
        queryModelSelectionRules.add(new SphereEnumerationQueryModelSelectionRule());
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.Boolean", "BooleanQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.String", "StringQuerySortingModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.reviews.ReviewRatingStatistics", "ReviewRatingStatisticsQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.models.Address", "AddressQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.time.ZonedDateTime", "TimestampSortingModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("com.neovisionaries.i18n.CountryCode", "io.sphere.sdk.carts.queries.CartShippingInfoQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.carts.TaxedPrice", "io.sphere.sdk.carts.queries.TaxedPriceOptionalQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("javax.money.MonetaryAmount", "io.sphere.sdk.queries.MoneyQueryModel"));
    }

    @Override
    void execute() {
        builder.addImport("io.sphere.sdk.annotations.HasQueryModelImplementation");
        final AnnotationModel a = new AnnotationModel();
        a.setName("HasQueryModelImplementation");
        builder.addAnnotation(a);
        builder.addImport(typeElement.getQualifiedName().toString());
        final List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
            final Stream<? extends TypeMirror> subInterfaces = interfaces.stream()
                    .map(i -> (DeclaredType) i)
                    .map(i -> (TypeElement) i.asElement())
                    .flatMap(i -> i.getInterfaces().stream());
        Stream.concat(interfaces.stream(), subInterfaces).distinct().forEach(i -> interfaceRules.stream()
                .filter(r -> r.accept((ReferenceType)i))
                .findFirst());
        createBeanGetterStream(typeElement).forEach(beanGetter -> beanMethodRules.stream()
                .filter(r -> r.accept((ExecutableElement)beanGetter))
                .findFirst());
    }

    private class ResourceRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType typeMirror) {
            if (typeMirror.toString().startsWith(Resource.class.getCanonicalName() + "<")) {
                builder.addInterface(ResourceQueryModel.class.getSimpleName() + "<" + typeElement.getSimpleName() + ">");
                builder.addImport(ResourceQueryModel.class.getCanonicalName());
                beanMethodRules.addFirst(new IgnoreStandardResourceFields());
                builder.addMethod(createFactoryMethod());
            }
            return false;
        }
    }

    private MethodModel createFactoryMethod() {
        final MethodModel method = new MethodModel();
        method.setModifiers(Collections.singletonList("static"));
        method.setReturnType(builder.getName());
        method.setName("of");
        method.setBody("return new " + builder.getName() + "Impl(null, null);");
        return method;
    }

    public class GenerateMethodRule extends BeanMethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            final String fieldName = fieldNameFromGetter(beanGetter);
            final MethodModel methodModel = new MethodModel();
            methodModel.setName(fieldName);

            final String contextType = typeElement.getSimpleName().toString();

            queryModelSelectionRules.stream()
                    .filter(rule -> rule.x(beanGetter, methodModel, contextType))
                    .findFirst()
            .orElseThrow(() -> new RuntimeException("no query model type for " + beanGetter.getReturnType()+ " " + typeElement + " " + beanGetter + " in " + queryModelSelectionRules));
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
        public boolean accept(final ExecutableElement beanGetter) {
            return methodNames.contains(beanGetter.getSimpleName().toString());
        }
    }

    private abstract class QueryModelSelectionRule {
        public abstract boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType);
    }

    public class SetOfSphereEnumerationQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final TypeMirror returnType = beanGetter.getReturnType();
            if (returnType instanceof DeclaredType) {
                final DeclaredType x = (DeclaredType) returnType;
                final String uncheckedType = x.asElement().getSimpleName().toString();
                if (asList("Set", "List").contains(uncheckedType) && x.getTypeArguments().size() == 1) {
                    final DeclaredType declaredType = (DeclaredType) x.getTypeArguments().get(0);
                    final TypeElement classInList = (TypeElement) declaredType.asElement();
                    final Optional<TypeElement> typeParameterOptional = classInList.getInterfaces().stream()
                            .map(i -> (DeclaredType) i)
                            .map(i -> (TypeElement) i.asElement())
                            .filter(i -> i.getQualifiedName().toString().equals(SphereEnumeration.class.getCanonicalName())).findFirst();
                    final boolean isSphereEnumeration = typeParameterOptional.map(xs -> true).orElse(false);
                    if (isSphereEnumeration) {
                        final String typeParameterName = typeParameterOptional.get().getQualifiedName().toString();
                        methodModel.setReturnType("SphereEnumerationCollectionQueryModel<" + contextType + ", " + typeParameterName + ">");
                        builder.addImport("io.sphere.sdk.queries.SphereEnumerationCollectionQueryModel");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class SphereEnumerationQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final DeclaredType returnType = (DeclaredType) beanGetter.getReturnType();
            final TypeElement element = (TypeElement) returnType.asElement();
            if (element.getInterfaces().stream().anyMatch(i -> i.toString().contains(SphereEnumeration.class.getSimpleName()))) {
                methodModel.setReturnType("SphereEnumerationQueryModel<" + contextType + ", " + returnType.toString() + ">");
                builder.addImport("io.sphere.sdk.queries.SphereEnumerationCollectionQueryModel");
                return true;
            }
            return false;
        }
    }

    public class SimpleQueryModelSelectionRule extends QueryModelSelectionRule {

        private final String expectedType;
        private final String resultGenericType;

        public SimpleQueryModelSelectionRule(final String expectedType, final String resultGenericType) {
            this.expectedType = expectedType;
            this.resultGenericType = resultGenericType;
        }

        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            return ds(beanGetter, methodModel, contextType, expectedType, resultGenericType);
        }

        private boolean ds(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType, final String expectedType, final String resultGenericType) {
            if (beanGetter.getReturnType().toString().equals(expectedType)) {
                methodModel.setReturnType(resultGenericType + "<" + contextType + ">");
                return true;
            }
            return false;
        }
    }

    private class LocalizedStringQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            if (beanGetter.getReturnType().toString().equals(LocalizedString.class.getCanonicalName())) {
                final String type = beanGetter.getAnnotation(Nullable.class) != null ? "LocalizedStringOptionalQueryModel" : "LocalizedStringQueryModel";
                methodModel.setReturnType(type + "<" + contextType + ">");
                return true;
            }
            return false;
        }
    }

    private class CustomFieldsRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType typeMirror) {
            if (typeMirror.toString().equals("io.sphere.sdk.types.Custom")) {
                final String contextType = typeElement.getSimpleName().toString();
                builder.addInterface("WithCustomQueryModel<" + contextType + ">");
                builder.addImport("io.sphere.sdk.types.queries.WithCustomQueryModel");
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

    private class IgnoreAnnotatedElements extends BeanMethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            return beanGetter.getAnnotation(IgnoreInQueryModel.class) != null;
        }
    }

    private class AnnotationSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final QueryModelHint a = beanGetter.getAnnotation(QueryModelHint.class);
            if (a != null) {
                methodModel.setReturnType(a.type());
                return true;
            }
            return false;
        }
    }

    private class ReferenceQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean x(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            if (beanGetter.getReturnType().toString().startsWith(Reference.class.getCanonicalName() + "<")) {
                final String type = beanGetter.getAnnotation(Nullable.class) != null ? "ReferenceOptionalQueryModel" : "ReferenceQueryModel";
                methodModel.setReturnType(type + "<" + contextType + ">");
                return true;
            }
            return false;
        }
    }
}
