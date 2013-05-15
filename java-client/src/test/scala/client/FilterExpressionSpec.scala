package io.sphere.client

import io.sphere.client.filters.expressions._
import io.sphere.client.filters.expressions.FilterType._
import filters.expressions.FilterExpressions._
import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import com.google.common.collect.Ranges
import com.google.common.collect.Range
import com.google.common.base.Strings
import org.joda.time.{DateTimeZone, DateTime}
import io.sphere.client.shop.{JsonTestObjects, CategoryTree}
import TestUtil._
import collection.mutable.ListBuffer

class FilterExpressionSpec extends WordSpec with MustMatchers {
  /** Converts QueryParam to a tuple for easier asserts. */
  def param(filter: FilterExpression): (String, String) = {
    val ps = filter.createQueryParams()
    if (ps == null) fail("Filter.createQueryParams should never return null.")
    if (ps.size == 0) null else (ps.get(0).getName, ps.get(0).getValue)
  }

  /** Helper for creating Java decimals. */
  def decimal(d: java.lang.Double): java.math.BigDecimal = new java.math.BigDecimal(d)

  "Fulltext filter" in {
    param(new Fulltext("foo")) must be ("text", "foo")
    new Fulltext("").createQueryParams().size must be (0)
    new Fulltext(null).createQueryParams().size must be (0)
  }

  private def categories: CategoryTree = MockSphereClient.create(categoriesResponse = FakeResponse(JsonTestObjects.categoriesJson)).categories

  def splitByCommaAndSort(s: String): List[String] = {
    val b = new ListBuffer[String]()
    b ++= s.split(",")
    b.toList.sorted
  }

  "Category filters" in {
    param(new Categories(categories.getById("id-v6"))) must be ("filter.query", "categories.id:\"id-v6\"")
    param(new Categories(categories.getById("id-sport"), categories.getById("id-v6"))) must be ("filter.query", "categories.id:\"id-sport\",\"id-v6\"")

    val multipleSub = param(new CategoriesOrSubcategories(categories.getBySlug("v8")))
    (multipleSub._1, splitByCommaAndSort(multipleSub._2))  must be ("filter.query", List("\"id-super\"", "\"id-turbo\"", "categories.id:\"id-v8\""))

    val multipleSub2 = param(new CategoriesOrSubcategories(categories.getBySlug("v8"), categories.getBySlug("convertibles")))
    (multipleSub2._1, splitByCommaAndSort(multipleSub2._2))  must be ("filter.query", List("\"id-convert\"", "\"id-super\"", "\"id-turbo\"", "categories.id:\"id-v8\""))
  }
  
  "StringAttribute filters" should {
    "StringAttribute.Equals" in {
      param(new StringAttribute.Equals("fuel", "petrol")) must be("filter.query", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol").setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol").setFilterType(FACETS)) must be("filter.facets", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol").setFilterType(RESULTS)) must be("filter", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "")) must be (null)
      param(new StringAttribute.Equals("fuel", null)) must be (null)
      param(new StringAttribute.Equals("fuel", "").setFilterType(RESULTS)) must be (null)
      param(new StringAttribute.Equals("fuel", null).setFilterType(RESULTS)) must be (null)
    }
  
    "StringAttribute.EqualsAnyOf" in {
      param(new StringAttribute.EqualsAnyOf("fuel", "petrol", "diesel")) must be("filter.query", "fuel:\"petrol\",\"diesel\"")
      param(new StringAttribute.EqualsAnyOf("fuel", lst("petrol")).setFilterType(RESULTS)) must be("filter", "fuel:\"petrol\"")
    }
  }

