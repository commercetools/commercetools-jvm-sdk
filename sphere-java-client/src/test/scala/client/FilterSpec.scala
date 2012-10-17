package de.commercetools.sphere.client

import org.scalatest.matchers.MustMatchers
import org.scalatest.WordSpec
import de.commercetools.sphere.client.Filters._
import com.google.common.collect.Ranges
import com.google.common.collect.Range
import java.util.Arrays
import java.math
import com.google.common.base.Strings

class FilterSpec extends WordSpec with MustMatchers {

  /** Converts QueryParam to a tuple for easier asserts. */
  def param(filter: Filter): (String, String) = {
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
    param(new Category("123")) must be ("filter.query", "categories.id:\"123\"")
    param(new CategoryAnyOf("123", "234")) must be ("filter.query", "categories.id:\"123\",\"234\"")
  }
  
  "StringAttribute filters" should {
    "StringAttribute.Equals" in {
      param(new StringAttribute.Equals("fuel", "petrol")) must be("filter.query", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol", FilterType.DEFAULT)) must be("filter.query", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol", FilterType.FACETS_ONLY)) must be("filter.facets", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "petrol", FilterType.RESULTS_ONLY)) must be("filter", "fuel:\"petrol\"")
      param(new StringAttribute.Equals("fuel", "")) must be (null)
      param(new StringAttribute.Equals("fuel", null)) must be (null)
      param(new StringAttribute.Equals("fuel", "", FilterType.RESULTS_ONLY)) must be (null)
      param(new StringAttribute.Equals("fuel", null, FilterType.RESULTS_ONLY)) must be (null)
    }
  
    "StringAttribute.EqualsAnyOf" in {
      param(new StringAttribute.EqualsAnyOf("fuel", "petrol", "diesel")) must be("filter.query", "fuel:\"petrol\",\"diesel\"")
      param(new StringAttribute.EqualsAnyOf("fuel", Arrays.asList("petrol"), FilterType.RESULTS_ONLY)) must be("filter", "fuel:\"petrol\"")
    }
  }

  "NumberAttribute filters" should {
    "NumberAttribute.Equals" in {
      param(new NumberAttribute.Equals("damage", 1.2)) must be("filter.query", "damage:1.2")
      param(new NumberAttribute.Equals("damage", 2.015, FilterType.DEFAULT)) must be("filter.query", "damage:2.015")
      param(new NumberAttribute.Equals("damage", 1, FilterType.FACETS_ONLY)) must be("filter.facets", "damage:1.0")
      param(new NumberAttribute.Equals("damage", 0.41281117, FilterType.RESULTS_ONLY)) must be("filter", "damage:0.41281117")
      param(new StringAttribute.Equals("damage", null)) must be (null)
    }
  
    "NumberAttribute.EqualsAnyOf" in {
      param(new NumberAttribute.EqualsAnyOf("damage", 1.14, 1.0)) must be("filter.query", "damage:1.14,1.0")
      param(new NumberAttribute.EqualsAnyOf("damage", Arrays.asList[java.lang.Double](null, 2.0, null), FilterType.RESULTS_ONLY)) must be("filter", "damage:2.0")
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
      param(new MoneyAttribute.Equals("cash", decimal(2.01), FilterType.DEFAULT)) must be("filter.query", "cash.centAmount:201")
      param(new MoneyAttribute.Equals("cash", decimal(0.4128111818113151351517), FilterType.RESULTS_ONLY)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash", new math.BigDecimal("0.41281" + Strings.repeat("124571135", 10*1000)), FilterType.RESULTS_ONLY)) must be("filter", "cash.centAmount:41")
      param(new MoneyAttribute.Equals("cash", null)) must be (null)
      param(new Price(decimal(2.01))) must be("filter.query", "variants.price.centAmount:201")
      param(new Price(null)) must be(null)
    }

    "MoneyAttribute.EqualsAnyOf" in {
      param(new MoneyAttribute.EqualsAnyOf("cash", decimal(1.14), decimal(1.0))) must be("filter.query", "cash.centAmount:114,100")
      param(new MoneyAttribute.EqualsAnyOf("cash", Arrays.asList[java.math.BigDecimal](null, decimal(2.0), null), FilterType.RESULTS_ONLY)) must be("filter", "cash.centAmount:200")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.EqualsAnyOf("cash", dNull, dNull)) must be(null)
      param(new PriceAnyOf(decimal(1.14), decimal(1.0))) must be("filter.query", "variants.price.centAmount:114,100")
      param(new PriceAnyOf(dNull)) must be(null)
    }

    "MoneyAttribute.AtLeast" in {
      param(new MoneyAttribute.AtLeast("cash", decimal(1.5))) must be("filter.query", "cash.centAmount:range(150 to *)")
      param(new MoneyAttribute.AtLeast("cash", null)) must be(null)
      param(new PriceAtLeast(decimal(1.5))) must be("filter.query", "variants.price.centAmount:range(150 to *)")
      param(new PriceAtLeast(null)) must be(null)
    }

    "MoneyAttribute.AtMost" in {
      param(new MoneyAttribute.AtMost("cash", decimal(1.5))) must be("filter.query", "cash.centAmount:range(* to 150)")
      param(new MoneyAttribute.AtMost("cash", null)) must be(null)
      // huge price
      param(new PriceAtMost(new java.math.BigDecimal(Strings.repeat("124571135", 10)))) must be("filter.query", "variants.price.centAmount:range(* to " + Strings.repeat("124571135", 10) +"00)")
      param(new PriceAtMost(null)) must be(null)
    }

    "MoneyAttribute.Range" in {
      param(new MoneyAttribute.Range("cash", Ranges.closed(decimal(1.5), decimal(2.15)))) must be("filter.query", "cash.centAmount:range(150 to 215)")
      param(new MoneyAttribute.Range("cash", Ranges.lessThan(decimal(2.20)))) must be("filter.query", "cash.centAmount:range(* to 220)")
      param(new MoneyAttribute.Range("cash", decimal(1.5), decimal(2.5))) must be("filter.query", "cash.centAmount:range(150 to 250)")
      param(new MoneyAttribute.Range("cash", decimal(1.5), null)) must be("filter.query", "cash.centAmount:range(150 to *)")
      val dNull: java.math.BigDecimal = null
      param(new MoneyAttribute.Range("cash", dNull, dNull)) must be(null)
    }

    "MoneyAttribute.Ranges" in {
      val range1 = Ranges.closed(decimal(1.5), decimal(2.5))
      val range2 = Ranges.open(decimal(1.1), decimal(2.1))
      val range3: Range[java.math.BigDecimal] = null
      param(new MoneyAttribute.Ranges("cash", range1, range2, range3)) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
      param(new MoneyAttribute.Ranges("cash", Arrays.asList(range1, range2, range3))) must be("filter.query", "cash.centAmount:range(150 to 250),(110 to 210)")
    }
  }
}
