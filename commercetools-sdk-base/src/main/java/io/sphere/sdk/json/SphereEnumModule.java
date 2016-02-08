package io.sphere.sdk.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.sphere.sdk.models.SphereEnumeration;

final class SphereEnumModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    SphereEnumModule() {
        addSerializer(SphereEnumeration.class, new EnumSerializer());
    }

}
