import config.Utils
import exceptions.RequestHandlingException
import model.*
import rest.SpaceMarineCollectionController
import service.DatabaseService
import utils.*
import xml.Marshallers
import xml.Unmarshallers
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.bind.JAXBException
import kotlin.math.min

@WebServlet(name = "SpaceMarines", value = ["/marines"])
class SpaceMarineCollectionServlet : HttpServlet() {
  var dbService = DatabaseService

  private val marinesInit =
      listOf(
          getMarine("vasya"), getMarine("petya"),
          getMarine("Vova"), getMarine("Immanuil1"),
          getMarine("vova0"), getMarine("Immanuil2"),
          getMarine("vova1"), getMarine("Immanuil Velikiy"),
          getMarine("vova2123"), getMarine("Privet kto"),
          getMarine("vova3123"), getMarine("Ya petr")
      )

  init {
    dbService.save(marinesInit)

  }

  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = SpaceMarineCollectionController(req, resp)
    controller.doGet()
  }

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = SpaceMarineCollectionController(req, resp)
    controller.doPost()
  }


}

fun getMarine(name: String): SpaceMarine {
  val coordinates = Coordinates(1, 2f)
  val chapter = Chapter("Thousand_of_sons", "legion1", 1000, "Prospero")
  return SpaceMarine(
      name,
      coordinates,
      1,
      1,
      AstartesCategory.AGGRESSOR,
      MeleeWeapon.CHAIN_SWORD,
      chapter)
}
