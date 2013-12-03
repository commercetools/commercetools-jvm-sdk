## v0.50.0

##### Play SDK
* remove method sphere.InventoryService.byId
* remove method sphere.InventoryService.byProductVariant

##### Java Client
* remove method io.sphere.client.shop.InventoryService.byId
* remove method io.sphere.client.shop.InventoryService.byProductVariant

## v0.49.0

##### Play SDK


##### Java Client

* Products can be found by using the query API.
* Categories can be obtained as sorted list for one level.
* CustomObjectNotFound sphere error type removed (ResourceNotFound is returned instead).
* Add InvalidSubject error.
* Bugfix for Order changes API.

## v0.48.0

##### Play SDK

##### Java Client

* Password reset tokens can have a custom expire time.

## v0.47.0

##### Play SDK

* expand customer groups for ```sphere.currentCustomer()```

##### Java Client

## v0.46.0

##### Play SDK


##### Java Client

* Add support for sorting products by name.

## v0.45.0

##### Play SDK

* Add the `CurrentCart.orderCart()` method that does not require a snapshot id.

##### Java Client

* Add custom line items to carts and orders.
* Add toString method to many classes.

## v0.44.0

##### Play SDK

There is now `FacetExpr`, analogous to `FilterExpr` which provides
a nice little DSL for creating facet expressions.

Current version:
`new FacetExpressions.Terms("color")`
`new FacetExpressions.MoneyAttribute.RangesMultiSelect("cost", list(r1, r2), r2)`

New version:
`FacetExpr.stringAttribute("color").terms()`
`FacetExpr.moneyAttribute("cost").rangesMultiSelect(r1, r2).selected(r2)`

With the DSL, it's more clear what each parameter means also
also the user does not have to create the expressions using `new`
which was a bit confusing. Also, filter expressions already have
a DSL like this.

The DSL is implemented as a thin layer on top of the expression
classes, so the examples shown above are simply syntax sugar
for the code shown below them.

##### Java Client

* Categories are sorted by the order hint.
* Add `taxRate` to the `ShippingInfo`.


## v0.41.0

* Improvements to the release process

### v0.40.0

API

* Add [custom object storage](http://sphere.io/dev/http-api-projects-custom-objects.html)

### v0.39.0

API

* Make category slugs mandatory and allow querying for them

### v0.38.0

API

* Add `LocalizedString#get(Locales...)` which allows you implement a fallback
  logic for localized strings
* `LocalizedString#get(locale)` no longer returns a locale other than the
  requested one. Use `get(locales...)` to implement a fallback logic.
* Change order of arguments in `ProductService#bySlug` to bring it in line
  with the various filter methods in the same interface

Fixes

* Correctly parse locales set in `application.conf`

Play SDK

* Add `products().bySlug(slug)` that uses the configured default locale for
  slug query

### v0.37.0

API

* Pass default locale through to `ProductService` saving the developer to
  specify the locale multiple times

### v0.36.1

Fixes

* Make `LineItem.productName` a `LocalizedString`

### v0.36

API

* The Java client can now handle i18n for the name, description and the meta
  attributes of `Category` and `Product` instances

Play SDK

* Default locale can be set in `application.conf`'s `sphere.defaultLocale`

### v0.35

##### General

API

Extended errors handling:
* `createOrder` now throws `OutOfStockException` / `PriceChangedException`.

* Methods `byCustomer`, `byProduct` renamed to `forCustomer`, `forProduct`.

FIX

* Fixed a bug that caused `CustomerService.createEmailVerificationToken` to fail with 400 Bad Request.
* Fixed request body encoding problem.

##### Play SDK

API

* Renamed `checkoutSnapshotId` to `cartSnapshotId`.
* `Sphere.loginAsync` and `Sphere.signupAsync` return a `SphereResult<SignInResult>`. The `login` method still returns
  a boolean because the failure case is a very common one.
* Add helper Facet.getResult(searchResult) for all facet components.

Extended error handling:
* `CurrentCart.createOrder[Async]` now throws a `CartModifiedException`

##### Java client

* Simplified login and signup APIs.
* Introduced a DSL for constructing search filter expressions.

### v0.34

##### General

__New error handling__: Errors returned by the Sphere Projects Web Service are now properly parsed and reported.
See the [API docs](http://sphere.io/dev/HTTP_API_Projects_Errors.html) for reference.

##### Play SDK

API

* `Sphere.products`, `Sphere.orders` etc. are now methods instead of public final fields to allow for easier mocking.
* New error handling: the return type of all async methods that modify backend state changed
from `Promise<T>` to `Promise<SphereResult<T>>`.

##### Java client

API

* Added a method `SphereClient.shutdown` that releases all resources acquired by the SphereClient.
This closes all HTTP connections and shuts down all internal thread pools.
* New error handling: the return type of all async methods that modify backend state changed
from `ListenableFuture<T>` to `ListenableFuture<SphereResult<T>>`.

MISC

* `Image.getSize` falls back to the original image if requested size is not available.
* Updated Async HTTP client to 1.7.16
* Updated Joda time to 2.2
* Updated Joda convert to 1.3.1
* Updated Jackson to 1.9.10
* Removed dependency on Apache commons codec

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

* Removed dependency on Scala standard library.)`
