package de.commercetools.sphere.client
package shop

import org.scalatest.{Tag, WordSpec}
import org.scalatest.matchers.MustMatchers
import JsonTestObjects._
import collection.JavaConverters._
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.DateTimeZone
import de.commercetools.internal.request.{TestableRequestHolder, TestableRequest, ProductSearchRequest, SearchRequestImpl}
import filters.expressions.FilterExpressions

class ProductServiceSpec extends WordSpec with MustMatchers {

  val noProductsClient = MockShopClient.create(productsResponse = FakeResponse("{}"))

  val oneProductClient: ShopClient = {
    val productsJson = """{
      "offset" : 0,
      "count" : 1,
      "total" : 1,
      "results" : [ %s ]
    }""" format (
      productJson("prod1", List()))
    MockShopClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }

  val twoProductsClient: ShopClient = {
    val productsJson = """{
      "offset" : 0,
      "count" :2,
      "total" : 2,
      "results" : [ %s, %s ]
    }""" format (
      productJson("prod111", List("cat-sports", "cat-Deleted", "cat-V6")),
      productJson("prod222", List()))
    MockShopClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }


  "Parse zero products" in {
    val searchResult = noProductsClient.products.all.fetch
    searchResult.getCount must be(0)
    searchResult.getOffset must be(0)
    searchResult.getResults.size must be(0)
  }

  "Parse string attributes" in {
    val prod = twoProductsClient.products.all.fetch.getResults.get(0)
    prod.getString("tags") must be ("convertible")
  }

  "Parse number attributes" in {
    val prod = twoProductsClient.products.all.fetch.getResults.get(0)
    prod.getInt("numberAttributeWhole") must be (1.0)
    prod.getDouble("numberAttributeFractional") must be (1.2)
  }

  "Parse money attributes" in {
    val prod = twoProductsClient.products.all.fetch.getResults.get(0)
    prod.getMoney("cost").getCurrencyCode must be ("EUR")
    prod.getMoney("cost").getAmount must  be (new java.math.BigDecimal(16500.0))
  }

  "Parse date/time attributes" in {
    val prod = twoProductsClient.products.all.fetch.getResults.get(0)
    prod.getDate("dateAttribute").toString(ISODateTimeFormat.date) must be ("2013-06-24")
    prod.getTime("timeAttribute").toString(ISODateTimeFormat.time()) must be ("16:54:10.000")
    prod.getDateTime("dateTimeAttribute").withZone(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime) must be ("2013-06-24T16:54:10.000Z")
  }

  "Parse two products and resolve categories" in {
    val searchResult = twoProductsClient.products.all.fetch
    searchResult.getCount must be (2)
    searchResult.getOffset must be (0)
    searchResult.getResults.size must be (2)
    val prod1 = searchResult.getResults.get (0)
    prod1.getCategories.asScala.toList.map(_.getName) must be (List("Sports cars", "V6"))
    val prod2 = searchResult.getResults.get (1)
    prod2.getCategories.size must be (0)
  }

  "Get product by slug" in {
    val optionalProduct = oneProductClient.products.bySlug("bmw_116_convertible_4_door").fetch
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }

  "Get product by wrong slug" in {
    val optionalProduct = noProductsClient.products.bySlug("bmw_116").fetch
    optionalProduct.isPresent must be (false)
  }

  "Get multiple products by slug" in {
    val optionalProduct = twoProductsClient.products.bySlug("bmw_116_convertible_4_door").fetch
    // if there are multiple products with the same slug (should normally not happen), return the first one
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }

  "Set currentPage and totalPages" in {
    val searchResult = twoProductsClient.products.all.fetch
    searchResult.getCurrentPage must be (0)
    searchResult.getTotalPages must be (1)
  }

  type SphereProduct = de.commercetools.sphere.client.shop.model.Product
  def asImpl(req: SearchRequest[SphereProduct]): TestableRequestHolder = {
    req.asInstanceOf[ProductSearchRequest].getUnderlyingRequest.asInstanceOf[TestableRequest].getRequestHolder
  }

  "Set search API query params" in {
    val searchRequestAsc = noProductsClient.products.all.sort(ProductSort.price.asc)
    asImpl(searchRequestAsc).getUrlWithQueryParams must be ("/product-projections/search?sort=price+asc")

    val searchRequestDesc = noProductsClient.products.all.sort(ProductSort.price.desc)
    asImpl(searchRequestDesc).getUrlWithQueryParams must be ("/product-projections/search?sort=price+desc")

    val searchRequestRelevance = noProductsClient.products.all.sort(ProductSort.relevance)
    asImpl(searchRequestRelevance).getUrlWithQueryParams must be ("/product-projections/search?")
  }

  "Set filter params" in {
    val searchRequestPrice = noProductsClient.products.filter(
      new FilterExpressions.Price.AtLeast(new java.math.BigDecimal(25.5))).sort(ProductSort.price.asc)
    val fullUrl = asImpl(searchRequestPrice).getUrlWithQueryParams
    fullUrl must include ("filter.query=variants.price.centAmount%3Arange%282550+to+*%29")
    fullUrl must include ("sort=price+asc")
  }
}
