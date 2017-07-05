package io.sphere.sdk.annotations.processors.generators;

import io.sphere.sdk.utils.SphereInternalLogger;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.stream.Collectors;


public class FakeMessager implements Messager {

    private final SphereInternalLogger logger = SphereInternalLogger.getLogger(this.getClass());

    public FakeMessager() {
    }

    private void log(String message) {
        logger.trace(() -> message);
    }


    public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
        printArgs(kind, msg);
    }


    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
        printArgs(kind, msg, e);
    }


    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
        printArgs(kind, msg, e, a);
    }


    public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
        printArgs(kind, msg, e, a, v);
    }

    private void printArgs(Diagnostic.Kind kind, Object... objs) {

        final String str = Arrays.stream(objs).map(o -> o.toString()).collect(Collectors.joining(", "));
        log("Annotation processor message : " + str);
        if (kind == Diagnostic.Kind.ERROR) {
            throw new Error("Error occurred while processing annotation " + str);
        }
    }
}
