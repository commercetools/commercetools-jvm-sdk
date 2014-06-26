package io.sphere.sdk.queries;

import com.google.common.collect.Iterables;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.requests.ClientRequest;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import com.github.slugify.Slugify;

import static java.util.stream.Collectors.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * A base class concerning queries. It is only applicable if the model can be created by a (possible unique) name.
 * @param <T>
 */
public abstract class QueryIntegrationTest<T extends Versioned> extends IntegrationTest {

    @Test
    public void queryAllScenario() {
        assertModelsNotPresent();
        assertModelsInBackend();
        final PagedQueryResult<T> queryResult = queryAll();
        assertThat(getNames(queryResult.getResults())).contains(modelNames());
        cleanUpByName(modelNames());
    }

    @Test
    public void queryByNameScenario() {
        assertModelsNotPresent();
        assertModelsInBackend();
        final String nameToFind = modelNames().get(1);
        final List<T> results = queryByName(nameToFind).getResults();
        assertThat(getNames(results)).containsExactly(nameToFind);
        assertModelsNotPresent();
    }

    /**
     * Removes all items with the name in {@code names}
     * Should not throw exceptions if the elements are not existing.
     * @param names the names of the items to delete
     */
    protected void cleanUpByName(final List<String> names){
        queryByName(names).getResults().forEach(item -> delete(item));
    }

    protected void delete(Versioned item) {
        client.execute(deleteCommand(item));
    }

    protected abstract ClientRequest<T> deleteCommand(Versioned item);

    protected List<T> createInBackendByName(final List<String> names) {
        return names.stream().map(name -> client.execute(newCreateCommandForName(name))).collect(toList());
    }

    protected abstract ClientRequest<T> newCreateCommandForName(String name);

    protected abstract String extractName(final T instance);

    protected PagedQueryResult<T> queryAll() {
        return client.execute(queryRequestForQueryAll());
    }

    protected abstract ClientRequest<PagedQueryResult<T>> queryRequestForQueryAll();

    protected PagedQueryResult<T> queryByName(final String name) {
        return client.execute(queryObjectForName(name));
    }

    protected abstract ClientRequest<PagedQueryResult<T>> queryObjectForName(final String name);

    protected PagedQueryResult<T> queryByName(final List<String> names) {
        return client.execute(queryObjectForNames(names));
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

    private int assertModelsInBackend() {
        final List<T> instances = createInBackendByName(modelNames());
        final int minAmountOfInstances = 3;
        assertThat(instances.stream().map(o -> extractName(o)).collect(toList())).
                overridingErrorMessage(String.format("The test requires at least %d instances with the names %d.", minAmountOfInstances, Iterables.toString(modelNames()))).
                contains(modelNames());
        return minAmountOfInstances;
    }

    private void assertModelsNotPresent() {
        cleanUpByName(modelNames());
        assertThat(getNames(queryAll().getResults())).overridingErrorMessage("the instances with the names " + modelNames() + " should not be present.").excludes(modelNames());
    }
}
