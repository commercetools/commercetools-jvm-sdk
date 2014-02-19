package sphere

import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.map.ObjectMapper
import org.scalatest._

class CustomObjectIntegrationSpec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  val container = "container"
  val coKey = "key"
  val mapper = new ObjectMapper
  val service = client.customObjects
  def toJson(s: String) = mapper.convertValue(s, classOf[JsonNode])

  "sphere client" must {
    "create custom objects" in {
      val customObject = service.set(container, coKey, "value").execute()
      customObject.getValue.toString must be ("\"value\"")
      customObject.getVersion must be (1)
      service.delete(container, coKey).execute.get must be (customObject)
    }

    "get custom objects" in {
      service.set(container, coKey, "value").execute()
      service.get(container, coKey).fetch.get.getValue must be (toJson("value"))
      service.delete(container, coKey).execute
    }

    "check for not existing objects" in {
      service.get("notthere", "no").fetch.isPresent must be (false)
    }
    
    "update custom object" in {
      val createdObject = service.set(container, coKey, "value").execute()
      createdObject.getValue.toString must be ("\"value\"")
      createdObject.getVersion must be (1)
      val updatedObject = service.set(container, coKey, "value2").execute()
      updatedObject.getValue.toString must be ("\"value2\"")
      updatedObject.getVersion must be (2)
      service.delete(container, coKey).execute
    }

    "update versioned custom object" in {
      val createdObject = service.set(container, coKey, "value").execute()
      createdObject.getValue.toString must be ("\"value\"")
      createdObject.getVersion must be (1)
      val updatedObject = service.set(container, coKey, "value2", 1).execute()
      updatedObject.getValue.toString must be ("\"value2\"")
      updatedObject.getVersion must be (2)
      service.delete(container, coKey).execute
    }

    "delete objects" in {
      val customObject = service.set(container, coKey, "value").execute()
      service.delete(container, coKey).execute.get must be (customObject)
      service.get(container, coKey).fetch.isPresent must be (false)
    }

    "delete objects, ckecking not present" in {
      val execute = service.delete(container, "nope").execute
      execute.isPresent must be (false)
    }
  }
}
