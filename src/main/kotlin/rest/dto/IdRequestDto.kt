package rest.dto

import javax.validation.constraints.Min

data class IdRequestDto(
  @get:Min(1, message = "id should be greater than 0 ") val id: Long
) : RequestDto