package xml

import model.PageableSpaceMarineList
import model.SpaceMarine
import model.SpaceMarineList
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.SchemaOutputResolver
import javax.xml.bind.Unmarshaller
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory

object Marshallers {
  val MARINE = initSpaceMarineMarshaller()
  val MARINE_LIST = initSpaceMarineListMarshaller()
  val PAGEABLE_SPACE_MARINE_LIST = initPageableSpaceMarineListMarshaller()

  private fun initPageableSpaceMarineListMarshaller(): Marshaller {
    return initMarshaller(PageableSpaceMarineList::class.java)
  }

  private fun initSpaceMarineListMarshaller(): Marshaller {
    return initMarshaller(SpaceMarineList::class.java)
  }

  private fun initSpaceMarineMarshaller(): Marshaller {
    return initMarshaller(SpaceMarine::class.java)
  }

  private fun initMarshaller(entityClass: Class<*>): Marshaller {
    val context = JAXBContext.newInstance(entityClass)
    val marshaller = context.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    return marshaller
  }
}

object Unmarshallers {
  val XML_MARINE = initMarineUnmarshaller()
  val MARINE_LIST = initSpaceMarineListUnmarshaller()

  private fun initSpaceMarineListUnmarshaller(): Unmarshaller {
    return initUnmarshaller(SpaceMarineList::class.java)
  }

  private fun initMarineUnmarshaller(): Unmarshaller {
    return initUnmarshaller(SpaceMarine::class.java)
  }

  private fun initUnmarshaller(entityClass: Class<*>): Unmarshaller {
    val context = JAXBContext.newInstance(entityClass)
    val schema = generateSchemaByContext(context)

    return context.createUnmarshaller().also { it.schema = schema }
  }

  private fun generateSchemaByContext(context: JAXBContext): Schema {
    val schemaDocs: MutableList<ByteArrayOutputStream> = ArrayList()
    context.generateSchema(object : SchemaOutputResolver() {
      @Throws(IOException::class)
      override fun createOutput(
        namespaceUri: String,
        suggestedFileName: String
      ): javax.xml.transform.Result {
        val outputStream = ByteArrayOutputStream()
        val streamResult = StreamResult(outputStream)
        schemaDocs.add(outputStream)
        streamResult.systemId = suggestedFileName
        return streamResult
      }
    })

    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val size = schemaDocs.size
    val schemaSources: Array<Source?> = arrayOfNulls(size)
    for (i in 0 until size) {
      schemaSources[i] = StreamSource(
          ByteArrayInputStream(schemaDocs[i].toByteArray()))
    }
    return schemaFactory.newSchema(schemaSources)
  }
}
