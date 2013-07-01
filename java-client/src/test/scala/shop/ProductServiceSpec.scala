package io.sphere.client
package shop

import java.util.Locale
import scala.collection.JavaConverters._

import io.sphere.client.FakeResponse
import io.sphere.client.filters.FilterExpr
import io.sphere.client.shop.model._
import io.sphere.internal.request._
import JsonResponses._
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat
import org.scalatest._
import TestUtil._

class ProductServiceSpec extends WordSpec with MustMatchers {

  val noProductsClient = MockSphereClient.create(productsResponse = FakeResponse("{}"))

  val EN = Locale.ENGLISH

  val oneProductClient: SphereClient = {
    val productsJson = """{
      "offset" : 0,
      "count" : 1,
      "total" : 1,
      "results" : [ %s ]
    }""" format (
      productJson("prod1", List()))
    MockSphereClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }

  val twoProductsClient: SphereClient = {
    val productsJson = """{
      "offset" : 0,
      "count" :2,
      "total" : 2,
      "results" : [ %s, %s ]
    }""" format (
      productJson("prod111", List("cat-sports", "cat-Deleted", "cat-V6")),
      productJson("prod222", List()))
    MockSphereClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }

  "Parse zero products" in {
    val searchResult = noProductsClient.products.all(EN).fetch
    searchResult.getCount must be(0)
    searchResult.getOffset must be(0)
    searchResult.getResults.size must be(0)
  }

  "Parse price" in {
    val prod = oneProductClient.products.all(EN).fetch.getResults.get(0)
    prod.getPrice.getValue must be (eur(17000))
    prod.getMasterVariant.getPrice.getValue must be (eur(17000))
  }

  "Parse string attributes" in {
    val prod = twoProductsClient.products.all(EN).fetch.getResults.get(0)
    prod.getAttribute("tags") must be (new Attribute("tags", "convertible"))
    prod.getString("tags") must be ("convertible")
  }

  "Parse number attributes" in {
    val prod = twoProductsClient.products.all(EN).fetch.getResults.get(0)
    prod.getInt("numberAttributeWhole") must be (1)
    prod.getDouble("numberAttributeFractional") must be (1.2)
  }

  "Parse money attributes" in {
    val prod = twoProductsClient.products.all(EN).fetch.getResults.get(0)
    prod.getMoney("cost") must be (eur(16500))
    //prod.getMoney("cost").getCurrencyCode must be ("EUR")
    //prod.getMoney("cost").getAmount must  be (new java.math.BigDecimal(16500.0))
  }

  "Parse date/time attributes" in {
    val prod = twoProductsClient.products.all(EN).fetch.getResults.get(0)
    prod.getDateTime("dateTimeAttribute").withZone(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime) must be ("2013-06-24T16:54:10.000Z")
  }

  "Parse variants" in {
    val prod = oneProductClient.products.all(EN).fetch.getResults.get(0)
    prod.getVariants.size must be (2)
    val variant = prod.getVariants.asList.get(1)
    variant.getPrice.getValue must be (eur(19500))
    variant.getString("tags") must be ("convertible")
    variant.getInt("numberAttributeWhole") must be (-2)
    variant.getDouble("numberAttributeFractional") must be (-2.2)
    variant.getMoney("cost") must be (eur(23500))
    variant.getDateTime("dateTimeAttribute").withZone(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime) must be ("2014-06-24T16:54:10.000Z")
  }

  "Parse two products and resolve categories" in {
    val searchResult = twoProductsClient.products.all(EN).fetch
    searchResult.getCount must be (2)
    searchResult.getOffset must be (0)
    searchResult.getResults.size must be (2)
    val prod1 = searchResult.getResults.get (0)
    prod1.getCategories.asScala.toList.map(_.getName) must be (List("Sports cars", "V6"))
    val prod2 = searchResult.getResults.get (1)
    prod2.getCategories.size must be (0)
  }

  "Parse images" in {
    val prod = oneProductClient.products.all(EN).fetch.getResults.get(0)
    prod.getMasterVariant.getImages.size must be (2)
    prod.getMasterVariant.getFeaturedImage.getLabel must be ("Snowboard")
    prod.getImages.size must be (2)
    prod.getFeaturedImage must be (new Image("http://a016.rackcdn.com/snowboard.jpeg", "Snowboard", new Dimensions(500, 321)))

    prod.getImages.get(0) must be (new Image("http://a016.rackcdn.com/snowboard.jpeg", "Snowboard", new Dimensions(500, 321)))
    prod.getImages.get(1) must be (new Image("http://a016.rackcdn.com/snowboard-bigair.jpeg", "", new Dimensions(700, 421)))
    prod.getVariants.asList.get(1).getImages.get(0) must be(
      new Image("http://a016.rackcdn.com/snowboard-jump.jpeg", "Snowboard - jump over the BMW", new Dimensions(45, 170)))
  }

  "Parse product by slug" in {
    val optionalProduct = oneProductClient.products.bySlug("bmw_116_convertible_4_door", EN).fetch
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }

