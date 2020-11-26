package config

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

object Utils {
  const val SpaceMarineTableName = "SpaceMarine"
  val PossibleValues = listOf(
      "name", "id", "coordinates_x", "coordinates_y", "creation_date", "health",
      "heart_count", "category", "melee_weapon", "chapter_name", "chapter_parent_legion",
      "chapter_marines_count", "chapter_world"
  )
  val validator = initValidator()

  private fun initValidator(): Validator {
    val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    return factory.validator
  }
}
