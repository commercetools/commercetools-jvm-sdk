package introspection.rules;

import java.lang.reflect.Constructor;
import java.util.List;

import static java.util.Arrays.asList;

public class PublicConstructorsAreTheExceptionRule extends ConstructorStrategyRule {
    private final List<String> fullClassNamesWhiteList =
            asList(("io.sphere.sdk.models.Base," +
                    "io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingByKeyGet," +
                    "io.sphere.sdk.customobjects.commands.CustomObjectCustomJsonMappingUpsertCommand," +
                    "io.sphere.sdk.expansion.ExpansionModel," +
                    "io.sphere.sdk.carts.queries.TaxRateQueryModelImpl," +
                    "io.sphere.sdk.products.queries.PriceCollectionQueryModel," +
                    "io.sphere.sdk.products.queries.ProductVariantQueryModel," +
                    "io.sphere.sdk.search.MetaModelSearchDslBuilder," +
                    "ResourceMetaModelSearchDslBuilderImpl," +
                    "io.sphere.sdk.http.HttpClientAdapterBase," +
                    "io.sphere.sdk.products.attributes.DefaultProductAttributeFormatter," +
                    "io.sphere.sdk.search.model.TermFilterExpression," +
                    "io.sphere.sdk.utils.JavaMoneySphereClientModule," +
                    "io.sphere.sdk.client.SolutionInfo," +
                    "io.sphere.sdk.client.SphereApacheHttpClientFactory," +
                    "io.sphere.sdk.client.SphereAsyncHttpClientFactory," +
                    "io.sphere.sdk.client.SphereHttpClientFactory," +
                    "io.sphere.sdk.utils.OSGiPriorityAwareServiceProvider").split(",( )?"));

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return !isNotIncluded(clazz) &&!isInWhiteList(clazz);
    }

    private boolean isInWhiteList(final Class<?> clazz) {
        return fullClassNamesWhiteList.contains(clazz.getName())
                || clazz.getSimpleName().endsWith("SearchModel")
                || clazz.getSimpleName().endsWith("Impl")
                || clazz.getSimpleName().endsWith("ExpansionModel")
                ;
    }

    private boolean isNotIncluded(final Class<?> clazz) {
        return isException(clazz);
    }

    @Override
    protected boolean isRuleConform(final Constructor<?> constructor) {
        return false;
    }
}
