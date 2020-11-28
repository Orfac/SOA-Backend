import model.SpaceMarineList
import org.eclipse.jetty.http.HttpStatus
import rest.Controller
import rest.dto.CategoryRequestDto
import rest.dto.HealthRequestDto
import service.DatabaseService
import xml.Marshallers
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarinesHealthComparator", value = ["/compare/*"])
class HealthComparatorSerlvet : HttpServlet() {
  val dbService = DatabaseService
  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = Controller(req, resp)
    controller.doRequest<HealthRequestDto> {
      val marines = dbService.get()
          .filter { spaceMarine -> spaceMarine.health != null && spaceMarine.health > it.health }
      resp.status = HttpStatus.OK_200
      Marshallers.MARINE_LIST.marshal(SpaceMarineList(marines), resp.writer)
    }
  }
}