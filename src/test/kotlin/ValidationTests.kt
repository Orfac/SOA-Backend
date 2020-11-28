import config.Utils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rest.dto.IdRequestDto
import rest.dto.RequestDto
import javax.validation.ConstraintViolation
import javax.validation.Valid
import javax.validation.Validator

class ValidationTests {

  lateinit var validator: Validator

  @BeforeEach
  fun init() {
    validator = Utils.validator
  }

  @Test
  fun `id request dto with nonvalid id is non-valid`() {
    val idRequestDto = IdRequestDto(0) as RequestDto
    val violations = validator.validate(idRequestDto)
    assert(violations.isNotEmpty())
  }
}