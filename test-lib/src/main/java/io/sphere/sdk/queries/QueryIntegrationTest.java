package io.sphere.sdk.queries;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.IterableUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.github.slugify.Slugify;

import static java.util.stream.Collectors.*;
import static org.fest.assertions.Assertions.assertThat;

import static io.sphere.sdk.utils.SphereInternalLogger.*;

/**
 * A base class concerning queries. It is only applicable if the model can be created by a (possible unique) name.
 * @param <T>
 */
public abstract class QueryIntegrationTest<T extends Versioned<T>> extends IntegrationTest {

    @Test
    public void queryAllScenario() {
        assertModelsNotPresent();
        assertModelsInBackend();
        final PagedQueryResult<T> queryResult = queryAll();
        assertThat(getNames(queryResult.getResults()).stream().
                filter(name -> modelNames().contains(name)).sorted().collect(toList())).isEqualTo(modelNames());
        cleanUpByName(modelNames());
    }

    @Test
    public void queryByNameScenario() {
        assertModelsNotPresent();
        assertModelsInBackend();
        final String nameToFind = modelNames().get(1);
        final List<T> results = queryByName(nameToFind).getResults();
        assertThat(results).hasSize(1);
        assertThat(getNames(results)).containsExactly(nameToFind);
        assertModelsNotPresent();
    }

    protected void withByName(final String name, final Consumer<T> consumer) {
        cleanUpByName(name);
        final T instance = createInBackendByName(name);
        consumer.accept(instance);
        cleanUpByName(name);
    }

    /**
     * Removes all items with the name in {@code names}
     * Should not throw exceptions if the elements are not existing.
     * @param names the names of the items to delete
     */
    protected void cleanUpByName(final List<String> names){
        queryByName(names).getResults().forEach(item -> delete(item));
    }

    protected void cleanUpByName(final String name) {
        cleanUpByName(Arrays.asList(name));
    }

    protected void delete(T item) {
        try {
            execute(deleteCommand(item));
        } catch (final Exception e) {
            getLogger("test.fixtures").warn(() -> String.format("tried to delete %s but an Exception occurred: %s", item, e.toString()));
        }
    }

    protected abstract ClientRequest<T> deleteCommand(T item);

    protected List<T> createInBackendByName(final List<String> names) {
        return names.stream().map(name -> execute(newCreateCommandForName(name))).collect(toList());
    }

    protected T createInBackendByName(final String name) {
        return createInBackendByName(Arrays.asList(name)).get(0);
    }

    protected abstract ClientRequest<T> newCreateCommandForName(String name);

    protected abstract String extractName(final T instance);

    protected PagedQueryResult<T> queryAll() {
        return execute(queryRequestForQueryAll());
    }

    protected abstract ClientRequest<PagedQueryResult<T>> queryRequestForQueryAll();

    protected PagedQueryResult<T> queryByName(final String name) {
        return execute(queryObjectForName(name));
    }

    protected abstract ClientRequest<PagedQueryResult<T>> queryObjectForName(final String name);

    protected PagedQueryResult<T> queryByName(final List<String> names) {
        return execute(queryObjectForNames(names));
    }

    protected abstract ClientRequest<PagedQueryResult<T>> queryObjectForNames(List<String> names);

    private String sluggedClassName() {
        final String className = this.getClass().toString();
        return new Slugify().slugify(className);
    }

    protected List<String> modelNames(){
        return IntStream.of(1, 2, 3).mapToObj(i -> sluggedClassName() + i).collect(toList());
    }

    protected List<String> getNames(final List<T> elements) {
        return elements.stream().map(o -> extractName(o)).collect(toList());
    }

    private void assertModelsInBackend() {
        final List<T> instances = createInBackendByName(modelNames());
        final List<String> actualNames = instances.stream().map(o -> extractName(o)).
                filter(name -> modelNames().contains(name)).sorted().collect(toList());
        assertThat(actualNames).
                overridingErrorMessage(String.format("The test requires instances with the names %s.", IterableUtils.toString(modelNames()))).
                isEqualTo(modelNames());
    }

    private void assertModelsNotPresent() {
        cleanUpByName(modelNames());
        assertThat(getNames(queryAll().getResults())).overridingErrorMessage("the instances with the names " + modelNames() + " should not be present.").excludes(modelNames());
    }
}
