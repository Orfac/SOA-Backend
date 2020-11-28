package rest.dto

import model.SpaceMarine
import javax.validation.Valid
import javax.validation.constraints.Min

data class MarineWithIdRequestDto(
  @get:Valid val spaceMarine: SpaceMarine,
  @get:Valid @Min(1, message = "id should be greater than 0 ") val id: Long
) : RequestDto