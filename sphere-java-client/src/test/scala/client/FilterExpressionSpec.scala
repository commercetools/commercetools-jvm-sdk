package de.commercetools.sphere.client

import de.commercetools.sphere.client.filters.expressions._
import de.commercetools.sphere.client.filters.expressions.FilterType._
import filters.expressions.FilterExpressions._
import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import com.google.common.collect.Ranges
import com.google.common.collect.Range
import java.util.Arrays
import java.math
import com.google.common.base.Strings
import org.joda.time.{LocalTime, DateTimeZone, DateTime, LocalDate}

class FilterExpressionSpec extends WordSpec with MustMatchers {

  /** Converts QueryParam to a tuple for easier asserts. */
  def param(filter: FilterExpression): (String, String) = {
    val p = filter.createQueryParam()
    if (p == null) null else (p.getName, p.getValue)
  }

  /** Helper for creating Java decimals. */
  def decimal(d: java.lang.Double): java.math.BigDecimal = new java.math.BigDecimal(d)

  "Fulltext filter" in {
    param(new Fulltext("foo")) must be ("text", "foo")
    new Fulltext("").createQueryParam() must be (null)
    new Fulltext(null).createQueryParam() must be (null)
  }

  "Category filters" in {
    param(new Category.Equals("123")) must be ("filter.query", "categories.id:\"123\"")
    param(new Category.EqualsAnyOf("123", "234")) must be ("filter.query", "categories.id:\"123\",\"234\"")
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
      param(new StringAttribute.EqualsAnyOf("fuel", Arrays.asList("petrol")).setFilterType(RESULTS)) must be("filter", "fuel:\"petrol\"")
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
      param(new NumberAttribute.EqualsAnyOf("damage", Arrays.asList[java.lang.Double](null, 2.0, null)).setFilterType(RESULTS)) must be("filter", "damage:2.0")
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
      param(new NumberAttribute.Ranges("damage", Arrays.asList(range1, range2, range3))) must be("filter.query", "damage:range(1.5 to 2.5),(1.1 to 2.1)")
    }
  }

  "MoneyAttribute, Price filters" should {
    "MoneyAttribute.Equals" in {
      param(new MoneyAttribute.Equals("cash", decimal(2.01)).setFilterType(RESULTS_AND_FACETS)) must be("filter.query", "cash.centAmount:201")
      param(new MoneyAttribute.Equals("cash", decimal(0.4128111818113151351517)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash", new math.BigDecimal("0.41281" + Strings.repeat("124571135", 10*1000))).setFilterType(RESULTS)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash", null)) must be (null)
      param(new Price.Equals(decimal(2.01))) must be("filter.query", "variants.price.centAmount:201")
      param(new Price.Equals(null)) must be(null)
    }

    "MoneyAttribute.EqualsAnyOf" in {
      param(new MoneyAttribute.EqualsAnyOf("cash", decimal(1.14), decimal(1.0))) must be("filter.query", "cash.centAmount:114,100")
      param(new MoneyAttribute.EqualsAnyOf("cash", Arrays.asList[java.math.BigDecimal](null, decimal(2.0), null)).setFilterType(RESULTS)) must be("filter", "cash.centAmount:200")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.EqualsAnyOf("cash", dNull, dNull)) must be(null)
      param(new Price.EqualsAnyOf(decimal(1.14), decimal(1.0))) must be("filter.query", "variants.price.centAmount:114,100")
      param(new Price.EqualsAnyOf(dNull)) must be(null)
    }

    "MoneyAttribute.AtLeast" in {
      param(new MoneyAttribute.AtLeast("cash", decimal(1.5))) must be("filter.query", "cash.centAmount:range(150 to *)")
      param(new MoneyAttribute.AtLeast("cash", null)) must be(null)
      param(new Price.AtLeast(decimal(1.5))) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(new Price.AtLeast(null)) must be(null)
    }

    "MoneyAttribute.AtMost" in {
      param(new MoneyAttribute.AtMost("cash", decimal(1.5))) must be("filter.query", "cash.centAmount:range(* to 150)")
      param(new MoneyAttribute.AtMost("cash", null)) must be(null)
      // huge price
      param(new Price.AtMost(new java.math.BigDecimal(Strings.repeat("124571135", 10)))) must be("filter.query", "variants.price.centAmount:range(* to " + Strings.repeat("124571135", 10) +"00)")
      param(new Price.AtMost(null)) must be(null)
    }

    "MoneyAttribute.Range" in {
      param(new MoneyAttribute.Range("cash", Ranges.closed(decimal(1.5), decimal(2.15)))) must be("filter.query", "cash.centAmount:range(150 to 215)")
      param(new MoneyAttribute.Range("cash", Ranges.lessThan(decimal(2.20)))) must be("filter.query", "cash.centAmount:range(* to 220)")
      param(new MoneyAttribute.Range("cash", decimal(1.5), decimal(2.5))) must be("filter.query", "cash.centAmount:range(150 to 250)")
      param(new MoneyAttribute.Range("cash", decimal(1.5), null)) must be("filter.query", "cash.centAmount:range(150 to *)")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.Range("cash", dNull, dNull)) must be(null)
      param(new Price.Range(decimal(1.5), null)) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(new Price.Range(dNull, dNull)) must be(null)
    }

    "MoneyAttribute.Ranges" in {
      val range1 = Ranges.closed(decimal(1.5), decimal(2.5))
      val range2 = Ranges.open(decimal(1.1), decimal(2.1))
      val range3: Range[java.math.BigDecimal] = null
      param(new MoneyAttribute.Ranges("cash", range1, range2, range3)) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(new MoneyAttribute.Ranges("cash", Arrays.asList(range1, range2, range3))) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(new Price.Ranges(Arrays.asList(range1, range2, range3))) must be("filter.query", "variants.price.centAmount:range(150 to 250),(110 to 210)")
    }
  }

  "Date, Time, DateTime filters" should {
    "Date & Time.Equals" in {
      param(new DateAttribute.Equals("birthday", new LocalDate(2012, 6, 10))) must be("filter.query", "birthday:\"2012-06-10\"")
      param(new DateAttribute.Equals("birthday", null)) must be(null)
      param(new TimeAttribute.Equals("eventTime", new LocalTime(15, 30, 00))) must be("filter.query", "eventTime:\"15:30:00.000\"")
      param(new TimeAttribute.Equals("eventTime", null)) must be(null)
      param(new DateTimeAttribute.Equals("respawn", new DateTime(2014, 01, 01, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(new DateTimeAttribute.Equals("respawn", null)) must be(null)
    }

    "Date & Time.EqualsAnyOf" in {
      param(new DateAttribute.EqualsAnyOf("birthday", new LocalDate(2012, 6, 10))) must be("filter.query", "birthday:\"2012-06-10\"")
      param(new DateAttribute.EqualsAnyOf("birthday", Arrays.asList[LocalDate](null))) must be(null)
      param(new TimeAttribute.EqualsAnyOf("eventTime", new LocalTime(15, 30, 00))) must be("filter.query", "eventTime:\"15:30:00.000\"")
      param(new TimeAttribute.EqualsAnyOf("eventTime", null, null, null).setFilterType(RESULTS)) must be(null)
      param(new DateTimeAttribute.EqualsAnyOf("respawn", new DateTime(2014, 01, 01, 10, 0, 0, DateTimeZone.UTC))) must be("filter.query", "respawn:\"2014-01-01T10:00:00.000Z\"")
      param(new DateTimeAttribute.EqualsAnyOf("respawn", Arrays.asList[DateTime](null, null))) must be(null)
    }

    "Date & Time AtLeast, AtMost Range, Ranges" in {
      def rangeAtLeast(s: String) = ("filter.query", "a:range(\"%s\" to *)" format s)
      def rangeAtMost(s: String) = ("filter.query", "a:range(* to \"%s\")" format s)
      def range(s: String) = ("filter.query", "a:range(\"%s\" to \"%s\")" format (s, s))
      def ranges(start: String, end: String) = ("filter.query", "a:range(\"%s\" to \"%s\"),(\"%s\" to \"%s\")" format (start, end, start, end))

      val (date, dateString) = (new LocalDate(2012, 6, 10), "2012-06-10")
      val (date2, dateString2) = (new LocalDate(2013, 6, 10), "2013-06-10")
      param(new DateAttribute.AtLeast("a", date)) must be(rangeAtLeast(dateString))
      param(new DateAttribute.AtMost("a", date)) must be(rangeAtMost(dateString))
      param(new DateAttribute.Range("a", date, date)) must be(range(dateString))
      param(new DateAttribute.Ranges("a", Ranges.closed(date, date2), Ranges.closed(date, date2))) must be(ranges(dateString, dateString2))
      param(new DateAttribute.AtLeast("a", null)) must be(null)
      param(new DateAttribute.AtMost("a", null)) must be(null)
      param(new DateAttribute.Range("a", null, null)) must be(null)
      param(new DateAttribute.Ranges("a", null, null).setFilterType(FACETS)) must be(null)

      val (time, timeString) = (new LocalTime(15, 30, 00), "15:30:00.000")
      val (time2, timeString2) = (new LocalTime(16, 30, 00), "16:30:00.000")
      param(new TimeAttribute.AtLeast("a", time)) must be(rangeAtLeast(timeString))
      param(new TimeAttribute.AtMost("a", time)) must be(rangeAtMost(timeString))
      param(new TimeAttribute.Range("a", time, time)) must be(range(timeString))
      param(new TimeAttribute.Ranges("a", Ranges.closed(time, time2), Ranges.closed(time, time2))) must be(ranges(timeString, timeString2))
      param(new TimeAttribute.AtLeast("a", null)) must be(null)
      param(new TimeAttribute.AtMost("a", null)) must be(null)
      param(new TimeAttribute.Range("a", null, null)) must be(null)
      param(new TimeAttribute.Ranges("a", Arrays.asList[Range[LocalTime]](null))) must be(null)

      val (dateTime, dateTimeString) = (new DateTime(2012, 6, 10, 15, 30, 00, DateTimeZone.UTC), "2012-06-10T15:30:00.000Z")
      val (dateTime2, dateTimeString2) = (new DateTime(2013, 6, 10, 15, 30, 00, DateTimeZone.UTC), "2013-06-10T15:30:00.000Z")
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
