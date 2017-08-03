package introspection.rules;

public class EveryObjectHasAGoodBaseClass extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return !clazz.isInterface() && !clazz.isEnum()
                && !clazz.getName().endsWith("Documentation")
                && !clazz.getName().endsWith("SphereInternalLogger")
                && !clazz.getName().endsWith("SphereResources")
                && !clazz.getName().endsWith("TroubleshootingGuide")
                && !clazz.getName().endsWith("Demo")
                && !clazz.getName().endsWith("Utils")
                && !clazz.getName().endsWith("ReleaseNotes")
                && !clazz.getName().endsWith("MultiValueSortSearchModelFactory")
                && !clazz.getName().endsWith("SdkDefaults")
                && !clazz.getName().endsWith("ProductProjectionComparators")
                && !clazz.getName().endsWith("DefaultCurrencyUnits")
                && !clazz.getName().endsWith("HttpStatusCode")
                && !clazz.getName().equals("io.sphere.sdk.models.Base")
                && !clazz.getName().equals("io.sphere.sdk.utils.OSGiPriorityAwareServiceProvider")
                && !clazz.getName().equals("io.sphere.sdk.meta.BuildInfo");
    }

    //this should remind you to inherit from Base to get toString/equals/hashCode from it
    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return !clazz.getSuperclass().getSimpleName().equals("Object");
    }
}
