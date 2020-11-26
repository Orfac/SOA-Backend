import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import utils.getMarine
import xml.Marshallers
import xml.Unmarshallers
import xml.dto.XmlSpaceMarine
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXBException

class XmlTests {
  @Test
  fun `can serialize and deserialize marine`() {
    val stringObject = getXmlResource("default_marine.xml")

    val xmlSpaceMarineObject = Unmarshallers.XML_MARINE.unmarshal(StringReader(stringObject))
    val xmlSpaceMarine = xmlSpaceMarineObject as XmlSpaceMarine
    val spaceMarine = xmlSpaceMarine.toSpaceMarine()

    val marshalledChapter = StringWriter()
    Marshallers.MARINE.marshal(spaceMarine, marshalledChapter)
    assertEquals(stringObject, marshalledChapter.toString())
  }

  companion object {
    @JvmStatic
    fun wrong_marines() = arrayOf(
        Arguments.of("wrong_attribute.xml"),
        Arguments.of("wrong_element.xml"),
        Arguments.of("missing_element.xml"),
        Arguments.of("broken_marine.xml"),
        Arguments.of("broken_schema.xml"),
        Arguments.of("broken_sub_element.xml")
    )
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