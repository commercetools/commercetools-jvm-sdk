package io.sphere.sdk.annotations.processors.generators.examples;


import io.sphere.sdk.annotations.HasUpdateAction;

import javax.annotation.Nullable;
import java.util.Locale;

public interface ExampleResourceWithUpdateAction {

    @HasUpdateAction(value = "customName",className = "SomeClasse")
    String getUserName();

    @Nullable
    @HasUpdateAction("customName")
    String getOptionalUserName();


    @HasUpdateAction("changeLocale")
    Locale getLocale();

    @Nullable
    @HasUpdateAction
    Locale getOptionalLocale();

    @Deprecated
    @HasUpdateAction
    String getDeprecatedField();
}
