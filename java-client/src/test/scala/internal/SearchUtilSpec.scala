package io.sphere.internal

import org.scalatest._
import io.sphere.internal.util.SearchUtil
import org.joda.time.DateTime

class SearchUtilSpec extends WordSpec with MustMatchers {
  import SearchUtil._

  "Adjusting facet endpoints to make them behave as inclusive" should {
    "Adjust number facet endpoints" in {
      adjustDoubleForSearch(1.113287) must be (1.114)
      adjustDoubleForSearch(1.1139999) must be (1.115)
      adjustDoubleForSearch(1.1191111) must be (1.120)
      adjustDoubleForSearch(1.119999) must be (1.121)
      adjustDoubleForSearch(1.1195001) must be (1.121)
      adjustDoubleForSearch(1.2) must be (1.201)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.113287)) must be (1.113)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.1139999)) must be (1.114)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.1191111)) must be (1.119)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.119999)) must be (1.120)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.1195001)) must be (1.120)
      adjustDoubleBackFromSearch(adjustDoubleForSearch(1.2)) must be (1.2)
    }
    "Adjust long facet endpoints (used for Money centAmounts)" in {
      adjustLongForSearch(10115) must be (10116)
      adjustLongBackFromSearch(adjustLongForSearch(10115)) must be (10115)
    }
    "Adjust DateTime facet endpoints" in {
      // y m d h M s ms
      adjustDateTimeForSearch(new DateTime(2013, 6, 10, 0, 0, 0, 0)) must be (new DateTime(2013, 6, 10, 0, 0, 0, 1))
      adjustDateTimeForSearch(new DateTime(2013, 6, 10, 15, 30, 12, 215)) must be (new DateTime(2013, 6, 10, 15, 30, 12, 216))
      adjustDateTimeBackFromSearch(adjustDateTimeForSearch(new DateTime(2013, 6, 10, 0, 0, 0, 0))) must be (
        new DateTime(2013, 6, 10, 0, 0, 0, 0))
      adjustDateTimeBackFromSearch(adjustDateTimeForSearch(new DateTime(2013, 6, 10, 15, 30, 12, 215))) must be (
        new DateTime(2013, 6, 10, 15, 30, 12, 215))
    }
  }
}
