package introspection.rules;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class BuildersNotForResourcesRule extends ClassStrategyRule {
    private final List<String> simpleNamesWhitelist = Arrays.asList(("AttributeDefinitionBuilder," +
            "AddressBuilder, MetaModelGetDslBuilder, MetaModelQueryDslBuilder, Builder," +
            "UpdateCommandDslBuilder, TrackingDataBuilder, TaxRateBuilder, PriceBuilder, MetaModelSearchDslBuilder").split(",( )?"));

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Builder") && !clazz.getSimpleName().startsWith("MetaModel") && !clazz.getCanonicalName().contains(".http.");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("DraftBuilder") || simpleNamesWhitelist.contains(clazz.getSimpleName()) || builtClassDoesNotExtendResource(clazz);
    }

    private boolean builtClassDoesNotExtendResource(final Class<?> clazz) {
        try {
            final String buildedClassName = StringUtils.removeEnd(clazz.getCanonicalName(), "Builder");
            final Class<?> buildedClass = Class.forName(buildedClassName);
            final Class<?> resourceClass = Class.forName("io.sphere.sdk.models.Resource");
            return !isSubTypeOf(buildedClass, resourceClass);
        } catch (final ClassNotFoundException e) {
            return true;
        }
    }
}
