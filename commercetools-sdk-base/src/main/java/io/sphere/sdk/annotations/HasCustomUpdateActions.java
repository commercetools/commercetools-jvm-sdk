package io.sphere.sdk.annotations;


import java.lang.annotation.*;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface HasCustomUpdateActions {
    HasUpdateAction[] value();
}
