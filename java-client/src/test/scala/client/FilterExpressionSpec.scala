package io.sphere.client

import io.sphere.client.filters.expressions._
import io.sphere.client.filters.expressions.FilterType._
import io.sphere.client.filters.FilterExpr._
import io.sphere.client.shop.{JsonResponses, CategoryTree}
import TestUtil._

import com.google.common.collect.Ranges
import com.google.common.collect.Range
import com.google.common.base.Strings
import org.joda.time.{DateTimeZone, DateTime}

import scala.collection.mutable.ListBuffer
import org.scalatest._
import java.util.Locale

class FilterExpressionSpec extends WordSpec with MustMatchers {
  /** Converts QueryParam to a tuple for easier asserts. */
  def param(filter: FilterExpression): (String, String) = {
    val ps = filter.createQueryParams()
    if (ps == null) fail("Filter.createQueryParams should never return null.")
    if (ps.size == 0) null else (ps.get(0).getName, ps.get(0).getValue)
  }

  /** Helper for creating Java decimals. */
  def decimal(d: java.lang.Double): java.math.BigDecimal = new java.math.BigDecimal(d)

  val EN = Locale.ENGLISH;

  "Fulltext filter" in {
    param(fulltext("foo", EN)) must be ("text", "foo")
    fulltext("", EN).createQueryParams().size must be (0)
    fulltext(null, EN).createQueryParams().size must be (0)
  }

  def splitByCommaAndSort(s: String): List[String] = {
    val b = new ListBuffer[String]()
    b ++= s.split(",")
    b.toList.sorted
  }

  "Category filters" in {
    val sphereCategories = MockSphereClient.create(categoriesResponse = FakeResponse(JsonResponses.categoriesJson)).categories

    param(categories(sphereCategories.getById("id-v6"))) must be ("filter.query", "categories.id:\"id-v6\"")
    param(categories(sphereCategories.getById("id-sport"), sphereCategories.getById("id-v6"))) must be ("filter.query", "categories.id:\"id-sport\",\"id-v6\"")

    val multipleSub = param(categoriesOrSubcategories(sphereCategories.getBySlug("v8")))
    (multipleSub._1, splitByCommaAndSort(multipleSub._2))  must be ("filter.query", List("\"id-super\"", "\"id-turbo\"", "categories.id:\"id-v8\""))

    val multipleSub2 = param(categoriesOrSubcategories(sphereCategories.getBySlug("v8"), sphereCategories.getBySlug("convertibles")))
    (multipleSub2._1, splitByCommaAndSort(multipleSub2._2))  must be ("filter.query", List("\"id-convert\"", "\"id-super\"", "\"id-turbo\"", "categories.id:\"id-v8\""))
  }
  
  "StringAttribute filters" should {
    "StringAttribute.Equals" in {
      param(stringAttribute("fuel").equal("petrol")) must be("filter.query", "fuel:\"petrol\"")
      param(stringAttribute("fuel").equal("petrol").setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "fuel:\"petrol\"")
      param(stringAttribute("fuel").equal("petrol").setFilterType(FACETS)) must be("filter.facets", "fuel:\"petrol\"")
      param(stringAttribute("fuel").equal("petrol").setFilterType(RESULTS)) must be("filter", "fuel:\"petrol\"")
      param(stringAttribute("fuel").equal("")) must be (null)
      param(stringAttribute("fuel").equal(null)) must be (null)
      param(stringAttribute("fuel").equal("").setFilterType(RESULTS)) must be (null)
      param(stringAttribute("fuel").equal(null).setFilterType(RESULTS)) must be (null)
    }
  
    "StringAttribute.EqualsAnyOf" in {
      param(stringAttribute("fuel").equalsAnyOf("petrol", "diesel")) must be("filter.query", "fuel:\"petrol\",\"diesel\"")
      param(stringAttribute("fuel").equalsAnyOf(lst("petrol")).setFilterType(RESULTS)) must be("filter", "fuel:\"petrol\"")
    }
  }

