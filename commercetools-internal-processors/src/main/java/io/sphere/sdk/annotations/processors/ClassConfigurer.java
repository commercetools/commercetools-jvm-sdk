package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static io.sphere.sdk.annotations.processors.ClassModelFactory.createField;
import static io.sphere.sdk.annotations.processors.ResourceDraftBuilderClassModelFactory.BEAN_GETTER_PREDICATE;
import static java.util.Arrays.asList;

final class ClassConfigurer {
    public static SourceHolder ofSource(final TypeElement typeElement) {
        return new SourceHolder(typeElement);
    }

    public static class SourceHolder {
        private TypeElement typeElement;

        private SourceHolder(final TypeElement typeElement) {
            this.typeElement = typeElement;
        }

        public PackageHolder samePackageFromSource() {
            return packageFrom(typeElement);
        }

        public PackageHolder packageFrom(final TypeElement typeElement) {
            final String packageName = ClassModelFactory.packageName(typeElement);
            return new PackageHolder(packageName, this);
        }
    }

    public static class PackageHolder {
        private String packageName;
        private SourceHolder sourceHolder;

        private PackageHolder(final String packageName, final SourceHolder sourceHolder) {
            this.packageName = packageName;
            this.sourceHolder = sourceHolder;
        }

        public ImportHolder withDefaultImports() {
            final List<String> imports = new LinkedList<>();
            return new ImportHolder(imports, this);
        }
    }



    public static class ImportHolder {
        private List<String> imports;
        private PackageHolder packageHolder;

        private ImportHolder(final List<String> imports, final PackageHolder packageHolder) {
            this.imports = imports;
            this.packageHolder = packageHolder;
        }

        public ClassModifierHolder modifiers(final String ... modifiers) {
            return modifiers(asList(modifiers));
        }

        public ClassModifierHolder modifiers(final List<String> modifiers) {
            return new ClassModifierHolder(modifiers, this);
        }
    }

    public static class ClassModifierHolder {
        private List<String> modifiers;
        private ImportHolder importHolder;

        private ClassModifierHolder(final List<String> modifiers, final ImportHolder importHolder) {
            this.modifiers = modifiers;
            this.importHolder = importHolder;
        }

        public ClassNameHolder className(final UnaryOperator<String> op) {
            return new ClassNameHolder(op.apply(importHolder.packageHolder.sourceHolder.typeElement.getSimpleName().toString()), this);
        }
    }

    public static class ClassNameHolder {
        private String name;
        private ClassModifierHolder classModifierHolder;

        private ClassNameHolder(final String name, final ClassModifierHolder classModifierHolder) {
            this.name = name;
            this.classModifierHolder = classModifierHolder;
        }

        public BaseClassHolder extending(final String baseClassName) {
            return new BaseClassHolder(baseClassName, this);
        }

        public BaseClassHolder extending(final Class<?> baseClass) {
            return extending(baseClass.getName());
        }
    }

    public static class BaseClassHolder {
        private String baseClassName;
        private ClassNameHolder classNameHolder;

        private BaseClassHolder(final String baseClassName, final ClassNameHolder classNameHolder) {
            this.baseClassName = baseClassName;
            this.classNameHolder = classNameHolder;
        }

        public InterfacesHolder implementing(final String ... interfaces) {
            return new InterfacesHolder(asList(interfaces), this);
        }

        public InterfacesHolder implementing(final TypeElement typeElement) {
            return implementing(typeElement.getSimpleName().toString());
        }
    }

    public static class InterfacesHolder {
        private final List<String> interfaces;
        private final BaseClassHolder baseClassHolder;

        private InterfacesHolder(final List<String> interfaces, final BaseClassHolder baseClassHolder) {
            this.interfaces = interfaces;
            this.baseClassHolder = baseClassHolder;
        }

        public FieldsHolder fieldsFromBeanGetters() {
            final List<FieldModel> fields = this.baseClassHolder.classNameHolder.classModifierHolder.importHolder.packageHolder.sourceHolder.typeElement.getEnclosedElements()
                    .stream()
                    .filter(BEAN_GETTER_PREDICATE)
                    .map(typeElement -> createField(typeElement))
                    .collect(Collectors.toList());
            return fields(fields);
        }

        public FieldsHolder fields(final List<FieldModel> fields) {
            return new FieldsHolder(fields, this);
        }
    }

    public static class FieldsHolder {
        private List<FieldModel> fields;
        private InterfacesHolder interfacesHolder;

        private FieldsHolder(final List<FieldModel> fields, final InterfacesHolder interfacesHolder) {
            this.fields = fields;
            this.interfacesHolder = interfacesHolder;
        }
    }
}
