package de.commercetools.sphere.client

import de.commercetools.internal._
import de.commercetools.sphere.client.model.SearchResult
import de.commercetools.sphere.client.model.SearchResult
import de.commercetools.sphere.client.shop._
import de.commercetools.sphere.client.util._
import de.commercetools.sphere.client.model.SearchResult
import org.codehaus.jackson.`type`.TypeReference
import oauth.ClientCredentials

object Mocks {
  val endpoints = new ProjectEndpoints("")
  
  def mockProducts(responseBody: String, status: Int = 200) =
    new ProductsImpl(
      mockRequestFactory(responseBody, status),
      endpoints)

  def mockCategories(responseBody: String, status: Int = 200) = 
    new CategoriesImpl(
      mockRequestFactory(responseBody, status),
      endpoints)

  def mockCarts(responseBody: String, status: Int = 200) =
    new CartsImpl(
      mockRequestFactory(responseBody, status),
      endpoints)

  /** Mocks the backend by returning prepared responses.
    * The fake string response is still processed using the default (not mocked) response parsing logic. */
  def mockRequestFactory(responseBody: String, status: Int) = new RequestFactory {

    def createGetRequest[T](url: String)  = mockRequestHolder[T](responseBody, status)

    def createPostRequest[T](url: String) = mockRequestHolder[T](responseBody, status)

    def createQueryRequest[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      mockRequestBuilder(responseBody, status, jsonParserTypeRef)

    def createSearchRequest[T](fullTextQuery: String, url: String, jsonParserTypeRef: TypeReference[SearchResult[T]]) =
      mockSearchRequestBuilder(responseBody, status, jsonParserTypeRef)

    def createCommandRequest[T](url: String, command: Command) =
      mockRequestHolder[T](responseBody, status)
  }

  def mockRequestHolder[T](responseBody: String, status: Int) = new MockRequestHolder[T](status, responseBody)

  def mockRequestBuilder[T](responseBody: String, status: Int, jsonParserTypeRef: TypeReference[T]) =
    new RequestBuilderImpl(mockRequestHolder[T](responseBody, status), jsonParserTypeRef)

  def mockSearchRequestBuilder[T](responseBody: String, status: Int, jsonParserTypeRef: TypeReference[SearchResult[T]]) =
    new SearchRequestBuilderImpl("", mockRequestHolder[SearchResult[T]](responseBody, status), jsonParserTypeRef)
}
