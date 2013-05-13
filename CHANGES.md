### v0.32

##### Play SDK
* API: Config key  `sphere.api.mode` renamed to `sphere.products.mode`. Valid values are `"published"`, `"staged"`.
* API: Config keys `sphere.cartCurrency`, `sphere.cartInventoryMode` are now `sphere.cart.currency`, `sphere.cart.inventoryMode`.
* API: Renamed `checkoutSummaryId` to `checkoutSnapshotId`.
* API: resetPassword() is now a method of CustomerService.
* Removed test dependency on Scalamock.

##### Java client
* API: CustomerService.signup() and signupWithCart() now accept a CustomerName object.
* FIX: Customer update actions now match the backend by referring to addresses by ids.
* Removed dependency on Scala standard library.
* Removed test dependency on Scalamock.