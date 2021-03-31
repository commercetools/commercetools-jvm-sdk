package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.*;
import io.sphere.sdk.customers.commands.updateactions.*;
import io.sphere.sdk.customers.messages.*;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.AddressBuilder;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import io.sphere.sdk.types.TypeFixtures;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import static com.neovisionaries.i18n.CountryCode.FR;
import static io.sphere.sdk.customergroups.CustomerGroupFixtures.withB2cCustomerGroup;
import static io.sphere.sdk.customers.CustomerFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CustomerUpdateCommandIntegrationTest extends CustomerIntegrationTest {

    @Test
    public void setKey() {
        withCustomer(client(), customer -> {
            String newKey = randomKey();
            assertThat(customer.getKey()).isNotEqualTo(newKey);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetKey.of(newKey)));
            assertThat(updatedCustomer.getKey()).isEqualTo(newKey);
        });
    }

    @Test
    public void setFirstName() {
        withCustomer(client(), customer -> {
            String newFirstName = "Jane";
            assertThat(customer.getFirstName()).isNotEqualTo(newFirstName);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetFirstName.of(newFirstName)));
            assertThat(updatedCustomer.getFirstName()).isEqualTo(newFirstName);
        });
    }

    @Test
    public void setLastName() {
        withCustomer(client(), customer -> {
            String newLastName = "Doe";
            assertThat(customer.getLastName()).isNotEqualTo(newLastName);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetLastName.of(newLastName)));
            assertThat(updatedCustomer.getLastName()).isEqualTo(newLastName);
        });
    }

    @Test
    public void setMiddleName() {
        withCustomer(client(), customer -> {
            String newMiddleName = "Petronella";
            assertThat(customer.getMiddleName()).isNotEqualTo(newMiddleName);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetMiddleName.of(newMiddleName)));
            assertThat(updatedCustomer.getMiddleName()).isEqualTo(newMiddleName);
        });
    }

    @Test
    public void setSalutation() {
        withCustomer(client(), customer -> {
            String newSalutation = "Dear Dr.";
            assertThat(customer.getSalutation()).isNotEqualTo(newSalutation);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetSalutation.of(newSalutation)));
            assertThat(updatedCustomer.getSalutation()).isEqualTo(newSalutation);
        });
    }

    @Test
    public void setTitle() {
        withCustomer(client(), customer -> {
            String newTitle = "Dr. Eng.";
            assertThat(customer.getTitle()).isNotEqualTo(newTitle);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetTitle.of(newTitle)));
            assertThat(updatedCustomer.getTitle()).isEqualTo(newTitle);
        });
    }

    @Test
    public void changeName() throws Exception {
        withCustomer(client(), customer -> {
            final CustomerName newName = CustomerName.ofTitleFirstAndLastName("Mister", "John", "Smith");
            assertThat(customer.getName()).isNotEqualTo(newName);
            final Customer updatedCustomer = client().executeBlocking(new VrapRequestDecorator<>(CustomerUpdateCommand.of(customer, ChangeName.of(newName)), "request"));
            assertThat(updatedCustomer.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void nameUpdates() throws Exception {
        withCustomer(client(), customer -> {
            final List<UpdateAction<Customer>> updateActions = asList(
                    SetTitle.of("Dr."),
                    SetFirstName.of("Petronella"),
                    SetMiddleName.of("M."),
                    SetLastName.of("Osgood")
            );
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, updateActions));
            assertThat(updatedCustomer.getTitle()).isEqualTo("Dr.");
            assertThat(updatedCustomer.getFirstName()).isEqualTo("Petronella");
            assertThat(updatedCustomer.getMiddleName()).isEqualTo("M.");
            assertThat(updatedCustomer.getLastName()).isEqualTo("Osgood");
        });
    }

    @Test
    public void changeEmail() throws Exception {
        withCustomer(client(), customer -> {
            final String newEmail = randomEmail(CustomerUpdateCommandIntegrationTest.class);
            assertThat(customer.getEmail()).isNotEqualTo(newEmail);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, ChangeEmail.of(newEmail)));
            assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);

            Query<CustomerEmailChangedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerEmailChangedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerEmailChangedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerEmailChangedMessage message = queryResult.head().get();
                assertThat(message.getEmail()).isEqualTo(newEmail);
            });
        });
    }

    @Test
    public void changeSalutation() throws Exception {
        withCustomer(client(), customer -> {
            final String newSalutation = "Hello";
            assertThat(customer.getSalutation()).isNotEqualTo(newSalutation);
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetSalutation.of(newSalutation)));
            assertThat(updatedCustomer.getSalutation()).isEqualTo(newSalutation);
        });
    }

    @Test
    public void addAddress() throws Exception {
        withCustomer(client(), customer -> {
            final String city = "addAddress";
            final Address newAddress = AddressBuilder.of(DE).city(city).build();
            final Predicate<Address> containsNewAddressPredicate = a -> a.getCity().equals(city);
            assertThat(customer.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate))
                    .overridingErrorMessage("address is not present, yet")
                    .isFalse();
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, AddAddress.of(newAddress)));
            assertThat(updatedCustomer.getAddresses().stream()
                    .anyMatch(containsNewAddressPredicate)).isTrue();

            Query<CustomerAddressAddedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerAddressAddedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerAddressAddedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
            });
        });
    }

    @Test
    public void changeAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final String city = "new city";
            assertThat(customer.getAddresses()).hasSize(1);
            assertThat(customer.getAddresses().get(0).getCity()).isNotEqualTo(city);

            final Address oldAddress = customer.getAddresses().get(0);
            assertThat(oldAddress.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();

            final Address newAddress = oldAddress.withCity(city);
            final ChangeAddress updateAction = ChangeAddress.ofOldAddressToNewAddress(oldAddress, newAddress);
            final Customer customerWithReplacedAddress = client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(customerWithReplacedAddress.getAddresses()).hasSize(1);
            assertThat(customerWithReplacedAddress.getAddresses().get(0).getCity()).contains(city);

            Query<CustomerAddressChangedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerAddressChangedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerAddressChangedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerAddressChangedMessage message = queryResult.head().get();
                assertThat(message.getAddress()).isEqualTo(newAddress);
            });
        });
    }

    @Test
    public void removeAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address oldAddress = customer.getAddresses().get(0);
            assertThat(oldAddress.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();

            final RemoveAddress action = RemoveAddress.of(oldAddress);
            final Customer customerWithoutAddresses =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, action));

            assertThat(customerWithoutAddresses.getAddresses()).isEmpty();

            Query<CustomerAddressRemovedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerAddressRemovedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerAddressRemovedMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerAddressRemovedMessage message = queryResult.head().get();
                assertThat(message.getAddress()).isEqualTo(oldAddress);
            });
        });
    }

    @Test
    public void setDefaultShippingAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address address = customer.getAddresses().get(0);
            assertThat(address.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();
            assertThat(customer.getDefaultShippingAddressId()).isNull();
            assertThat(customer.findDefaultShippingAddress()).isEmpty();

            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetDefaultShippingAddress.ofAddress(address)));

            assertThat(updatedCustomer.getDefaultShippingAddressId()).contains(address.getId());
            assertThat(updatedCustomer.findDefaultShippingAddress()).contains(address);
            return updatedCustomer;
        });
    }

    @Test
    public void setAddressCustomType() throws Exception {
        TypeFixtures.withUpdateableType(client(), type -> {
            withCustomerWithOneAddress(client(), customer -> {
                final Address address = customer.getAddresses().get(0);
                final Customer updatedCustomer =
                        client().executeBlocking(CustomerUpdateCommand.of(customer, SetAddressCustomType.ofTypeIdAndObjects(address.getId(), type.getId(), TypeFixtures.STRING_FIELD_NAME, "bar")));
                assertThat(updatedCustomer.findAddressById(address.getId()).get().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar");

                final Customer updatedCustomer2 =
                        client().executeBlocking(CustomerUpdateCommand.of(updatedCustomer, SetAddressCustomType.ofTypeIdAndObjects(address.getId(), type.getId(), TypeFixtures.STRING_FIELD_NAME, "bar2")));
                assertThat(updatedCustomer2.findAddressById(address.getId()).get().getCustomFields().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("bar2");

                return updatedCustomer2;
            });
            return type;
        });
    }

    @Test
    public void setDefaultBillingAddress() throws Exception {
        withCustomerWithOneAddress(client(), customer -> {
            final Address address = customer.getAddresses().get(0);
            assertThat(address.getId())
                    .overridingErrorMessage("only fetched address contains an ID")
                    .isNotNull();
            assertThat(customer.getDefaultBillingAddressId()).isNull();
            assertThat(customer.getDefaultBillingAddress()).isNull();

            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetDefaultBillingAddress.ofAddress(address)));

            assertThat(updatedCustomer.getDefaultBillingAddressId()).contains(address.getId());
            assertThat(updatedCustomer.getDefaultBillingAddress()).isEqualTo(address);
        });
    }

    @Test
    public void setCustomerNumber() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getCustomerNumber()).isNull();

            final String customerNumber = randomString();
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetCustomerNumber.of(customerNumber)));

            assertThat(updatedCustomer.getCustomerNumber()).contains(customerNumber);
        });
    }

    @Test
    public void setExternalId() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getExternalId()).isNull();

            final String externalId = randomString();
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetExternalId.of(externalId)));

            assertThat(updatedCustomer.getExternalId()).isEqualTo(externalId);
        });
    }

    @Test
    public void setCompanyName() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getCompanyName()).isNull();

            final String companyName = "Big coorp";
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetCompanyName.of(companyName)));
            assertThat(updatedCustomer.getCompanyName()).isEqualTo(companyName);

            Query<CustomerCompanyNameSetMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerCompanyNameSetMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerCompanyNameSetMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerCompanyNameSetMessage message = queryResult.head().get();
                assertThat(message.getCompanyName()).isEqualTo(companyName);
            });

        });
    }

    @Test
    public void setVatId() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getVatId()).isNull();

            final String vatId = randomString();
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetVatId.of(vatId)));

            assertThat(updatedCustomer.getVatId()).isEqualTo(vatId);
        });
    }

    @Test
    public void setDateOfBirth() throws Exception {
        withCustomer(client(), customer -> {
            assertThat(customer.getDateOfBirth()).isNull();

            final LocalDate dateOfBirth = LocalDate.now();
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, SetDateOfBirth.of(dateOfBirth)));

            assertThat(updatedCustomer.getDateOfBirth()).isEqualTo(dateOfBirth);

            Query<CustomerDateOfBirthSetMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(customer))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withLimit(1L)
                    .forMessageType(CustomerDateOfBirthSetMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<CustomerDateOfBirthSetMessage> queryResult = client().executeBlocking(messageQuery);
                assertThat(queryResult.head()).isPresent();
                final CustomerDateOfBirthSetMessage message = queryResult.head().get();
                assertThat(message.getDateOfBirth()).isEqualTo(dateOfBirth);
            });
        });
    }

    @Test
    public void setCustomerGroup() throws Exception {
        withB2cCustomerGroup(client(), customerGroup -> {
            withCustomer(client(), customer -> {
                assertThat(customer.getCustomerGroup()).isNull();
                final Customer updateCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetCustomerGroup.of(customerGroup)));
                assertThat(updateCustomer.getCustomerGroup()).isEqualTo(customerGroup.toReference());

                Query<CustomerGroupSetMessage> messageQuery = MessageQuery.of()
                        .withPredicates(m -> m.resource().is(customer))
                        .withSort(m -> m.createdAt().sort().desc())
                        .withLimit(1L)
                        .forMessageType(CustomerGroupSetMessage.MESSAGE_HINT);

                assertEventually(() -> {
                    final PagedQueryResult<CustomerGroupSetMessage> queryResult = client().executeBlocking(messageQuery);
                    assertThat(queryResult.head()).isPresent();
                    final CustomerGroupSetMessage message = queryResult.head().get();
                    assertThat(message.getCustomerGroup()).isEqualTo(customerGroup.toReference());
                });
            });
        });
    }

    @Test
    public void locale() {
        withCustomer(client(), customer -> {
            assertThat(customer.getLocale()).isNull();
            final Customer updatedCustomer = client().executeBlocking(CustomerUpdateCommand.of(customer, SetLocale.of(Locale.GERMAN)));
            assertThat(updatedCustomer.getLocale()).isEqualTo(GERMAN);
        });
    }

    @Test
    public void addShippingAddressId() {
        final List<Address> addresses = asList(Address.of(DE), Address.of(FR));
        final CustomerDraft draft = newCustomerDraft().withAddresses(addresses);
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getShippingAddressIds()).isEmpty();

            final Address addressForShipping = customer.getAddresses().get(1);
            final String addressId = addressForShipping.getId();
            final AddShippingAddressId updateAction = AddShippingAddressId.of(addressId);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getShippingAddressIds()).containsExactly(addressId);
        });
    }

    @Test
    public void addShippingAddressKey() {
        String key = randomKey();
        final List<Address> addresses = asList(Address.of(DE).withKey(key), Address.of(FR).withKey(key));
        final CustomerDraft draft = newCustomerDraft().withAddresses(addresses);
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getShippingAddressIds()).isEmpty();

            final Address addressForShipping = customer.getAddresses().get(1);
            final String addressKey = addressForShipping.getKey();
            final AddShippingAddressId updateAction = AddShippingAddressId.ofKey(addressKey);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getAddresses().get(1).getKey()).isEqualTo(addressKey);
        });
    }

    @Test
    public void addBillingAddressId() {
        final List<Address> addresses = asList(Address.of(DE), Address.of(FR));
        final CustomerDraft draft = newCustomerDraft().withAddresses(addresses);
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getBillingAddressIds()).isEmpty();

            final Address addressForBilling = customer.getAddresses().get(1);
            final String addressId = addressForBilling.getId();
            final AddBillingAddressId updateAction = AddBillingAddressId.of(addressId);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getBillingAddressIds()).containsExactly(addressId);
        });
    }

    @Test
    public void addBillingAddressKey() {
        String key = randomKey();
        final List<Address> addresses = asList(Address.of(DE).withKey(key), Address.of(FR).withKey(key));
        final CustomerDraft draft = newCustomerDraft().withAddresses(addresses);
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getBillingAddressIds()).isEmpty();

            final Address addressForBilling = customer.getAddresses().get(1);
            final String addressKey = addressForBilling.getKey();
            assertThat(addressKey.equals(key));
            final AddBillingAddressId updateAction = AddBillingAddressId.ofKey(addressKey);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getAddresses().get(1).getKey()).isEqualTo(addressKey);
        });
    }

    @Test
    public void removeBillingAddressId() {
        final List<Address> addresses = asList(Address.of(DE), Address.of(FR));
        final CustomerDraft draft = newCustomerDraft()
                .withAddresses(addresses)
                .withBillingAddresses(singletonList(1));
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getBillingAddressIds()).isNotEmpty();

            final String addressId = customer.getBillingAddressIds().get(0);
            final RemoveBillingAddressId updateAction = RemoveBillingAddressId.of(addressId);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getBillingAddressIds()).isEmpty();
        });
    }
    @Test
    public void removeBillingAddressKey() {
        String key = randomKey();
        final List<Address> addresses = asList(Address.of(DE).withKey(key), Address.of(FR).withKey(key));
        final CustomerDraft draft = newCustomerDraft()
                .withAddresses(addresses)
                .withBillingAddresses(singletonList(1));
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getBillingAddressIds()).isNotEmpty();
            assertThat(customer.getAddresses().get(0).getKey().equals(key));

            final RemoveBillingAddressId updateAction = RemoveBillingAddressId.ofKey(key);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getBillingAddressIds()).isEmpty();
        });
    }

    @Test
    public void removeShippingAddressId() {
        final List<Address> addresses = asList(Address.of(DE), Address.of(FR));
        final CustomerDraft draft = newCustomerDraft()
                .withAddresses(addresses)
                .withShippingAddresses(singletonList(1));
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getShippingAddressIds()).isNotEmpty();

            final String addressId = customer.getShippingAddressIds().get(0);
            final RemoveShippingAddressId updateAction = RemoveShippingAddressId.of(addressId);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getShippingAddressIds()).isEmpty();
        });
    }

    @Test
    public void removeShippingAddressKey() {
        String key = randomKey();
        final List<Address> addresses = asList(Address.of(DE).withKey(key), Address.of(FR).withKey(key));
        final CustomerDraft draft = newCustomerDraft()
                .withAddresses(addresses)
                .withShippingAddresses(singletonList(1));
        withCustomer(client(), draft, customer -> {
            assertThat(customer.getShippingAddressIds()).isNotEmpty();
            assertThat(customer.getAddresses().get(0).getKey().equals(key));

            final RemoveShippingAddressId updateAction = RemoveShippingAddressId.ofKey(key);
            final Customer updatedCustomer =
                    client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));

            assertThat(updatedCustomer.getShippingAddressIds()).isEmpty();
        });
    }

    @Test
    public void setStores() {
        StoreFixtures.withStore(client(), store -> {
            CustomerFixtures.withUpdateableCustomer(client(), customer -> {
                ResourceIdentifier<Store> storeResourceIdentifier = ResourceIdentifier.ofKey(store.getKey());
                List<ResourceIdentifier<Store>> stores = new ArrayList<>();
                stores.add(storeResourceIdentifier);
                SetStores updateAction = SetStores.of(stores);
                final Customer updatedCustomer =
                        client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));
                assertThat(updatedCustomer.getStores().stream().anyMatch(storeKeyReference -> storeKeyReference.getKey().equals(storeResourceIdentifier.getKey()))).isTrue();
                return updatedCustomer;
            });
        });
    }

    @Test
    public void addStore() {
        StoreFixtures.withStore(client(), store -> {
            CustomerFixtures.withUpdateableCustomer(client(), customer -> {
                ResourceIdentifier<Store> storeResourceIdentifier = ResourceIdentifier.ofKey(store.getKey());
                AddStore updateAction = AddStore.of(storeResourceIdentifier);
                final Customer updatedCustomer =
                        client().executeBlocking(CustomerUpdateCommand.of(customer, updateAction));
                assertThat(updatedCustomer.getStores().stream().anyMatch(storeKeyReference -> storeKeyReference.getKey().equals(storeResourceIdentifier.getKey()))).isTrue();
                return updatedCustomer;
            });
        });
    }

    @Test
    public void removeStore() {
        StoreFixtures.withStore(client(), store -> {
            CustomerFixtures.withUpdateableCustomer(client(), customer -> {
                ResourceIdentifier<Store> storeResourceIdentifier = ResourceIdentifier.ofKey(store.getKey());
                AddStore addStore = AddStore.of(storeResourceIdentifier);
                Customer updatedCustomer =
                        client().executeBlocking(CustomerUpdateCommand.of(customer, addStore));
                assertThat(updatedCustomer.getStores().stream().anyMatch(storeKeyReference -> storeKeyReference.getKey().equals(storeResourceIdentifier.getKey()))).isTrue();

                RemoveStore removeStore = RemoveStore.of(storeResourceIdentifier);
                updatedCustomer =
                        client().executeBlocking(CustomerUpdateCommand.of(updatedCustomer, removeStore));
                assertThat(updatedCustomer.getStores().stream().noneMatch(storeKeyReference -> storeKeyReference.getKey().equals(storeResourceIdentifier.getKey()))).isTrue();

                return updatedCustomer;
            });
        });
    }
}
