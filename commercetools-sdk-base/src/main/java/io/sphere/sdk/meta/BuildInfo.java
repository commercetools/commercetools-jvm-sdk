package io.sphere.sdk.meta;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Manifest;

public final class BuildInfo {
    private static final String version = resolveVersionFromManifest();
    private static final String userAgent = "commercetools JVM SDK " + version;

    private BuildInfo() {
        //utility class
    }

    public static String userAgent() {
        return userAgent;
    }

    public static String version() {
        return version;
    }

    private static String resolveVersionFromManifest() {
        URLClassLoader cl = (URLClassLoader) BuildInfo.class.getClassLoader();
        try {
            URL url = cl.findResource("META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(url.openStream());
            final String value = manifest.getMainAttributes().getValue("commercetools-jvm-sdk-version");
            return value != null ? value : "<unknown version>";
        } catch (final IOException e) {
            throw new RuntimeException("cannot resolve JVM SDK version", e);
        }
    }
}