  "NumberAttribute filters" should {
    "NumberAttribute.Equals" in {
      param(numberAttribute("damage").equal(1.2)) must be("filter.query", "damage:1.2")
      param(numberAttribute("damage").equal(2.015).setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "damage:2.015")
      param(numberAttribute("damage").equal(1).setFilterType(FACETS)) must be("filter.facets", "damage:1.0")
      param(numberAttribute("damage").equal(0.41281117).setFilterType(RESULTS)) must be("filter", "damage:0.41281117")
      param(numberAttribute("damage").equal(null)) must be (null)
    }
  
    "NumberAttribute.EqualsAnyOf" in {
      param(numberAttribute("damage").equalsAnyOf(1.14, 1.0)) must be("filter.query", "damage:1.14,1.0")
      param(numberAttribute("damage").equalsAnyOf(lst[java.lang.Double](null, 2.0, null)).setFilterType(RESULTS)) must be("filter", "damage:2.0")
      val dNull: java.lang.Double = null
      param(numberAttribute("damage").equalsAnyOf(dNull, dNull)) must be(null)
    }
  
    "NumberAttribute.AtLeast" in {
      param(numberAttribute("damage").atLeast(101.5)) must be("filter.query", "damage:range(101.5 to *)")
      param(numberAttribute("damage").atLeast(null)) must be(null)
    }
  
    "NumberAttribute.AtMost" in {
      param(numberAttribute("damage").atMost(1.5)) must be("filter.query", "damage:range(* to 1.5)")
      param(numberAttribute("damage").atMost(null)) must be(null)
    }
  
    "NumberAttribute.Range" in {
      param(numberAttribute("damage").range(Ranges.closed(1.5, 2.15))) must be("filter.query", "damage:range(1.5 to 2.15)")
      param(numberAttribute("damage").range(Ranges.lessThan(2.20))) must be("filter.query", "damage:range(* to 2.2)")
      param(numberAttribute("damage").range(1.5, 2.5)) must be("filter.query", "damage:range(1.5 to 2.5)")
      param(numberAttribute("damage").range(1.5, null)) must be("filter.query", "damage:range(1.5 to *)")
      param(numberAttribute("damage").range(null, 150)) must be("filter.query", "damage:range(* to 150.0)")
      val dNull: java.lang.Double = null
      param(numberAttribute("damage").range(dNull, dNull)) must be(null)
    }
  
    "NumberAttribute.Ranges" in {
      val range1 = Ranges.closed[java.lang.Double](1.5, 2.5)
      val range2 = Ranges.open[java.lang.Double](1.1, 2.1)
      val range3: Range[java.lang.Double] = null
      param(numberAttribute("damage").ranges(range1, range2, range3)) must be("filter.query", "damage:range(1.5 to 2.5),(1.1 to 2.1)")
      param(numberAttribute("damage").ranges(lst(range1, range2, range3))) must be("filter.query", "damage:range(1.5 to 2.5),(1.1 to 2.1)")
    }
  }

