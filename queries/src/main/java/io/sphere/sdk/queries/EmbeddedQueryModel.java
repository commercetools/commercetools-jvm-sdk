package io.sphere.sdk.queries;

import java.util.Optional;

/**
 * If you want a field to query and be searchable use something like
 * {@code
     public StringQuerySortingModel<T> name() {
            return nameModel();
     }
   }
 *
 * If you want a field to query and NOT be searchable use something like (remove Sorting)
 * {@code
     public StringQueryModel<T> name() {
            return nameModel();
     }
   }
 *
 * @param <T> TODO
 */
public abstract class EmbeddedQueryModel<T> extends QueryModelImpl<T> {
    protected EmbeddedQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    protected LocalizedStringQuerySortingModel<T> localizedStringQueryModel(final String pathSegment) {
        return new LocalizedStringQuerySortingModel<T>(Optional.of(this), Optional.of(pathSegment));
    }
}