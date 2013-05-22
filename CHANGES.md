### v0.34

##### General

__New error handling__: Error returned by the Sphere Projects Web Service are now properly parsed and reported.
See the [API docs](http://sphere.io/dev/HTTP_API_Projects_Errors.html) for reference.

##### Play SDK

API

* `Sphere.products`, `Sphere.orders` etc. are now methods instead of public final fields to allow for easier mocking.
* New error handling: The return type of all async methods that modify backend state changed
from `Promise<T>` to `Promise<SphereResult<T>`.

##### Java client

API

* Add method `SphereClient.shutdown` that releases all resources acquired by the SphereClient.
This closes all HTTP connections and shuts down all internal thread pools.
* New error handling: The return type of all async methods that modify backend state changed
from `ListenableFuture<T>` to `ListenableFuture<SphereResult<T>`.

MISC

* `Image.getSize` falls back to the original image if requested size is not available.

### v0.33

##### General

API

* All service methods that used to accept the `(id, version)` pair now accept a `VersionedId`.

##### Play SDK

API

* Renamed `CurrentCustomer.updateCustomer` to `CurrentCustomer.update`.
* Renamed `CurrentCart.unsetShippingAddress[Async]` to `clearShippingAddress[Async]`.
* Merged the class `SphereClient` into `Sphere`.
* All service methods that modify backend state now have two versions: synchronous and asynchronous. This is to
make calling `service.update(...)` less error prone: previously such call would do nothing unless you said
`service.update(...).execute()`.

##### Java client

API

* Changed the type of variant id from `String` to `int`.
* Annotated all data object methods that are guaranteed to never return null with `@Nonnull`.
* Added a `getIdAndVersion` method to all versioned data objects. This method returns a `VersionedId`, which is an id plus version.
Removed the `getVersion` method, but kept `getId` for convenience as it is often needed.
* Removed `SphereClient` and `AppClient` that don't have a good use case yet.
* Renamed `ShopClient` to `SphereClient`.
* Added `LineItemContainer.customerEmail`.
* Added `Customer.getAddressById`
* Order now has a `getCurrency` method.
* Renamed `OrderService.orderCart` to `createOrder`.
* Removed methods to get individual parts of Customer's name. Use `Customer.getName`.
* Removed `ScaledImage.getScaleRatio`, added 'Image.isSizeAvailable(size)'.
* Renamed `[Comment|Review]Update.setAuthorName` to `setAuthor`.
* Renamed `[Comment|Review].getAuthorName` to `getAuthor`.
* Renamed `CustomerUpdate.unsetDefault[Shipping|Billing]Address` to `clearDefault[Shipping|Billing]Address`.

MISC

* Improved Javadoc

### v0.32

##### General

MISC

* Removed test dependency on Scalamock.


##### Play SDK

API

* Config key  `sphere.api.mode` renamed to `sphere.products.mode`. Valid values are `"published"`, `"staged"`.
* Config keys `sphere.cartCurrency`, `sphere.cartInventoryMode` are now `sphere.cart.currency`, `sphere.cart.inventoryMode`.
* Renamed `checkoutSummaryId` to `checkoutSnapshotId`.
* `resetPassword1 is now a method of CustomerService.

##### Java client

API

* `CustomerService.signup()` and `signupWithCart()` now accept a `CustomerName` object.

FIX

* Customer update actions now match the backend by referring to addresses by ids.

MISC

* Removed dependency on Scala standard library.