package io.sphere.sdk.models;

/**
 * Something that has an ID and a version.
 *
 * <p>Typically this is a parameter of the an update or delete command.</p>
 * <p>So for example you have <pre>{@code ProductUpdateCommand of(final Versioned<Product> versioned, final UpdateAction<Product> updateAction)}</pre> then you should <strong>NOT</strong> do:
 *
 * <pre>{@code final Product product = getProduct();
 * //NOT GOOD
 * final ProductUpdateCommand cmd = ProductUpdateCommand.of(Versioned.of(product.getId(), product.getVersion()), Publish.of()); }</pre>
 *
 * <p>The resources already implement {@link Versioned} so you can use them directly:</p>
 * <pre>{@code final Product product = getProduct();
 * //Product implements Versioned<Product>
 * final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, Publish.of());}</pre>
 * </p>
 *
 * <p>But there occasions where you submit ID and version in a form and want to update a resource without fetching it before:</p>
 * <pre>{@code final String id = "c7b25e66-465f-4b77-8c8b-10f0958a1436";
 * final long version = 5;
 * final ProductUpdateCommand cmd = ProductUpdateCommand.of(Versioned.of(id, version), Publish.of());}</pre>
 *
 * @param <T> The type which has an ID and version.
 */
public interface Versioned<T> extends Identifiable<T> {
    @Override
    String getId();

    Long getVersion();

    /**
     * Creates a versioned that only contains the id and the version.
     * @param versioned the template object to use its ID and version
     * @param <T> The type which has an ID and version.
     * @return versioned
     */
    static <T> Versioned<T> of(final Versioned<T> versioned) {
        return of(versioned.getId(), versioned.getVersion());
    }

    static <T> Versioned<T> of(final String id, final long version) {
        return new SimpleVersioned<>(id, version);
    }
}
