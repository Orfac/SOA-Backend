package rest

import config.Utils
import exceptions.NotFoundException
import exceptions.RequestHandlingException
import org.eclipse.jetty.http.HttpStatus
import org.eclipse.jetty.http.HttpStatus.*
import rest.dto.*
import utils.*
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.Validator
import javax.xml.bind.JAXBException
import javax.xml.bind.UnmarshalException

open class Controller(val request: HttpServletRequest, val response: HttpServletResponse) {
  val validator: Validator = Utils.validator

  inline fun <reified T : RequestDto> doRequest(processFunction: (T) -> Unit) {
    try {
      val requestDto: T = parse<T>(request) as T
      validate(requestDto)
      processFunction(requestDto)
    } catch (ex: RequestHandlingException) {
      response.status = BAD_REQUEST_400
      response.writer.write(ex.message ?: "Bad request parameters")
    } catch (ex: IllegalArgumentException) {
      response.status = BAD_REQUEST_400
      response.writer.write(ex.message ?: "Bad request parameters")
    } catch (ex: JAXBException) {
      response.status = UNPROCESSABLE_ENTITY_422
      if (ex is UnmarshalException) {
        response.writer.write(ex.linkedException.localizedMessage)
      } else {
        response.writer.write(ex.message ?: "Xml Processing failed")
      }
    } catch (ex: NotFoundException) {
      response.status = NOT_FOUND_404
      response.writer.write(ex.message ?: "Item not found")
    }
  }

  inline fun <reified T : RequestDto> parse(request: HttpServletRequest): RequestDto {
    return when (T::class) {
      IdRequestDto::class -> parseId(request)
      MarineWithIdRequestDto::class -> parseIdWithMarine(request)
      NoParametersRequestDto::class -> NoParametersRequestDto()
      MarineRequestDto::class -> parseMarine(request)
      MarineCollectionRequestDto::class -> parseMarineCollectionDto(request)
      CategoryRequestDto::class -> parseCategory(request)
      HealthRequestDto::class -> parseHealth(request)
      else -> throw RequestHandlingException("Cannot identify request dto")
    }
  }

  open fun <T : RequestDto> validate(@Valid requestDto: T) {
    val constraintViolations = validator.validate(requestDto)
    if (constraintViolations.isNotEmpty()) {
      throw RequestHandlingException(constraintViolations.map { it.message }.joinToString(";"))
    }
  }
}