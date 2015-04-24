package io.sphere.sdk.orders.queries;

import org.junit.Test;

import static io.sphere.sdk.queries.QuerySortDirection.ASC;
import static org.fest.assertions.Assertions.assertThat;

public class SyncInfoQueryModelTest {
    @Test
    public void sortBySyncedAt() throws Exception {
        assertThat(OrderQuery.model().syncInfo().syncedAt().sort(ASC).toSphereSort())
        .isEqualTo("syncInfo.syncedAt asc");
    }
}