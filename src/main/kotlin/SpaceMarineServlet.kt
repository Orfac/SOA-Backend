import exceptions.RequestHandlingException
import model.SpaceMarine
import service.DatabaseService
import utils.getId
import xml.Marshallers
import xml.Unmarshallers
import java.lang.IndexOutOfBoundsException
import javax.servlet.ServletRequest
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SpaceMarine", value = ["/marines/*"])
class SpaceMarineServlet : HttpServlet() {
  var dbService = DatabaseService
  override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
      try {
        val marine = dbService.getById(id)
        Marshallers.MARINE.marshal(marine, resp.writer)
      } catch (ex: IndexOutOfBoundsException) {
        resp.sendError(404, "There is no spacemarine with such an id")
      }
    } catch (ex : RequestHandlingException){
      resp.sendError(400, ex.message)
    }

  }

  override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
        dbService.deleteById(id)
    } catch (ex : RequestHandlingException){
      resp.sendError(400, ex.message)
    }

  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
      val spaceMarine = Unmarshallers.MARINE.unmarshal(req.reader) as SpaceMarine
      dbService.updateById(id,spaceMarine)
    } catch (ex : RequestHandlingException){
      resp.sendError(400, ex.message)
    }
  }

  override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
      dbService.deleteById(id)
    } catch (ex : RequestHandlingException){
      resp.sendError(400, ex.message)
    }
  }


}