  "MoneyAttribute, Price filters" should {
    "MoneyAttribute.Equals" in {
      param(moneyAttribute("cash.centAmount").equal(decimal(2.01)).setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "cash.centAmount:201")
      param(moneyAttribute("cash.centAmount").equal(decimal(0.4128117)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(moneyAttribute("cash.centAmount").equal(new java.math.BigDecimal("0.41281" + Strings.repeat("124571135", 10*1000))).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(moneyAttribute("cash.centAmount").equal(null)) must be (null)
      param(price.equal(decimal(2.01))) must be("filter.query", "variants.price.centAmount:201")
      param(price.equal(decimal(2.016))) must be("filter.query", "variants.price.centAmount:202") // HALF_EVEN
      param(price.equal(null)) must be(null)
    }

    "MoneyAttribute.EqualsAnyOf" in {
      param(moneyAttribute("cash.centAmount").equalsAnyOf(decimal(1.14), decimal(1.0))) must be("filter.query", "cash.centAmount:114,100")
      param(moneyAttribute("cash.centAmount").equalsAnyOf(lst[java.math.BigDecimal](null, decimal(2.0), null)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:200")
      val dNull: java.math.BigDecimal = null
      param(moneyAttribute("cash.centAmount").equalsAnyOf(dNull, dNull)) must be(null)
      param(price.equalsAnyOf(decimal(1.14), decimal(1.0))) must be("filter.query", "variants.price.centAmount:114,100")
      param(price.equalsAnyOf(dNull)) must be(null)
    }

    "MoneyAttribute.AtLeast" in {
      param(moneyAttribute("cash.centAmount").atLeast(decimal(1.5))) must be("filter.query", "cash.centAmount:range(150 to *)")
      param(moneyAttribute("cash.centAmount").atLeast(null)) must be(null)
      param(price.atLeast(decimal(1.5))) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(price.atLeast(null)) must be(null)
    }

    "MoneyAttribute.AtMost" in {
      param(moneyAttribute("cash.centAmount").atMost(decimal(1.5))) must be("filter.query", "cash.centAmount:range(* to 150)")
      param(moneyAttribute("cash.centAmount").atMost(null)) must be(null)
      param(price.atMost(null)) must be(null)
    }

    "MoneyAttribute.Range" in {
      param(moneyAttribute("cash.centAmount").range(Ranges.closed(decimal(1.5), decimal(2.15)))) must be("filter.query", "cash.centAmount:range(150 to 215)")
      param(moneyAttribute("cash.centAmount").range(Ranges.lessThan(decimal(2.20)))) must be("filter.query", "cash.centAmount:range(* to 220)")
      param(moneyAttribute("cash.centAmount").range(decimal(1.5), decimal(2.5))) must be("filter.query", "cash.centAmount:range(150 to 250)")
      param(moneyAttribute("cash.centAmount").range(decimal(1.5), null)) must be("filter.query", "cash.centAmount:range(150 to *)")
      val dNull: java.math.BigDecimal = null
      param(moneyAttribute("cash.centAmount").range(dNull, dNull)) must be(null)
      param(price.range(decimal(1.5), null)) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(price.range(dNull, dNull)) must be(null)
    }

    "MoneyAttribute.Ranges" in {
      val range1 = Ranges.closed(decimal(1.5), decimal(2.5))
      val range2 = Ranges.open(decimal(1.1), decimal(2.1))
      val range3: Range[java.math.BigDecimal] = null
      param(moneyAttribute("cash.centAmount").ranges(range1, range2, range3)) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(moneyAttribute("cash.centAmount").ranges(lst(range1, range2, range3))) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(price.ranges(lst(range1, range2, range3))) must be("filter.query", "variants.price.centAmount:range(150 to 250),(110 to 210)")
    }
  }

  "DateTime filters" should {
    "Date & Time.Equals" in {
      param(dateTimeAttribute("respawn").equal(new DateTime(2014, 1, 1, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(dateTimeAttribute("respawn").equal(null)) must be(null)
    }

    "DateTime.EqualsAnyOf" in {
      param(dateTimeAttribute("respawn").equalsAnyOf(new DateTime(2014, 1, 1, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(dateTimeAttribute("respawn").equalsAnyOf(lst[DateTime](null, null))) must be(null)
    }

    "DateTime AtLeast, AtMost Range, Ranges" in {
      def rangeAtLeast(s: String) = ("filter.query", "a:range(\"%s\" to *)" format s)
      def rangeAtMost(s: String) = ("filter.query", "a:range(* to \"%s\")" format s)
      def range(s: String) = ("filter.query", "a:range(\"%s\" to \"%s\")" format (s, s))
      def ranges(start: String, end: String) = ("filter.query", "a:range(\"%s\" to \"%s\"),(\"%s\" to \"%s\")" format (start, end, start, end))

      val (dateTime, dateTimeString) = (new DateTime(2012, 6, 10, 15, 30, 0, DateTimeZone.UTC), "2012-06-10T15:30:00.000Z")
      val (dateTime2, dateTimeString2) = (new DateTime(2013, 6, 10, 15, 30, 0, DateTimeZone.UTC), "2013-06-10T15:30:00.000Z")
      param(dateTimeAttribute("a").atLeast(dateTime)) must be(rangeAtLeast(dateTimeString))
      param(dateTimeAttribute("a").atMost(dateTime)) must be(rangeAtMost(dateTimeString))
      param(dateTimeAttribute("a").range(dateTime, dateTime)) must be(range(dateTimeString))
      param(dateTimeAttribute("a").ranges(Ranges.closed(dateTime, dateTime2), Ranges.closed(dateTime, dateTime2))) must be(ranges(dateTimeString, dateTimeString2))
      param(dateTimeAttribute("a").atLeast(null)) must be(null)
      param(dateTimeAttribute("a").atMost(null)) must be(null)
      param(dateTimeAttribute("a").range(null, null)) must be(null)
      val i: java.lang.Iterable[Range[DateTime]] = null
      param(dateTimeAttribute("a").ranges(i)) must be(null)
    }
  }
}
