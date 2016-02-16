package introspection.rules;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.Resource;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReferenceableResourceCanCreateReferenceRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return isSubTypeOf(clazz, Referenceable.class)
                && isSubTypeOf(clazz, Resource.class)
                && Modifier.isPublic(clazz.getModifiers())
                && Arrays.stream(clazz.getMethods()).anyMatch(m -> m.getName().equals("referenceTypeId"));
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return Arrays.stream(clazz.getMethods()).anyMatch(method -> method.getName().equals("referenceOfId") && Modifier.isStatic(method.getModifiers()));
    }
}
