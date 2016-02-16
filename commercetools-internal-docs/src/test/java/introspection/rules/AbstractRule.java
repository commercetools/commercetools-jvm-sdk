package introspection.rules;

public abstract class AbstractRule implements Rule {
    protected static boolean isSubTypeOf(final Class<?> clazz, final Class<?> superClass) {
        return superClass.isAssignableFrom(clazz);
    }

    protected static boolean isException(final Class<?> clazz) {
        return isSubTypeOf(clazz, Exception.class);
    }
}
