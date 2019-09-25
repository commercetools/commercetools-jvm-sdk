package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Point;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.channels.ChannelFixtures.*;
import static io.sphere.sdk.reviews.ReviewFixtures.withReview;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class ChannelQueryIntegrationTest extends IntegrationTest {
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
        withChannelOfRole(client(), ChannelRole.PRIMARY , channel -> {
            withReview(client(), b -> b.target(channel).rating(1), review1 -> {
                withReview(client(), b -> b.target(channel).rating(3), review2 -> {
                    final ChannelQuery query = ChannelQuery.of()
                            .withPredicates(m -> m.reviewRatingStatistics().averageRating().is(2.0))
                            .plusPredicates(m -> m.reviewRatingStatistics().count().is(2))
                            .plusPredicates(m -> m.is(channel));
                    assertEventually(() -> {
                        final List<Channel> results = client().executeBlocking(query).getResults();
                        assertThat(results).hasSize(1);
                        final Channel loadedChannel = results.get(0);
                        assertThat(loadedChannel.getId()).isEqualTo(channel.getId());
                        assertThat(loadedChannel.getReviewRatingStatistics().getCount()).isEqualTo(2);
                    });
                });
            });
        });
    }

    @Test
    public void queryByRole() {
        final ChannelRole channelRole = ChannelRole.PRODUCT_DISTRIBUTION;
        withChannelOfRole(client(), channelRole, channel -> {
            final ChannelQuery query = ChannelQuery.of()
                    .plusPredicates(m -> m.roles().containsAny(singleton(channelRole)))
                    .plusPredicates(m -> m.is(channel));
            final List<Channel> results = client().executeBlocking(query).getResults();
            assertThat(results).hasSize(1);
        });
    }

    @Test
    public void queryByNameAndDescription() {
        final LocalizedString name = randomSlug();
        final LocalizedString description = randomSlug();
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                .withName(name)
                .withDescription(description);
        withChannel(client(), channelDraft, channel -> {
            final ChannelQuery query = ChannelQuery.of()
                    .plusPredicates(m -> m.is(channel))
                    .plusPredicates(m -> m.name().locale(ENGLISH).is(name.get(ENGLISH)))
                    .plusPredicates(m -> m.description().locale(ENGLISH).is(description.get(ENGLISH)));
            final List<Channel> results = client().executeBlocking(query).getResults();
            assertThat(results).hasSize(1);
        });
    }

    @Ignore
    @Test
    public void queryByGeoLocation() {
        final Point geoLocation = Point.of(52.0, 40.0);
        final ChannelDraft channelDraft =
                ChannelDraft.of(randomKey())
                        .withGeoLocation(geoLocation);
        withChannel(client(), channelDraft, channel -> {
            final ChannelQuery withinCircleQuery = ChannelQuery.of()
                    .plusPredicates(m -> m.is(channel))
                    .plusPredicates(m -> m.geoLocation().withinCircle(geoLocation, 1.0));
            final List<Channel> withinCircleResults = client().executeBlocking(withinCircleQuery).getResults();
            assertThat(withinCircleResults).hasSize(1);
            assertThat(withinCircleResults.get(0)).isEqualTo(channel);

            final ChannelQuery isPresentQuery = ChannelQuery.of()
                    .plusPredicates(m -> m.is(channel))
                    .plusPredicates(m -> m.geoLocation().isPresent());
            final List<Channel> isPresentResults = client().executeBlocking(isPresentQuery).getResults();
            assertThat(isPresentResults).hasSize(1);
            assertThat(isPresentResults.get(0)).isEqualTo(channel);
        });
    }
}