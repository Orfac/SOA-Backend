import org.eclipse.jetty.http.HttpStatus
import rest.Controller
import rest.dto.CategoryRequestDto
import service.DatabaseService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarinesDelete", value = ["/random/*"])
class DeleteRandomServlet : HttpServlet(){
  private val dbService = DatabaseService
  override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = Controller(req,resp)
    controller.doRequest<CategoryRequestDto> {
      dbService.deleteRandomByCategory(it.category)
      resp.status = HttpStatus.NO_CONTENT_204
    }
  }
}