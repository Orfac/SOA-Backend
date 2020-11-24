import config.Utils
import exceptions.RequestHandlingException
import model.*
import service.DatabaseService
import utils.*
import xml.Marshallers
import xml.Unmarshallers
import java.lang.Exception
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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
    try {
      dbService.save(marinesInit)
    } catch (ex: Exception) {
      val i = 1
    }
  }

  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    var marines: List<SpaceMarine>
    marines = if (req.isSorting()) {
      val sortingFields = req.getParameter("sortBy").split(",")
      require(sortingFields.all { it.isEnglishAlphabet() })
      dbService.get(sortingFields)
    } else {
      dbService.get()
    }

    marines = filterByRequest(marines, req)

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

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val spaceMarine = Unmarshallers.MARINE.unmarshal(req.reader) as SpaceMarine
      val constraints = Utils.validator.validate(spaceMarine)
      if (constraints.isEmpty()) {
        dbService.save(spaceMarine)
      } else {
        resp.sendError(400, constraints.joinToString())
      }


    } catch (ex: RequestHandlingException) {
      resp.sendError(400, ex.message)
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
