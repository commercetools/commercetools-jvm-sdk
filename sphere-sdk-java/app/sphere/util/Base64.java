package sphere.util;

import org.apache.commons.codec.binary.StringUtils;

public class Base64 {
    public static String decode(String s) {
        return StringUtils.newStringUtf8(org.apache.commons.codec.binary.Base64.decodeBase64(s));
    }
    public static String encode(String s) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(StringUtils.getBytesUtf8(s));
    }
}