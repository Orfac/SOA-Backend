import config.Utils
import exceptions.RequestHandlingException
import model.SpaceMarine
import service.DatabaseService
import utils.getId
import xml.Marshallers
import xml.Unmarshallers
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

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
    } catch (ex: RequestHandlingException) {
      resp.sendError(400, ex.message)
    }

  }

  override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
      dbService.deleteById(id)
    } catch (ex: Exception) {
      resp.sendError(400, ex.message)
    }

  }

  override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    try {
      val id = req.getId()
      val spaceMarine = Unmarshallers.MARINE.unmarshal(req.reader) as SpaceMarine
      val constraints = Utils.validator.validate(spaceMarine)
      if (constraints.isEmpty()){
        dbService.updateById(id, spaceMarine)
      } else {
        resp.sendError(400, constraints.joinToString())
      }
    } catch (ex: Exception) {
      resp.sendError(400, ex.message)
    }
  }




}


