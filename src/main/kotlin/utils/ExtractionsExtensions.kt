package utils

import exceptions.RequestHandlingException
import model.SpaceMarine
import rest.dto.IdRequestDto
import rest.dto.MarineRequestDto
import rest.dto.MarineWithIdRequestDto
import xml.Unmarshallers
import java.io.Reader
import java.lang.NumberFormatException
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min

data class PageableParameters(
  @Min(1, message = "Page size starts from 1") val pageSize: Int,
  @Min(1, message = "Page index starts from 1") val pageIndex: Int
)

fun HttpServletRequest.getPageableParams(): PageableParameters {
  val pageSizeString = this.getParameter("pageSize")
  val pageNumberString = this.getParameter("pageNumber")
  var pageNumber = 1
  var pageSize = 1
  try {
    pageNumber = pageNumberString.toInt()
    pageSize = pageSizeString.toInt()
  } catch (ex: NumberFormatException) {
    throw RequestHandlingException("Page number and page size should be integer values")
  }
  return PageableParameters(pageSize = pageSize, pageIndex = pageNumber)
}

fun HttpServletRequest.isPageable(): Boolean {
  val pageSizeString = this.getParameter("pageSize")
  val pageNumberString = this.getParameter("pageNumber")
  return pageNumberString != null && pageSizeString != null
}

fun HttpServletRequest.isSorting(): Boolean {
  val sortingString = this.getParameter("sortBy")
  return sortingString != null
}

fun String.isEnglishAlphabet(): Boolean {
  return this.toCharArray().all { it1 ->
    it1 in 'a'..'z' || it1 in 'A'..'Z'
  }
}

fun parseMarine(request: HttpServletRequest): MarineRequestDto {
  val spaceMarine = Unmarshallers.XML_MARINE.unmarshal(request.reader) as SpaceMarine
  return MarineRequestDto(spaceMarine)
}

fun parseId(request: HttpServletRequest): IdRequestDto {
  val parts = request.pathInfo.split("/")
  if (parts.size != 2 || parts[0] != "") throw RequestHandlingException("Wrong url format")
  return try {
    IdRequestDto(parts[1].toLong())
  } catch (ex: NumberFormatException) {
    throw RequestHandlingException("Id must be integer value")
  }
}

fun parseIdWithMarine(request: HttpServletRequest): MarineWithIdRequestDto {
  val idRequestDto = parseId(request)
  val spaceMarineDto = parseMarine(request)
  return MarineWithIdRequestDto(spaceMarineDto.spaceMarine, idRequestDto.id)
}

