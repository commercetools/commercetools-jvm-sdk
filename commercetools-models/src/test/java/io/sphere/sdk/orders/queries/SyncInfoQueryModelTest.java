package io.sphere.sdk.orders.queries;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncInfoQueryModelTest {
    @Test
    public void sortBySyncedAt() throws Exception {
        assertThat(OrderQueryModel.of().syncInfo().syncedAt().sort().asc().toSphereSort())
        .isEqualTo("syncInfo.syncedAt asc");
    }
}