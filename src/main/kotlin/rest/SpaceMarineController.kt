package rest

import exceptions.NotFoundException
import org.eclipse.jetty.http.HttpStatus
import rest.dto.IdRequestDto
import rest.dto.MarineWithIdRequestDto
import rest.dto.NoParametersRequestDto
import service.DatabaseService
import xml.Marshallers
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SpaceMarineController(request: HttpServletRequest, response: HttpServletResponse) :
  Controller(request, response) {

  private val databaseService = DatabaseService

  fun doPut() {
    doRequest<MarineWithIdRequestDto> {
      databaseService.updateById(it.id, it.spaceMarine)
      response.status = HttpStatus.NO_CONTENT_204
    }
  }

  fun doDelete() {
    doRequest<IdRequestDto> {
      databaseService.deleteById(it.id)
      response.status = HttpStatus.NO_CONTENT_204
    }
  }

  fun doGet() {
    doRequest<IdRequestDto> {
      val marine = databaseService.getById(it.id)
      response.status = HttpStatus.OK_200
      Marshallers.MARINE.marshal(marine, response.writer)
    }
  }
}