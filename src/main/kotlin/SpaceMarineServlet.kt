import rest.SpaceMarineController
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarine", value = ["/marines/*"])
class SpaceMarineServlet : HttpServlet() {
  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = SpaceMarineController(req, resp)
    controller.doGet()
  }

  override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = SpaceMarineController(req, resp)
    controller.doDelete()
  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    val controller = SpaceMarineController(req, resp)
    controller.doPut()
  }
}


