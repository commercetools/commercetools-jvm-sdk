package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.SphereEnumeration;
import io.sphere.sdk.queries.ResourceQueryModel;

import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.sphere.sdk.annotations.processors.ClassConfigurer.createBeanGetterStream;
import static io.sphere.sdk.annotations.processors.ClassConfigurer.fieldNameFromGetter;
import static java.util.Arrays.asList;

final class QueryModelRules extends GenerationRules {
    private final LinkedList<QueryModelSelectionRule> queryModelSelectionRules = new LinkedList<>();

    QueryModelRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        super(typeElement, builder);
        interfaceRules.add(new ResourceRule());
        interfaceRules.add(new CustomFieldsRule());
        beanMethodRules.add(new GenerateMethodRule());
        queryModelSelectionRules.add(new LocalizedStringQueryModelSelectionRule());
        queryModelSelectionRules.add(new SetOfSphereEnumerationQueryModelSelectionRule());
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("java.lang.String", "StringQuerySortingModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.reviews.ReviewRatingStatistics", "ReviewRatingStatisticsQueryModel"));
        queryModelSelectionRules.add(new SimpleQueryModelSelectionRule("io.sphere.sdk.models.Address", "AddressQueryModel"));
    }

    @Override
    void execute() {
        builder.addImport("io.sphere.sdk.annotations.HasQueryModelImplementation");
        final AnnotationModel a = new AnnotationModel();
        a.setName("HasQueryModelImplementation");
        builder.addAnnotation(a);
        builder.addImport(typeElement.getQualifiedName().toString());
        typeElement.getInterfaces().forEach(i -> interfaceRules.stream()
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
            }
            return false;
        }
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
            .orElseThrow(() -> new RuntimeException("no query model type for " + typeElement + " " + beanGetter));
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
                builder.addInterface("WithCustomQueryModel");
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
}
