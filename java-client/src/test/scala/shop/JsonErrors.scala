package io.sphere.client
package shop

object JsonErrors {
  private def errorResponse(errors: String*): String =
    """{
      "statusCode": 400,
      "message": "First error message",
      "errors": [""" +
        errors.mkString(", ") +
      """]
    }"""

  val outOfStock = errorResponse(
    """{"code":"OutOfStock", "message": "Some line items are out of stock.", "lineItems": ["l1", "l2"]}""")

  val priceChanged = errorResponse(
    """{"code":"PriceChanged", "message": "The price of the cart has changed.", "lineItems": ["l3", "l4"]}""")

  val priceChangedAndOutOfStock = errorResponse(
    """{"code":"PriceChanged", "message": "The price of the cart has changed.", "lineItems": ["l3", "l4"]}""",
    """{"code":"OutOfStock", "message": "Some line items are out of stock.", "lineItems": ["l1", "l2"]}""")

  val invalidPassword = errorResponse(
    """{"code":"InvalidInput", "message": "Invalid input."}""")

  val emailAlreadyTaken = errorResponse(
    """{"code":"DuplicateField", "message": "Duplicate field: 'email'.", "field": "email", "duplicateValue": "some@example.com"}""")
}
