import config.EntityManagerConfig
import exceptions.RequestHandlingException
import model.*
import org.eclipse.jetty.http.HttpStatus
import service.DatabaseService
import utils.PageableParameters
import utils.getPageableParams
import utils.isPageable
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
import javax.validation.ValidatorFactory
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
    val marines = dbService.get()
    if (req.isPageable()) {
      try {
        val pageableParameters = req.getPageableParams()
        handlePageable(marines, pageableParameters, resp)
      } catch (ex: RequestHandlingException) {
        resp.sendError(400, ex.message)
      }

    } else {
      Marshallers.MARINE_LIST.marshal(SpaceMarineList(marines), resp.writer)
    }


  }

  private fun handlePageable(
    marines: List<SpaceMarine>,
    params: PageableParameters,
    resp: HttpServletResponse
  ) {

    val firstIndex = params.pageSize * (params.pageIndex - 1)
    val secondIndex = min(params.pageSize * (params.pageIndex), marines.size)
    val pageableSpaceMarineList =
        PageableSpaceMarineList(
            marines.slice(firstIndex until secondIndex),
            params.pageSize,
            params.pageIndex)
    if (pageableSpaceMarineList.marines.size == 0) {
      resp.sendError(404)
    } else {
      Marshallers.PAGEABLE_SPACE_MARINE_LIST.marshal(pageableSpaceMarineList, resp.writer)
    }
  }

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doPost(req, resp)
  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    val marshalledMarineToSave = req.parameterMap["marine"]?.first()
    val marine: SpaceMarine =
        Unmarshallers.MARINE.unmarshal(StringReader(marshalledMarineToSave)) as SpaceMarine
    dbService.save(marine)
    resp.status = HttpStatus.CREATED_201
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