  "NumberAttribute filters" should {
    "NumberAttribute.Equals" in {
      param(new NumberAttribute.Equals("damage", 1.2)) must be("filter.query", "damage:1.2")
      param(new NumberAttribute.Equals("damage", 2.015).setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "damage:2.015")
      param(new NumberAttribute.Equals("damage", 1).setFilterType(FACETS)) must be("filter.facets", "damage:1.0")
      param(new NumberAttribute.Equals("damage", 0.41281117).setFilterType(RESULTS)) must be("filter", "damage:0.41281117")
      param(new StringAttribute.Equals("damage", null)) must be (null)
    }
  
    "NumberAttribute.EqualsAnyOf" in {
      param(new NumberAttribute.EqualsAnyOf("damage", 1.14, 1.0)) must be("filter.query", "damage:1.14,1.0")
      param(new NumberAttribute.EqualsAnyOf("damage", lst[java.lang.Double](null, 2.0, null)).setFilterType(RESULTS)) must be("filter", "damage:2.0")
      val dNull: java.lang.Double = null
      param(new NumberAttribute.EqualsAnyOf("damage", dNull, dNull)) must be(null)
    }
  
    "NumberAttribute.AtLeast" in {
      param(new NumberAttribute.AtLeast("damage", 1.5)) must be("filter.query", "damage:range(1.5 to *)")
      param(new NumberAttribute.AtLeast("damage", null)) must be(null)
    }
  
    "NumberAttribute.AtMost" in {
      param(new NumberAttribute.AtMost("damage", 1.5)) must be("filter.query", "damage:range(* to 1.5)")
      param(new NumberAttribute.AtMost("damage", null)) must be(null)
    }
  
    "NumberAttribute.Range" in {
      param(new NumberAttribute.Range("damage", Ranges.closed(1.5, 2.15))) must be("filter.query", "damage:range(1.5 to 2.15)")
      param(new NumberAttribute.Range("damage", Ranges.lessThan(2.20))) must be("filter.query", "damage:range(* to 2.2)")
      param(new NumberAttribute.Range("damage", 1.5, 2.5)) must be("filter.query", "damage:range(1.5 to 2.5)")
      param(new NumberAttribute.Range("damage", 1.5, null)) must be("filter.query", "damage:range(1.5 to *)")
      val dNull: java.lang.Double = null
      param(new NumberAttribute.Range("damage", dNull, dNull)) must be(null)
    }
  
    "NumberAttribute.Ranges" in {
      val range1 = Ranges.closed[java.lang.Double](1.5, 2.5)
      val range2 = Ranges.open[java.lang.Double](1.1, 2.1)
      val range3: Range[java.lang.Double] = null
      param(new NumberAttribute.Ranges("damage", range1, range2, range3)) must be("filter.query", "damage:range(1.5 to 2.5),(1.1 to 2.1)")
      param(new NumberAttribute.Ranges("damage", lst(range1, range2, range3))) must be("filter.query", "damage:range(1.5 to 2.5),(1.1 to 2.1)")
    }
  }

