package io.sphere.sdk.meta;

import io.sphere.sdk.json.SphereJsonUtils;

import java.io.IOException;
import java.util.Properties;

public final class BuildInfo {
    private static final String version;
    private static final String userAgent;

    static {
        version = readVersion();
        userAgent = "commercetools-jvm-sdk/" + version;
    }

    private BuildInfo() {
        //utility class
    }

    public static String userAgent() {
        return userAgent;
    }

    public static String version() {
        return version;
    }

    private static String readVersion(){
        try{
            Properties properties = new Properties();
            properties.load(SphereJsonUtils.getResourceInputStream("app.properties"));
            return properties.getProperty("version", null);
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Couldn't load version from app.properties resource");
            System.exit(-1);
            return null;
        }
    }
}
