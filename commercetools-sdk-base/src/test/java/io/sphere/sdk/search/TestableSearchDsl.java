package io.sphere.sdk.search;

import com.fasterxml.jackson.core.type.TypeReference;

public final class TestableSearchDsl extends MetaModelSearchDslImpl<Object, TestableSearchDsl, Object, Object, Object, Object> {

        TestableSearchDsl(){
            super("", new TypeReference<PagedSearchResult<Object>>() {}, new Object(), new Object(), new Object(), new Object(), TestableSearchDsl::new);
        }

        private TestableSearchDsl(final MetaModelSearchDslBuilder<Object, TestableSearchDsl, Object, Object, Object, Object> builder) {
            super(builder);
        }
    }