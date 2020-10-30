package xml

import model.Chapter
import model.SpaceMarine
import model.SpaceMarineList
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller

object Marshallers {
  val CHAPTER = initChapterMarshaller()
  val MARINE = initSpaceMarineMarshaller()
  val MARINE_LIST = initSpaceMarineListMarshaller()

  private fun initSpaceMarineListMarshaller(): Marshaller {
    return initMarshaller(SpaceMarineList::class.java)
  }

  private fun initChapterMarshaller() : Marshaller{
    return initMarshaller(Chapter::class.java)
  }
  private fun initSpaceMarineMarshaller() : Marshaller{
    return initMarshaller(SpaceMarine::class.java)
  }

  private fun initMarshaller(entityClass: Class<*>) : Marshaller{
    val context = JAXBContext.newInstance(entityClass)
    val marshaller = context.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    return marshaller
  }
}
object Unmarshallers{
  val CHAPTER = initChapterUnmarshaller()
  val MARINE = initMarineUnmarshaller()
  val MARINE_LIST = initSpaceMarineListUnmarshaller()

  private fun initSpaceMarineListUnmarshaller(): Unmarshaller {
      return initUnmarshaller(SpaceMarineList::class.java)
  }

  private fun initMarineUnmarshaller(): Unmarshaller {
    return initUnmarshaller(SpaceMarine::class.java)
  }

  private fun initChapterUnmarshaller(): Unmarshaller {
    return initUnmarshaller(Chapter::class.java)
  }
  private fun initUnmarshaller(entityClass: Class<*>): Unmarshaller{
    val context = JAXBContext.newInstance(entityClass)
    return context.createUnmarshaller()
  }
}
