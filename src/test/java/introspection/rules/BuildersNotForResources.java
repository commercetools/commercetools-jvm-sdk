package introspection.rules;

import java.util.Arrays;
import java.util.List;

public class BuildersNotForResources extends ClassStrategyRule {
    private final List<String> simpleNamesWhitelist = Arrays.asList(("AttributeDefinitionBuilder," +
            "AddressBuilder, MetaModelFetchDslBuilder, MetaModelQueryDslBuilder, Builder," +
            "UpdateCommandDslBuilder, TrackingDataBuilder, TaxRateBuilder, PriceBuilder").split(",( )?"));

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Builder") && !clazz.getCanonicalName().contains(".http.");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("DraftBuilder") || simpleNamesWhitelist.contains(clazz.getSimpleName());
    }
}
