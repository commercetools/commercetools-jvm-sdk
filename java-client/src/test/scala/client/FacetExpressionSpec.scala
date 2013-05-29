package io.sphere.client

import io.sphere.client.facets.expressions.FacetExpressions._
import io.sphere.client.facets.expressions._
import org.scalatest._
import scala.collection.JavaConverters._
import java.util

class FacetExpressionSpec extends WordSpec with MustMatchers {
  /** Converts QueryParams to a tuples for easier asserts. */
  def params(facet: FacetExpression): List[(String, String)] = {
    facet.createQueryParams().asScala.toList.map(param => (param.getName, param.getValue))
  }
  /** Converts a single QueryParams to a tuple for easier asserts. */
  def param(facet: FacetExpression): (String, String) = {
    val ps = params(facet)
    if (ps.length != 1) sys.error("Facet creates more than one query parameter.")
    ps.head
  }

  private def emptyList = new util.ArrayList[String]()

  "Terms facet" in {
    param(new StringAttribute.Terms("a")) must be ("facet", "a")
    param(new NumberAttribute.Terms("a")) must be ("facet", "a")
    param(new MoneyAttribute.Terms("a")) must be ("facet", "a")
    param(new DateTimeAttribute.Terms("a")) must be ("facet", "a")
    param(new Price.Terms()) must be ("facet", "variants.price.centAmount")
    param(new Categories.Terms()) must be ("facet", "categories.id")
  }

  "StringAttribute facets" should {
    "StringAttribute.TermsMultiSelect" in {
      params(new StringAttribute.TermsMultiSelect("a", "sel1", "sel2")) must be (
        List(("facet","a"), ("filter","a:\"sel1\",\"sel2\""), ("filter.facets","a:\"sel1\",\"sel2\"")))
    }
  }
}
