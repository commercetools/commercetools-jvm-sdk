package io.sphere.sdk.messages.queries;

import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.DeliveryAddedMessage;
import io.sphere.sdk.orders.messages.ReturnInfoAddedMessage;
import io.sphere.sdk.orders.messages.SimpleOrderMessage;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.messages.ProductCreatedMessage;
import io.sphere.sdk.products.messages.ProductPublishedMessage;
import io.sphere.sdk.products.messages.SimpleProductMessage;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrderAndReturnInfo;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageQueryIntegrationTest extends IntegrationTest {

    @Test
    public void convertAfterQueryToSpecificMessageClasses() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource());
            assertEventually(() -> {
                final List<Message> results = client().executeBlocking(query).getResults();

                final Optional<Message> returnInfoAddedUntypedMessage = results.stream()
                        .filter(m -> {
                            final String messageType = ReturnInfoAddedMessage.MESSAGE_TYPE;
                            return m.getType().equals(messageType);
                        })
                        .findFirst();

                assertThat(returnInfoAddedUntypedMessage).isPresent();
                final ReturnInfoAddedMessage returnInfoAddedMessage =
                        returnInfoAddedUntypedMessage.get().as(ReturnInfoAddedMessage.class);

                assertThat(order.getReturnInfo()).contains(returnInfoAddedMessage.getReturnInfo());
                final Order expandedOrder = returnInfoAddedMessage.getResource().getObj();
                assertThat(expandedOrder.getCreatedAt()).isEqualTo(order.getCreatedAt());
            });

            return order;
        }));
    }

    @Test
    public void queryForASpecificResource() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<SimpleOrderMessage> query = MessageQuery.of()
                    .withPredicates(m -> m.resource().id().is(order.getId()))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .forMessageType(SimpleOrderMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final List<SimpleOrderMessage> results = client().executeBlocking(query).getResults();

                final Optional<Order> orderOptional = Optional.ofNullable(results.get(0).getResource().getObj());
                assertThat(orderOptional.map(o -> o.getCreatedAt())).contains(order.getCreatedAt());
            });

            return order;
        }));
    }

    @Test
    public void queryForMultipleSpecificMessageClasses() throws Exception {
        ProductFixtures.withUpdateableProduct(client(), product -> {
            //create some messages apart from ProductCreatedMessage
            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final Product unpublishedProduct = client().executeBlocking(ProductUpdateCommand.of(publishedProduct, Unpublish.of()));

            //these are the classes which are expected as message
            final List<MessageDerivateHint<? extends Message>> messageHints =
                    asList(ProductCreatedMessage.MESSAGE_HINT,
                            ProductPublishedMessage.MESSAGE_HINT,
                            //as fallback for other product messages
                            SimpleProductMessage.MESSAGE_HINT);

            final Query<Message> query = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .withSort(m -> m.type().sort().asc())
                    .withExpansionPaths(m -> m.resource())
                    .forMessageTypes(messageHints);

            assertEventually(Duration.ofSeconds(45), Duration.ofMillis(300), () -> {
                final List<Message> messages = client().executeBlocking(query).getResults();
                assertThat(messages).hasSize(3);
                assertThat(messages.get(0)).isInstanceOf(ProductCreatedMessage.class);
                assertThat(messages.get(1)).isInstanceOf(ProductPublishedMessage.class);
                assertThat(messages.get(2)).isInstanceOf(SimpleProductMessage.class);

                //use some kind of pattern matching
                messages.stream()
                        .forEachOrdered(message -> {
                            if (message instanceof ProductCreatedMessage) {
                                final ProductCreatedMessage m = (ProductCreatedMessage) message;
                                assertThat(m.getResource()).isEqualTo(product.toReference());
                            } else if (message instanceof ProductPublishedMessage) {
                                final ProductPublishedMessage m = (ProductPublishedMessage) message;
                                assertThat(m.getResource()).isEqualTo(product.toReference());
                            } else if (message instanceof SimpleProductMessage) {
                                final SimpleProductMessage m = (SimpleProductMessage) message;
                                assertThat(m.getResource()).isEqualTo(product.toReference());
                                assertThat(m.getType()).isEqualTo("ProductUnpublished");
                            } else {
                                throw new RuntimeException("unexpected type of " + message);
                            }
                        });
            });
            return unpublishedProduct;
        });
    }

    @Test
    public void queryForASpecificMessage() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<ReturnInfoAddedMessage> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.resource().is(order))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .forMessageType(ReturnInfoAddedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ReturnInfoAddedMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<ReturnInfoAddedMessage> message = pagedQueryResult.head();

                assertThat(message).isPresent();
                assertThat(message.get().getReturnInfo()).isEqualTo(returnInfo);
                assertThat(message.get().getResource().getObj()).isNotNull();
                assertThat(message.get().getResource().getId()).isEqualTo(order.getId());
            });

            return order;
        }));
    }

    @Test
    public void messageGetPayload() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final Query<Message> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.type().is(ReturnInfoAddedMessage.MESSAGE_HINT).and(m.resource().is(order)))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L);

            assertEventually(() -> {
                final PagedQueryResult<Message> pagedQueryResult = client().executeBlocking(query);
                final Optional<Message> message = pagedQueryResult.head();

                assertThat(message).isPresent();
                final String fetchedItemId = message.get().getPayload().get("returnInfo").get("items").get(0).get("id").asText();
                final String actualItemId = returnInfo.getItems().get(0).getId();
                assertThat(fetchedItemId).isEqualTo(actualItemId).isNotNull();
            });

            return order;
        }));
    }

    @Test
    public void queryForAllMessages() throws Exception {
        withOrderAndReturnInfo(client(), ((order, returnInfo) -> {
            final MessageQuery query = MessageQuery.of()
                    //example predicate to fetch for a specific message type
                    .withPredicates(m -> m.type().is("ReturnInfoAdded").and(m.resource().is(order)))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L);

            assertEventually(() -> {
                final Optional<Message> message = client().executeBlocking(query).head();

                assertThat(message).isPresent();
                assertThat(message.get().getResource().getObj()).isNotNull();
                assertThat(message.get().getResource()).isEqualTo(order.toReference());
                assertThat(message.get().getResource().getId()).isEqualTo(order.getId());
            });

            return order;
        }));
    }
}