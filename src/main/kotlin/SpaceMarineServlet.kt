import config.EntityManagerConfig
import model.*
import org.eclipse.jetty.http.HttpStatus
import service.DatabaseService
import xml.Marshallers
import xml.Unmarshallers
import java.io.StringReader
import java.lang.IndexOutOfBoundsException
import java.lang.Integer.parseInt
import java.lang.NumberFormatException
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarines", value = ["/marines"])
class SpaceMarineServlet : HttpServlet() {
  var dbService = DatabaseService
  private val marinesInit = listOf(getMarine("vasya"), getMarine("petya"))

  init {
    dbService.save(marinesInit)
  }

  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val idParameter = req.getParameter("id")
    if (idParameter == null) {
      val marines = dbService.get()
      Marshallers.MARINE_LIST.marshal(SpaceMarineList(marines), resp.writer)
    } else {
      try {
        val id = parseInt(idParameter)
        handleGetById(req, resp, id)
      } catch (ex: NumberFormatException) {
        resp.sendError(400, "Id should be an integer value")
      }

    }
  }

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doPost(req, resp)
  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    val marshalledMarineToSave = req.getParameter("marine")
    val marine: SpaceMarine =
        Unmarshallers.MARINE.unmarshal(StringReader(marshalledMarineToSave)) as SpaceMarine
    dbService.save(marine)
    resp.status = HttpStatus.CREATED_201
  }

  private fun handleGetById(req: HttpServletRequest, resp: HttpServletResponse, id: Int) {
    try {
      val marine = dbService.getById(id)
      Marshallers.MARINE.marshal(marine, resp.writer)
    } catch (ex: IndexOutOfBoundsException) {
      resp.sendError(404, "There is no spacemarine with such an id")
    }
  }
}

fun getMarine(name: String): SpaceMarine {
  val coordinates = Coordinates(1, 2f)
  val chapter = Chapter("string", "legion1", 1, "World1")
  return SpaceMarine(
      name,
      coordinates,
      LocalDateTime.now(),
      1,
      1,
      AstartesCategory.AGGRESSOR,
      MeleeWeapon.CHAIN_SWORD,
      chapter)
}
