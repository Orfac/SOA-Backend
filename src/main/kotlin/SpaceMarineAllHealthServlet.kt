import org.eclipse.jetty.http.HttpStatus
import rest.Controller
import rest.dto.NoParametersRequestDto
import service.DatabaseService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarinesHealth", value = ["/health"])
class SpaceMarineAllHealthServlet : HttpServlet() {
  val dbService = DatabaseService
  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = Controller(req,resp)
    controller.doRequest<NoParametersRequestDto> {
      var summ : Long = 0
      for (marine in dbService.get()){
        if (marine.health != null){
          summ += marine.health
        }
      }

      resp.status = HttpStatus.OK_200
      resp.writer.write(summ.toString())
    }
  }
}