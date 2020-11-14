package utils

import config.Utils
import model.SpaceMarine
import xml.LocalDateTimeAdapter
import javax.servlet.http.HttpServletRequest

fun filterByRequest(
  enteredMarines: List<SpaceMarine>,
  request: HttpServletRequest
): List<SpaceMarine> {
  var marines = enteredMarines
  Utils.PossibleValues.forEach { valueToFilter ->
    if (valueToFilter in request.parameterNames.toList()) {
      marines = marines.filterByValue(valueToFilter, request.getParameter(valueToFilter))
    }
  }
  return marines
}

private fun List<SpaceMarine>.filterByValue(
  filterName: String,
  parameter: String
): List<SpaceMarine> {
  return this.filter {
    when (filterName) {
      "name" -> {
        it.name == parameter
      }
      "id" -> {
        it.id == parameter.toLong()
      }
      "coordinates_x" -> {
        it.coordinates.x == parameter.toLong()
      }
      "coordinates_y" -> {
        it.coordinates.y == parameter.toFloat()
      }
      "creation_date" -> {
        val localDateTimeAdapter = LocalDateTimeAdapter()
        localDateTimeAdapter.marshal(it.creationDate) == parameter
      }
      "health" -> {
        it.health == parameter.toLong()
      }
      "heart_count" -> {
        it.heartCount == parameter.toInt()
      }
      "category" -> {
        it.category.name == parameter
      }
      "melee_weapon" -> {
        it.meleeWeapon.name == parameter
      }
      "chapter_name" -> {
        it.chapter.name == parameter
      }
      "chapter_parent_legion" -> {
        it.chapter.parentLegion == parameter
      }
      "chapter_marines_count" -> {
        it.chapter.marinesCount == parameter.toLong()
      }
      "chapter_world" -> {
        it.chapter.world == parameter
      }
      else -> {
        true
      }
    }
  }
}
