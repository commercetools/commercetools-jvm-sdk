package sphere;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public class Util {
    /** Extract the id portion from an URL slug (see {@link sphere.model.products.Product#getSlugWithID()}). */
    public static String getIDFromSlug(String slug) {
        String[] parts = slug.split("-");
        if (parts.length < 5) {
            throw new RuntimeException("Not a valid UUID in URL slug: " + slug);
        }
        return StringUtils.join(Arrays.copyOfRange(parts, parts.length - 5, parts.length), "-");
    }
}
