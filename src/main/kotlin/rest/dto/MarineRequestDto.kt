package rest.dto

import model.SpaceMarine
import javax.validation.Valid
import javax.validation.constraints.Min

data class MarineRequestDto(
  @get:Valid val spaceMarine: SpaceMarine
) : RequestDto