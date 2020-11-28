package rest.dto

import model.AstartesCategory
import javax.validation.constraints.Min

data class CategoryRequestDto(
  val category: AstartesCategory
) : RequestDto