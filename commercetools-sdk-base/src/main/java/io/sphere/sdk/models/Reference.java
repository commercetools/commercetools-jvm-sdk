package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A {@link io.sphere.sdk.models.Reference} is a loose reference to another resource on the platform.
 *
 * <p>The reference <em>may</em> have a copy of the referenced object available via the method {@link io.sphere.sdk.models.Reference#getObj()} on {@link io.sphere.sdk.models.Reference#getObj() certain conditions}.</p>
 *
 * For equals, only the {@link Reference#getTypeId()} and {@link io.sphere.sdk.models.Reference#getId()} are compared used and {@link Reference#getObj()} will be ignored.
 *
 * @param <T> the type of the referenced object
 */
@JsonDeserialize(as = ReferenceImpl.class)
public interface Reference<T> extends Referenceable<T>, Identifiable<T>, ResourceIdentifier<T> {

    /**
     * Id of the object this reference represents.
     * @return the id
     */
    @Override
    String getId();

    /**
     * Type id of the object this reference represents, e.g. "customer".
     * @return the type id
     */
    @Override
    String getTypeId();

    /**
     * The nullable value of the referenced object.
     *
     * This value is by default null on requests:
     *
     * {@include.example io.sphere.sdk.models.ReferenceIntegrationTest#referencesAreNotByDefaultExpanded()}
     *
     * But it can be expanded with using {@link io.sphere.sdk.expansion.ReferenceExpansionDsl#withExpansionPaths(List)} on requests:
     *
     * {@include.example io.sphere.sdk.models.ReferenceIntegrationTest#howToExpandReferences()}
     *
     * Refer to {@link io.sphere.sdk.expansion.ReferenceExpansionDsl} which endpoints support reference expansion.
     *
     * @return The value of the referenced object or null.
     */
    @Nullable
    T getObj();

    default Reference<T> filled(@Nullable final T obj) {
        return new ReferenceImpl<>(getTypeId(), getId(), obj);
    }

    static <T> Reference<T> of(final String typeId, final String id) {
        return new ReferenceImpl<>(typeId, id, null);
    }

    static <T> Reference<T> of(final String typeId, final String id, T obj) {
        return Reference.<T>of(typeId, id).filled(obj);
    }

    static <T extends Identifiable<T>> Reference<T> of(final String typeId, final T obj) {
        return Reference.of(typeId, obj.getId(), obj);
    }

    static <T> Reference<T> ofResourceTypeIdAndId(final String typeId, final String id) {
        return new ReferenceImpl<>(typeId, id, null);
    }

    static <T> Reference<T> ofResourceTypeIdAndIdAndObj(final String typeId, final String id, T obj) {
        return Reference.<T>ofResourceTypeIdAndId(typeId, id).filled(obj);
    }

    static <T extends Identifiable<T>> Reference<T> ofResourceTypeIdAndObj(final String typeId, final T obj) {
        return Reference.ofResourceTypeIdAndIdAndObj(typeId, obj.getId(), obj);
    }

    default boolean referencesSameResource(final Referenceable<T> counterpart) {
        final Reference<T> reference = counterpart.toReference();
        return reference.getId().equals(getId()) && reference.getTypeId().equals(getTypeId());
    }

    @Override
    default Reference<T> toReference() {
        return this;
    }

    @Override
    default ResourceIdentifier<T> toResourceIdentifier() {
        return this;
    }

    /**
     * In references the key should always be null
     * @return null
     */
    @Nullable
    @Override
    default String getKey() {
        return null;
    }
}
