package rest

import config.Utils
import exceptions.NotFoundException
import exceptions.RequestHandlingException
import org.eclipse.jetty.http.HttpStatus
import org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400
import org.eclipse.jetty.http.HttpStatus.NOT_FOUND_404
import rest.dto.*
import utils.parseId
import utils.parseIdWithMarine
import utils.parseMarine
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.Validator
import javax.xml.bind.JAXBException

open class Controller(val request: HttpServletRequest, val response: HttpServletResponse) {
  val validator: Validator = Utils.validator

  inline fun <reified T : RequestDto> doRequest(processFunction: (T) -> Unit) {
    try {
      val requestDto: T = parse<T>(request) as T
      validate(requestDto)
      processFunction(requestDto)
    } catch (ex: RequestHandlingException) {
      response.sendError(BAD_REQUEST_400, ex.message)
    } catch (ex: JAXBException) {
      response.sendError(HttpStatus.UNPROCESSABLE_ENTITY_422, ex.message)
    } catch (ex: NotFoundException) {
      response.sendError(NOT_FOUND_404, ex.message)
    }
  }

  inline fun <reified T : RequestDto> parse(request: HttpServletRequest): RequestDto {
    return when (T::class) {
      IdRequestDto::class -> parseId(request)
      MarineWithIdRequestDto::class -> parseIdWithMarine(request)
      NoParametersRequestDto::class -> NoParametersRequestDto()
      MarineRequestDto::class -> parseMarine(request)
      else -> throw RequestHandlingException("Cannot identify request dto")
    }
  }

  fun validate(@Valid requestDto: RequestDto) {
    val constraintViolations = validator.validate(requestDto)
    if (constraintViolations.isNotEmpty()) {
      throw RequestHandlingException(constraintViolations.joinToString())
    }
  }
}