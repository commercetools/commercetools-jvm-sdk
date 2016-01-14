package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.channels.ChannelFixtures.withChannelOfRole;
import static io.sphere.sdk.channels.ChannelFixtures.withUpdatableChannelOfRole;
import static io.sphere.sdk.reviews.ReviewFixtures.withReview;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelQueryTest extends IntegrationTest {
    @Test
    public void execution() {
        withUpdatableChannelOfRole(client(), ChannelRole.PRIMARY, channel -> {
            final String key = channel.getKey();
            final PagedQueryResult<Channel> pagedQueryResult = client().executeBlocking(ChannelQuery.of().byKey(key));

            assertThat(pagedQueryResult).has(onlyTheResult(channel));
            return channel;
        });
    }

    @Test
    public void queryByReviewRating() {
        withChannelOfRole(client(), ChannelRole.PRIMARY ,product -> {
            withReview(client(), b -> b.target(product).rating(1), review1 -> {
                withReview(client(), b -> b.target(product).rating(3), review2 -> {
                    final ChannelQuery query = ChannelQuery.of()
                            .withPredicates(m -> m.reviewRatingStatistics().averageRating().is(2.0))
                            .plusPredicates(m -> m.reviewRatingStatistics().count().is(2))
                            .plusPredicates(m -> m.is(product));
                    final List<Channel> results = client().executeBlocking(query).getResults();
                    assertThat(results).hasSize(1);
                    final Channel channel = results.get(0);
                    assertThat(channel.getId()).isEqualTo(product.getId());
                    assertThat(channel.getReviewRatingStatistics().getCount()).isEqualTo(2);
                });
            });
        });
    }
}