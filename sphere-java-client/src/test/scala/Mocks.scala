package de.commercetools.sphere.client

import de.commercetools.sphere.client.shop._
import de.commercetools.sphere.client.util._
import de.commercetools.sphere.client.model.SearchResult
import org.codehaus.jackson.`type`.TypeReference
import oauth.ClientCredentials

object Mocks {
  val endpoints = new ProjectEndpoints("")
  
  val credentials = new ClientCredentials {
    def accessToken: String = "fakeToken"
  }

  def mockProducts(responseBody: String, status: Int = 200) = 
    new DefaultProducts(
      mockRequestBuilderFactory(responseBody, status),
      mockSearchRequestBuilderFactory(responseBody, status),
      endpoints,
      credentials)

  def mockCategories(responseBody: String, status: Int = 200) = 
    new DefaultCategories(
      mockRequestBuilderFactory(responseBody, status),
      endpoints,
      credentials)
  
  def mockRequestBuilderFactory(responseBody: String, status: Int) = new RequestBuilderFactory {
    def create[T](url: String, credentials: ClientCredentials, jsonParserTypeRef: TypeReference[T]): RequestBuilder[T] =
      mockRequestBuilder(responseBody, status, jsonParserTypeRef)
  }

  def mockSearchRequestBuilderFactory(responseBody: String, status: Int) = new SearchRequestBuilderFactory {
    def create[T](fullTextQuery: String,  url: String, credentials: ClientCredentials, jsonParserTypeRef: TypeReference[SearchResult[T]]): SearchRequestBuilder[T] =
      mockSearchRequestBuilder(responseBody, status, jsonParserTypeRef)
  }

  def mockRequestBuilder[T](responseBody: String, status: Int, jsonParserTypeRef: TypeReference[T])
  : RequestBuilder[T] =
    new RequestBuilderImpl(new MockRequestHolder(status, responseBody), jsonParserTypeRef)

  def mockSearchRequestBuilder[T](responseBody: String, status: Int, jsonParserTypeRef: TypeReference[SearchResult[T]])
  : SearchRequestBuilder[T] =
    new SearchRequestBuilderImpl("", new MockRequestHolder(status, responseBody), jsonParserTypeRef)
}
