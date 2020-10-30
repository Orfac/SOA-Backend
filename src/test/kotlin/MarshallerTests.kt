import model.Chapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import xml.Marshallers
import xml.Unmarshallers
import java.io.StringReader
import java.io.StringWriter

class MarshallerTests {
  @Test
  fun `can serialize and deserialize chapter`(){
    val stringObject = getDefaultChapter()
    val chapter = Unmarshallers.CHAPTER.unmarshal(StringReader(stringObject)) as Chapter
    val marshalledChapter = StringWriter()
    Marshallers.CHAPTER.marshal(chapter, marshalledChapter)
    assertEquals(stringObject, marshalledChapter.toString())

  }

  private fun getDefaultChapter() = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
      "<Chapter>\n" +
      "    <name>string</name>\n" +
      "    <parentLegion>legion1</parentLegion>\n" +
      "    <marinesCount>1</marinesCount>\n" +
      "    <world>World1</world>\n" +
      "</Chapter>\n"


}