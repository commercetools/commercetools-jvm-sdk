package introspection.rules;

import java.util.List;

import static java.util.Arrays.asList;

public class ResourceClassesWithTypeReferenceMethod extends ClassStrategyRule {
    private final List<String> simpleClassNamesWhiteList =
            asList(("CartLike, GenericMessage, Resource").split(",( )?"));

    private static Class<?> baseClass;
    static {
        try {
            baseClass = Class.forName("io.sphere.sdk.models.Resource");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.isInterface() && isSubTypeOf(clazz, baseClass)
                && !simpleClassNamesWhiteList.contains(clazz.getSimpleName())
                && !clazz.getSimpleName().matches("Simple.*Message");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        try {
            return clazz.getMethod("typeReference") != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}