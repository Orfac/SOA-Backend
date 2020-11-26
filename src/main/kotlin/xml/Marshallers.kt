package xml

import model.Chapter
import model.PageableSpaceMarineList
import model.SpaceMarine
import model.SpaceMarineList
import xml.dto.XmlSpaceMarine
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import java.io.ByteArrayInputStream

import javax.xml.transform.stream.StreamSource

import javax.xml.validation.SchemaFactory

import javax.xml.transform.stream.StreamResult

import java.io.ByteArrayOutputStream

import java.io.IOException

import javax.xml.bind.SchemaOutputResolver

import java.util.ArrayList
import javax.xml.XMLConstants
import javax.xml.transform.Source
import javax.xml.validation.Schema

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
    return initMarshaller(XmlSpaceMarine::class.java)
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
    return initUnmarshaller(XmlSpaceMarine::class.java)
  }

  private fun initUnmarshaller(entityClass: Class<*>): Unmarshaller {
    val context = JAXBContext.newInstance(entityClass)
    val schemaDocs: MutableList<ByteArrayOutputStream> = ArrayList()
    context.generateSchema(object : SchemaOutputResolver() {
      @Throws(IOException::class)
      override fun createOutput(
        namespaceUri: String,
        suggestedFileName: String
      ): javax.xml.transform.Result {
        val baos = ByteArrayOutputStream()
        val sr = StreamResult(baos)
        schemaDocs.add(baos)
        sr.systemId = suggestedFileName
        return sr
      }
    })
    val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val size = schemaDocs.size
    val schemaSources: Array<Source?> = arrayOfNulls(size)
    for (i in 0 until size) {
      schemaSources[i] = StreamSource(
          ByteArrayInputStream(schemaDocs[i].toByteArray()))
    }
    val schema = sf.newSchema(schemaSources)

    return context.createUnmarshaller().also { it.schema = schema }
  }
}
