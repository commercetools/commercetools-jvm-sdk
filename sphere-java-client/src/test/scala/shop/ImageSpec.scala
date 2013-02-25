package de.commercetools.sphere.client
package shop
package model

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class ImageSpec extends WordSpec with MustMatchers  {
  "no scaling (original)" in {
    val image = new Image("https:/a1.rackcdn.com/snowboard.jpg", null, new Dimensions(500, 321))
    val orig = image.getSize(ImageSize.ORIGINAL)
    orig.getUrl must be ("https:/a1.rackcdn.com/snowboard.jpg")
    orig.getLabel must be ("")
    orig.getWidth must be (500)
    orig.getHeight must be (321)
    orig.getScaleRatio must be (1.0)
    orig.isScaledUp must be (false)
  }

  "scaling up - landscape" in {
    val landscape = new Image("https:/a1.rackcdn.com/snowboard.jpg", "Big air!", new Dimensions(500, 321))
    val largeL = landscape.getSize(ImageSize.LARGE)
    largeL.getUrl must be ("https:/a1.rackcdn.com/snowboard-large.jpg")
    largeL.getLabel must be ("Big air!")
    largeL.getWidth must be (700)
    largeL.getHeight must be (449)
    math.abs(largeL.getScaleRatio - 1.4) must be < (0.01)
    largeL.isScaledUp must be (true)
    largeL.isScaledDown must be (false)
  }

  "scaling up - portrait" in {
    val portrait = new Image("https:/a1.rackcdn.com/snowboard.jpg", "Big air!", new Dimensions(321, 500))
    val largeP = portrait.getSize(ImageSize.LARGE)
    largeP.getUrl must be ("https:/a1.rackcdn.com/snowboard-large.jpg")
    largeP.getLabel must be ("Big air!")
    largeP.getWidth must be (449)
    largeP.getHeight must be (700)
    math.abs(largeP.getScaleRatio - 1.4) must be < (0.01)
    largeP.isScaledUp must be (true)
    largeP.isScaledDown must be (false)
  }

  "scaling down - landscape" in {
    val landscape = new Image("https:/a1.rackcdn.com/snowboard.jpg", null, new Dimensions(500, 321))
    val smallL = landscape.getSize(ImageSize.SMALL)
    smallL.getUrl must be ("https:/a1.rackcdn.com/snowboard-small.jpg")
    smallL.getLabel must be ("")
    smallL.getWidth must be (150)
    smallL.getHeight must be (96)
    math.abs(smallL.getScaleRatio - 0.3) must be < (0.01)
    smallL.isScaledUp must be (false)
    smallL.isScaledDown must be (true)
  }

  "scaling down - portrait" in {
    val portrait = new Image("https:/a1.rackcdn.com/snowboard.jpg", "", new Dimensions(321, 500))
    val smallP = portrait.getSize(ImageSize.SMALL)
    smallP.getUrl must be ("https:/a1.rackcdn.com/snowboard-small.jpg")
    smallP.getLabel must be ("")
    smallP.getWidth must be (96)
    smallP.getHeight must be (150)
    math.abs(smallP.getScaleRatio - 0.3) must be < (0.01)
    smallP.isScaledUp must be (false)
    smallP.isScaledDown must be (true)
  }
}