  "MoneyAttribute, Price filters" should {
    "MoneyAttribute.Equals" in {
      param(new MoneyAttribute.Equals("cash.centAmount", decimal(2.01)).setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "cash.centAmount:201")
      param(new MoneyAttribute.Equals("cash.centAmount", decimal(0.4128111818113151351517)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash.centAmount", new java.math.BigDecimal("0.41281" + Strings.repeat("124571135", 10*1000))).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash.centAmount", null)) must be (null)
      param(new Price.Equals(decimal(2.01))) must be("filter.query", "variants.price.centAmount:201")
      param(new Price.Equals(null)) must be(null)
    }

    "MoneyAttribute.EqualsAnyOf" in {
      param(new MoneyAttribute.EqualsAnyOf("cash.centAmount", decimal(1.14), decimal(1.0))) must be("filter.query", "cash.centAmount:114,100")
      param(new MoneyAttribute.EqualsAnyOf("cash.centAmount", lst[java.math.BigDecimal](null, decimal(2.0), null)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:200")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.EqualsAnyOf("cash.centAmount", dNull, dNull)) must be(null)
      param(new Price.EqualsAnyOf(decimal(1.14), decimal(1.0))) must be("filter.query", "variants.price.centAmount:114,100")
      param(new Price.EqualsAnyOf(dNull)) must be(null)
    }

    "MoneyAttribute.AtLeast" in {
      param(new MoneyAttribute.AtLeast("cash.centAmount", decimal(1.5))) must be("filter.query", "cash.centAmount:range(150 to *)")
      param(new MoneyAttribute.AtLeast("cash.centAmount", null)) must be(null)
      param(new Price.AtLeast(decimal(1.5))) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(new Price.AtLeast(null)) must be(null)
    }

    "MoneyAttribute.AtMost" in {
      param(new MoneyAttribute.AtMost("cash.centAmount", decimal(1.5))) must be("filter.query", "cash.centAmount:range(* to 150)")
      param(new MoneyAttribute.AtMost("cash.centAmount", null)) must be(null)
      param(new Price.AtMost(null)) must be(null)
    }

    "MoneyAttribute.Range" in {
      param(new MoneyAttribute.Range("cash.centAmount", Ranges.closed(decimal(1.5), decimal(2.15)))) must be("filter.query", "cash.centAmount:range(150 to 215)")
      param(new MoneyAttribute.Range("cash.centAmount", Ranges.lessThan(decimal(2.20)))) must be("filter.query", "cash.centAmount:range(* to 220)")
      param(new MoneyAttribute.Range("cash.centAmount", decimal(1.5), decimal(2.5))) must be("filter.query", "cash.centAmount:range(150 to 250)")
      param(new MoneyAttribute.Range("cash.centAmount", decimal(1.5), null)) must be("filter.query", "cash.centAmount:range(150 to *)")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.Range("cash.centAmount", dNull, dNull)) must be(null)
      param(new Price.Range(decimal(1.5), null)) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(new Price.Range(dNull, dNull)) must be(null)
    }

    "MoneyAttribute.Ranges" in {
      val range1 = Ranges.closed(decimal(1.5), decimal(2.5))
      val range2 = Ranges.open(decimal(1.1), decimal(2.1))
      val range3: Range[java.math.BigDecimal] = null
      param(new MoneyAttribute.Ranges("cash.centAmount", range1, range2, range3)) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(new MoneyAttribute.Ranges("cash.centAmount", lst(range1, range2, range3))) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(new Price.Ranges(lst(range1, range2, range3))) must be("filter.query", "variants.price.centAmount:range(150 to 250),(110 to 210)")
    }
  }

  "DateTime filters" should {
    "Date & Time.Equals" in {
      param(new DateTimeAttribute.Equals("respawn", new DateTime(2014, 1, 1, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(new DateTimeAttribute.Equals("respawn", null)) must be(null)
    }

    "DateTime.EqualsAnyOf" in {
      param(new DateTimeAttribute.EqualsAnyOf("respawn", new DateTime(2014, 1, 1, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(new DateTimeAttribute.EqualsAnyOf("respawn", lst[DateTime](null, null))) must be(null)
    }

    "DateTime AtLeast, AtMost Range, Ranges" in {
      def rangeAtLeast(s: String) = ("filter.query", "a:range(\"%s\" to *)" format s)
      def rangeAtMost(s: String) = ("filter.query", "a:range(* to \"%s\")" format s)
      def range(s: String) = ("filter.query", "a:range(\"%s\" to \"%s\")" format (s, s))
      def ranges(start: String, end: String) = ("filter.query", "a:range(\"%s\" to \"%s\"),(\"%s\" to \"%s\")" format (start, end, start, end))

      val (dateTime, dateTimeString) = (new DateTime(2012, 6, 10, 15, 30, 0, DateTimeZone.UTC), "2012-06-10T15:30:00.000Z")
      val (dateTime2, dateTimeString2) = (new DateTime(2013, 6, 10, 15, 30, 0, DateTimeZone.UTC), "2013-06-10T15:30:00.000Z")
      param(new DateTimeAttribute.AtLeast("a", dateTime)) must be(rangeAtLeast(dateTimeString))
      param(new DateTimeAttribute.AtMost("a", dateTime)) must be(rangeAtMost(dateTimeString))
      param(new DateTimeAttribute.Range("a", dateTime, dateTime)) must be(range(dateTimeString))
      param(new DateTimeAttribute.Ranges("a", Ranges.closed(dateTime, dateTime2), Ranges.closed(dateTime, dateTime2))) must be(ranges(dateTimeString, dateTimeString2))
      param(new DateTimeAttribute.AtLeast("a", null)) must be(null)
      param(new DateTimeAttribute.AtMost("a", null)) must be(null)
      param(new DateTimeAttribute.Range("a", null, null)) must be(null)
      val i: java.lang.Iterable[Range[DateTime]] = null
      param(new DateTimeAttribute.Ranges("a", i)) must be(null)
    }
  }
}
