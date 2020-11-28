package rest.dto

import utils.PageableParameters
import javax.validation.Valid

open class MarineCollectionRequestDto(
  @get:Valid val pageableParameters: PageableParameters?,
  val filterList: List<Pair<String, String>>?,
  val sortSequence: String?
) : RequestDto