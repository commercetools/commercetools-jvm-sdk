package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CustomerUpdateCommand extends UpdateCommandDslImpl<Customer> {
 public CustomerUpdateCommand(final Versioned<Customer> versioned, final List<UpdateAction<Customer>> updateActions) {
  super(versioned, updateActions, CustomersEndpoint.ENDPOINT);
 }

 public CustomerUpdateCommand(final Versioned<Customer> versioned, final UpdateAction<Customer> updateAction) {
  this(versioned, asList(updateAction));
 }
}
