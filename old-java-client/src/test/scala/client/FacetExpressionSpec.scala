package io.sphere.client

import io.sphere.client.facets.expressions._
import io.sphere.client.facets.FacetExpr._
import org.scalatest._
import scala.collection.JavaConverters._

class FacetExpressionSpec extends WordSpec with MustMatchers {
  /** Converts QueryParams to a tuples for easier asserts. */
  def params(facet: FacetExpression): List[(String, String)] = {
    facet.createQueryParams().asScala.toList.map(param => (param.getName, param.getValue))
  }
  /** Converts a single QueryParam to a tuple for easier asserts. */
  def param(facet: FacetExpression): (String, String) = {
    val ps = params(facet)
    if (ps.length != 1) sys.error("FacetExpression created more than one query parameter.")
    ps.head
  }

  "Terms facet" in {
    param(stringAttribute("a").terms()) must be ("facet", "a")
    param(numberAttribute("a").terms()) must be ("facet", "a")
    param(moneyAttribute("a").terms()) must be ("facet", "a")
    param(dateTimeAttribute("a").terms()) must be ("facet", "a")
    param(price.terms()) must be ("facet", "variants.price.centAmount")
    param(categories.terms()) must be ("facet", "categories.id")
  }

  "StringAttribute facets" should {
    "StringAttribute.TermsMultiSelect" in {
      params(stringAttribute("a").termsMultiSelect("sel1", "sel2")) must be (
        List(("facet","a"), ("filter","a:\"sel1\",\"sel2\""), ("filter.facets","a:\"sel1\",\"sel2\"")))
    }
  }
}
