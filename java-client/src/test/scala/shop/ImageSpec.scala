package io.sphere.client
package shop
package model

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class ImageSpec extends WordSpec with MustMatchers  {
  "no scaling (original)" in {
    val image = new Image("https://a1.rackcdn.com/snowboard.jpg", null, new Dimensions(500, 321))
    val orig = image.getSize(ImageSize.ORIGINAL)
    orig.getUrl must be ("https://a1.rackcdn.com/snowboard.jpg")
    orig.getLabel must be ("")
    orig.getWidth must be (500)
    orig.getHeight must be (321)
  }

  "scaling down - landscape" in {
    val landscape = new Image("https://a1.rackcdn.com/snowboard.jpg", null, new Dimensions(500, 321))
    val smallL = landscape.getSize(ImageSize.SMALL)
    smallL.getUrl must be ("https://a1.rackcdn.com/snowboard-small.jpg")
    smallL.getLabel must be ("")
    smallL.getWidth must be (150)
    smallL.getHeight must be (96)
  }

  // Note: no scaling up.

  "scaling down - portrait" in {
    val portrait = new Image("https://a1.rackcdn.com/snowboard.jpg", "", new Dimensions(321, 500))
    val smallP = portrait.getSize(ImageSize.SMALL)
    smallP.getUrl must be ("https://a1.rackcdn.com/snowboard-small.jpg")
    smallP.getLabel must be ("")
    smallP.getWidth must be (96)
    smallP.getHeight must be (150)
  }
  
  "isSizeAvailable - landscape" in {
    val img = new Image("https://a1.rackcdn.com/snowboard.jpg", "Big air!", new Dimensions(500, 421))
    img.isSizeAvailable(ImageSize.ORIGINAL) must be (true)
    img.isSizeAvailable(ImageSize.THUMBNAIL) must be (true)
    img.isSizeAvailable(ImageSize.SMALL) must be (true)
    img.isSizeAvailable(ImageSize.MEDIUM) must be (true)

    img.isSizeAvailable(ImageSize.LARGE) must be (false)
    img.isSizeAvailable(ImageSize.ZOOM) must be (false)
  }

  "isSizeAvailable - portrait" in {
    val img = new Image("https://a1.rackcdn.com/snowboard.jpg", "Big air!", new Dimensions(421, 500))
    img.isSizeAvailable(ImageSize.ORIGINAL) must be (true)
    img.isSizeAvailable(ImageSize.THUMBNAIL) must be (true)
    img.isSizeAvailable(ImageSize.SMALL) must be (true)
    img.isSizeAvailable(ImageSize.MEDIUM) must be (true)

    img.isSizeAvailable(ImageSize.LARGE) must be (false)
    img.isSizeAvailable(ImageSize.ZOOM) must be (false)
  }

  "fallback to original if size not available" in {
    val img = new Image("https://a1.rackcdn.com/snowboard.jpg", "Big air!", new Dimensions(500, 421))
    val fallback = img.getSize(ImageSize.ZOOM)
    fallback.getSize must be (ImageSize.ORIGINAL)
    fallback.getUrl must be ("https://a1.rackcdn.com/snowboard.jpg")
    fallback.getLabel must be ("Big air!")
    fallback.getWidth must be (500)
    fallback.getHeight must be (421)
  }
}
