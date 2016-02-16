package io.sphere.sdk.client;

import org.apache.commons.io.IOUtils;

public final class ResourceUtil {
    private ResourceUtil() {
    }

    public static String stringFromResource(final String resourcePath) throws Exception {
        return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath), "UTF-8");
    }

}
