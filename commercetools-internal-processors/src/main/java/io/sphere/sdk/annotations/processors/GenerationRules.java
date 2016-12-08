package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.util.LinkedList;

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
    abstract void execute();

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
