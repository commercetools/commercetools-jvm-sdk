package io.sphere.sdk.annotations.processors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.annotations.HasQueryModel;
import io.sphere.sdk.annotations.IgnoreInQueryModel;
import io.sphere.sdk.annotations.QueryModelHint;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.SphereEnumeration;
import io.sphere.sdk.queries.ResourceQueryModel;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ReferenceType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

final class QueryModelRules extends GenerationRules {
    QueryModelRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        interfaceRules.add(new ResourceSuperInterfaceRule());
        interfaceRules.add(new CustomFieldsRule());
        methodRules.add(new IgnoreMethodsAnnotatedWithIgnoreInQueryModel());
        methodRules.add(new GenerateMethodRule());
    }

    @Override
    protected void beforeExecute() {
        final HasQueryModel hasQueryModel = typeElement.getAnnotation(HasQueryModel.class);
        addAnnotationHasQueryModelImplementation(hasQueryModel);
        addAdditions(hasQueryModel);
        addSuperInterfaces(hasQueryModel);
        addImportsForCartOrder();

    }

    @Override
    protected Stream<Element> createMethodElementStream() {
        return createBeanGetterStream(typeElement);
    }

    private void addSuperInterfaces(final HasQueryModel hasQueryModel) {
        for (final String baseInterface : hasQueryModel.baseInterfaces()) {
            builder.addInterface(baseInterface);
        }
    }

    private void addAdditions(final HasQueryModel hasQueryModel) {
        final String content = Arrays.stream(hasQueryModel.additionalContents()).collect(joining("\n\n"));
        builder.setAdditions(content);
    }

    private void addAnnotationHasQueryModelImplementation(final HasQueryModel hasQueryModel) {
        builder.addImport("io.sphere.sdk.annotations.HasQueryModelImplementation");
        final AnnotationModel a = new AnnotationModel();
        a.setName("HasQueryModelImplementation");
        if (StringUtils.isNotEmpty(hasQueryModel.implBaseClass())) {
            a.setValues(Collections.singletonMap("implBaseClass", hasQueryModel.implBaseClass()));
        }
        builder.addAnnotation(a);
    }

    private void addImportsForCartOrder() {
        if ((Stream.of("Order", "Cart").anyMatch(name -> builder.getName().contains(name)))) {
            builder.addImport("io.sphere.sdk.carts.queries.*");
            builder.addImport("io.sphere.sdk.orders.queries.*");
            builder.addImport("io.sphere.sdk.payments.queries.*");
        }
    }

    /**
     * Rule which is applied if the processed interface extends {@link Resource},
     * so the query methods can be implemented by a base class.
     */
    private class ResourceSuperInterfaceRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType referenceType) {
            final String resourceClassName = Resource.class.getCanonicalName();
            if (referenceType.toString().startsWith(resourceClassName + "<")) {
                builder.addInterface(ResourceQueryModel.class.getSimpleName() + "<" + typeElement.getSimpleName() + ">");
                builder.addImport(ResourceQueryModel.class.getCanonicalName());
                methodRules.addFirst(new IgnoreStandardResourceFields());
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

    public class GenerateMethodRule extends MethodRule {
        private final LinkedList<QueryModelSelectionRule> queryModelSelectionRules = new LinkedList<>();

        public GenerateMethodRule() {
            queryModelSelectionRules.add(new UseQueryModelHintRule());
            queryModelSelectionRules.add(new LocalizedStringQueryModelSelectionRule());
            queryModelSelectionRules.add(new ReferenceQueryModelSelectionRule());
            queryModelSelectionRules.add(new SetOfSphereEnumerationQueryModelSelectionRule());
            queryModelSelectionRules.add(new SetOfReferenceSelectionRule());
            queryModelSelectionRules.add(new SetOfStringSelectionRule());
            queryModelSelectionRules.add(new SphereEnumerationQueryModelSelectionRule());
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.Boolean", "BooleanQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.String", "StringQuerySortingModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.reviews.ReviewRatingStatistics", "ReviewRatingStatisticsQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.models.Address", "AddressQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.time.ZonedDateTime", "TimestampSortingModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("com.neovisionaries.i18n.CountryCode", "io.sphere.sdk.queries.CountryQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("javax.money.MonetaryAmount", "io.sphere.sdk.queries.MoneyQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.util.Locale", "io.sphere.sdk.queries.LocaleQueryModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.Long", "io.sphere.sdk.queries.LongQuerySortingModel"));
            queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.Integer", "io.sphere.sdk.queries.IntegerQuerySortingModel"));
        }

        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            final String fieldName = fieldNameFromGetter(beanGetter);
            final MethodModel methodModel = new MethodModel();
            methodModel.setName(fieldName);

            addHintForFieldName(beanGetter, methodModel);

            final String contextType = typeElement.getSimpleName().toString();

            queryModelSelectionRules.stream()
                    .filter(rule -> rule.accept(beanGetter, methodModel, contextType))
                    .findFirst()
            .orElseThrow(() -> new RuntimeException("Cannot generate " + builder.getName() + ", does not know query model class for " + beanGetter.getReturnType()+ " " + beanGetter.getSimpleName() + "() in " + typeElement + " with rules " + queryModelSelectionRules));
            builder.addMethod(methodModel);
            return true;
        }

        //so for boolean values important if they start with "is"
        private void addHintForFieldName(final ExecutableElement beanGetter, final MethodModel methodModel) {
            Optional.ofNullable(beanGetter.getAnnotation(JsonProperty.class))
                    .map(JsonProperty::value)
                    .ifPresent(platformFieldName -> {
                        final AnnotationModel a = new AnnotationModel();
                        a.setName("io.sphere.sdk.annotations.QueryModelFieldName");
                        a.setValue(platformFieldName);
                        methodModel.addAnnotation(a);
                    });
        }
    }

    private class IgnoreStandardResourceFields extends MethodRule {

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
        public abstract boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType);
    }

    private static Optional<DeclaredType> collectionTypeReturnType(final ExecutableElement beanGetter) {
        return Optional.ofNullable(beanGetter.getReturnType())
                .filter(g -> g instanceof DeclaredType)
                .map(g -> (DeclaredType) g)
                .filter(g -> {
                    final String uncheckedType = g.asElement().getSimpleName().toString();
                    return asList("Set", "List").contains(uncheckedType) && g.getTypeArguments().size() == 1;
                });
    }

    public class SetOfSphereEnumerationQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final Optional<DeclaredType> returnTypeOptional = collectionTypeReturnType(beanGetter);
            if (returnTypeOptional.isPresent()) {
                final DeclaredType returnType = returnTypeOptional.get();
                final DeclaredType declaredType = (DeclaredType) returnType.getTypeArguments().get(0);
                final TypeElement classInList = (TypeElement) declaredType.asElement();
                if (isSphereEnumeration(classInList)) {
                    applyEnumerationRule(methodModel, contextType, declaredType);
                    return true;
                }
            }
            return false;
        }

        private boolean isSphereEnumeration(final TypeElement classInList) {
            return classInList.getInterfaces().stream()
                                    .map(i -> (DeclaredType) i)
                                    .map(i -> (TypeElement) i.asElement())
                                    .filter(i -> i.getQualifiedName().toString().equals(SphereEnumeration.class.getCanonicalName())).findFirst()
                                    .map(xs -> true).orElse(false);
        }

        private void applyEnumerationRule(final MethodModel methodModel, final String contextType, final DeclaredType declaredType) {
            final String typeParameterName = declaredType.asElement().getSimpleName().toString();
            methodModel.setReturnType("SphereEnumerationCollectionQueryModel<" + contextType + ", " + typeParameterName + ">");
            builder.addImport("io.sphere.sdk.queries.SphereEnumerationCollectionQueryModel");
        }
    }

    private class SphereEnumerationQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
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
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            if (beanGetter.getReturnType().toString().equals(expectedType)) {
                methodModel.setReturnType(resultGenericType + "<" + contextType + ">");
                return true;
            }
            return false;
        }

    }

    private class LocalizedStringQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            if (beanGetter.getReturnType().toString().equals(LocalizedString.class.getCanonicalName())) {
                final String type = beanGetter.getAnnotation(Nullable.class) != null ? "LocalizedStringOptionalQueryModel" : "LocalizedStringQuerySortingModel";
                methodModel.setReturnType(type + "<" + contextType + ">");
                return true;
            }
            return false;
        }
    }

    private class CustomFieldsRule extends InterfaceRule {
        @Override
        public boolean accept(final ReferenceType referenceType) {
            final String refType = referenceType.toString();
            if (refType.matches("^(io.sphere.sdk.types.)?Custom$")) {
                final String contextType = typeElement.getSimpleName().toString();
                builder.addInterface("WithCustomQueryModel<" + contextType + ">");
                builder.addImport("io.sphere.sdk.types.queries.WithCustomQueryModel");
                methodRules.addFirst(new IgnoreCustomFields());
            }
            return false;
        }
    }

    private class IgnoreCustomFields extends MethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            return beanGetter.getSimpleName().toString().equals("getCustom");
        }
    }

    private class IgnoreMethodsAnnotatedWithIgnoreInQueryModel extends MethodRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter) {
            return beanGetter.getAnnotation(IgnoreInQueryModel.class) != null;
        }
    }

    private class UseQueryModelHintRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final QueryModelHint a = beanGetter.getAnnotation(QueryModelHint.class);
            if (a != null) {
                methodModel.setReturnType(a.type());
                final AnnotationModel annotationModel = new AnnotationModel();
                annotationModel.setName(QueryModelHint.class.getCanonicalName());
                annotationModel.getValues().put("type", a.type());
                annotationModel.getValues().put("impl", a.impl());
                methodModel.addAnnotation(annotationModel);
                return true;
            }
            return false;
        }
    }

    private class ReferenceQueryModelSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            if (beanGetter.getReturnType().toString().startsWith(Reference.class.getCanonicalName() + "<")) {
                final String type = beanGetter.getAnnotation(Nullable.class) != null ? "ReferenceOptionalQueryModel" : "ReferenceQueryModel";
                final String paramType = removeEnd(removeStart(beanGetter.getReturnType().toString(), Reference.class.getCanonicalName() + "<"), ">");
                final String returnType = "?".equals(paramType)
                        ? "AnyReferenceQueryModel" + "<" + contextType + ">"
                        : type + "<" + contextType + ", " + paramType + ">";
                methodModel.setReturnType(returnType);
                return true;
            }
            return false;
        }
    }

    private class SetOfReferenceSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final Optional<DeclaredType> declaredTypeOptional = collectionTypeReturnType(beanGetter)
                    .filter(returnType -> {
                        final DeclaredType declaredType = (DeclaredType) returnType.getTypeArguments().get(0);
                        return declaredType.asElement().getSimpleName().toString().equals(Reference.class.getSimpleName());
                    });
            declaredTypeOptional.ifPresent(returnType -> {
                final String param = ((DeclaredType)returnType.getTypeArguments().get(0)).getTypeArguments().get(0).toString();
                methodModel.setReturnType("ReferenceCollectionQueryModel<" + param + ", " + contextType + ">");
            });
            return declaredTypeOptional.isPresent();
        }
    }

    private class SetOfStringSelectionRule extends QueryModelSelectionRule {
        @Override
        public boolean accept(final ExecutableElement beanGetter, final MethodModel methodModel, final String contextType) {
            final Map<String, String> map = new HashMap<>();
            map.put("String", "StringCollectionQueryModel");
            final Optional<String> typeParameterOptional = collectionTypeReturnType(beanGetter)
                    .map(returnType -> {
                        final DeclaredType declaredType = (DeclaredType) returnType.getTypeArguments().get(0);
                        final String beanGetterCollectionTypeParameter = declaredType.asElement().getSimpleName().toString();
                        return beanGetterCollectionTypeParameter;
                    });
            typeParameterOptional.ifPresent(param -> {
                methodModel.setReturnType(map.get(param) + "<" + contextType + ">");
            });
            return typeParameterOptional.isPresent();
        }
    }
}
