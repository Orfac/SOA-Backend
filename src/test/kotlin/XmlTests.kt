import model.SpaceMarine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import xml.Marshallers
import xml.Unmarshallers
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXBException

class XmlTests {
  companion object {
    @JvmStatic
    fun wrong_marines() = arrayOf(
        Arguments.of("wrong_attribute.xml"),
        Arguments.of("wrong_element.xml"),
        Arguments.of("broken_marine.xml"),
        Arguments.of("broken_schema.xml"),
        Arguments.of("broken_sub_element.xml")
    )
    @JvmStatic
    fun good_marines() = arrayOf(
        Arguments.of("default_marine.xml"),
        Arguments.of("marine_with_missing_field.xml"),
        Arguments.of("default_marine_without_id.xml")
    )
  }

  @ParameterizedTest
  @MethodSource("good_marines")
  fun `can serialize and deserialize marine`(input: String) {
    val stringObject = getXmlResource(input)

    val xmlSpaceMarineObject = Unmarshallers.XML_MARINE.unmarshal(StringReader(stringObject))
    val spaceMarine = xmlSpaceMarineObject as SpaceMarine

    val marshalledChapter = StringWriter()
    Marshallers.MARINE.marshal(spaceMarine, marshalledChapter)
    assertEquals(stringObject, marshalledChapter.toString())
  }


  @ParameterizedTest
  @MethodSource("wrong_marines")
  fun `throws exception when deserializes wrong marine`(input: String) {
    assertThrows<JAXBException> {
      val stringObject = getXmlResource("wrong_marines/${input}")
      Unmarshallers.XML_MARINE.unmarshal(StringReader(stringObject))
    }
  }

}