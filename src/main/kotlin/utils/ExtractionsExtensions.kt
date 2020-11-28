package utils

import config.Utils
import exceptions.RequestHandlingException
import model.AstartesCategory
import model.SpaceMarine
import rest.dto.*
import xml.Unmarshallers
import java.io.Reader
import java.lang.NumberFormatException
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.Min

data class PageableParameters(
  @get:Min(1, message = "Page size starts from 1") var pageSize: Int,
  @get:Min(1, message = "Page index starts from 1") var pageIndex: Int
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

fun parseMarineCollectionDto(request: HttpServletRequest): MarineCollectionRequestDto {
  request.checkIfCollectionRequest()

  val filterParams = mutableListOf<Pair<String, String>>()
  request.parameterMap.forEach {
    if (it.key in Utils.PossibleValues) {
      filterParams.add(Pair(it.key, it.value[0]))
    }
  }

  val isSorting = request.isSorting()
  val sortBy: String? = if (isSorting) request.getParameter("sortBy") else null

  val isPageable = request.isPageable()
  val pageableParameters: PageableParameters? =
      if (isPageable) request.getPageableParams() else null


  return MarineCollectionRequestDto(pageableParameters, filterParams, sortBy)

}

fun parseCategory(request: HttpServletRequest): CategoryRequestDto {
  if (request.pathInfo == null) throw RequestHandlingException("Wrong url format")
  val parts = request.pathInfo.split("/")
  if (parts.size != 2 || parts[0] != "") throw RequestHandlingException("Wrong url format")
  return CategoryRequestDto(AstartesCategory.valueOf(parts[1]))
}

fun parseHealth(request: HttpServletRequest): HealthRequestDto {
  if (request.pathInfo == null) throw RequestHandlingException("Wrong url format")
  val parts = request.pathInfo.split("/")
  if (parts.size != 2 || parts[0] != "") throw RequestHandlingException("Wrong url format")
  return try {
    HealthRequestDto(parts[1].toLong())
  } catch (ex: NumberFormatException) {
    throw RequestHandlingException("Health must be integer value")
  }
}
private fun HttpServletRequest.checkIfCollectionRequest() {
  val parameterNames = this.parameterNames
  if (!parameterNames.toList().all {
        it in Utils.PossibleValues || it == "pageSize" || it == "pageNumber" || it == "sortBy"
      }) {
    throw RequestHandlingException("Extra parameters except pagination, sorting and filtering are added")
  }
}
