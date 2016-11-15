package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.*;

final class ReturnTypeElementVisitor implements ElementVisitor<String, Void>{
    @Override
    public String visit(final Element e, final Void aVoid) {
        return null;
    }

    @Override
    public String visit(final Element e) {
        return null;
    }

    @Override
    public String visitPackage(final PackageElement e, final Void aVoid) {
        return null;
    }

    @Override
    public String visitType(final TypeElement e, final Void aVoid) {
        return null;
    }

    @Override
    public String visitVariable(final VariableElement e, final Void aVoid) {
        return null;
    }

    @Override
    public String visitExecutable(final ExecutableElement e, final Void aVoid) {
        return e.getReturnType().toString();
    }

    @Override
    public String visitTypeParameter(final TypeParameterElement e, final Void aVoid) {
        return null;
    }

    @Override
    public String visitUnknown(final Element e, final Void aVoid) {
        return null;
    }
}