  "Parse empty result" in {
    val optionalProduct = noProductsClient.products.bySlug("bmw_116", EN).fetch
    optionalProduct.isPresent must be (false)
  }

  "Get multiple products by slug" in {
    val optionalProduct = twoProductsClient.products.bySlug("bmw_116_convertible_4_door", EN).fetch
    // if there are multiple products with the same slug (should normally not happen), return the first one
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }

  "Set currentPage and totalPages" in {
    val searchResult = twoProductsClient.products.all(EN).fetch
    searchResult.getCurrentPage must be (0)
    searchResult.getTotalPages must be (1)
  }

  "Get product by slug" in {
    val reqBySlug = MockSphereClient.create(apiMode = ApiMode.Staged).products.bySlug("slug-123", EN)
    params(asFetchReqImpl(reqBySlug)) must be (Map("where" -> "slug%28en%3D%22slug-123%22%29", "staged" -> "true"))
  }

  "Set search API query params" in {
    val searchRequestAsc = noProductsClient.products.all(EN).sort(ProductSort.price.asc)
    asImpl(searchRequestAsc).getUrl must startWith ("/product-projections/search")
    params(asImpl(searchRequestAsc)) must be (Map("sort" -> "price+asc", "staged" -> "true"))

    val searchRequestDesc = noProductsClient.products.all(EN).sort(ProductSort.price.desc)
    asImpl(searchRequestDesc).getUrl must startWith ("/product-projections/search")
    params(asImpl(searchRequestDesc)) must be (Map("sort" -> "price+desc", "staged" -> "true"))

    val searchRequestRelevance = noProductsClient.products.all(EN).sort(ProductSort.relevance)
    asImpl(searchRequestRelevance).getUrl must startWith ("/product-projections/search")
    params(asImpl(searchRequestRelevance)) must be (Map("staged" -> "true"))
  }

  "Set filter params" in {
    val searchRequestPrice = noProductsClient.products.
      filter(EN, FilterExpr.price.atLeast(new java.math.BigDecimal(25.5))).
      sort(ProductSort.price.asc)
    params(asImpl(searchRequestPrice)) must be (Map(
      "filter.query" -> "variants.price.centAmount%3Arange%282550+to+*%29", "sort" -> "price+asc", "staged" -> "true"))
  }

  "Set API mode" in {
    val reqStagingSearch = MockSphereClient.create(apiMode = ApiMode.Staged).products.all(EN)
    asImpl(reqStagingSearch).getUrl must startWith ("/product-projections")
    params(asImpl(reqStagingSearch)) must be (Map("staged" -> "true"))

    val reqLiveSearch = MockSphereClient.create(apiMode = ApiMode.Published).products.all(EN)
    params(asImpl(reqLiveSearch)) must be (Map("staged" -> "false"))

    val reqStagingById = MockSphereClient.create(apiMode = ApiMode.Staged).products.byId("123")
    asImpl(reqStagingById).getUrl must startWith ("/product-projections/123")
    params(asImpl(reqStagingById)) must be (Map("staged" -> "true"))

    val reqLiveById = MockSphereClient.create(apiMode = ApiMode.Published).products.byId("123")
    params(asImpl(reqLiveById)) must be (Map("staged" -> "false"))

    val reqStagingBySlug = MockSphereClient.create(apiMode = ApiMode.Staged).products.bySlug("slug-123", EN)
    asFetchReqImpl(reqStagingBySlug).getUrl must startWith ("/product-projections")
    params(asFetchReqImpl(reqStagingBySlug)) must be (Map("where" -> "slug%28en%3D%22slug-123%22%29", "staged" -> "true"))

    val reqLiveBySlug = MockSphereClient.create(apiMode = ApiMode.Published).products.bySlug("slug-123", EN)
    params(asFetchReqImpl(reqLiveBySlug)) must be (Map("where" -> "slug%28en%3D%22slug-123%22%29", "staged" -> "false"))
  }

  // ------------------------
  // Assertion helpers
  // ------------------------

  def getRequestHolder(req: Object): TestableRequestHolder =
    req.asInstanceOf[TestableRequest].getRequestHolder

  def asImpl(req: SearchRequest[Product]): TestableRequestHolder =
    getRequestHolder(req.asInstanceOf[ProductSearchRequest].getUnderlyingRequest)

  def asImpl(req: FetchRequest[Product]): TestableRequestHolder =
    getRequestHolder(req.asInstanceOf[ProductFetchRequest].getUnderlyingRequest)

  def asFetchReqImpl(req: FetchRequest[Product]): TestableRequestHolder =
      getRequestHolder(req.asInstanceOf[ProductFetchRequest].getUnderlyingRequest.
        asInstanceOf[FetchRequestBasedOnQuery[Product]].getUnderlyingQueryRequest)

  /** Parses query params from url. */
  def params(req: TestableRequestHolder): Map[String, String] = {
    val queryString = req.getUrl.substring(req.getUrl.indexOf("?") + 1)
    queryString.split("&").map { keyValue =>
      keyValue.split("=") match {
        case Array(key, value) => (key, value)
        case _ => throw new IllegalArgumentException("Malformed URL")
      }
    }.toMap
  }
}
