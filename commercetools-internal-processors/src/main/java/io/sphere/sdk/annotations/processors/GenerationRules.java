package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeMirror;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static io.sphere.sdk.annotations.processors.QueryModelImplRules.packageOfModels;

/**
 * Some rules to apply while generating a class model with a {@link ClassModelBuilder}.
 * Uses the visitor pattern where a visitor is called a rule.
 */
public abstract class GenerationRules {
    protected final TypeElement typeElement;
    protected final ClassModelBuilder builder;
    protected final LinkedList<InterfaceRule> interfaceRules = new LinkedList<>();
    protected final LinkedList<MethodRule> methodRules = new LinkedList<>();

    public GenerationRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        this.typeElement = typeElement;
        this.builder = builder;
    }

    /**
     * Applies the rules to the {@link ClassModelBuilder} (side effects).
     */
    public final void execute() {
        beforeExecute();
        builder.addImport(packageOfModels(typeElement) +  ".*");
        applyInterfaceRules();
        applyMethodRules(createMethodElementStream());
    }

    protected abstract Stream<? extends Element> createMethodElementStream();

    protected void beforeExecute() {
    }

    private void applyInterfaceRules() {
        final List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
        final Stream<? extends TypeMirror> subInterfaces = interfaces.stream()
                .map(_interface -> (DeclaredType) _interface)
                .map(_interface -> (TypeElement) _interface.asElement())
                .flatMap(_interface -> _interface.getInterfaces().stream());
        Stream.concat(interfaces.stream(), subInterfaces).distinct().forEach(_interface -> interfaceRules.stream()
                .filter(rule -> rule.accept((ReferenceType)_interface))
                .findFirst());
    }

    private void applyMethodRules(final Stream<? extends Element> beanGetterStream) {
        beanGetterStream.forEach(beanGetter -> methodRules.stream()
                .filter(rule -> rule.accept((ExecutableElement)beanGetter))
                .findFirst());
    }


    /**
     * A rule concerning the interface of a class which is processed.
     */
    protected abstract class InterfaceRule {
        /**
         * Presents one interface to this rule.
         * @param referenceType the interface
         * @return true, if the {@code referenceType} should not be processed by the next {@link InterfaceRule}
         */
        public abstract boolean accept(final ReferenceType referenceType);
    }

    /**
     * A rule concerning a bean getter of a class which is processed.
     */
    protected abstract class MethodRule {
        /**
         * Accepts a method as parameter and may applies a rule to update the {@link ClassModelBuilder}.
         * @param beanGetter a bean getter method from an annotation processed class
         * @return @return true, if the {@code referenceType} should not be processed by the next {@link MethodRule}
         */
        public abstract boolean accept(final ExecutableElement beanGetter);
    }
}
