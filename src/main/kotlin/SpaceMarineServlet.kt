import config.EntityManagerConfig
import model.*
import service.DatabaseService
import xml.Marshallers
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
  var entityManager : EntityManager = EntityManagerConfig.getEntityManager()
  val marines = listOf(getMarine("vasya"), getMarine("petya"))

  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
//    val connection = DatabaseService().getConnection()
    entityManager.persist(marines[0])
    val idParameter = req.getParameter("id")
    if (idParameter == null) {
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

  private fun handleGetById(req: HttpServletRequest, resp: HttpServletResponse, id: Int) {
    try {
      Marshallers.MARINE.marshal(marines[id], resp.writer)
    } catch (ex: IndexOutOfBoundsException) {
      resp.sendError(404, "There is no spacemarine with such an id")
    }
  }
}

fun getMarine(name: String): SpaceMarine {
  val coordinates = Coordinates(1, 2f)
  val chapter = Chapter("string", "legion1", 1, "World1")
  return SpaceMarine(
      1,
      name,
      coordinates,
      LocalDateTime.now(),
      1,
      1,
      AstartesCategory.AGGRESSOR,
      MeleeWeapon.CHAIN_SWORD,
      chapter)
}
