package rest

import exceptions.RequestHandlingException
import model.PageableSpaceMarineList
import model.SpaceMarine
import model.SpaceMarineList
import org.eclipse.jetty.http.HttpStatus
import rest.dto.MarineCollectionRequestDto
import rest.dto.MarineRequestDto
import service.DatabaseService
import utils.*
import xml.Marshallers
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.min

class SpaceMarineCollectionController(request: HttpServletRequest, response: HttpServletResponse) :
  Controller(request, response) {
  val dbService = DatabaseService

  fun doGet() {
    doRequest<MarineCollectionRequestDto> {
      val sortSequence = it.sortSequence
      var marines = if (sortSequence != null) {
        getSortedMarines(sortSequence)
      } else {
        dbService.get()
      }

      it.filterList?.forEach { filterAndValue ->
        marines = marines.filterByValue(filterAndValue.first, filterAndValue.second)
      }

      if (it.pageableParameters != null) {
        processPageable(marines, it.pageableParameters)
      } else {
        sendMarines(marines)
      }
    }
  }

  fun doPost() {
    doRequest<MarineRequestDto> {
      dbService.saveMarine(it.spaceMarine)
      response.status = HttpStatus.CREATED_201
    }
  }

  private fun processPageable(marines: List<SpaceMarine>, pageableParameters: PageableParameters) {
    val firstIndex = pageableParameters.pageSize * (pageableParameters.pageIndex - 1)
    val secondIndex =
        min(pageableParameters.pageSize * (pageableParameters.pageIndex), marines.size)
    val slicedMarines = marines.slice(firstIndex until secondIndex)

    if (slicedMarines.isEmpty()) {
      sendMarines(slicedMarines)
      return;
    }

    val pageableSpaceMarineList =
        PageableSpaceMarineList(
            slicedMarines,
            pageableParameters.pageSize,
            pageableParameters.pageIndex)


    Marshallers.PAGEABLE_SPACE_MARINE_LIST.marshal(pageableSpaceMarineList, response.writer)

  }

  private fun getSortedMarines(sortSequence: String): List<SpaceMarine> {
    val sortingFields = sortSequence.split(",")
    try {
      require(sortingFields.all { field -> field.isEnglishAlphabet() })
    } catch (ex: IllegalArgumentException) {
      throw RequestHandlingException("sort sequence is not valid")
    }
    return dbService.get(sortingFields)
  }

  private fun sendMarines(marines: List<SpaceMarine>) {
    response.status = HttpStatus.OK_200
    Marshallers.MARINE_LIST.marshal(SpaceMarineList(marines), response.writer)
  }

  //  override fun <T : RequestDto> validate(@Valid requestDto: T) {
  //    if (requestDto is MarineCollectionRequestDto) {
  //      validateMarineCollectionRequestDto(requestDto as MarineCollectionRequestDto)
  //    } else {
  //      super.validate(requestDto)
  //    }
  //  }
}