package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.util.LinkedList;

public abstract class GenerationRules {
    protected final TypeElement typeElement;
    protected final ClassModelBuilder builder;
    protected final LinkedList<InterfaceRule> interfaceRules = new LinkedList<>();
    protected final LinkedList<BeanMethodRule> beanMethodRules = new LinkedList<>();

    public GenerationRules(final TypeElement typeElement, final ClassModelBuilder builder) {
        this.typeElement = typeElement;
        this.builder = builder;
    }

    abstract void execute();

    protected abstract class InterfaceRule {
        public abstract boolean accept(final ReferenceType i);
    }

    protected abstract class BeanMethodRule {
        public abstract boolean accept(final ExecutableElement beanGetter);
    }
}
